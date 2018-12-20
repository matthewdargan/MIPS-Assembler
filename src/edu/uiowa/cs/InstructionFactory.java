package edu.uiowa.cs;

public class InstructionFactory extends Instruction {

    // this should never be called because InstructionFactories are not created.
    private InstructionFactory(){
        super();
    }

    public static Instruction CreateCopy(Instruction instruction){
        return instruction.copy();
    }

    /**
    public static Instruction CreateSll(int rd, int rt, int shift_amount){
        return new Instruction(ID.sll, rd, 0, rt, shift_amount);
    }

    public static Instruction CreateSrl(int rd, int rt, int shift_amount){
        return new Instruction(ID.srl, rd, 0, rt, shift_amount);
    }

    public static Instruction CreateSra(int rd, int rt, int shift_amount){
        return new Instruction(ID.sra, rd, 0, rt, shift_amount);
    }

    public static Instruction CreateSllv(int rd, int rt, int shift_amount){
        return new Instruction(ID.sllv, rd, 0, rt, shift_amount);
    }

    public static Instruction CreateSrlv(int rd, int rt, int shift_amount){
        return new Instruction(ID.srlv, rd, 0, rt, shift_amount);
    }

    public static Instruction CreateSrav(int rd, int rt, int shift_amount){
        return new Instruction(ID.srav, rd, 0, rt, shift_amount);
    }

    public static Instruction CreateJr(int rs){
        return new Instruction(ID.jr, 0, rs, 0, 0);
    }

    public static Instruction CreateJalr(int rd, int rs){
        return new Instruction(ID.jalr, rd, rs, 0, 0);
    }

    public static Instruction CreateJalr(int rs){
        return new Instruction(ID.jalr, 31, rs, 0, 0);
    }

    public static Instruction CreateMfhi(int rd){
        return new Instruction(ID.mfhi, rd, 0, 0, 0);
    }

    public static Instruction CreateMthi(int rs){
        return new Instruction(ID.mthi, 0, rs, 0, 0);
    }

    public static Instruction CreateMflo(int rd){
        return new Instruction(ID.mflo, rd, 0, 0, 0);
    }

    public static Instruction CreateMtlo(int rs){
        return new Instruction(ID.mtlo, 0, rs, 0, 0);
    }

    public static Instruction CreateMult(int rs, int rt){
        return new Instruction(ID.mult, 0, rs, rt, 0);
    }

    public static Instruction CreateMultu(int rs, int rt){
        return new Instruction(ID.multu, 0, rs, rt, 0);
    }

    public static Instruction CreateDiv(int rs, int rt){
        return new Instruction(ID.div, 0, rs, rt, 0);
    }

    public static Instruction CreateDivu(int rs, int rt){
        return new Instruction(ID.divu, 0, rs, rt, 0);
    }

    public static Instruction CreateAdd(int rd, int rs, int rt){
        return new Instruction(ID.add, rd, rs, rt, 0);
    }
    */

    public static Instruction CreateAddu(int rd, int rs, int rt){
        return InstructionFactory.CreateAddu(rd, rs, rt, "");
    }

    /**
    public static Instruction CreateSub(int rd, int rs, int rt){
        return new Instruction(ID.sub, rd, rs, rt, 0);
    }

    public static Instruction CreateSubu(int rd, int rs, int rt){
        return new Instruction(ID.subu, rd, rs, rt, 0);
    }

    public static Instruction CreateAnd(int rd, int rs, int rt){
        return new Instruction(ID.and, rd, rs, rt, 0);
    }
    */

    public static Instruction CreateOr(int rd, int rs, int rt){
        return InstructionFactory.CreateOr(rd, rs, rt, "");
    }

    /**
    public static Instruction CreateXor(int rd, int rs, int rt){
        return new Instruction(ID.xor, rd, rs, rt, 0);
    }

    public static Instruction CreateNor(int rd, int rs, int rt){
        return new Instruction(ID.nor, rd, rs, rt, 0);
    }
    */

    public static Instruction CreateSlt(int rd, int rs, int rt){
        return InstructionFactory.CreateSlt(rd, rs, rt, "");
    }

    /**
    public static Instruction CreateSltu(int rd, int rs, int rt){
        return new Instruction(ID.sltu, rd, rs, rt, 0);
    }
     */

    public static Instruction CreateJ(int jump_address) { return InstructionFactory.CreateJ(jump_address, ""); }

