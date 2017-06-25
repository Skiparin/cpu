/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.school.cpu;

import java.io.PrintStream;

public class Machine {

    private Cpu cpu = new Cpu();
    private Memory memory = new Memory();

    public void load(Program program) {
        int index = 0;
        for (int instr : program) {
            memory.set(index++, instr);
        }
    }

    public void tick() {
        int instr = memory.get(cpu.getIp());

        if (instr == 0b0000_0000) {
            // 0000 0000  NOP
            cpu.incIp();
            // cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0001) {
            // 0000 0001 ADD A B
            cpu.setA(cpu.getA() + cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_0010) {
            // 0000 0010	MUL	A ← A*B; IP++
            cpu.setA(cpu.getA() * cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_0011) {
            // 0000 0011	DIV	A ← A/B; IP++
            cpu.setA(cpu.getA() / cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0000_0100) {
            // 0000 0100	ZERO	F ← A = 0; IP++
            if (cpu.getA() == 0) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_0101) {
            // 0000 0101	NEG	F ← A < 0; IP++
            if (cpu.getA() < 0) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_0110) {
            // 0000 0110	POS	F ← A > 0; IP++
            if (cpu.getA() > 0) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_0111) {
            // 0000 0111	NZERO	F ← A ≠ 0; IP++
            if (cpu.getA() != 0) {
                cpu.setFlag(true);
            }else cpu.setFlag(false);
            cpu.incIp();
        } else if (instr == 0b0000_1000) {
            // 0000 1000	EQ	F ← A = B; IP++
            if (cpu.getA() == cpu.getB()) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_1001) {
            // 0000 1001	LT	F ← A < B; IP++
            if (cpu.getA() < cpu.getB()) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_1010) {
            // 0000 1010	GT	F ← A > B; IP++
            if (cpu.getA() > cpu.getB()) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_1011) {
            // 0000 1011	NEQ	F ← A ≠ B; IP++
            if (cpu.getA() != cpu.getB()) {
                cpu.setFlag(true);
            }
            cpu.incIp();
        } else if (instr == 0b0000_1100) {
            // 0000 1100	ALWAYS	F ← true; IP++
            cpu.setFlag(true);
            cpu.incIp();
        } else if (instr == 0b0000_1111) {
            // 0000 1111	HALT	Halts execution
            System.out.println("Halt?");
            System.exit(0);
        } else if ((instr & 0b1111_1110) == 0b0001_0000) {
            //  0001 000r	PUSH r	[--SP] ← r; IP++
            cpu.decSp();
            int r = (instr & 0b0000_0001); //////????????????????????
            if (r == cpu.A) {
                memory.set(cpu.getSp(), cpu.getA());
            } else {
                memory.set(cpu.getSp(), cpu.getB());
            }
            cpu.incIp();
        } else if ((instr & 0b1111_1110) == 0b0001_0010) {
            // 0001 001r	POP r	r ← [SP++]; IP++
            int r = (instr & 0b0000_0001);
            if (r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp()));
            } else {
                cpu.setB(memory.get(cpu.getSp()));
            }
            cpu.setSp(cpu.getSp() + 1);
            cpu.incIp();
        } else if (instr == 0b0001_0100) {
            // 0001 0100	MOV A B	B ← A; IP++
            cpu.setB(cpu.getA());
            cpu.incIp();
        } else if (instr == 0b0001_0101) {
            // 0001 0101	MOV B A	A ← B; IP++
            cpu.setA(cpu.getB());
            cpu.incIp();
        } else if (instr == 0b0001_0110) {
            // 0001 0110	INC	A++; IP++
            cpu.setA(cpu.getA() + 1);
            cpu.incIp();
        } else if (instr == 0b0001_0111) {
            // 0001 0111	DEC	A--; IP++
            cpu.setA(cpu.getA() - 1);
            cpu.incIp();
        } else if ((instr & 0b1111_1000) == 0b0001_1000) {
            // 0001 1ooo	RTN +o	IP ← [SP++]; SP += o; IP++
            int o = instr & 0b0000_0111;
            cpu.setIp(memory.get(cpu.getSp()));
            cpu.setSp(cpu.getSp() + 1);
            cpu.setSp(cpu.getSp() + o);
        } else if ((instr & 0b1111_0000) == 0b0010_0000) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++

            // 0010 1 011 MOV B (=1) +3  [SP +3] // Move register B to memory position of SP with offset 3
            // 00101011 finding instruction
            //    and
            // 11110000
            // --------
            // 00100000
            // 00101011 finding offset
            //    and
            // 00000111
            // --------
            // 00000011 = 3
            // 00101011 finding register
            //    and
            // 00001000
            // --------
            // 00001000 = 8
            //    >> 3
            // 00000001 = 1
            int o = instr & 0b0000_0111;
            int r = (instr & 0b0000_1000) >> 3;
            if (r == cpu.A) {
                memory.set(cpu.getSp() + o, cpu.getA());
            } else {
                memory.set(cpu.getSp() + o, cpu.getB());
            }
            cpu.incIp();

        } else if ((instr & 0b1111_0000) == 0b0011_0000) {
            int r = (instr & 0b0000_0001);
            int o = (instr & 0b0000_1110) >> 1;

            if (r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp() + o));
            } else {
                cpu.setB(memory.get(cpu.getSp() + o));
            }
            cpu.incIp();

        } else if ((instr & 0b1100_0000) == 0b0100_0000) {
            int v = ((instr & 0b0011_1110) >> 1);
            if (((instr & 0b0010_0000)) == 0b0010_0000) {
                v = -((instr & 0b0001_1110) >> 1);
            }

            System.out.println("v = " + v);
            int r = (instr & 0b0000_0001);
            if (r == cpu.A) {
                cpu.setA(v);
            } else {
                cpu.setB(v);
            }
            cpu.incIp();
        } else if ((instr & 0b1100_0000) == 0b1000_0000) {
            System.out.println("JUMP #a");
            if (cpu.isFlag()) {
                int a = instr & 0b0011_1111;
                cpu.setIp(a);
            } else {
                cpu.incIp();
            }
        } //CALL #a: if F then [--SP] ← IP; IP ← a else IP++
        //11aa aaaa
        else if ((instr & 0b1100_0000) == 0b1100_0000) {
            System.out.println("CALL #a");
            if (cpu.isFlag()) {
                //Maybe decrease the Stack pointer first?
                cpu.decSp();
                memory.set(cpu.getSp(), cpu.getIp());
                cpu.setIp((instr & 0b0011_1111));
            } else {
                cpu.incIp();
            }
        }
    }

    public void print(PrintStream out) {
        memory.print(out);
        out.println("-------------");
        cpu.print(out);
    }

}
