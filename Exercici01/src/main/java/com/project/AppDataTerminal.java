package com.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public class AppDataTerminal {
    private static volatile AppDataTerminal instance;
    private static Object lock = new Object();

    private AppDataTerminal() {}

    public static AppDataTerminal getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new AppDataTerminal();
                }
            }
        }
        return instance;
    }

    public String showRules() {
        return executeCommand("netsh advfirewall firewall show rule name=all");
    }

    public String createRule(String ruleName, String ruleCommand) {
        return executeCommand("netsh advfirewall firewall add rule name=\"" + ruleName + "\" " + ruleCommand);
    }

    public String deleteRule(String ruleName) {
        return executeCommand("netsh advfirewall firewall delete rule name=\"" + ruleName + "\"");
    }

    private String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.IGNORE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), decoder));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