    public static Instruction CreateJ(String branch_label) { return InstructionFactory.CreateJ(branch_label, ""); }

    /**
    public static Instruction CreateJal(int jump_address){
        return new Instruction(ID.jal, jump_address);
    }

    public static Instruction CreateJal(String branch_label){
        return new Instruction(ID.jal, branch_label);
    }
    */

    public static Instruction CreateBeq(int rs, int rt, int immediate){
        return InstructionFactory.CreateBeq(rs, rt, immediate, "");
    }

    public static Instruction CreateBeq(int rs, int rt, String branch_label){
        return InstructionFactory.CreateBeq(rs, rt, branch_label, "");
    }

    public static Instruction CreateBne(int rs, int rt, int immediate){
        return InstructionFactory.CreateBne(rs, rt, immediate, "");
    }

    public static Instruction CreateBne(int rs, int rt, String branch_label){
        return InstructionFactory.CreateBne(rs, rt, branch_label, "");
    }

    /**
    public static Instruction CreateBlez(int rs, int immediate){
        return new Instruction(ID.blez, rs, 0, immediate);
    }

    public static Instruction CreateBlez(int rs, String branch_label){
        return new Instruction(ID.blez, rs, 0, branch_label);
    }

    public static Instruction CreateBgtz(int rs, int immediate){
        return new Instruction(ID.bgtz, rs, 0, immediate);
    }

    public static Instruction CreateBgtz(int rs, String branch_label){
        return new Instruction(ID.bgtz, rs, 0, branch_label);
    }

    public static Instruction CreateAddi(int rt, int rs, int immediate){
        return new Instruction(ID.addi, rs, rt, immediate);
    }
    */

    public static Instruction CreateAddiu(int rt, int rs, int immediate){
        return InstructionFactory.CreateAddiu(rt, rs, immediate, "");
    }

    /**
    public static Instruction CreateSlti(int rt, int rs, int immediate){
        return new Instruction(ID.slti, rs, rt, immediate);
    }

    public static Instruction CreateSltiu(int rt, int rs, int immediate){
        return new Instruction(ID.sltiu, rs, rt, immediate);
    }

    public static Instruction CreateAndi(int rt, int rs, int immediate){
        return new Instruction(ID.andi, rs, rt, immediate);
    }
    */

    public static Instruction CreateOri(int rt, int rs, int immediate){
        return InstructionFactory.CreateOri(rt, rs, immediate, "");
    }

    /**
    public static Instruction CreateXori(int rt, int rs, int immediate){
        return new Instruction(ID.xori, rs, rt, immediate);
    }
    */

    public static Instruction CreateLui(int rt, int immediate){
        return InstructionFactory.CreateLui(rt, immediate, "");
    }

