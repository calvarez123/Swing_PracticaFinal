package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class VistaNuevaRegla extends JDialog {
    private JTextField nombreField;
    private JTextField puertoField;
    private JRadioButton entradaRadioButton;
    private JRadioButton salidaRadioButton;
    private JRadioButton permitirRadioButton;
    private JRadioButton bloquearRadioButton;
    private JRadioButton tcpRadioButton;
    private JRadioButton udpRadioButton;
    private JButton botonGuardar;
    private boolean reglaAgregada;
    private static final String NOMBRE_ARCHIVO = "data/nameReglas.json";

    public VistaNuevaRegla(JFrame parent) {
        super(parent, "Nueva Regla", true);
        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);
        panel.add(new JLabel("Puerto:"));
        puertoField = new JTextField();
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
                guardarRegla();
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

    private void guardarNombreRegla(String nombre) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", nombre);

        try (FileWriter file = new FileWriter(NOMBRE_ARCHIVO)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarRegla() {
        AppDataTerminal terminal = AppDataTerminal.getInstance();
        String nombre = nombreField.getText();
        String puerto = puertoField.getText();
        String direccionTrafico = entradaRadioButton.isSelected() ? "in" : "out";
        String accion = permitirRadioButton.isSelected() ? "allow" : "block";
        String protocolo = tcpRadioButton.isSelected() ? "TCP" : "UDP";

        String comando = "netsh advfirewall firewall add rule name=" + nombre +
                " dir=" + direccionTrafico +
                " action=" + accion +
                " protocol=" + protocolo +
                " localport=" + puerto;

        terminal.executeCommand(comando);

        guardarNombreRegla(nombre);

        reglaAgregada = true;
        botonGuardar.setEnabled(false);
        dispose();
    }

    public boolean isReglaAgregada() {
        return reglaAgregada;
    }

    public String[] getRegla() {
        String nombre = nombreField.getText();
        String puerto = puertoField.getText();
        String direccionTrafico = entradaRadioButton.isSelected() ? "in" : "out";
        String accion = permitirRadioButton.isSelected() ? "allow" : "block";
        String protocolo = tcpRadioButton.isSelected() ? "TCP" : "UDP";

        return new String[] {nombre, puerto, direccionTrafico, accion, protocolo};
    }

    public void mostrarVentana() {
        setVisible(true);
    }
}
