package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorFirewall extends JFrame {
    private DefaultTableModel modeloTabla;
    private JButton botonMostrarReglas;
    private JButton botonAgregarRegla;
    private JButton botonModificarRegla;
    private JButton botonEliminarRegla;
    private JTable tablaReglas;
    private List<String[]> listaReglas;

    public AdministradorFirewall() {
        setTitle("Administrador de Firewall");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        String[] nombresColumnas = {"Nombre", "Puerto", "Direccion Trafico", "Accion", "Usuario", "Protocolo"};
        modeloTabla = new DefaultTableModel(nombresColumnas, 0);
        tablaReglas = new JTable(modeloTabla);

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

        listaReglas = new ArrayList<>();

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
        VistaNuevaRegla nuevaReglaDialog = new VistaNuevaRegla(this);
        nuevaReglaDialog.mostrarVentana();
        
        if (nuevaReglaDialog.isReglaAgregada()) {
            listaReglas.add(nuevaReglaDialog.getRegla());
            System.out.println(nuevaReglaDialog.getRegla());
            actualizarTabla();
        }
    }

    private void modificarRegla() {
        int filaSeleccionada = tablaReglas.getSelectedRow();
        if (filaSeleccionada != -1) {
            String[] datosFila = listaReglas.get(filaSeleccionada);
            VistaModificacionRegla modificacionReglaDialog = new VistaModificacionRegla(this, datosFila);
            modificacionReglaDialog.mostrarVentana();

            if (modificacionReglaDialog.isReglaModificada()) {
                listaReglas.set(filaSeleccionada, modificacionReglaDialog.getReglaModificada());
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una regla para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (String[] regla : listaReglas) {
            modeloTabla.addRow(regla);
        }
    }

    private void eliminarRegla() {
        int filaSeleccionada = tablaReglas.getSelectedRow();
        if (filaSeleccionada != -1) {
            String[] datosFila = listaReglas.get(filaSeleccionada);
            int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas eliminar la regla?", "Confirmación de Eliminación", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                listaReglas.remove(filaSeleccionada);
                AppDataTerminal terminal = AppDataTerminal.getInstance();
                String comando = "netsh advfirewall firewall delete rule name=" + datosFila[0];
                terminal.executeCommand(comando);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una regla para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdministradorFirewall::new);
    }
}
