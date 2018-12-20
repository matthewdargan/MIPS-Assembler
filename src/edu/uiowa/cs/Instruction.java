package edu.uiowa.cs;

public class Instruction {
    public enum ID{
        //R-Type
        /*
        sll,
        srl,
        sra,
        sllv,
        srlv,
        srav,
        jr,
        jalr,
        mfhi,
        mthi,
        mflo,
        mtlo,
        mult,
        multu,
        div,
        divu,
        add,
        */
        addu,
        /*
        sub,
        subu,
        and,
        */
        or,
        /*
        xor,
        nor,
        */
        slt,
        /*
        sltu,
        */
        //J-Type
        j,
        /*
        jal,
        */
        //I-Type
        beq,
        bne,
        /*
        blez,
        bgtz,
        addi,
        */
        addiu,
        /*
        slti,
        sltiu,
        andi,
        */
        ori,
        /*
        xori,
        */
        lui,
        /*
        lb,
        lh,
        lw,
        lbu,
        lhu,
        sb,
        sh,
        sw,
        */

        //the following IDs are psuedo instructions
        blt,
        bge,
        /*
        bgt,
        ble,
        mov,
        li,
        la,
        */
    };

    public final ID instruction_id;   // id indicating the instruction
    public final int rd;            // register number destination
    public final int rs;            // register number source
    public final int rt;            // register number secondary source
    public final int immediate;     // immediate, may use up to 32 bits
    public final int jump_address;  // jump address  (not used, so it is always 0)
    public final int shift_amount;  // shift amount (not used, so it is always 0)
    public final String label;      // label for line
    public final String branch_label;  // label used by branch or jump instructions

    protected Instruction(){
        this(ID.addiu, 0, 0, 0, 0, 0, 0, "", "");
    }

    //All
    protected Instruction(ID instruction_id, int rd, int rs, int rt, int immediate, int jump_address, int shift_amount, String label, String branch_label) {
        this.instruction_id = instruction_id;
        this.rd = rd;
        this.rs = rs;
        this.rt = rt;
        this.immediate = immediate;
        this.jump_address = jump_address;
        this.shift_amount = shift_amount;
        this.label = label;
        this.branch_label = branch_label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instruction that = (Instruction) o;

        if (instruction_id != that.instruction_id) return false;
        if (rd != that.rd) return false;
        if (rs != that.rs) return false;
        if (rt != that.rt) return false;
        if (immediate != that.immediate) return false;
        if (jump_address != that.jump_address) return false;
        if (shift_amount != that.shift_amount) return false;
        if (label == null ? that.label != null : !label.equals(that.label)) return false;
        return (branch_label == null ? that.branch_label == null : branch_label.equals(that.branch_label));

    }

    @Override
    public int hashCode() {
        int result = instruction_id.hashCode();
        result = 31 * result + rd;
        result = 31 * result + rs;
        result = 31 * result + rt;
        result = 31 * result + immediate;
        result = 31 * result + jump_address;
        result = 31 * result + shift_amount;
        result = 31 * result + label.hashCode();
        result = 31 * result + branch_label.hashCode();
        return result;
    }

    public Instruction copy() {
        return new Instruction(instruction_id, rd, rs, rt, immediate, jump_address, shift_amount, label, branch_label);
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "instruction_id=" + instruction_id.toString() +
                ", rd=" + rd +
                ", rs=" + rs +
                ", rt=" + rt +
                ", immediate=" + immediate +
                ", jump_address=" + jump_address +
                ", shift_amount=" + shift_amount +
                ", label_id=" + label +
                ", branch_label=" + branch_label +
                '}';
    }
}