package com.project;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        AppDataTerminal terminal = AppDataTerminal.getInstance();

        // Obtener el mapa de reglas
        Map<String, List<String>> rulesMap = terminal.showRules();

        // Imprimir las reglas formateadas
        System.out.println("Últimas 10 Reglas:");

        for (Map.Entry<String, List<String>> entry : rulesMap.entrySet()) {
            String ruleName = entry.getKey();
            List<String> ruleData = entry.getValue();
            
            // Construir una cadena de texto formateada para la regla
            StringBuilder formattedRule = new StringBuilder(ruleName).append(":\n");
            for (String data : ruleData) {
                formattedRule.append("\t").append(data).append("\n");
            }

            // Imprimir la regla formateada
            System.out.println(formattedRule.toString());
        }
    }
}
