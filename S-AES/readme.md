# SAES (Simplified Advanced Encryption Standard)

A Python implementation of the Simplified AES cipher for educational purposes.

## Overview

This implementation provides both single-block encryption and ECB (Electronic Codebook) mode for encrypting longer messages. SAES operates on 16-bit blocks using 16-bit keys.

## Features

- **Single Block Encryption**: Encrypt/decrypt 16-bit integers
- **ECB Mode**: Encrypt/decrypt strings or large integers by splitting into 16-bit blocks
- **Multiple Output Formats**: Returns encrypted data as bits, text, base64, and hex
- **Visual State Representation**: Pretty-printed 2x2 matrix state during encryption

## Usage

```python
from SAES import SAES

saes = SAES()

# Encrypt a 16-bit integer
bits = 0b1101001011010011
key = 0b1101001010011010
ciphertext = saes.encrypt(bits, key)
plaintext = saes.decrypt(ciphertext, key)

# Encrypt a string using ECB mode
text = "Hello World"
encrypted = saes.encrypt_ecb(text, key)
decrypted = saes.decrypt_ecb(encrypted['bits'], key)

print(f"Original: {text}")
print(f"Decrypted: {decrypted['decrypted_text']}")
```

## Key Components

- **S-Box Substitution**: 4x4 substitution table for nibble transformation
- **Shift Rows**: Simple row shifting operation
- **Mix Columns**: Galois Field multiplication with transformation matrix
- **Key Expansion**: Generates round keys from the master key
- **ECB Mode**: Splits input into 16-bit blocks for encryption

## Output Formats

The `encrypt_ecb` method returns a dictionary with:

- `bits`: List of encrypted 16-bit integers
- `text`: String representation
- `base64_text`: Base64 encoded output
- `hex_text`: Hexadecimal representation

## Requirements

- Python 3.6+
- No external dependencies (uses only built-in modules)

## Note

This is a simplified version of AES designed for educational purposes. It should not be used for actual cryptographic security.
