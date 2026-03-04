# Relatório de Implementação do Algoritmo SDES

## 1. Introdução

A criptografia simétrica representa um dos pilares fundamentais da segurança da informação moderna, caracterizando-se pelo uso da mesma chave tanto para cifragem quanto para decifragem dos dados. Neste contexto, o algoritmo de criptografia Simplified Data Encryption Standard (SDES) surge como uma versão didática e simplificada do algoritmo DES (Data Encryption Standard), que foi amplamente utilizado nas décadas de 1970 a 1990 como padrão de criptografia.

O SDES foi desenvolvido com propósito educacional, visando facilitar o entendimento dos conceitos fundamentais da criptografia de bloco sem a complexidade completa do DES original. Sua implementação permite compreender os princípios de substituição, permutação, confusão e difusão presentes nos sistemas criptográficos modernos.

A relevância deste estudo está na possibilidade de compreender os fundamentos dos algoritmos de criptografia simétrica através de uma implementação simplificada, mas que contém os elementos essenciais para o entendimento dos mecanismos de proteção de dados. Além disso, a implementação do SDES permite explorar diferentes modos de operação, como ECB (Electronic Codebook) e CBC (Cipher Block Chaining), evidenciando suas diferenças em termos de segurança e aplicabilidade.

## 2. Fundamentação Teórica

### Princípios Básicos de Criptografia de Bloco

A criptografia de bloco opera transformando dados de entrada em blocos de tamanho fixo através de uma série de operações determinadas por uma chave secreta. O SDES, assim como o DES, é classificado como um cifrador de bloco, trabalhando com blocos de 8 bits (diferentemente do DES original que utiliza blocos de 64 bits).

Os princípios fundamentais que regem este tipo de criptografia são:

- **Confusão**: Visa tornar a relação entre a chave e o texto cifrado o mais complexa possível.
- **Difusão**: Busca fazer com que cada bit do texto cifrado dependa de vários bits do texto claro.
- **Substituição**: Troca de valores por outros através de tabelas predefinidas (S-boxes).
- **Permutação**: Reordenação dos bits seguindo um padrão específico (P-boxes).

### Diferença entre DES Completo e SDES

O SDES difere do DES completo principalmente nos seguintes aspectos:

| Característica | SDES | DES |
|----------------|------|-----|
| Tamanho do bloco | 8 bits | 64 bits |
| Tamanho da chave | 10 bits | 56 bits efetivos (64 bits com paridade) |
| Número de rodadas | 2 | 16 |
| Complexidade das S-boxes | 2 S-boxes de 2x4 | 8 S-boxes de 6x4 |
| Expansão | De 4 para 8 bits | De 32 para 48 bits |

Estas simplificações tornam o SDES didaticamente valioso, porém absolutamente inadequado para uso prático em segurança da informação, servindo apenas como ferramenta educacional.

### Componentes Principais

Os componentes principais do SDES são:

- **P-boxes (Caixas de Permutação)**: Realizam o rearranjo determinístico dos bits de entrada. No SDES, são utilizadas as permutações IP (Initial Permutation), IP⁻¹ (Inverse Initial Permutation), P10, P8 e P4.

- **S-boxes (Caixas de Substituição)**: Responsáveis pela operação não-linear do algoritmo, substituindo padrões de bits por outros valores predefinidos. O SDES utiliza duas S-boxes (S0 e S1), cada uma mapeando 4 bits de entrada para 2 bits de saída.

- **Função de Feistel**: Estrutura que divide o bloco em duas metades e aplica uma série de transformações em uma das metades usando uma função específica (função F), combinando o resultado com a outra metade através de operação XOR. Esta estrutura tem a vantagem de ser facilmente reversível para a operação de decifragem.

## 3. Visão Geral do Algoritmo

### Estrutura do SDES

O algoritmo SDES segue uma estrutura típica de cifrador de Feistel, com as seguintes etapas principais:

1. Geração de subchaves a partir da chave original
2. Permutação inicial (IP) do bloco de entrada
3. Aplicação da primeira rodada de Feistel com a subchave K1
4. Troca das metades esquerda e direita
5. Aplicação da segunda rodada de Feistel com a subchave K2
6. Permutação final inversa (IP⁻¹)

Esta estrutura forma um sistema balanceado de substituição e permutação que cumpre os princípios de confusão e difusão necessários para um algoritmo criptográfico.

### Geração de Subchaves

O processo de geração das subchaves K1 e K2 a partir da chave original de 10 bits segue os seguintes passos:

1. Aplicação da permutação P10 à chave original
2. Divisão em duas metades de 5 bits cada
3. Realização de um deslocamento circular à esquerda em cada metade
4. Aplicação da permutação P8 para obter K1 (8 bits)
5. Realização de dois deslocamentos circulares adicionais à esquerda em cada metade
6. Aplicação da permutação P8 para obter K2 (8 bits)

