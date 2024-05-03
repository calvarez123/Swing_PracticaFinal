package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class AdministradorFirewall extends JFrame {
    private DefaultTableModel modeloTabla;
    private JButton botonMostrarReglas;
    private JButton botonAgregarRegla;
    private JButton botonModificarRegla;
    private JButton botonEliminarRegla;

    public AdministradorFirewall() {
        setTitle("Administrador de Firewall");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] nombresColumnas = {"Nombre", "Puerto", "Tipo", "Aplicación", "Usuario", "Dirección IP", "Acción", "Interfaz", "Sentido"};
        modeloTabla = new DefaultTableModel(nombresColumnas, 0);
        JTable tablaReglas = new JTable(modeloTabla);
        JScrollPane panelDesplazamiento = new JScrollPane(tablaReglas);
        add(panelDesplazamiento, BorderLayout.CENTER);

        botonMostrarReglas = new JButton("Mostrar Reglas");
        botonMostrarReglas.addActionListener(e -> mostrarReglas());
        
        botonAgregarRegla = new JButton("Añadir Regla");
        botonAgregarRegla.addActionListener(e -> agregarRegla());
        
        botonModificarRegla = new JButton("Modificar Regla");
        botonModificarRegla.addActionListener(e -> modificarRegla());
        
        botonEliminarRegla = new JButton("Eliminar Regla");
        botonEliminarRegla.addActionListener(e -> eliminarRegla());

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonMostrarReglas);
        panelBotones.add(botonAgregarRegla);
        panelBotones.add(botonModificarRegla);
        panelBotones.add(botonEliminarRegla);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void mostrarReglas() {
        String reglas = AppDataTerminal.getInstance().showRules();
        JTextArea textArea = new JTextArea(reglas);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Reglas del Firewall", JOptionPane.INFORMATION_MESSAGE);
    }

    private void agregarRegla() {
        // Aquí puedes abrir la vista de añadir regla
        // Puedes usar un controlador para manejar esta acción
    }
    
    private void modificarRegla() {
        // Aquí puedes abrir la vista de modificar regla
        // Puedes usar un controlador para manejar esta acción
    }
    
    private void eliminarRegla() {
        // Aquí puedes abrir la vista de eliminar regla
        // Puedes usar un controlador para manejar esta acción
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdministradorFirewall::new);
    }
}