    /**
    public static Instruction CreateLb(int rs, int immediate){
        return new Instruction(ID.lb, rs, 0, immediate);
    }

    public static Instruction CreateLh(int rt, int rs, int immediate){
        return new Instruction(ID.lh, rs, rt, immediate);
    }

    public static Instruction CreateLw(int rt, int rs, int immediate){
        return new Instruction(ID.lw, rs, rt, immediate);
    }

    public static Instruction CreateLbu(int rt, int rs, int immediate){
        return new Instruction(ID.lbu, rs, rt, immediate);
    }

    public static Instruction CreateLhu(int rt, int rs, int immediate){
        return new Instruction(ID.lhu, rs, rt, immediate);
    }

    public static Instruction CreateSb(int rt, int rs, int immediate){
        return new Instruction(ID.sb, rs, rt, immediate);
    }

    public static Instruction CreateSh(int rt, int rs, int immediate){
        return new Instruction(ID.sh, rs, rt, immediate);
    }

    public static Instruction CreateSw(int rt, int rs, int immediate){
        return new Instruction(ID.sw, rs, rt, immediate);
    }

    public static Instruction CreateSll(int rd, int rt, int shift_amount, String label){
        return new Instruction(ID.sll, rd, 0, rt, shift_amount, label);
    }

    public static Instruction CreateSrl(int rd, int rt, int shift_amount, String label){
        return new Instruction(ID.srl, rd, 0, rt, shift_amount, label);
    }

    public static Instruction CreateSra(int rd, int rt, int shift_amount, String label){
        return new Instruction(ID.sra, rd, 0, rt, shift_amount, label);
    }

    public static Instruction CreateSllv(int rd, int rt, int shift_amount, String label){
        return new Instruction(ID.sllv, rd, 0, rt, shift_amount, label);
    }

    public static Instruction CreateSrlv(int rd, int rt, int shift_amount, String label){
        return new Instruction(ID.srlv, rd, 0, rt, shift_amount, label);
    }

    public static Instruction CreateSrav(int rd, int rt, int shift_amount, String label){
        return new Instruction(ID.srav, rd, 0, rt, shift_amount, label);
    }

    public static Instruction CreateJr(int rs, String label){
        return new Instruction(ID.jr, 0, rs, 0, 0, label);
    }

    public static Instruction CreateJalr(int rd, int rs, String label){
        return new Instruction(ID.jalr, rd, rs, 0, 0, label);
    }

    public static Instruction CreateJalr(int rs, String label){
        return new Instruction(ID.jalr, 31, rs, 0, 0, label);
    }

    public static Instruction CreateMfhi(int rd, String label){
        return new Instruction(ID.mfhi, rd, 0, 0, 0, label);
    }

    public static Instruction CreateMthi(int rs, String label){
        return new Instruction(ID.mthi, 0, rs, 0, 0, label);
    }

    public static Instruction CreateMflo(int rd, String label){
        return new Instruction(ID.mflo, rd, 0, 0, 0, label);
    }

    public static Instruction CreateMtlo(int rs, String label){
        return new Instruction(ID.mtlo, 0, rs, 0, 0, label);
    }

    public static Instruction CreateMult(int rs, int rt, String label){
        return new Instruction(ID.mult, 0, rs, rt, 0, label);
    }

    public static Instruction CreateMultu(int rs, int rt, String label){
        return new Instruction(ID.multu, 0, rs, rt, 0, label);
    }

    public static Instruction CreateDiv(int rs, int rt, String label){
        return new Instruction(ID.div, 0, rs, rt, 0, label);
    }

    public static Instruction CreateDivu(int rs, int rt, String label){
        return new Instruction(ID.divu, 0, rs, rt, 0, label);
    }

    public static Instruction CreateAdd(int rd, int rs, int rt, String label){
        return new Instruction(ID.add, rd, rs, rt, 0, label);
    }
    */

    public static Instruction CreateAddu(int rd, int rs, int rt, String label){
        return new Instruction(ID.addu, rd, rs, rt, 0, 0, 0, label, "");
    }

    /**
    public static Instruction CreateSub(int rd, int rs, int rt, String label){
        return new Instruction(ID.sub, rd, rs, rt, 0, label);
    }

    public static Instruction CreateSubu(int rd, int rs, int rt, String label){
        return new Instruction(ID.subu, rd, rs, rt, 0, label);
    }

    public static Instruction CreateAnd(int rd, int rs, int rt, String label){
        return new Instruction(ID.and, rd, rs, rt, 0, label);
    }
    */

    public static Instruction CreateOr(int rd, int rs, int rt, String label){
        return new Instruction(ID.or, rd, rs, rt, 0, 0, 0, label, "");
    }

    /**
    public static Instruction CreateXor(int rd, int rs, int rt, String label){
        return new Instruction(ID.xor, rd, rs, rt, 0, label);
    }

    public static Instruction CreateNor(int rd, int rs, int rt, String label){
        return new Instruction(ID.nor, rd, rs, rt, 0, label);
    }
    */

    public static Instruction CreateSlt(int rd, int rs, int rt, String label){
        return new Instruction(ID.slt, rd, rs, rt, 0, 0, 0, label, "");
    }

    /**
    public static Instruction CreateSltu(int rd, int rs, int rt, String label){
        return new Instruction(ID.sltu, rd, rs, rt, 0, label);
    }
     */

    public static Instruction CreateJ(int jump_address, String label){
        return new Instruction(ID.j, 0, 0, 0, 0, jump_address, 0, label, "");
    }

    public static Instruction CreateJ(String branch_label, String label){
        return new Instruction(ID.j, 0, 0, 0, 0, 0, 0, label, branch_label);
    }

    /**
    public static Instruction CreateJal(int jump_address, String label){
        return new Instruction(ID.jal, jump_address, label);
    }

    public static Instruction CreateJal(String branch_label, String label){
        return new Instruction(ID.jal, branch_label, label);
    }
     */

    public static Instruction CreateBeq(int rs, int rt, int immediate, String label){
        return new Instruction(ID.beq, 0, rs, rt, immediate, 0, 0, label, "");
    }

