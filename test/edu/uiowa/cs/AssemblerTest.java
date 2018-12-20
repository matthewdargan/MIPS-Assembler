package edu.uiowa.cs;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AssemblerTest {
        private static int MARS_TEXT_SEGMENT_START = 0x00400000;

        private static void testHelperPhase1(Instruction[] input, Instruction[] expectedP1) {
            // Phase 1
            List<Instruction> tals = Phase1.mal_to_tal(Arrays.asList(input));
            // null list
            assertNotNull(tals);
            // same length?
            assertEquals(expectedP1.length, tals.size());
            // same objects?
            for(int i = 0; i < expectedP1.length; i++){
                assertEquals(expectedP1[i], tals.get(i));
            }
        }
        
        private static void testHelperPhase2(Instruction[] expectedP1, Instruction[] expectedP2) {
            // Phase 2
            List<Instruction> resolved_tals = Phase2.resolve_addresses(Arrays.asList(expectedP1), MARS_TEXT_SEGMENT_START);
            // null list
            assertNotNull(resolved_tals);
            // same length?
            assertEquals(expectedP2.length, resolved_tals.size());
            // same objects?
            for(int i = 0; i < expectedP2.length; i++){
                assertEquals(expectedP2[i], resolved_tals.get(i));
            }
        }
        
        private static void testHelperPhase3(Instruction[] expectedP2, Integer[] expectedP3) {
            // Phase 3
            List<Integer> translated = Phase3.translate_instructions(Arrays.asList(expectedP2));
            // null list
            assertNotNull(translated);
            // same length?
            assertEquals(expectedP3.length, translated.size());
            // same objects?
            for(int i = 0; i < expectedP3.length; i++){
                assertEquals(expectedP3[i], translated.get(i));
            }
        }

        @Test
        public void test1Phase1() {
            Instruction[] input = {
                // label1: addu $t0, $zero, $zero
                InstructionFactory.CreateAddu(8, 0, 0, "label1"),
                // addu $s0, $s7, $t4
                InstructionFactory.CreateAddu(16, 23, 12),
                // blt  $s0,$t0,label1
                InstructionFactory.CreateBlt(16, 8, "label1"),
                // addiu $s1,$s2,0xF00000
                InstructionFactory.CreateAddiu(17, 18, 0xF00000),
            };
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateAddu(8, 0, 0, "label1"), // label1: addu $t0, $zero, $zero
                    InstructionFactory.CreateAddu(16, 23, 12), // addu $s0, $s7, $t4
                    InstructionFactory.CreateSlt(1, 16, 8),  // slt $at,$s0,$t0
                    InstructionFactory.CreateBne(1, 0, "label1"),     // bne $at,$zero,label1
                    InstructionFactory.CreateLui(1, 0x00F0), // lui $at, 0x00F0
                    InstructionFactory.CreateOri(1, 1, 0x0000), // ori $at, $at 0x0000
                    InstructionFactory.CreateAddu(17, 18, 1) // addu $s1,$s2,$at
            };

            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test1Phase2() {
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateAddu(8, 0, 0, "label1"), // label1: addu $t0, $zero, $zero
                    InstructionFactory.CreateAddu(16, 23, 12), // addu $s0, $s7, $t4
                    InstructionFactory.CreateSlt(1, 16, 8),  // slt $at,$s0,$t0
                    InstructionFactory.CreateBne(1, 0, "label1"),     // bne $at,$zero,label1
                    InstructionFactory.CreateLui(1, 0x00F0), // lui $at, 0x00F0
                    InstructionFactory.CreateOri(1, 1, 0x0000), // ori $at, $at 0x0000
                    InstructionFactory.CreateAddu(17, 18, 1) // addu $s1,$s2,$at
            };

            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateAddu(8,0, 0, "label1"),//new Instruction(2,8,0,0,0,0,0,1,0),
                    InstructionFactory.CreateAddu(16,23,12),//new Instruction(2,16,23,12,0,0,0,0,0),
                    InstructionFactory.CreateSlt(1, 16, 8),//new Instruction(8,1,16,8,0,0,0,0,0),
                    InstructionFactory.CreateBne(1, 0, 0xfffffffc),//new Instruction(6,0,1,0,0xfffffffc,0,0,0,1),
                    InstructionFactory.CreateLui(1, 0x00F0),// new Instruction(9,0,0,1,0x00F0,0,0,0,0),
                    InstructionFactory.CreateOri(1, 1, 0x0000),// new Instruction(10,0,1,1,0x0000,0,0,0,0),
                    InstructionFactory.CreateAddu(17, 18, 1)// new Instruction(2,17,18,1,0,0,0,0,0)
            };

            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test1Phase3() {
            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateAddu(8,0, 0, "label1"),//new Instruction(2,8,0,0,0,0,0,1,0),
                    InstructionFactory.CreateAddu(16,23,12),//new Instruction(2,16,23,12,0,0,0,0,0),
                    InstructionFactory.CreateSlt(1, 16, 8),//new Instruction(8,1,16,8,0,0,0,0,0),
                    InstructionFactory.CreateBne(1, 0, 0xfffffffc),//new Instruction(6,0,1,0,0xfffffffc,0,0,0,1),
                    InstructionFactory.CreateLui(1, 0x00F0),// new Instruction(9,0,0,1,0x00F0,0,0,0,0),
                    InstructionFactory.CreateOri(1, 1, 0x0000),// new Instruction(10,0,1,1,0x0000,0,0,0,0),
                    InstructionFactory.CreateAddu(17, 18, 1)// new Instruction(2,17,18,1,0,0,0,0,0)
            };

            // Phase 3
            Integer[] phase3_expected = {
                    // HINT: to get these, type the input program into MARS, assemble, and copy the binary values into your test case
                    0x00004021,
                    0x02ec8021,
                    0x0208082a,
                    0x1420fffc,
                    0x3c0100f0,
                    0x34210000,
                    0x02418821
            };

            testHelperPhase3(phase2_expected, phase3_expected);
        }
        
        @Test
        public void test2Phase1() { 
            Instruction[] input = {
                // Label1: addiu $t0, 0, imm: 1 (less than 16 bit imm)
                InstructionFactory.CreateAddiu(8, 0, 0x0001, "label1"),
                // Label2: addiu $t1, 0, imm: 0 (also less than 16 bit imm)
                InstructionFactory.CreateAddiu(9, 0, 0x0000, "label2"),
                // bge $t0, $t1, Label1
                InstructionFactory.CreateBge(9, 8, "label1"),
                // bge $t1, $t0, Label2
                InstructionFactory.CreateBge(8, 9, "label2")
            };
            
            //Phase 1:
            Instruction[] phase1_expected = {
                InstructionFactory.CreateAddiu(8, 0, 0x0001, "label1"),
                InstructionFactory.CreateAddiu(9, 0, 0x0000, "label2"),
                InstructionFactory.CreateSlt(1, 9, 8),
                InstructionFactory.CreateBeq(1, 0, "label1"),
                InstructionFactory.CreateSlt(1, 8, 9),
                InstructionFactory.CreateBeq(1, 0, "label2")
            };
            
            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test2Phase2() {
            //Phase 1:
            Instruction[] phase1_expected = {
                InstructionFactory.CreateAddiu(8, 0, 0x0001, "label1"),
                InstructionFactory.CreateAddiu(9, 0, 0x0000, "label2"),
                InstructionFactory.CreateSlt(1, 9, 8),
                InstructionFactory.CreateBeq(1, 0, "label1"),
                InstructionFactory.CreateSlt(1, 8, 9),
                InstructionFactory.CreateBeq(1, 0, "label2")
            };
            
            //Phase 2:
            Instruction[] phase2_expected = {
                InstructionFactory.CreateAddiu(8, 0, 0x0001, "label1"),
                InstructionFactory.CreateAddiu(9, 0, 0x0000, "label2"),
                InstructionFactory.CreateSlt(1, 9, 8),
                InstructionFactory.CreateBeq(1, 0, 0xfffffffc),
                InstructionFactory.CreateSlt(1, 8, 9),
                InstructionFactory.CreateBeq(1, 0, 0xfffffffb)
            };
            
            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test2Phase3() {
            //Phase 2:
            Instruction[] phase2_expected = {
                InstructionFactory.CreateAddiu(8, 0, 0x0001, "label1"),
                InstructionFactory.CreateAddiu(9, 0, 0x0000, "label2"),
                InstructionFactory.CreateSlt(1, 9, 8),
                InstructionFactory.CreateBeq(1, 0, 0xfffffffc),
                InstructionFactory.CreateSlt(1, 8, 9),
                InstructionFactory.CreateBeq(1, 0, 0xfffffffb)
            };
            
            //Phase 3:   
            Integer[] phase3_expected = {
                0x24080001,
                0x24090000,
                0x0128082a,
                0x1020fffc,
                0x0109082a,
                0x1020fffb
            };
            
            testHelperPhase3(phase2_expected, phase3_expected);
        }
        
        @Test
        public void test3Phase1() {
            Instruction[] input = {
                // label1: addiu $t0, $zero, -5
                InstructionFactory.CreateAddiu(8, 0, -5, "label1"),
                // bge $t0, $zero, label1
                InstructionFactory.CreateBge(8, 0, "label1")
            };

            // Phase 1
            Instruction[] phase1_expected = {
                InstructionFactory.CreateAddiu(8, 0, -5, "label1"), // label1: addiu $t0, $zero, -5
                InstructionFactory.CreateSlt(1, 8, 0), // slt $at, $t0, $zero 
                InstructionFactory.CreateBeq(1, 0, "label1") // beq $at, $zero, label1
            };

            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test3Phase2() {
            // Phase 1
            Instruction[] phase1_expected = {
                InstructionFactory.CreateAddiu(8, 0, -5, "label1"), // label1: addiu $t0, $zero, -5
                InstructionFactory.CreateSlt(1, 8, 0), // slt $at, $t0, $zero 
                InstructionFactory.CreateBeq(1, 0, "label1") // beq $at, $zero, label1
            };

            // Phase 2
            Instruction[] phase2_expected = {
                InstructionFactory.CreateAddiu(8, 0, -5, "label1"),
                InstructionFactory.CreateSlt(1, 8, 0),
                InstructionFactory.CreateBeq(1, 0, -3)
            };
            
            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test3Phase3() {
            // Phase 2
            Instruction[] phase2_expected = {
                InstructionFactory.CreateAddiu(8, 0, -5, "label1"),
                InstructionFactory.CreateSlt(1, 8, 0),
                InstructionFactory.CreateBeq(1, 0, -3)
            };

            // Phase 3
            Integer[] phase3_expected = {
                    // HINT: to get these, type the input program into MARS, assemble, and copy the binary values into your test case
                0x2408fffb,
                0x0100082a,
                0x1020fffd
            };

            testHelperPhase3(phase2_expected, phase3_expected);
        }
        
        @Test
        public void test4Phase1() {
            Instruction[] input = {
                InstructionFactory.CreateAddiu(19, 0, 22),
                InstructionFactory.CreateAddiu(9,0,10),
                InstructionFactory.CreateBge(19, 9, "label4"),
                InstructionFactory.CreateOr(20, 19, 9),
                InstructionFactory.CreateAddu(21,20,19,"label4"),
                InstructionFactory.CreateOri(11,9,44)
            };
            //Phase1
            Instruction[] phase1_expected ={
                InstructionFactory.CreateAddiu(19, 0, 22),
                InstructionFactory.CreateAddiu(9,0,10),
                InstructionFactory.CreateSlt( 1,19,9 ),
                InstructionFactory.CreateBeq(1, 0, "label4"),
                InstructionFactory.CreateOr(20,19,9),
                InstructionFactory.CreateAddu(21, 20 , 19,"label4"),
                InstructionFactory.CreateOri(11, 9, 44)
            };
            
            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test4Phase2() {
            //Phase1
            Instruction[] phase1_expected ={
                InstructionFactory.CreateAddiu(19, 0, 22),
                InstructionFactory.CreateAddiu(9,0,10),
                InstructionFactory.CreateSlt( 1,19,9 ),
                InstructionFactory.CreateBeq(1, 0, "label4"),
                InstructionFactory.CreateOr(20,19,9),
                InstructionFactory.CreateAddu(21, 20 , 19,"label4"),
                InstructionFactory.CreateOri(11, 9, 44)
            };
            
            Instruction[] phase2_expected={
                InstructionFactory.CreateAddiu(19, 0, 22),
                InstructionFactory.CreateAddiu(9,0,10),
                InstructionFactory.CreateSlt( 1,19,9 ),
                InstructionFactory.CreateBeq(1, 0, 1),
                InstructionFactory.CreateOr(20,19,9),
                InstructionFactory.CreateAddu(21, 20 , 19,"label4"),
                InstructionFactory.CreateOri(11, 9, 44)
            };
            
            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test4Phase3() {
            Instruction[] phase2_expected={
                InstructionFactory.CreateAddiu(19, 0, 22),
                InstructionFactory.CreateAddiu(9,0,10),
                InstructionFactory.CreateSlt( 1,19,9 ),
                InstructionFactory.CreateBeq(1, 0, 1),
                InstructionFactory.CreateOr(20,19,9),
                InstructionFactory.CreateAddu(21, 20 , 19,"label4"),
                InstructionFactory.CreateOri(11, 9, 44)
            };
            
            Integer[] phase3_expected={
                0x24130016,
                0x2409000a,
                0x0269082a,
                0x10200001,
                0x0269a025,
                0x0293a821,
                0x352b002c
            };
            
            testHelperPhase3(phase2_expected, phase3_expected);
        }
        
        @Test
        public void test5Phase1() {
            Instruction[] input = {
                // bge $s0, $s1, A
                InstructionFactory.CreateBge(16, 17, "A"),
                // addiu $t0, $t1, 0x0A31
                InstructionFactory.CreateAddiu(8, 9, 0x0A31),
                // A: or $t0, $zero, $t1
                InstructionFactory.CreateOr(8, 0, 9, "A"),
            };
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateSlt(1, 16, 17),  // slt $at,$s0,$s1
                    InstructionFactory.CreateBeq(1, 0, "A"),     // beq $at,$zero,A
                    InstructionFactory.CreateAddiu(8, 9, 0x0A31), //addiu $t0, $t1, 0x0A31
                    InstructionFactory.CreateOr(8,0,9,"A") //A: or $t0, $zero, $t1
            };
            
            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test5Phase2() {
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateSlt(1, 16, 17),  // slt $at,$s0,$s1
                    InstructionFactory.CreateBeq(1, 0, "A"),     // beq $at,$zero,A
                    InstructionFactory.CreateAddiu(8, 9, 0x0A31), //addiu $t0, $t1, 0x0A31
                    InstructionFactory.CreateOr(8,0,9,"A") //A: or $t0, $zero, $t1
            };

            // Phase 2
            Instruction[] phase2_expected = {
                InstructionFactory.CreateSlt(1, 16, 17),  // slt $at,$s0,$t0
                InstructionFactory.CreateBeq(1, 0, 0x00000001),     // beq $at,$zero,A
                InstructionFactory.CreateAddiu(8,9,0x0A31), //addiu $t0, $t1, 0x0A31
                InstructionFactory.CreateOr(8,0,9,"A") // A: or $t0, $zer0, $t1
            };

            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test5Phase3() {
            // Phase 2
            Instruction[] phase2_expected = {
                InstructionFactory.CreateSlt(1, 16, 17),  // slt $at,$s0,$t0
                InstructionFactory.CreateBeq(1, 0, 0x00000001),     // beq $at,$zero,A
                InstructionFactory.CreateAddiu(8,9,0x0A31), //addiu $t0, $t1, 0x0A31
                InstructionFactory.CreateOr(8,0,9,"A") // A: or $t0, $zer0, $t1
            };

            // Phase 3
            Integer[] phase3_expected = {
                    // HINT: to get these, type the input program into MARS, assemble, and copy the binary values into your test case
                    0x0211082A,
                    0x10200001,
                    0x25280A31,
                    0x00094025
            };
            
            testHelperPhase3(phase2_expected, phase3_expected);
        }
        
        @Test
        public void test6Phase1() {
            Instruction[] input = {
                // target: addu $t0, $zero, $zero
                InstructionFactory.CreateAddu(8, 0, 0, "target"),
                InstructionFactory.CreateAddu(16, 17, 9),
                // blt  $s0,$t0,target
                InstructionFactory.CreateBlt(16, 8, "target"),
                // addiu $s1,$s2,0xF00000
                InstructionFactory.CreateAddiu(17, 18, 0xF00000),
                // ori $s1, $s1, 0xF0000
                InstructionFactory.CreateOri(17, 17, 0xF000)
            };
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateAddu(8, 0, 0, "target"), // target: addu $t0, $zero, $zero
                    InstructionFactory.CreateAddu(16, 17, 9), // addu $s0, $s1, $t1
                    InstructionFactory.CreateSlt(1, 16, 8),  // slt $at,$s0,$t0
                    InstructionFactory.CreateBne(1, 0, "target"),     // bne $at,$zero,target
                    InstructionFactory.CreateLui(1, 0x00F0), // lui $at, 0x00F0
                    InstructionFactory.CreateOri(1, 1, 0x0000), // ori $at, $at 0x0000
                    InstructionFactory.CreateAddu(17, 18, 1), // addu $s1,$s2,$at
                    InstructionFactory.CreateOri(17, 17, 0xF000)
            };
            
            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test6Phase2() {
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateAddu(8, 0, 0, "target"), // target: addu $t0, $zero, $zero
                    InstructionFactory.CreateAddu(16, 17, 9), // addu $s0, $s1, $t1
                    InstructionFactory.CreateSlt(1, 16, 8),  // slt $at,$s0,$t0
                    InstructionFactory.CreateBne(1, 0, "target"),     // bne $at,$zero,target
                    InstructionFactory.CreateLui(1, 0x00F0), // lui $at, 0x00F0
                    InstructionFactory.CreateOri(1, 1, 0x0000), // ori $at, $at 0x0000
                    InstructionFactory.CreateAddu(17, 18, 1), // addu $s1,$s2,$at
                    InstructionFactory.CreateOri(17, 17, 0xF000)
            };

            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateAddu(8,0, 0, "target"),//new Instruction(2,8,0,0,0,0,0,1,0),
                    InstructionFactory.CreateAddu(16,17,9),//new Instruction(2,16,23,12,0,0,0,0,0),
                    InstructionFactory.CreateSlt(1, 16, 8),//new Instruction(8,1,16,8,0,0,0,0,0),
                    InstructionFactory.CreateBne(1, 0, 0xfffffffc),//new Instruction(6,0,1,0,0xfffffffc,0,0,0,1),
                    InstructionFactory.CreateLui(1, 0x00F0),// new Instruction(9,0,0,1,0x00F0,0,0,0,0),
                    InstructionFactory.CreateOri(1, 1, 0x0000),// new Instruction(10,0,1,1,0x0000,0,0,0,0),
                    InstructionFactory.CreateAddu(17, 18, 1),// new Instruction(2,17,18,1,0,0,0,0,0)
                    InstructionFactory.CreateOri(17, 17, 0xF000)
            };
            
            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test6Phase3() {
            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateAddu(8,0, 0, "target"),//new Instruction(2,8,0,0,0,0,0,1,0),
                    InstructionFactory.CreateAddu(16,17,9),//new Instruction(2,16,23,12,0,0,0,0,0),
                    InstructionFactory.CreateSlt(1, 16, 8),//new Instruction(8,1,16,8,0,0,0,0,0),
                    InstructionFactory.CreateBne(1, 0, 0xfffffffc),//new Instruction(6,0,1,0,0xfffffffc,0,0,0,1),
                    InstructionFactory.CreateLui(1, 0x00F0),// new Instruction(9,0,0,1,0x00F0,0,0,0,0),
                    InstructionFactory.CreateOri(1, 1, 0x0000),// new Instruction(10,0,1,1,0x0000,0,0,0,0),
                    InstructionFactory.CreateAddu(17, 18, 1),// new Instruction(2,17,18,1,0,0,0,0,0)
                    InstructionFactory.CreateOri(17, 17, 0xF000)
            };

            // Phase 3
            Integer[] phase3_expected = {
                    0x00004021,
                    0x02298021,
                    0x0208082a,
                    0x1420fffc,
                    0x3c0100f0,
                    0x34210000,
                    0x02418821,
                    0x3631F000
            };
            
            testHelperPhase3(phase2_expected, phase3_expected);
        }
        
        @Test
        public void test7Phase1() {
            Instruction[] input = {
            // or $t1, $t3, $t4
                InstructionFactory.CreateOr(9, 11, 12),
                // addu $t0, $zero, $t9
                InstructionFactory.CreateAddu(8, 0, 25),
                // label1: addu $s0, $s7, $t4
                InstructionFactory.CreateAddu(16, 23, 12, "label1"),
                // bne $t1, $t7, label2
                InstructionFactory.CreateBne(9, 15, "label2"),
                // addu $t1, $t1, $s0
                InstructionFactory.CreateAddu(9, 9, 16),
                // bge $s2,$zero,label1
                InstructionFactory.CreateBge(0, 18, "label1"),
                // addiu $s1,$s2,0xF000
                InstructionFactory.CreateAddiu(17, 18, 0xFFFFF000),
                // label2: addu $t0, $t0, $t1
                InstructionFactory.CreateAddu(8, 8, 9,"label2")
            };
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateOr(9, 11, 12), // or $t1, $t3, $t4
                    InstructionFactory.CreateAddu(8, 0, 25), // addu $t0, $zero, $t9
                    InstructionFactory.CreateAddu(16, 23, 12, "label1"),  // label1: addu $s0, $s7, $t4
                    InstructionFactory.CreateBne(9, 15, "label2"), // bne $t1, $t7, label2
                    InstructionFactory.CreateAddu(9, 9, 16), // addu $t1, $t1, $s0
                    InstructionFactory.CreateSlt(1, 0, 18), // slt $at, $s2, $zero
                    InstructionFactory.CreateBeq(1, 0, "label1"), // beq $at, $zero, "label1"
                    InstructionFactory.CreateAddiu(17, 18, 0xFFFFF000), // addiu $s1,$s2,0xf000
                    InstructionFactory.CreateAddu(8, 8, 9, "label2") // label2: addu $t0, $t0, $t1
            };
            
            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test7Phase2() {
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateOr(9, 11, 12), // or $t1, $t3, $t4
                    InstructionFactory.CreateAddu(8, 0, 25), // addu $t0, $zero, $t9
                    InstructionFactory.CreateAddu(16, 23, 12, "label1"),  // label1: addu $s0, $s7, $t4
                    InstructionFactory.CreateBne(9, 15, "label2"), // bne $t1, $t7, label2
                    InstructionFactory.CreateAddu(9, 9, 16), // addu $t1, $t1, $s0
                    InstructionFactory.CreateSlt(1, 0, 18), // slt $at, $s2, $zero
                    InstructionFactory.CreateBeq(1, 0, "label1"), // beq $at, $zero, "label1"
                    InstructionFactory.CreateAddiu(17, 18, 0xFFFFF000), // addiu $s1,$s2,0xf000
                    InstructionFactory.CreateAddu(8, 8, 9, "label2") // label2: addu $t0, $t0, $t1
            };

            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateOr(9, 11, 12), // or $t1, $t3, $t4
                    InstructionFactory.CreateAddu(8, 0, 25), // addu $t0, $zero, 10
                    InstructionFactory.CreateAddu(16, 23, 12, "label1"),  // label1: addu $s0, $s7, $t4
                    InstructionFactory.CreateBne(9, 15, 0x00000004), // bne $t1, 12, label2
                    InstructionFactory.CreateAddu(9, 9, 16), // addu $t1, $t1, 1
                    InstructionFactory.CreateSlt(1, 0, 18), // slt $at, $t4, $s0
                    InstructionFactory.CreateBeq(1, 0, 0xFFFFFFFb), // beq $a0, $zero, "label1"
                    InstructionFactory.CreateAddiu(17, 18, 0xFFFFF000), // addu $s1,$s2,0xf000
                    InstructionFactory.CreateAddu(8, 8, 9, "label2") // label2: addu $t0, $t0, $t1
            };
            
            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test7Phase3() {
            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateOr(9, 11, 12), // or $t1, $t3, $t4
                    InstructionFactory.CreateAddu(8, 0, 25), // addu $t0, $zero, 10
                    InstructionFactory.CreateAddu(16, 23, 12, "label1"),  // label1: addu $s0, $s7, $t4
                    InstructionFactory.CreateBne(9, 15, 0x00000004), // bne $t1, 12, label2
                    InstructionFactory.CreateAddu(9, 9, 16), // addu $t1, $t1, 1
                    InstructionFactory.CreateSlt(1, 0, 18), // slt $at, $t4, $s0
                    InstructionFactory.CreateBeq(1, 0, 0xFFFFFFFb), // beq $a0, $zero, "label1"
                    InstructionFactory.CreateAddiu(17, 18, 0xFFFFF000), // addu $s1,$s2,0xf000
                    InstructionFactory.CreateAddu(8, 8, 9, "label2") // label2: addu $t0, $t0, $t1
            };

            // Phase 3
            Integer[] phase3_expected = {
                    // HINT: to get these, type the input program into MARS, assemble, and copy the binary values into your test case
                    0x016c4825,
                    0x00194021,
                    0x02ec8021,
                    0x152f0004,
                    0x01304821,
                    0x0012082a,
                    0x1020fffb,
                    0x2651f000,
                    0x01094021
            };
            
            testHelperPhase3(phase2_expected, phase3_expected);
        }
        
        @Test
        public void test8Phase1() {
            Instruction[] input = {
                // label1: addu $t1, $zero, $zero
                InstructionFactory.CreateAddu(9, 0, 0),
                // addu $s1, $s8, $t5
                InstructionFactory.CreateAddu(17, 24, 13),
                // bge $s1, $t1, label1
                InstructionFactory.CreateBge(17, 9, "label1"),
                // addu $s1, $s6, $t3
                InstructionFactory.CreateAddu(17, 22, 11),
                // ori $t0, 0x0300, 0x12045, label1
                InstructionFactory.CreateOri(8, 8, 0x12045, "label1"),
            };
            
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateAddu(9, 0, 0), 
                    InstructionFactory.CreateAddu(17, 24, 13), 
                    InstructionFactory.CreateSlt(1, 17, 9),  // slt $at,$s0,$t0
                    InstructionFactory.CreateBeq(1, 0, "label1"),     // bne $at,$zero,label1
                    InstructionFactory.CreateAddu(17, 22, 11),
                    InstructionFactory.CreateLui(1, 0x0001, "label1"),    
                    InstructionFactory.CreateOri(1, 1, 0x2045), 
                    InstructionFactory.CreateOr(8, 8, 1) 
            };
            
            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test8Phase2() {
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateAddu(9, 0, 0), 
                    InstructionFactory.CreateAddu(17, 24, 13), 
                    InstructionFactory.CreateSlt(1, 17, 9),  // slt $at,$s0,$t0
                    InstructionFactory.CreateBeq(1, 0, "label1"),     // bne $at,$zero,label1
                    InstructionFactory.CreateAddu(17, 22, 11),
                    InstructionFactory.CreateLui(1, 0x0001, "label1"),    
                    InstructionFactory.CreateOri(1, 1, 0x2045), 
                    InstructionFactory.CreateOr(8, 8, 1) 
            };
            
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateAddu(9, 0, 0), //addu $t1, $0, $0
                    InstructionFactory.CreateAddu(17, 24, 13), //addu $s1, $t8, $t5
                    InstructionFactory.CreateSlt(1, 17, 9),  // slt $at,$s1,$t1
                    InstructionFactory.CreateBeq(1, 0, 1),     // beq $at,$zero,label1
                    InstructionFactory.CreateAddu(17, 22, 11), //addu $s1, $s6, $t3
                    InstructionFactory.CreateLui(1, 0x0001, "label1"), //lui $at, 0x0001, "label1"
                    InstructionFactory.CreateOri(1, 1, 0x2045), //$at, $at, 0x2045
                    InstructionFactory.CreateOr(8,8, 1) //addu $t0, 0x0300, $at
            };
            
            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test8Phase3() {
            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateAddu(9, 0, 0), //addu $t1, $0, $0
                    InstructionFactory.CreateAddu(17, 24, 13), //addu $s1, $t8, $t5
                    InstructionFactory.CreateSlt(1, 17, 9),  // slt $at,$s1,$t1
                    InstructionFactory.CreateBeq(1, 0, 1),     // beq $at,$zero,label1
                    InstructionFactory.CreateAddu(17, 22, 11), //addu $s1, $s6, $t3
                    InstructionFactory.CreateLui(1, 0x0001, "label1"), //lui $at, 0x0001, "label1"
                    InstructionFactory.CreateOri(1, 1, 0x2045), //$at, $at, 0x2045
                    InstructionFactory.CreateOr(8,8, 1) //addu $t0, 0x0300, $at
            };
            
            // Phase 3
            Integer[] phase3_expected = {
                0x00004821,
                0x030D8821,
                0x0229082A,
                0x10200001, 
                0x02CB8821,
                0x3C010001,
                0x34212045,
                0x01014025  
                }; 
            
            testHelperPhase3(phase2_expected, phase3_expected);
        }
        
        @Test
        public void test9Phase1() {
            Instruction[] input = {
                //label9: addiu $t0, $zero, 0xFFFFFFF
                InstructionFactory.CreateAddiu(8, 0, 0xFFFFFFF, "label9"),
                //slt $t1, $zero, $t0
                InstructionFactory.CreateSlt(9, 0, 8),
                //label5: or $t2, $t1, $t0
                InstructionFactory.CreateOr(10, 9, 8, "label5"),
                //bgt $zero, $t0, label5
                InstructionFactory.CreateBge(8, 0, "label5"),
                //ori $t3, $t0, 10
                InstructionFactory.CreateOri(11, 8, 10)
            };
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateLui(1, 0xFFF, "label9"), // label1: lui $t0, 0xF
                    InstructionFactory.CreateOri(1, 1, 0xFFFF), // ori $at, $at, 0xFFFFFF
                    InstructionFactory.CreateAddu(8, 0, 1),  // addu $t0,$at,$zero
                    InstructionFactory.CreateSlt(9, 0, 8),     // slt $t1,$zero,$t1
                    InstructionFactory.CreateOr(10, 9, 8, "label5"), // label5: or $t2, $t1, $t0
                    InstructionFactory.CreateSlt(1, 8, 0), // slt $at, $zero, $t0
                    InstructionFactory.CreateBeq(1, 0, "label5"), // bne $at,$zero,label5
                    InstructionFactory.CreateOri(11, 8, 10) // ori $t3,$t0,10 
            };
            
            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test9Phase2() {
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateLui(1, 0xFFF, "label9"), // label1: lui $t0, 0xF
                    InstructionFactory.CreateOri(1, 1, 0xFFFF), // ori $at, $at, 0xFFFFFF
                    InstructionFactory.CreateAddu(8, 0, 1),  // addu $t0,$at,$zero
                    InstructionFactory.CreateSlt(9, 0, 8),     // slt $t1,$zero,$t1
                    InstructionFactory.CreateOr(10, 9, 8, "label5"), // label5: or $t2, $t1, $t0
                    InstructionFactory.CreateSlt(1, 8, 0), // slt $at, $zero, $t0
                    InstructionFactory.CreateBeq(1, 0, "label5"), // bne $at,$zero,label5
                    InstructionFactory.CreateOri(11, 8, 10) // ori $t3,$t0,10 
            };

            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateLui(1, 0x0FFF, "label9"), // label1: lui $t0, 0xF
                    InstructionFactory.CreateOri(1, 1, 0xFFFF), // ori $at, $at, 0xFFFFFF
                    InstructionFactory.CreateAddu(8, 0, 1),  // addu $t0,$at,$zero
                    InstructionFactory.CreateSlt(9, 0, 8),     // slt $t1,$zero,$t1
                    InstructionFactory.CreateOr(10, 9, 8, "label5"), // label5: or $t2, $t1, $t0
                    InstructionFactory.CreateSlt(1, 8, 0), // slt $at, $zero, $t0
                    InstructionFactory.CreateBeq(1, 0, 0xFFFFFFFD), // bne $at,$zero,label5
                    InstructionFactory.CreateOri(11, 8, 10) // ori $t3,$t0,10
            };
            
            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test9Phase3() {
            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateLui(1, 0x0FFF, "label9"), // label1: lui $t0, 0xF
                    InstructionFactory.CreateOri(1, 1, 0xFFFF), // ori $at, $at, 0xFFFFFF
                    InstructionFactory.CreateAddu(8, 0, 1),  // addu $t0,$at,$zero
                    InstructionFactory.CreateSlt(9, 0, 8),     // slt $t1,$zero,$t1
                    InstructionFactory.CreateOr(10, 9, 8, "label5"), // label5: or $t2, $t1, $t0
                    InstructionFactory.CreateSlt(1, 8, 0), // slt $at, $zero, $t0
                    InstructionFactory.CreateBeq(1, 0, 0xFFFFFFFD), // bne $at,$zero,label5
                    InstructionFactory.CreateOri(11, 8, 10) // ori $t3,$t0,10
            };

            // Phase 3
            Integer[] phase3_expected = {
                    // HINT: to get these, type the input program into MARS, assemble, and copy the binary values into your test case
                    0x3c010fff,
                    0x3421ffff,
                    0x00014021,
                    0x0008482a,
                    0x01285025,
                    0x0100082a,
                    0x1020fffd,
                    0x350b000a
            };
            
            testHelperPhase3(phase2_expected, phase3_expected);
        }
        
        @Test
        public void test10Phase1() {
            Instruction[] input = {
                //for labeling purposes for bge to possibly branch to
                InstructionFactory.CreateAddu(8, 0, 0, "label1"),
                //addiu $s0, $0, 0xF
                //testing for an immediate that is within 16-bit range
                InstructionFactory.CreateAddiu(16, 0, 0xF),
                //for labeling purposes and also to test out OR
                //or $s3, $t2, $t8
                InstructionFactory.CreateOr(19, 10, 24, "label2"),
                //addiu $s1, $0, 0xFFFFFFF1 or -15
                //testing for an immediate that is negative and within range
                InstructionFactory.CreateAddiu(17, 0, 0xFFFFFFF1),
                //ori $s2, $0, Ox0ABC0000
                //testing for ori with an outside range positive immediate to see if it works as the addiu
                InstructionFactory.CreateOri(18, 18, 0x0ABC0000),
                //test the other MAL instruction of bge along with branching to the second label rather than the first
                //bge  $s3, $t1, label12
                InstructionFactory.CreateBge(19, 9, "label2")
            };
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateAddu(8, 0, 0, "label1"),
                    InstructionFactory.CreateAddiu(16, 0, 0xF),
                    InstructionFactory.CreateOr(19, 10, 24, "label2"), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateAddiu(17, 0, 0xFFFFFFF1),
                    InstructionFactory.CreateLui(1, 0x0ABC), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateOri(1, 1, 0x0000), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateOr(18, 18, 1), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateSlt(1, 19, 9),  //new instructions for bge  $s3, $t1, label12
                    InstructionFactory.CreateBeq(1, 0, "label2"),  //new instructions for bge  $s3, $t1, label12
            };
            
            testHelperPhase1(input, phase1_expected);
        }
        
        @Test
        public void test10Phase2() {
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateAddu(8, 0, 0, "label1"),
                    InstructionFactory.CreateAddiu(16, 0, 0xF),
                    InstructionFactory.CreateOr(19, 10, 24, "label2"), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateAddiu(17, 0, 0xFFFFFFF1),
                    InstructionFactory.CreateLui(1, 0x0ABC), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateOri(1, 1, 0x0000), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateOr(18, 18, 1), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateSlt(1, 19, 9),  //new instructions for bge  $s3, $t1, label12
                    InstructionFactory.CreateBeq(1, 0, "label2"),  //new instructions for bge  $s3, $t1, label12
            };
            
            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateAddu(8,0, 0, "label1"),
                    InstructionFactory.CreateAddiu(16, 0, 0xF),
                    InstructionFactory.CreateOr(19, 10, 24, "label2"),
                    InstructionFactory.CreateAddiu(17, 0, 0xFFFFFFF1),
                    InstructionFactory.CreateLui(1, 0x0ABC), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateOri(1, 1, 0x0000), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateOr(18, 18, 1), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateSlt(1, 19, 9), //new instructions for bge  $s3, $t1, label12
                    InstructionFactory.CreateBeq(1, 0, 0xFFFFFFF9)  //new instructions for bge  $s3, $t1, label12, immediate = -7
            };
            
            testHelperPhase2(phase1_expected, phase2_expected);
        }
        
        @Test
        public void test10Phase3() {
            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateAddu(8,0, 0, "label1"),
                    InstructionFactory.CreateAddiu(16, 0, 0xF),
                    InstructionFactory.CreateOr(19, 10, 24, "label2"),
                    InstructionFactory.CreateAddiu(17, 0, 0xFFFFFFF1),
                    InstructionFactory.CreateLui(1, 0x0ABC), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateOri(1, 1, 0x0000), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateOr(18, 18, 1), //new instructions for ori $s2, $0, Ox0ABC0000
                    InstructionFactory.CreateSlt(1, 19, 9), //new instructions for bge  $s3, $t1, label12
                    InstructionFactory.CreateBeq(1, 0, 0xFFFFFFF9)  //new instructions for bge  $s3, $t1, label12, immediate = -7
            };
            
            // Phase 3
            Integer[] phase3_expected = {
                    0x00004021, //label 1
                    0x2410000F, //small immediate
                    0x01589825, //label 2
                    0x2411FFF1, //negative immediate
                    0x3C010ABC, //new instructions for ori $s2, $0, Ox0ABC0000
                    0x34210000, //new instructions for ori $s2, $0, Ox0ABC0000
                    0x02419025, //new instructions for ori $s2, $0, Ox0ABC0000
                    0x0269082a, //new instructions for bge  $s3, $t1, label12
                    0x1020FFF9 //new instructions for bge  $s3, $t1, label12
            };
            
            testHelperPhase3(phase2_expected, phase3_expected);
        }

        @Test
        public void test11Phase1() {
            Instruction[] input = {
                    //loop: beq $t1, $zero, end
                    InstructionFactory.CreateBeq(9, 0, "end", "loop"),
                    // addiu $t1, $t1, -1
                    InstructionFactory.CreateAddiu(9, 9, -1),
                    // A: j loop
                    InstructionFactory.CreateJ("loop", "A"),
                    // end: addu $v0, $zero, $t1
                    InstructionFactory.CreateAddu(2, 0, 9, "end")
            };
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateBeq(9, 0, "end", "loop"),
                    InstructionFactory.CreateAddiu(9, 9, -1),
                    InstructionFactory.CreateJ("loop", "A"),
                    InstructionFactory.CreateAddu(2, 0, 9, "end")
            };

            testHelperPhase1(input, phase1_expected);
        }

        @Test
        public void test11Phase2() {
            // Phase 1
            Instruction[] phase1_expected = {
                    InstructionFactory.CreateBeq(9, 0, "end", "loop"),
                    InstructionFactory.CreateAddiu(9, 9, -1),
                    InstructionFactory.CreateJ("loop", "A"),
                    InstructionFactory.CreateAddu(2, 0, 9, "end")
            };

            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateBeq(9, 0, 2, "loop"),
                    InstructionFactory.CreateAddiu(9, 9, -1),
                    InstructionFactory.CreateJ(0x100000, "A"),
                    InstructionFactory.CreateAddu(2, 0, 9, "end")
            };

            testHelperPhase2(phase1_expected, phase2_expected);
        }

        @Test
        public void test11Phase3() {
            // Phase 2
            Instruction[] phase2_expected = {
                    InstructionFactory.CreateBeq(9, 0, 2, "loop"),
                    InstructionFactory.CreateAddiu(9, 9, -1),
                    InstructionFactory.CreateJ(0x100000, "A"),
                    InstructionFactory.CreateAddu(2, 0, 9, "end")
            };

            // Phase 3
            Integer[] phase3_expected = {
                    0x11200002,
                    0x2529ffff,
                    0x08100000,
                    0x00091021
            };
        };

    }