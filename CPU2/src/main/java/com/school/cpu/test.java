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
        Program program = new Program("01111010", "00010000", "01110011", "00010001",
                "00010010", "00010110", "00010110");
        Machine machine = new Machine();
        machine.load(program);
        machine.print(System.out);
        machine.tick();
        machine.tick();
        machine.tick();
        machine.tick();
        machine.tick();
        machine.tick();
        machine.tick();
        machine.print(System.out);

    }
}