    public static Instruction CreateBeq(int rs, int rt, String branch_label, String label){
        return new Instruction(ID.beq, 0, rs, rt, 0, 0, 0, label, branch_label);
    }

    public static Instruction CreateBne(int rs, int rt, int immediate, String label){
        return new Instruction(ID.bne, 0, rs, rt, immediate, 0, 0, label, "");
    }

    public static Instruction CreateBne(int rs, int rt, String branch_label, String label){
        return new Instruction(ID.bne, 0, rs, rt, 0, 0, 0, label, branch_label);
    }

    /**
    public static Instruction CreateBlez(int rs, int immediate, String label){
        return new Instruction(ID.blez, rs, 0, immediate, label);
    }

    public static Instruction CreateBlez(int rs, String branch_label, String label){
        return new Instruction(ID.blez, rs, 0, branch_label, label);
    }

    public static Instruction CreateBgtz(int rs, int immediate, String label){
        return new Instruction(ID.bgtz, rs, 0, immediate, label);
    }

    public static Instruction CreateBgtz(int rs, String branch_label, String label){
        return new Instruction(ID.bgtz, rs, 0, branch_label, label);
    }

    public static Instruction CreateAddi(int rt, int rs, int immediate, String label){
        return new Instruction(ID.addi, rs, rt, immediate, label);
    }
    */

    public static Instruction CreateAddiu(int rt, int rs, int immediate, String label){
        return new Instruction(ID.addiu, 0, rs, rt, immediate, 0, 0, label, "");
    }

    /**
    public static Instruction CreateSlti(int rt, int rs, int immediate, String label){
        return new Instruction(ID.slti, rs, rt, immediate, label);
    }

    public static Instruction CreateSltiu(int rt, int rs, int immediate, String label){
        return new Instruction(ID.sltiu, rs, rt, immediate, label);
    }

    public static Instruction CreateAndi(int rt, int rs, int immediate, String label){
        return new Instruction(ID.andi, rs, rt, immediate, label);
    }
    */

    public static Instruction CreateOri(int rt, int rs, int immediate, String label){
        return new Instruction(ID.ori, 0, rs, rt, immediate, 0, 0, label, "");
    }

    /**
    public static Instruction CreateXori(int rt, int rs, int immediate, String label){
        return new Instruction(ID.xori, rs, rt, immediate, label);
    }
    */

    public static Instruction CreateLui(int rt, int immediate, String label){
        return new Instruction(ID.lui, 0, 0, rt, immediate, 0, 0, label, "");
    }

    /**
    public static Instruction CreateLb(int rt, int rs, int immediate, String label){
        return new Instruction(ID.lb, rs, rt, immediate, label);
    }

    public static Instruction CreateLh(int rt, int rs, int immediate, String label){
        return new Instruction(ID.lh, rs, rt, immediate, label);
    }

    public static Instruction CreateLw(int rt, int rs, int immediate, String label){
        return new Instruction(ID.lw, rs, rt, immediate, label);
    }

    public static Instruction CreateLbu(int rt, int rs, int immediate, String label){
        return new Instruction(ID.lbu, rs, rt, immediate, label);
    }

    public static Instruction CreateLhu(int rt, int rs, int immediate, String label){
        return new Instruction(ID.lhu, rs, rt, immediate, label);
    }

    public static Instruction CreateSb(int rt, int rs, int immediate, String label){
        return new Instruction(ID.sb, rs, rt, immediate, label);
    }

    public static Instruction CreateSh(int rt, int rs, int immediate, String label){
        return new Instruction(ID.sh, rs, rt, immediate, label);
    }

    public static Instruction CreateSw(int rt, int rs, int immediate, String label){
        return new Instruction(ID.sw, rs, rt, immediate, label);
    }
    */

    // pseudo instructions
    public static Instruction CreateBlt(int rt, int rs, String branch_label){
        return InstructionFactory.CreateBlt(rt, rs, "", branch_label);
    }

    public static Instruction CreateBlt(int rt, int rs, String label, String branch_label){
        return new Instruction(ID.blt, 0, rs, rt, 0, 0, 0, label, branch_label);
    }

    public static Instruction CreateBge(int rt, int rs, String branch_label){
        return InstructionFactory.CreateBge(rt, rs, "", branch_label);
    }

    public static Instruction CreateBge(int rt, int rs, String label, String branch_label){
        return new Instruction(ID.bge, 0, rs, rt, 0, 0, 0, label, branch_label);
    }
}