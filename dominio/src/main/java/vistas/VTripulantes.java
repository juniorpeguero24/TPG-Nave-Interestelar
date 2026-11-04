package vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static vistas.Estilo.*;

public class VTripulantes extends JFrame implements IVista {

    private JTable tablaTripulantes;
    private JButton btnVolver;
    private JLabel lblTitulo;
    private JPanel panelTitulo;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTripulantes;
    private JPanel panelBotones;
    private JPanel panelTabla;

    public VTripulantes() {
        setSize(950, 650);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(fondo);

        lblTitulo = new JLabel("LISTA DE TRIPULANTES");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setForeground(celeste);

        panelTitulo = new JPanel(new FlowLayout());
        panelTitulo.setBackground(fondo);
        panelTitulo.add(lblTitulo);

        getContentPane().add(panelTitulo, BorderLayout.NORTH);

        String[] columnas = {
            "Nombre", "Cargo", "Origen", "Antiguedad", "Salario"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);

        tablaTripulantes = new JTable(modeloTabla);
        		
        tablaTripulantes.setBackground(fondoPanel);
        tablaTripulantes.setForeground(blanco);
        tablaTripulantes.setFont(fuenteBold);
        tablaTripulantes.setRowHeight(28);
        tablaTripulantes.setGridColor(celeste);
        tablaTripulantes.setSelectionBackground(celeste);
        tablaTripulantes.setSelectionForeground(fondo);

        tablaTripulantes.getTableHeader().setBackground(celeste);
        tablaTripulantes.getTableHeader().setForeground(fondo);
        tablaTripulantes.getTableHeader().setFont(fuentePlain);

        scrollTripulantes = new JScrollPane(tablaTripulantes);
        scrollTripulantes.getViewport().setBackground(fondoPanel);
        scrollTripulantes.setBackground(fondo);
        scrollTripulantes.getVerticalScrollBar().setBackground(fondoPanel);

        panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(fondo);
        panelTabla.add(scrollTripulantes, BorderLayout.CENTER);

        getContentPane().add(panelTabla, BorderLayout.CENTER);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(fuenteBold);
        btnVolver.setBackground(celeste);
        btnVolver.setForeground(fondo);
        btnVolver.setFocusPainted(false);

        panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(fondo);
        panelBotones.add(btnVolver);

        getContentPane().add(panelBotones, BorderLayout.SOUTH);

    }
    
    @Override
    public void setActionListener(ActionListener c){
        this.btnVolver.addActionListener(c);
    }

    public void setTablaTripulantes(Object[] tripulante) {

        this.modeloTabla.addRow(tripulante);

    }

}

