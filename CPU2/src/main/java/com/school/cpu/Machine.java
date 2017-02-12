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
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0010) {
            // 0000 0001 ADD A B
            cpu.setA(cpu.getA() * cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0011) {
            // 0000 0001 ADD A B
            cpu.setA(cpu.getA() / cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0100) {
            // 0000 0001 ADD A B
            cpu.setFlag(cpu.getA() == 0);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0101) {
            // 0000 0001 ADD A B
            cpu.setFlag(cpu.getA() < 0);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0110) {
            // 0000 0001 ADD A B
            cpu.setFlag(cpu.getA() > 0);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_0111) {
            // 0000 0001 ADD A B
            cpu.setFlag(cpu.getA() != 0);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1000) {
            // 0000 0001 ADD A B
            cpu.setFlag(cpu.getA() == cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1001) {
            // 0000 0001 ADD A B
            cpu.setFlag(cpu.getA() < cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1010) {
            // 0000 0001 ADD A B
            cpu.setFlag(cpu.getA() > cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1011) {
            // 0000 0001 ADD A B
            cpu.setFlag(cpu.getA() != cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1011) {
            // 0000 0001 ADD A B
            boolean always = true;
            cpu.setFlag(always);
            cpu.setIp(cpu.getIp() + 1);
        } else if (instr == 0b0000_1111) {
            // 0000 0001 ADD A B
            //mate
        }  else if ((instr & 0b1111_1110) == 0b0001_0010) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++
            int r = (instr & 0b0000_0001);
            cpu.setSp(cpu.getSp() + 1);
            if (r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp()));
            } else {
                cpu.setB(memory.get(cpu.getSp()));
            }
            cpu.setIp(cpu.getIp() + 1);
        }
        else if (instr == 0b0001_0100) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++
            cpu.setB(cpu.getA());
            cpu.setIp(cpu.getIp() + 1);
        }
        else if (instr == 0b0001_0101) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++
            cpu.setA(cpu.getB());
            cpu.setIp(cpu.getIp() + 1);
        }
        else if (instr == 0b0001_0110) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++
            cpu.setA(cpu.getA() + 1);
            cpu.setIp(cpu.getIp() + 1);
        }
        else if (instr == 0b0001_0111) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++
            cpu.setA(cpu.getA() - 1);
            cpu.setIp(cpu.getIp() + 1);
        }
        else if ((instr & 0b1111_1000) == 0b0001_1000) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++
            int o = instr & 0b0000_0111;
            cpu.setSp(cpu.getSp() + 1);
            memory.set(cpu.getIp(), cpu.getSp());
            cpu.setSp(cpu.getSp() + o);
            cpu.setIp(cpu.getIp() + 1);
        } 
        
        else if ((instr & 0b1111_0000) == 0b0010_0000) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++
            int o = instr & 0b0000_0111;
            int r = (instr & 0b0000_1000) >> 3;
            if (r == cpu.A) {
                memory.set(cpu.getSp() + o, cpu.getA());
            } else {
                memory.set(cpu.getSp() + o, cpu.getB());
            }
            cpu.setIp(cpu.getIp() + 1);
        } 
        else if ((instr & 0b1111_0000) == 0b0011_0000) {
            int o = instr & 0b0000_1110 >> 1;
            int r = (instr & 0b0000_0001);
            if (r == cpu.A) {
                cpu.setA(memory.get(cpu.getSp() + o));
            } else {
                cpu.setB(memory.get(cpu.getSp() + o));
            }
            cpu.setIp(cpu.getIp() + 1);
        }
        else if ((instr & 0b1111_0000) == 0b0001_0000) {
            // 0010 r ooo	MOV r o	   [SP + o] ← r; IP++
            int r = (instr & 0b0000_0001);
            cpu.decSp();
            if (r == cpu.A) {
                memory.set(cpu.getSp(), cpu.getA());
            } else {
                memory.set(cpu.getSp(), cpu.getB());
            }
            cpu.setIp(cpu.getIp() + 1);
        }
        else if ((instr & 0b1100_0000) == 0b0100_0000) {
            int v = (instr & 0b0011_1110) >> 1;
            int r = (instr & 0b0000_0001);
            if (r == cpu.A) {
                cpu.setA(v);
            } else {
                cpu.setB(v);
            }
            cpu.setIp(cpu.getIp() + 1);
        } else if ((instr & 0b1100_0000) == 0b1000_0000) {
            int a = instr & 0b0011_1111;
            if (cpu.isFlag()) {
                memory.set(cpu.getIp(), a);
            } else {
                cpu.setIp(cpu.getIp() + 1);
            }

        } else if ((instr & 0b1100_0000) == 0b1100_0000) {
            int a = instr & 0b0011_1111;
            if (cpu.isFlag()) {
                cpu.decSp();
                memory.set(cpu.getSp(), cpu.getIp());
                memory.set(cpu.getIp(), a);
            } else {
                cpu.setIp(cpu.getIp() + 1);
            }

        }

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
    }

    public void print(PrintStream out) {
        memory.print(out);
        out.println("-------------");
        cpu.print(out);
    }

}
