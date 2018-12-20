package edu.uiowa.cs;

import java.util.LinkedList;
import java.util.List;

public class Phase2 {

    /* Returns a list of copies of the Instructions with the
     * immediate field (i-type) or jump_address (j-type) of the instruction filled in
     * with the address calculated from the branch_label.
     *
     * The instruction should not be changed if it is not a branch or jump instruction.
     *
     * unresolved: input program, whose branch/jump instructions don't have resolved immediate/jump_address
     * first_pc: address where the first instruction of the program will eventually be placed in memory
     */
    public static List<List<Object>> mappings = new LinkedList<>();
    public static List<Instruction> addressCompleteTals = new LinkedList<>();

    public static List<Instruction> resolve_addresses(List<Instruction> unresolved, int first_pc) {
        int pc = first_pc;

        for (int i = 0; i < unresolved.size(); i++) {
            Instruction currentInstruction = unresolved.get(i);
            List<Object> mapping = new LinkedList<>();

            // if line has a label
            if (currentInstruction.label != "") {
                // save the address of line
                String label = currentInstruction.label;
                int label_pc = first_pc;
                mapping.add(label);
                mapping.add(label_pc);
            }

            first_pc += 4;

            // if instruction is a branch
            if (mapping.size() != 0) {
                mappings.add(mapping);
            }
        }

        for (int i = 0; i < unresolved.size(); i++) {
            Instruction currentInstruction = unresolved.get(i);

            if (currentInstruction.instruction_id == Instruction.ID.beq
                    || currentInstruction.instruction_id == Instruction.ID.blt
                    || currentInstruction.instruction_id == Instruction.ID.bge
                    || currentInstruction.instruction_id == Instruction.ID.bne) {
                for (int j = 0; j < mappings.size(); j++) {
                    // structure of elements in mappings: {String label, int label_pc}
                    List<Object> currentMapping = mappings.get(j);
                    int addr = (Integer) currentMapping.get(1);

                    if (currentMapping.get(0) == currentInstruction.branch_label) {
                        int immAddr = (addr - pc) / 4 - 1;
                        Instruction newInstruction = new Instruction(currentInstruction.instruction_id,
                                currentInstruction.rd,
                                currentInstruction.rs,
                                currentInstruction.rt,
                                immAddr,
                                currentInstruction.jump_address,
                                currentInstruction.shift_amount,
                                currentInstruction.label,
                                "");
                        currentInstruction = newInstruction;
                    }
                }
            }
            else if (currentInstruction.instruction_id == Instruction.ID.j) {
                int jump_address = (pc / 4) - 2;
                Instruction newInstruction = new Instruction(currentInstruction.instruction_id,
                        currentInstruction.rd,
                        currentInstruction.rs,
                        currentInstruction.rt,
                        currentInstruction.immediate,
                        jump_address,
                        currentInstruction.shift_amount,
                        currentInstruction.label,
                        "");
                currentInstruction = newInstruction;
            }

            pc += 4;
            addressCompleteTals.add(currentInstruction);
        }

        return addressCompleteTals;
    }
}
