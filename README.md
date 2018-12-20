# MIPS-Assembler
## Overview
This is a minimal MIPS assembler written in Java that converts MIPS assembly code
into machine code following these three steps:
1. Convert MAL instructions (pseudo MIPS instructions such as `move`) into their
   appropriate TAL instructions (instructions corresponding to a single machine code instruction).
2. Convert labels from branches or jumps into addresses.
3. Translate instructions into their binary encodings.

## Supported Instructions
- addiu (whether it is MAL depends on imm)
- addu
- or
- beq
- bne
- slt
- lui
- j
- ori (whether it is MAL depends on imm)
- blt (always MAL)
- bge (always MAL)