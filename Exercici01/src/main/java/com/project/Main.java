package com.project;

public class Main {
    public static void main(String[] args) {
        AppDataTerminal terminal = AppDataTerminal.getInstance();
        
        String currentRules = terminal.showRules();
        System.out.println("Current Rules:\n" + currentRules);
    }
}
