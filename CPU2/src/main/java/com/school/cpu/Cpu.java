/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.school.cpu;

import java.io.PrintStream;

/**
 *
 * @author Orvur
 */
public class Cpu {

    public static final int A = 0;
    public static final int B = 1;
    private int a = 0;
    private int b = 0;
    private int ip = 0;
    private int sp = 0;
    private boolean flag = false;

    public void decSp() {
        if (sp == 0) {
            sp = 63;
        } else {
            sp = sp - 1;
        }
    }

    public void incIp() {
        ip++;
        if (ip == 64) {
            ip = 0;
        }
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void print(PrintStream out) {
        out.printf("A:  %4d\n", a);
        out.printf("B:  %4d\n", b);
        out.printf("IP: %4d\n", ip);
        out.printf("SP: %4d\n", sp);
        out.println("F:  " + flag);
    }

    /*
    private void execute(String str) {
        switch (str) {
            case "NOP":
                IP++;
                break;
            case "ADD":
                A = A + B;
                IP++;
                break;
            case "MUL":
                A = A * B;
                IP++;
                break;
            case "DIV":
                A = A / B;
                IP++;
                break;
            case "ZERO":
                F = A == 0;
                IP++;
                break;
            case "NEG":
                F = A < 0;
                IP++;
                break;
            case "POS":
                F = A > 0;
                IP++;
                break;
            case "NZERO":
                F = A != 0;
                IP++;
                break;
            case "EQ":
                F = A == B;
                IP++;
                break;
            case "LT":
                F = A < B;
                IP++;
                break;
            case "GT":
                F = A > B;
                IP++;
                break;
            case "NEQ":
                F = A != B;
                IP++;
                break;
            case "ALWAYS":
                F = true;
                IP++;
                break;
            case "INC":
                A++;
                IP++;
                break;
            case "DEC":
                A--;
                IP++;
                break;
            case "HALT":
                while (true) {

                }
        }
    }

    private void execute(String str1, String str2) {
        switch (str1) {
            case "PUSH":
                if (str2.equals("A")) {
                    memory[SP++] = A;
                } else {
                    memory[SP++] = B;
                }
                IP++;
                break;
            case "POP":
                if (str2.equals("A")) {
                    memory[--SP] = A;
                } else {
                    memory[--SP] = B;
                }
                IP++;
                break;
        }
    }

    private void execute(String str1, String str2, String str3) {
        switch (str1) {
            case "MOV":
                if (!isNumeric(str2)) {
                    if (!isNumeric(str3)) {
                        if (str2.equals("A")) {
                            memory[SP + Integer.parseInt(str3)] = A;
                        } else {
                            memory[SP + Integer.parseInt(str3)] = B;
                        }
                    } else if (str2.equals("A")) {
                        memory[SP + Integer.parseInt(str3)] = A;
                    } else {
                        memory[SP + Integer.parseInt(str3)] = B;
                    }
                    IP++;
                    break;
                } else if (str2.contains("+") || str2.contains("-")) {
                    if (str3.equals("A")) {
                        memory[SP + Integer.parseInt(str2)] = A;
                    } else {
                        memory[SP + Integer.parseInt(str2)] = B;
                    }
                } else if (str3.equals("A")) {
                    A = Integer.parseInt(str2);
                } else {
                    B = Integer.parseInt(str2);
                }
                IP++;
                break;

        }
    }
     */
    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