### Processo de Cifragem

O processo de cifragem do SDES para um bloco de 8 bits envolve:

1. Aplicação da permutação inicial IP
2. Divisão do bloco em duas partes de 4 bits (L0 e R0)
3. Aplicação da função F ao bloco R0 com a subchave K1
4. Combinação do resultado com L0 através de XOR para obter R1
5. O novo bloco será formado por R0 (que se torna L1) e R1
6. Aplicação da função F ao bloco R1 com a subchave K2
7. Combinação do resultado com L1 através de XOR para obter R2
8. Formação do bloco final [R2, L1]
9. Aplicação da permutação final inversa IP⁻¹

A função F, componente central das rodadas de Feistel, realiza:
1. Expansão do bloco de 4 bits para 8 bits
2. XOR com a subchave da rodada
3. Divisão em duas partes e aplicação das S-boxes
4. Permutação P4 no resultado

### Implementação de ECB e CBC

O algoritmo SDES, assim como outros cifradores de bloco, pode ser implementado em diferentes modos de operação:

- **Electronic Codebook (ECB)**: Cada bloco é cifrado independentemente. Este é o modo mais simples, onde blocos idênticos de texto claro produzirão blocos idênticos de texto cifrado.

- **Cipher Block Chaining (CBC)**: Antes da cifragem, cada bloco de texto claro é combinado através de XOR com o bloco de texto cifrado anterior. Para o primeiro bloco, utiliza-se um vetor de inicialização (IV).

O modo CBC proporciona maior segurança ao remover padrões de repetição, pois blocos idênticos de texto claro resultarão em blocos diferentes de texto cifrado, devido à dependência do bloco anterior.

## 4. Implementação

### Linguagem de Programação e Ambiente

A implementação do algoritmo SDES foi realizada em Python, utilizando o ambiente Jupyter Notebook que permite a execução interativa de código e a documentação clara das etapas do processo. Python foi escolhido pela sua simplicidade sintática e pela facilidade de manipulação de estruturas de dados necessárias para o algoritmo.

### Estruturas de Dados Escolhidas

As principais estruturas de dados utilizadas na implementação foram:

- **Listas de inteiros binários**: Representando diretamente os bits (0 e 1) dos blocos e chaves
- **Matrizes bidimensionais (listas de listas)**: Para implementação das S-boxes
- **Tuplas**: Para retorno de múltiplos valores, como no caso das subchaves

Esta escolha de representação permite uma implementação clara e didática, privilegiando a compreensão do algoritmo em detrimento da eficiência computacional.

### Implementação do Código

A implementação foi organizada em componentes modulares para facilitar a compreensão e manutenção:

#### Funções Auxiliares
Quatro funções fundamentais foram implementadas para serem reutilizadas ao longo do código:

```python
def shift(bits: list, shifts: int) -> list:  # performs a circular left shift
    return bits[shifts:] + bits[:shifts]

def permutation(bits: list, pattern: list) -> list:  # simple pattern-guided permutation
    result = []
    for num in pattern:
        result.append(bits[num - 1])
    return result

def xor(bits1: list, bits2: list) -> list:  # bit-by-bit xor logic
    return [bit1 ^ bit2 for bit1, bit2 in zip(bits1, bits2)]

def sbox(bits: list, box) -> list:  # sbox logic
    row = (bits[0] << 1) + bits[3]  # calculate row index using the first and last bits
    col = (bits[1] << 1) + bits[2]  # calculate column index using the middle two bits
    value = box[row][col]  # lookup the value in the sbox
    return [int(b) for b in format(value, '02b')]  # convert the value to a 2-bit binary list
```

#### Geração de Subchaves
A função de geração de subchaves implementa o algoritmo descrito na fundamentação teórica:

```python
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
```

#### Função F e Rodada de Feistel
A implementação da função F e da rodada de Feistel segue a estrutura teórica:

```python
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

def Fk(bits: list, key: list) -> list:
    left, right = bits[:4], bits[4:]  # split the bits in half
    right_F = F_map(key, right)  # F map function
    bits = xor(left, right_F) + right

    return bits  # return the bits with the F map and Fk functions applied
```

#### Implementação Principal do SDES
A função principal que coordena todo o processo de cifragem:

```python
def sdes(bits: list, key: list) -> str:
    IP = [2, 6, 3, 1, 4, 8, 5, 7]  # initial permutation
    IP_1 = [4, 1, 3, 5, 7, 2, 8, 6]  # inverse initial permutation

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
```

#### Implementações dos Modos de Operação

Para o modo ECB:
```python
# divide the bits in blocks of eight bits
blocks = [bits_message[i:i+8] for i in range(0, len(bits_message), 8)]
cipher = []

# encrypt each block using the sdes function
for block in blocks:
    cipher.append(sdes(block, key))

# flatten the list of cipher blocks to display
flat = [bit for block in cipher for bit in block]
```

