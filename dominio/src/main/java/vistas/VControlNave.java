package vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Font;

public class VControlNave extends JFrame {

    private JPanel contentPane;
    private JPanel panelPrincipal;

    private JPanel panelInformacionNave;
    private JPanel panelBitacora;
    private JPanel panelMision;
    private JPanel panelGestionMision;
    private JPanel panelHistorial;

    private JPanel panelTablero;
    private JPanel panelMapa;

    private JLabel lblTitulo;

    private JComboBox<String> cbMisiones;
    private JButton btnIniciarMision;

    private JList<String> listaInformes;
    private DefaultListModel<String> modeloInformes;

    private JLabel lblCoordenadas;
    private JLabel lblEstado;

    private JButton btnTripulantes;
    private JButton btnVisorRecondito;
    private JPanel panelRecursos;
    private JPanel panelNave;
    private JLabel lblNave;
    private JLabel lblTipo;
    private JPanel panelRelleno;
    private JPanel panelRelleno2;
    private JPanel panelOrganizaRecursos;
    private JLabel lblCombustible;
    private JLabel lblEnergia;
    private JLabel lblDesgaste;
    private JList<String> listaBitacora;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VControlNave frame = new VControlNave();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VControlNave() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1600, 900);
        setLocationRelativeTo(null);
        setResizable(false);

        this.contentPane = new JPanel();
        this.contentPane.setBackground(Estilo.fondo);
        this.contentPane.setBorder(new EmptyBorder(15, 20, 15, 20));
        this.contentPane.setLayout(new BorderLayout(10, 15));
        setContentPane(this.contentPane);

        // TITULO

        this.lblTitulo = new JLabel(
                "CENTRO DE CONTROL",
                SwingConstants.CENTER);

        this.lblTitulo.setForeground(Estilo.celeste);
        this.lblTitulo.setFont(Estilo.fuenteTitulo);

        this.contentPane.add(this.lblTitulo, BorderLayout.NORTH);

        // PANEL PRINCIPAL

        this.panelPrincipal = new JPanel();
        this.panelPrincipal.setBackground(Estilo.fondo);
        this.panelPrincipal.setLayout(new GridLayout(1, 3, 20, 0));

        this.contentPane.add(this.panelPrincipal, BorderLayout.CENTER);

        // PANEL INFORMACION DE NAVE

        this.panelInformacionNave = new JPanel();
        this.panelInformacionNave.setBackground(Estilo.fondoPanel);
        this.panelInformacionNave.setBorder(
                new EmptyBorder(12, 12, 12, 12));
        this.panelInformacionNave.setLayout(
                new GridLayout(2, 1, 0, 12));

        this.panelPrincipal.add(this.panelInformacionNave);

        // INFORMACION GENERAL

        JPanel panelDatos = new JPanel();
        panelDatos.setBackground(Estilo.fondoPanel);

        this.panelInformacionNave.add(panelDatos);
        panelDatos.setLayout(new BorderLayout(0, 0));
        
        this.panelNave = new JPanel();
        panelDatos.add(this.panelNave, BorderLayout.NORTH);
        this.panelNave.setLayout(new GridLayout(1, 3, 0, 0));
        
        this.lblNave = new JLabel("Nave: ");
        this.lblNave.setForeground(new Color(80, 200, 230));
        this.lblNave.setFont(new Font("Monospaced", Font.BOLD, 13));
        this.panelNave.add(this.lblNave);
        
        this.lblTipo = new JLabel("Tipo: ");
        this.lblTipo.setForeground(new Color(235, 240, 245));
        this.lblTipo.setFont(new Font("Monospaced", Font.PLAIN, 13));
        this.panelNave.add(this.lblTipo);
        this.panelNave.setBackground(Estilo.fondoPanel);
        
        this.panelRelleno = new JPanel();
        this.panelNave.add(this.panelRelleno);
        this.panelRelleno.setBackground(Estilo.fondoPanel);
        
        this.panelRecursos = new JPanel();
        this.panelRecursos.setBackground(new Color(30, 42, 58));
        panelDatos.add(this.panelRecursos);
        this.panelRecursos.setLayout(new GridLayout(3, 1, 0, 0));
        
        this.panelRelleno2 = new JPanel();
        this.panelRecursos.add(this.panelRelleno2);
        this.panelRelleno2.setBackground(Estilo.fondoPanel);
        
        this.panelOrganizaRecursos = new JPanel();
        this.panelRecursos.add(this.panelOrganizaRecursos);
        this.panelOrganizaRecursos.setLayout(new GridLayout(3, 1, 0, 0));
        this.panelOrganizaRecursos.setBackground(Estilo.fondoPanel);
        this.panelOrganizaRecursos.setBorder(
        	    BorderFactory.createTitledBorder(
        	            BorderFactory.createLineBorder(Estilo.celeste),
        	            "RECURSOS DE LA NAVE",
        	            TitledBorder.LEFT,
        	            TitledBorder.TOP,
        	            Estilo.fuenteBold,
        	            Estilo.celeste
        	        )
        	    );
        
        this.lblCombustible = new JLabel("Combustible: 80%");
        this.lblCombustible.setForeground(new Color(235, 240, 245));
        this.lblCombustible.setFont(new Font("Monospaced", Font.PLAIN, 13));
        this.panelOrganizaRecursos.add(this.lblCombustible);
        
        this.lblEnergia = new JLabel("Energia: 65%");
        this.lblEnergia.setForeground(new Color(235, 240, 245));
        this.lblEnergia.setFont(new Font("Monospaced", Font.PLAIN, 13));
        this.panelOrganizaRecursos.add(this.lblEnergia);
        
        this.lblDesgaste = new JLabel("Desgaste: 20%");
        this.lblDesgaste.setForeground(new Color(235, 240, 245));
        this.lblDesgaste.setFont(new Font("Monospaced", Font.PLAIN, 13));
        this.panelOrganizaRecursos.add(this.lblDesgaste);

        // BITACORA

        this.panelBitacora = new JPanel();
        this.panelBitacora.setBackground(Estilo.fondoPanel);
        this.panelBitacora.setBorder(new EmptyBorder(12, 12, 12, 12));
        this.panelBitacora.setLayout(new BorderLayout(5, 8));
        this.panelBitacora.setBorder(null);

        this.panelInformacionNave.add(this.panelBitacora);

        JLabel lblTituloBitacora = new JLabel("BITACORA DE EVENTOS");
        lblTituloBitacora.setForeground(Estilo.celeste);
        lblTituloBitacora.setFont(Estilo.fuenteBold);

        this.panelBitacora.add(lblTituloBitacora, BorderLayout.NORTH);

        this.listaBitacora = new JList<String>();
        this.listaBitacora.setBackground(Estilo.fondoPanel);
        this.listaBitacora.setFont(Estilo.fuentePlain);
        this.listaBitacora.setForeground(Estilo.blanco);
        this.listaBitacora.setFocusable(false);

        JScrollPane scrollBitacora = new JScrollPane(this.listaBitacora);
        scrollBitacora.getViewport().setBackground(Estilo.fondoPanel);

        this.panelBitacora.add(scrollBitacora, BorderLayout.CENTER);

        // PANEL MISION

        JPanel panelMision = new JPanel();
        panelMision.setBackground(Estilo.fondo);
        panelMision.setBorder(new EmptyBorder(12, 12, 12, 12));
        panelMision.setLayout(new GridLayout(2, 1, 0, 12));

        this.panelPrincipal.add(panelMision);

        // GESTION DE MISIONES

        this.panelGestionMision = new JPanel();
        this.panelGestionMision.setBackground(Estilo.fondoPanel);
        this.panelGestionMision.setBorder(
                new EmptyBorder(12, 12, 12, 12));
        this.panelGestionMision.setLayout(new GridLayout(2, 1, 0, 12));

        panelMision.add(this.panelGestionMision);

        // SELECCION DE MISION

        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setBackground(Estilo.fondoPanel);
        panelSeleccion.setLayout(new GridLayout(3, 1, 5, 0));

        this.panelGestionMision.add(panelSeleccion);

        JLabel lblMisiones = new JLabel("GESTION DE MISIONES");
        lblMisiones.setForeground(Estilo.celeste);
        lblMisiones.setFont(Estilo.fuenteBold);
        panelSeleccion.add(lblMisiones);

        this.cbMisiones = new JComboBox<String>();
   
        this.cbMisiones.addItem("M-01 (Intercepción y Asistencia)");
        this.cbMisiones.addItem("M-02 (Recolección)");
        this.cbMisiones.addItem("M-03 (Retorno Seguro)");

        this.cbMisiones.setBackground(Estilo.fondoPanel);
        this.cbMisiones.setForeground(Estilo.blanco);
        this.cbMisiones.setFont(Estilo.fuentePlain);

        this.cbMisiones.setFocusable(false);

        panelSeleccion.add(this.cbMisiones);

        this.btnIniciarMision = new JButton("INICIAR MISION");
        this.btnIniciarMision.setBackground(Estilo.fondoPanel);
        this.btnIniciarMision.setForeground(Estilo.celeste);
        this.btnIniciarMision.setFont(Estilo.fuenteBold);
        this.btnIniciarMision.setFocusPainted(false);


        panelSeleccion.add(this.btnIniciarMision);

        // HISTORIAL DE INFORMES

        this.panelHistorial = new JPanel();
        this.panelHistorial.setBackground(Estilo.fondoPanel);
        this.panelHistorial.setBorder(new EmptyBorder(12, 12, 12, 12));
        this.panelHistorial.setLayout(new BorderLayout(5, 8));
        this.panelHistorial.setBorder(null);

        panelMision.add(this.panelHistorial);

        JLabel lblHistorial = new JLabel("HISTORIAL DE INFORMES");
        lblHistorial.setForeground(Estilo.celeste);
        lblHistorial.setFont(Estilo.fuenteBold);

        this.panelHistorial.add(lblHistorial, BorderLayout.NORTH);

        this.modeloInformes = new DefaultListModel<String>();
        this.modeloInformes.addElement("Sin informes registrados.");
        this.modeloInformes.addElement("Sin informes registrados.");
        this.modeloInformes.addElement("Sin informes registrados.");

        this.listaInformes = new JList<String>(this.modeloInformes);
        this.listaInformes.setBackground(Estilo.fondoPanel);
        this.listaInformes.setForeground(Estilo.blanco);
        this.listaInformes.setFont(Estilo.fuentePlain);
        this.listaInformes.setSelectionMode(0);
        this.listaInformes.setFocusable(false);

        JScrollPane scrollInformes = new JScrollPane(this.listaInformes);
        scrollInformes.getViewport().setBackground(Estilo.fondoPanel);

        this.panelHistorial.add(scrollInformes, BorderLayout.CENTER);

        // TABLERO DE NAVEGACION

        this.panelTablero = new JPanel();
        this.panelTablero.setBackground(Estilo.fondoPanel);
        this.panelTablero.setBorder(
                new EmptyBorder(12, 12, 12, 12));
        this.panelTablero.setLayout(new BorderLayout(5, 8));
        

        panelPrincipal.add(this.panelTablero);

        // INFORMACION DEL TABLERO

        JPanel panelDatosTablero = new JPanel();
        panelDatosTablero.setBackground(Estilo.fondoPanel);
        panelDatosTablero.setLayout(new GridLayout(2, 1, 5, 5));

        this.panelTablero.add(panelDatosTablero, BorderLayout.NORTH);

        JLabel lblTituloTablero = new JLabel("TABLERO DE NAVEGACION");
        lblTituloTablero.setForeground(Estilo.celeste);
        lblTituloTablero.setFont(Estilo.fuenteBold);

        panelDatosTablero.add(lblTituloTablero);

        JPanel panelCoordenadas = new JPanel();
        panelCoordenadas.setBackground(Estilo.fondoPanel);
        panelCoordenadas.setLayout(new GridLayout(1, 3, 10, 0));

        panelDatosTablero.add(panelCoordenadas);

        this.lblCoordenadas = new JLabel("X: 0 | Y: 0");
        this.lblCoordenadas.setForeground(Estilo.blanco);
        this.lblCoordenadas.setFont(Estilo.fuentePlain);
        panelCoordenadas.add(this.lblCoordenadas);

        this.lblEstado = new JLabel("Estado: En espera");
        this.lblEstado.setForeground(Estilo.blanco);
        this.lblEstado.setFont(Estilo.fuentePlain);
        panelCoordenadas.add(this.lblEstado);

        // AREA DEL MAPA

        this.panelMapa = new JPanel();
        this.panelMapa.setBackground(Estilo.fondoPanel);
        this.panelMapa.setLayout(null);

        this.panelTablero.add(this.panelMapa, BorderLayout.CENTER);

        // PANEL INFERIOR

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Estilo.fondo);
        panelInferior.setLayout(
                new FlowLayout(FlowLayout.CENTER, 20, 5));

        this.contentPane.add(panelInferior, BorderLayout.SOUTH);

        this.btnTripulantes = new JButton("TRIPULANTES");
        this.btnTripulantes.setBackground(Estilo.fondoPanel);
        this.btnTripulantes.setForeground(Estilo.celeste);
        this.btnTripulantes.setFont(Estilo.fuenteBold);
        this.btnTripulantes.setFocusPainted(false);

        panelInferior.add(this.btnTripulantes);

        this.btnVisorRecondito = new JButton("VISOR RECONDITO");
        this.btnVisorRecondito.setBackground(Estilo.fondoPanel);
        this.btnVisorRecondito.setForeground(Estilo.celeste);
        this.btnVisorRecondito.setFont(Estilo.fuenteBold);
        this.btnVisorRecondito.setFocusPainted(false);

        panelInferior.add(this.btnVisorRecondito);
    }
}
