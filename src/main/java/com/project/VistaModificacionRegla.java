package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VistaModificacionRegla extends JDialog {
    private JTextField nombreField;
    private JTextField puertoField;
    private JRadioButton entradaRadioButton;
    private JRadioButton salidaRadioButton;
    private JRadioButton permitirRadioButton;
    private JRadioButton bloquearRadioButton;
    private JRadioButton tcpRadioButton;
    private JRadioButton udpRadioButton;
    private JButton botonGuardar;
    private boolean reglaModificada;

    public VistaModificacionRegla(JFrame parent, String[] datosFila) {
        super(parent, "Modificar Regla", true);
        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField(datosFila[0]);
        panel.add(nombreField);
        panel.add(new JLabel("Puerto:"));
        puertoField = new JTextField(datosFila[1]);
        panel.add(puertoField);

        panel.add(new JLabel("Dirección del tráfico:"));
        ButtonGroup direccionGroup = new ButtonGroup();
        entradaRadioButton = new JRadioButton("Entrante");
        salidaRadioButton = new JRadioButton("Saliente");
        direccionGroup.add(entradaRadioButton);
        direccionGroup.add(salidaRadioButton);
        JPanel direccionPanel = new JPanel(new GridLayout(1, 2));
        direccionPanel.add(entradaRadioButton);
        direccionPanel.add(salidaRadioButton);
        panel.add(direccionPanel);

        panel.add(new JLabel("Acción:"));
        ButtonGroup accionGroup = new ButtonGroup();
        permitirRadioButton = new JRadioButton("Permitir");
        bloquearRadioButton = new JRadioButton("Bloquear");
        accionGroup.add(permitirRadioButton);
        accionGroup.add(bloquearRadioButton);
        JPanel accionPanel = new JPanel(new GridLayout(1, 2));
        accionPanel.add(permitirRadioButton);
        accionPanel.add(bloquearRadioButton);
        panel.add(accionPanel);

        panel.add(new JLabel("Protocolo:"));
        ButtonGroup protocoloGroup = new ButtonGroup();
        tcpRadioButton = new JRadioButton("TCP");
        udpRadioButton = new JRadioButton("UDP");
        protocoloGroup.add(tcpRadioButton);
        protocoloGroup.add(udpRadioButton);
        JPanel protocoloPanel = new JPanel(new GridLayout(1, 2));
        protocoloPanel.add(tcpRadioButton);
        protocoloPanel.add(udpRadioButton);
        panel.add(protocoloPanel);

        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarRegla(datosFila);
            }
        });
        panel.add(botonGuardar);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(botonCancelar);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(parent);
    }

    private void guardarRegla(String[] datos) {
        AppDataTerminal terminal = AppDataTerminal.getInstance();
        String comando = "netsh advfirewall firewall delete rule name=" + datos[0];
        terminal.executeCommand(comando);

        String nombre = nombreField.getText();
        String puerto = puertoField.getText();
        String direccionTrafico = entradaRadioButton.isSelected() ? "in" : "out";
        String accion = permitirRadioButton.isSelected() ? "allow" : "block";
        String protocolo = tcpRadioButton.isSelected() ? "TCP" : "UDP";

        comando = "netsh advfirewall firewall add rule name=" + nombre +
                         " dir=" + direccionTrafico +
                         " action=" + accion +
                         " protocol=" + protocolo +
                         " localport=" + puerto;

        terminal.executeCommand(comando);
        reglaModificada = true;
        botonGuardar.setEnabled(false);
        dispose();
    }

    public boolean isReglaModificada() {
        return reglaModificada;
    }

    public String[] getReglaModificada() {
        String nombre = nombreField.getText();
        String puerto = puertoField.getText();
        return new String[]{nombre, puerto};
    }

    public void mostrarVentana() {
        setVisible(true);
    }
}
