def shift(bits: list, shifts: int) -> list: # performs a circular left shift
  return bits[shifts:] + bits[:shifts]

def permutation(bits: list, pattern: list) -> list: # simple pattern-guided permutation
  result = []
  for num in pattern:
    result.append(bits[num - 1])
  return result

def xor(bits1: list, bits2: list) -> list: # bit-by-bit xor logic
  return [bit1 ^ bit2 for bit1, bit2 in zip(bits1, bits2)]

def sbox(bits: list, box) -> list: # sbox logic
  row = (bits[0] << 1) + bits[3] # calculate row index using the first and last bits
  col = (bits[1] << 1) + bits[2] # calculate column index using the middle two bits
  value = box[row][col] # lookup the value in the sbox
  return [int(b) for b in format(value, '02b')] # convert the value to a 2-bit binary list

def key_gen(key: list) -> tuple:
    P10 = [3, 5, 2, 7, 4, 10, 1, 9, 8, 6]
    P8 = [6, 3, 7, 4, 8, 5, 10, 9]

    # initial permutation
    key = permutation(key, P10)
    # creating left and right side
    left, right = key[:5], key[5:]
    # single circular left shift
    left, right = shift(left, 1), shift(right, 1)
    # first subkey K1
    k1 = permutation(left+right, P8)
    # double circular left shift
    left, right = shift(left, 2), shift(right, 2)
    # second subkey k2
    k2 = permutation(left+right, P8)

    return (k1, k2)

# F mapping function

# Substitution boxes
S0 = [[1, 0, 3, 2],
      [3, 2, 1, 0],
      [0, 2, 1, 3],
      [3, 1, 3, 2]]

S1 = [[0, 1, 2, 3],
      [2, 0, 1, 3],
      [3, 0, 1, 0],
      [2, 1, 0, 3]]

def F_map(key: list, bits: list) -> list:
    EXPAND = [4, 1, 2, 3, 2, 3, 4, 1]
    P4 = [2, 4, 3, 1] 

    # expanding the bits
    bits = permutation(bits, EXPAND)
    bits = xor(key, bits)

    # selecting and joining sbox bits
    sbox1 = sbox(bits[:4], S0)
    sbox2 = sbox(bits[4:], S1)

    # return the permutation of the sboxes
    bits = permutation(sbox1 + sbox2, P4)

    return bits

# Feistel encryption function

def Fk(bits: list, key: list) -> list:
    left, right = bits[:4], bits[4:]  # split the bits in half
    right_F = F_map(key, right)  # F map function
    bits = xor(left, right_F) + right

    return bits  # return the bits with the F map and Fk functions applied

def sdes(bits: list, key: list) -> str:
    IP = [2, 6, 3, 1, 4, 8, 5, 7] # initial permutation
    IP_1 = [4, 1, 3, 5, 7, 2, 8, 6] # inverse initial permutation

    # generating the subkeys
    k1, k2 = key_gen(key)
    # initial permutation
    bits = permutation(bits, IP)
    # first Feistel round
    bits = Fk(bits, k1)
    # Swap left and right halves
    bits = bits[4:] + bits[:4]
    # second Feistel round
    bits = Fk(bits, k2)
    # final permutation
    bits = permutation(bits, IP_1)

    # final result
    return bits

def decode_sdes(bits: list, key: list) -> str:
    IP = [2, 6, 3, 1, 4, 8, 5, 7] # initial permutation
    IP_1 = [4, 1, 3, 5, 7, 2, 8, 6] # inverse initial permutation
    
    k1, k2 = key_gen(key)

    # initial permutation
    bits = permutation(bits, IP)
    # feistel round with k2
    bits = Fk(bits, k2)
    # Swap left and right halves
    bits = bits[4:] + bits[:4]
    # feistel round with k1
    bits = Fk(bits, k1)
    # final permutation
    bits = permutation(bits, IP_1)

    # final result
    return bits

key = [1, 0, 1, 0, 0, 0, 0, 0, 1, 0] # 10 bits key
bits = [1, 1, 0, 1, 0, 1, 1, 1] # 8 bits 'plaintext' block
bits_message = [1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0] # 32 bits 'plaintext' message
vector = [0, 1, 0, 1, 0, 1, 0, 1] # 8 bits initialization vector

print(f"Chave\n{''.join(str(b) for b in key)}")
print("=="*20)
print(f"Mensagem pequena\n{''.join(str(b) for b in bits)}")
print("=="*20)

cipher = sdes(bits, key)
print(f"SDES com implementação básica\n{''.join(str(b) for b in cipher)}")
print("=="*20)

decipher = decode_sdes(cipher, key)
print(f"Decifrado\n{''.join(str(b) for b in decipher)}")
print("=="*20)

# divide the bits in blocks of eight bits
blocks = [bits_message[i:i+8] for i in range(0, len(bits_message), 8)]
cipher = []

# encrypt each block using the sdes function
for block in blocks:
    cipher.append(sdes(block, key))

# flatten the list of cipher blocks to display
flat = [bit for block in cipher for bit in block]
print(f"SDES com ECB\n{''.join(str(b) for b in flat)}")
print("=="*20)

# divide the bits in blocks of eight bits
blocks = [bits_message[i:i+8] for i in range(0, len(bits_message), 8)]
cipher = []

for i in range(len(blocks)):
    if i == 0:
        last = sdes(xor(blocks[i], vector), key)
    else:
        last = sdes(xor(blocks[i], last), key)
    cipher.append(last)

# flatten the list of cipher blocks to display
flat = [bit for block in cipher for bit in block]
print(f"SDES com CBC\n{''.join(str(b) for b in flat)}")
print("=="*20)
