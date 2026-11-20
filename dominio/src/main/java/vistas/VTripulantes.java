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
    private JButton btnAgregarConsejo;

    public VTripulantes() {
        setSize(950, 650);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        
        btnAgregarConsejo = new JButton("Agregar Consejo");
        btnAgregarConsejo.setFont(fuenteBold);
        btnAgregarConsejo.setBackground(celeste);
        btnAgregarConsejo.setForeground(fondo);
        btnAgregarConsejo.setFocusPainted(false);

        panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(fondo);
        panelBotones.add(btnVolver);
        panelBotones.add(btnAgregarConsejo);

        getContentPane().add(panelBotones, BorderLayout.SOUTH);

    }
    
    @Override
    public void setActionListener(ActionListener c){
        this.btnVolver.addActionListener(c);
        this.btnAgregarConsejo.addActionListener(c);
    }

    public void setTablaTripulantes(Object[] tripulante) {
        this.modeloTabla.addRow(tripulante);
    }
    
    public int getFilaSeleccionada() {
        return tablaTripulantes.getSelectedRow();
    }
    
    public void seleccionarFila(int fila) {
        if (fila >= 0 && fila < tablaTripulantes.getRowCount()) {
            // este metodo selecciona una fila de la tabla
            tablaTripulantes.setRowSelectionInterval(fila, fila);
        }
    }

    public void limpiarTablaTripulantes() {
        modeloTabla.setRowCount(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}

