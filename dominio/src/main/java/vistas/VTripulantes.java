package vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import static vistas.Estilo.*;

public class VTripulantes extends JFrame {

    private JTable tablaTripulantes;
    private JButton btnVolver;

    public VTripulantes() {
        setSize(850, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(fondo);

        JLabel lblTitulo = new JLabel("LISTA DE TRIPULANTES");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setForeground(celeste);

        JPanel panelTitulo = new JPanel(new FlowLayout());
        panelTitulo.setBackground(fondo);
        panelTitulo.add(lblTitulo);

        getContentPane().add(panelTitulo, BorderLayout.NORTH);

        String[] columnas = {
            "Nombre", "Cargo", "Origen", "Antiguedad", "Salario"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

       /* COMO SE AGREGA UN TRIPULANTE A LA TABLA:
      
           modelo.addRow(new Object[] {
                "NOMBRE", "CARGO", "ORIGEN", ANTIGUEDAD, SALARIO
            }); */

        tablaTripulantes = new JTable(modelo);
        		
        tablaTripulantes.setBackground(fondoTabla);
        tablaTripulantes.setForeground(blanco);
        tablaTripulantes.setFont(fuenteBold);
        tablaTripulantes.setRowHeight(28);
        tablaTripulantes.setGridColor(celeste);
        tablaTripulantes.setSelectionBackground(celeste);
        tablaTripulantes.setSelectionForeground(fondo);

        tablaTripulantes.getTableHeader().setBackground(celeste);
        tablaTripulantes.getTableHeader().setForeground(fondo);
        tablaTripulantes.getTableHeader().setFont(fuentePlain);

        JScrollPane scrollTripulantes = new JScrollPane(tablaTripulantes);
        scrollTripulantes.getViewport().setBackground(fondoTabla);
        scrollTripulantes.setBackground(fondo);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(fondo);
        panelTabla.add(scrollTripulantes, BorderLayout.CENTER);

        getContentPane().add(panelTabla, BorderLayout.CENTER);

        btnVolver = new JButton("Volver");
        btnVolver.setFont(fuenteBold);
        btnVolver.setBackground(celeste);
        btnVolver.setForeground(fondo);
        btnVolver.setFocusPainted(false);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(fondo);
        panelBotones.add(btnVolver);

        getContentPane().add(panelBotones, BorderLayout.SOUTH);

    }
    
    public static void main(String[] args) {
        VTripulantes ventana = new VTripulantes();
        ventana.setVisible(true);
    }
}

