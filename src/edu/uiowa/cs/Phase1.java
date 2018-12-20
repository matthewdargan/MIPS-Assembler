package edu.uiowa.cs;

import java.util.LinkedList;
import java.util.List;

public class Phase1 {

    /* Translates the MAL instruction to 1-3 TAL instructions
     * and returns the TAL instructions in a list
     *
     * mals: input program as a list of Instruction objects
     *
     * returns a list of TAL instructions (should be same size or longer than input list)
     */
    public static List<Instruction> temp = new LinkedList<>();

    public static List<Instruction> mal_to_tal(List<Instruction> mals) {
        for (int i = 0; i < mals.size(); i++) {
            Instruction current = mals.get(i);
            int rs = current.rs;
            int rd = current.rd;
            int rt = current.rt;
            int imm = current.immediate;
            int jumpAddress = current.jump_address;
            int shiftAmount = current.shift_amount;
            String label = current.label;
            String branchLabel = current.branch_label;
            int upperImm = imm >>> 16;
            int lowerImm = imm & 0xFFFF;
            int t1 = 0;
            int t2 = 0;
            int t3 = 0;
            int at = 0;

            if ((current.instruction_id == Instruction.ID.addiu
                    || current.instruction_id == Instruction.ID.ori) && (imm > 65535)) {
                at = 1;

                temp.add(InstructionFactory.CreateLui(at, upperImm, label));
                temp.add(InstructionFactory.CreateOri(at, at, lowerImm));

                if (current.instruction_id == Instruction.ID.addiu) temp.add(InstructionFactory.CreateAddu(rt, rs, at));
                else temp.add(InstructionFactory.CreateOr(rt, rs, at));
            }

            else if (current.instruction_id == Instruction.ID.blt) {
                //t1 = rt, t2 = rs
                if (rt > rs) {
                    at = 1;
                }

                temp.add(InstructionFactory.CreateSlt(at, rt, rs));
                temp.add(InstructionFactory.CreateBne(at, 0, branchLabel));
            }

            else if (current.instruction_id == Instruction.ID.bge) {
                at = 1;

                temp.add(InstructionFactory.CreateSlt(at, rt, rs));
                temp.add(InstructionFactory.CreateBeq(at,0, branchLabel));
            }

            else temp.add(current);
        }

        return temp;
    }
}
