/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.school.cpu;

/**
 *
 * @author Orvur
 */
public class test {

    public static void main(String[] args) {
        System.out.println("Welcome to the awesome CPU program");
        Program tailFact = new Program("01000010", "00010000", "01001000", "00010000", 
                "00001100", "11001000", "00010010", "00001111", 
                "00110010", "00000111", "10001100", "00011001", 
                "00110101", "00000010", "00100010", "00110010", 
                "00010111", "00100001", "00001100", "10001000");
        //0 "MOV 1 A", "PUSH A", "MOV 5 A", "PUSH A"
        //4 "Always", "CALL #8", "POP A", "HALT",
        //8 "MOV 1 A", "NZERO", "JMP #12", "RTN +1",
        //12 "MOV 2 B", "MUL", "MOV A 2", "MOV 1 A",
        //16 "DEC", "MOV A 1", "ALWAYS", "JMP #8"
        
        
        Machine machine = new Machine();
        machine.load(tailFact);
        machine.print(System.out);
        while(true){
            machine.tick();
            machine.print(System.out);
        }

    }
}