Para o modo CBC:
```python
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
```

## 5. Resultados

### Análise dos Resultados

A implementação do algoritmo SDES foi testada com diferentes entradas, demonstrando o funcionamento correto do processo de cifragem. Os resultados obtidos mostram o comportamento esperado de um algoritmo de criptografia simétrica:

1. **Cifragem simples de um bloco**:
   - Texto claro: `11010111`
   - Chave: `1010000010`
   - Texto cifrado: `10101000`

2. **Cifragem no modo ECB** de uma mensagem de 32 bits:
   - Texto claro: `11010111011011001011101011110000`
   - Texto cifrado: `10101000000011010010111001101101`

3. **Cifragem no modo CBC** da mesma mensagem:
   - Texto claro: ` 11010111011011001011101011110000`
   - Vetor de inicialização: `01010101`
   - Texto cifrado: `00001011101010011001101101101010`

### Validação dos Resultados

Para validar os resultados, foram realizadas as seguintes verificações:

1. **Comparação com implementações de referência**: Os resultados da cifragem foram consistentes com implementações de referência do SDES disponíveis na literatura.

2. **Análise dos modos de operação**: A comparação entre os resultados do modo ECB e CBC demonstrou claramente a diferença entre os dois métodos, especialmente na propagação de alterações. No modo CBC, a alteração de um único bit no início da mensagem resulta em mudanças significativas em todos os blocos subsequentes, enquanto no ECB, apenas o bloco alterado é afetado.

3. **Propriedades esperadas da cifragem**: A análise dos resultados confirma que o algoritmo possui as propriedades esperadas de confusão e difusão, onde pequenas alterações na entrada resultam em alterações significativas na saída, e cada bit do texto cifrado depende de vários bits do texto claro e da chave.

Os resultados demonstram que a implementação do SDES, embora simplificada, cumpre seu papel didático de ilustrar os princípios fundamentais da criptografia simétrica de bloco.

## 6. Conclusão

### Pontos Fortes e Fracos do SDES

**Pontos Fortes**:
- Simplicidade didática que permite compreender claramente os princípios de criptografia de bloco
- Estrutura de Feistel que facilita a implementação da decifragem
- Incorpora os elementos essenciais (permutação, substituição, confusão e difusão) presentes em algoritmos mais complexos

**Pontos Fracos**:
- Tamanho extremamente reduzido da chave (10 bits), tornando-o vulnerável a ataques de força bruta
- Número limitado de rodadas (apenas 2), reduzindo a difusão dos bits
- S-boxes simplificadas, que não seguem critérios rigorosos de projeto como no DES original

### Vulnerabilidades Conhecidas

O SDES, por ser uma versão simplificada com fins didáticos, possui diversas vulnerabilidades:

1. **Vulnerabilidade a ataque de força bruta**: Com apenas 10 bits de chave, existem apenas 1024 (2¹⁰) chaves possíveis, que podem ser testadas exaustivamente em frações de segundo em computadores modernos.

2. **Criptoanálise diferencial**: A simplicidade das S-boxes e o número reduzido de rodadas tornam o SDES suscetível à criptoanálise diferencial, onde pares de textos claros com diferenças específicas podem revelar informações sobre a chave.

3. **Vulnerabilidades do modo ECB**: Como demonstrado na implementação, o modo ECB preserva padrões do texto original no texto cifrado, o que pode levar à revelação de informações sobre o conteúdo da mensagem.

### Síntese do Trabalho Realizado

Este trabalho apresentou uma implementação completa do algoritmo de criptografia SDES, abordando desde os fundamentos teóricos até a implementação prática em Python. Foram implementadas as funções essenciais do algoritmo (permutação, substituição, XOR) e dois modos de operação (ECB e CBC), demonstrando o comportamento do algoritmo em diferentes cenários.

A implementação manteve o foco na clareza e na didática, priorizando a compreensão dos conceitos fundamentais de criptografia simétrica de bloco em detrimento da eficiência computacional.

### Lições Aprendidas

O desenvolvimento deste trabalho proporcionou importantes lições sobre criptografia:

1. A compreensão da importância do equilíbrio entre substituição (não-linearidade) e permutação (difusão) nos algoritmos criptográficos.

2. A percepção prática de como diferentes modos de operação (ECB e CBC) afetam a segurança do sistema, especialmente na presença de padrões nos dados.

3. O entendimento da necessidade de múltiplas rodadas e chaves de tamanho adequado para garantir segurança contra ataques.

4. A importância do projeto cuidadoso das S-boxes para resistência à criptoanálise.

Esta implementação do SDES, embora limitada em termos de segurança prática, cumpre seu propósito educacional de ilustrar os conceitos fundamentais da criptografia simétrica, permitindo a compreensão dos mecanismos subjacentes a algoritmos mais complexos e seguros utilizados em aplicações reais.
