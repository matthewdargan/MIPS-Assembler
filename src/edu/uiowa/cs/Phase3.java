package edu.uiowa.cs;

import java.util.LinkedList;
import java.util.List;

public class Phase3 {

    /* Translate each Instruction object into
     * a 32-bit number.
     *
     * tals: list of Instructions to translate
     *
     * returns a list of instructions in their 32-bit binary representation
     *
     */
    public static List<Integer> binaries = new LinkedList<>();

    private static int rType(Instruction i) {
        if (i.instruction_id == Instruction.ID.addu) {
            return (0b000000 << 26 | i.rs << 21 | i.rt << 16 | i.rd << 11 | i.shift_amount << 6 | 33);
        } else if (i.instruction_id == Instruction.ID.or) {
            return (0b000000 << 26 | i.rs << 21 | i.rt << 16 | i.rd << 11 | i.shift_amount << 6 | 37);
        } else if (i.instruction_id == Instruction.ID.slt) {
            return (0b000000 << 26 | i.rs << 21 | i.rt << 16 | i.rd << 11 | i.shift_amount << 6 | 42);
        } else {
            return -1;
        }
    }

    private static int iType(Instruction i) {
        int temp = 0x0000ffff & i.immediate;

        if (i.instruction_id == Instruction.ID.beq) {
            return (0b000100 << 26 | i.rs << 21 | i.rt << 16 | temp);
        } else if (i.instruction_id == Instruction.ID.bne) {
            return (0b000101 << 26 | i.rs << 21 | i.rt << 16 | temp);
        } else if (i.instruction_id == Instruction.ID.ori) {
            return (0b001101 << 26 | i.rs << 21 | i.rt << 16 | temp);
        } else if (i.instruction_id == Instruction.ID.lui) {
            return (0b001111 << 26 | i.rs << 21 | i.rt << 16 | temp);
        } else if (i.instruction_id == Instruction.ID.addiu) {
            return (0b001001 << 26 | i.rs << 21 | i.rt << 16 | temp);
        } else {
            return -1;
        }
    }

    private static int jType(Instruction i) {
        return (0b000010 << 26 | i.immediate);
    }

    public static List<Integer> translate_instructions(List<Instruction> tals) {
        // tals = Phase1.tals;
        // I-Type : addiu, beq, bne, lui, ori
        // I-Type : needs opcode, rs, rt, imm
        // R-Type : addu, or, alt
        // R-Type : needs opcode, rt, rs, rd, shamt, funct
        for (int i = 0; i < tals.size(); i++) {
            Instruction temp = tals.get(i);

            // R-Type
            if ((temp.instruction_id == Instruction.ID.addu) ||
                    (temp.instruction_id == Instruction.ID.or) ||
                    (temp.instruction_id == Instruction.ID.slt)) {
                binaries.add(rType(temp));
            }

            // I-Type
            else if ((temp.instruction_id == Instruction.ID.beq) ||
                    (temp.instruction_id == Instruction.ID.bne) ||
                    (temp.instruction_id == Instruction.ID.lui) ||
                    (temp.instruction_id == Instruction.ID.ori) ||
                    (temp.instruction_id == Instruction.ID.addiu)) {
                binaries.add(iType(temp));
            }

            // J-Type
            else {
                binaries.add(jType(temp));
            }
        }

        return binaries;
    }
}
