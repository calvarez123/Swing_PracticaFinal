package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirewallControlPanel extends JFrame {
    private AppDataTerminal terminal;
    private JTextArea outputTextArea;

    public FirewallControlPanel() {
        terminal = AppDataTerminal.getInstance();

        setTitle("Firewall Control Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton showRulesButton = new JButton("Show Rules");
        showRulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rulesOutput = terminal.showRules();
                // Dividir las reglas en líneas
                String[] rules = rulesOutput.split("\\n");
                // Obtener las primeras 10 reglas o menos si hay menos de 10 reglas en total
                StringBuilder first10Rules = new StringBuilder();
                int count = 0;
                for (String rule : rules) {
                    first10Rules.append(rule).append("\n");
                    count++;
                    if (count >= 10) {
                        break;
                    }
                }
                outputTextArea.setText(first10Rules.toString());
            }
        });
        buttonPanel.add(showRulesButton);

        JButton createRuleButton = new JButton("Create Rule");
        createRuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruleName = JOptionPane.showInputDialog("Enter Rule Name:");
                String ruleCommand = JOptionPane.showInputDialog("Enter Rule Command:");
                String result = terminal.createRule(ruleName, ruleCommand);
                outputTextArea.setText(result);
            }
        });
        buttonPanel.add(createRuleButton);

        JButton deleteRuleButton = new JButton("Delete Rule");
        deleteRuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruleName = JOptionPane.showInputDialog("Enter Rule Name to Delete:");
                String result = terminal.deleteRule(ruleName);
                outputTextArea.setText(result);
            }
        });
        buttonPanel.add(deleteRuleButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FirewallControlPanel();
            }
        });
    }
}
