package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;

public class VCreacion extends JFrame {

    private JPanel contentPane;
    private JPanel panelPrincipal;
    private JPanel panelTitulo;
    private JPanel panelComienzo;

    private JPanel panelNave;
    private JPanel panelTripulante;
    private JPanel panelDisponibles;

    private JLabel lblTitulo;
    private JLabel lblNombreNave;
    private JLabel lblNombreCreada;
    private JLabel lblTipoCreada;
    private JTextField tfNombreTripulante;
    private JTextField tfAntiguedad;

    private JComboBox<String> cbCargo;
    private JComboBox<String> cbOrigen;

    private JButton btnCrearNave;
    private JButton btnCrearTripulante;
    private JButton btnAsignar;
    private JButton btnIniciar;

    private JList<String> listaDisponibles;
    private DefaultListModel<String> modeloDisponibles;

    private JRadioButton rdbtnCarguero;
    private JRadioButton rdbtnExploradora;
    private JRadioButton rdbtnCombate;

    private final Color fondo = new Color(25, 35, 50);
    private final Color fondoPanel = new Color(30, 42, 58);
    private final Color celeste = new Color(100, 190, 215);
    private final Color blanco = new Color(235, 240, 245);
    private JTextField tfNombreNave;
    private JPanel panelbtnCrearNave;
    private JPanel panelbtnIniciar;
    private final ButtonGroup grupoTipoNave = new ButtonGroup();

    public VCreacion() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 560);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPane = new JPanel(new BorderLayout(10, 15));
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setBackground(fondo);
        setContentPane(contentPane);

        // TITULO

        panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(fondo);

        lblTitulo = new JLabel(
                "NAVE INTERESTELAR - CONFIGURACION",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(new Font("Monospaced", Font.BOLD, 19));
        lblTitulo.setForeground(celeste);

        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // PANEL PRINCIPAL

        panelPrincipal = new JPanel(new GridLayout(1, 3, 12, 0));
        panelPrincipal.setBackground(fondo);

        contentPane.add(panelPrincipal, BorderLayout.CENTER);

        // PANEL NAVE

        panelNave = new JPanel();
        panelNave.setBorder(new TitledBorder("Nave"));
        ((TitledBorder) panelNave.getBorder()).setTitleColor(celeste);
        ((TitledBorder) panelNave.getBorder()).setTitleFont(
                new Font("Monospaced", Font.BOLD, 12)
        );
        panelNave.setBackground(fondo);
        this.panelNave.setLayout(new GridLayout(3, 1, 0, 0));

        JPanel panelOpcionesNave = new JPanel(new GridLayout(3, 1, 0, 0));
        panelOpcionesNave.setBackground(fondo);


        rdbtnCarguero = new JRadioButton("Carguero");
        grupoTipoNave.add(this.rdbtnCarguero);
        rdbtnCarguero.setBackground(fondo);
        rdbtnCarguero.setForeground(blanco);
        rdbtnCarguero.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelOpcionesNave.add(rdbtnCarguero);

        rdbtnExploradora = new JRadioButton("Exploradora");
        grupoTipoNave.add(this.rdbtnExploradora);
        rdbtnExploradora.setBackground(fondo);
        rdbtnExploradora.setForeground(blanco);
        rdbtnExploradora.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelOpcionesNave.add(rdbtnExploradora);

        rdbtnCombate = new JRadioButton("Combate");
        grupoTipoNave.add(this.rdbtnCombate);
        rdbtnCombate.setBackground(fondo);
        rdbtnCombate.setForeground(blanco);
        rdbtnCombate.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelOpcionesNave.add(rdbtnCombate);

        panelNave.add(panelOpcionesNave);

        JPanel panelFormularioNave = new JPanel();
        panelFormularioNave.setBackground(fondo);

        lblNombreNave = new JLabel("Nombre:");
        lblNombreNave.setForeground(blanco);
        lblNombreNave.setFont(new Font("Monospaced", Font.PLAIN, 12));

        tfNombreNave = new JTextField(10);
        tfNombreNave.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tfNombreNave.setBackground(blanco);
        tfNombreNave.setForeground(fondo);
        tfNombreNave.setCaretColor(fondo);
        panelFormularioNave.setLayout(new GridLayout(3, 1, 0, 0));

        panelFormularioNave.add(lblNombreNave);
        panelFormularioNave.add(tfNombreNave);

        panelNave.add(panelFormularioNave);

        // BOTON CREAR NAVE

        JPanel panelInferiorNave = new JPanel(new GridLayout(2, 1, 0, 10));
        panelInferiorNave.setBackground(fondo);
        
        this.panelbtnCrearNave = new JPanel();
        panelInferiorNave.add(this.panelbtnCrearNave);
        panelbtnCrearNave.setBackground(fondo);

        btnCrearNave = new JButton("Crear nave");
        this.panelbtnCrearNave.add(this.btnCrearNave);
        btnCrearNave.setBackground(celeste);
        btnCrearNave.setForeground(fondo);
        btnCrearNave.setFont(new Font("Monospaced", Font.BOLD, 12));
        btnCrearNave.setFocusPainted(false);

        // INFORMACION DE LA NAVE CREADA

        JPanel panelInfoNave = new JPanel(new GridLayout(2, 1, 3, 3));
        panelInfoNave.setBorder(new TitledBorder("Nave creada"));
        ((TitledBorder) panelInfoNave.getBorder()).setTitleColor(celeste);
        ((TitledBorder) panelInfoNave.getBorder()).setTitleFont(
                new Font("Monospaced", Font.BOLD, 11)
        );
        panelInfoNave.setBackground(fondoPanel);

        lblNombreCreada = new JLabel("Nombre: -");
        lblNombreCreada.setForeground(blanco);
        lblNombreCreada.setFont(new Font("Monospaced", Font.PLAIN, 11));

        lblTipoCreada = new JLabel("Tipo: -");
        lblTipoCreada.setForeground(blanco);
        lblTipoCreada.setFont(new Font("Monospaced", Font.PLAIN, 11));

        panelInfoNave.add(lblNombreCreada);
        panelInfoNave.add(lblTipoCreada);

        panelInferiorNave.add(panelInfoNave);

        panelNave.add(panelInferiorNave);

        // PANEL TRIPULANTE

        panelTripulante = new JPanel(new GridLayout(8, 1, 4, 4));
        panelTripulante.setBorder(new TitledBorder("Crear tripulante"));
        ((TitledBorder) panelTripulante.getBorder()).setTitleColor(celeste);
        ((TitledBorder) panelTripulante.getBorder()).setTitleFont(
                new Font("Monospaced", Font.BOLD, 12)
        );
        panelTripulante.setBackground(fondo);

        JLabel lblNombreTripulante = new JLabel("Nombre:");
        lblNombreTripulante.setForeground(blanco);
        lblNombreTripulante.setFont(new Font("Monospaced", Font.PLAIN, 12));

        tfNombreTripulante = new JTextField();
        tfNombreTripulante.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tfNombreTripulante.setBackground(blanco);
        tfNombreTripulante.setForeground(fondo);
        tfNombreTripulante.setCaretColor(fondo);

        JLabel lblAntiguedad = new JLabel("Antiguedad:");
        lblAntiguedad.setForeground(blanco);
        lblAntiguedad.setFont(new Font("Monospaced", Font.PLAIN, 12));

        tfAntiguedad = new JTextField();
        tfAntiguedad.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tfAntiguedad.setBackground(blanco);
        tfAntiguedad.setForeground(fondo);
        tfAntiguedad.setCaretColor(fondo);

        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setForeground(blanco);
        lblCargo.setFont(new Font("Monospaced", Font.PLAIN, 12));

        cbCargo = new JComboBox<String>();
        cbCargo.addItem("Capitan");
        cbCargo.addItem("Consejero");
        cbCargo.addItem("Teniente");
        cbCargo.addItem("Alferez");
        cbCargo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        cbCargo.setBackground(blanco);
        cbCargo.setForeground(fondo);

        JLabel lblOrigen = new JLabel("Origen:");
        lblOrigen.setForeground(blanco);
        lblOrigen.setFont(new Font("Monospaced", Font.PLAIN, 12));

        cbOrigen = new JComboBox<String>();
        cbOrigen.addItem("Terricola");
        cbOrigen.addItem("Vulcano");
        cbOrigen.addItem("Marciano");
        cbOrigen.setFont(new Font("Monospaced", Font.PLAIN, 12));
        cbOrigen.setBackground(blanco);
        cbOrigen.setForeground(fondo);

        panelTripulante.add(lblNombreTripulante);
        panelTripulante.add(tfNombreTripulante);
        panelTripulante.add(lblAntiguedad);
        panelTripulante.add(tfAntiguedad);
        panelTripulante.add(lblCargo);
        panelTripulante.add(cbCargo);
        panelTripulante.add(lblOrigen);
        panelTripulante.add(cbOrigen);

        btnCrearTripulante = new JButton("Crear tripulante");
        btnCrearTripulante.setBackground(celeste);
        btnCrearTripulante.setForeground(fondo);
        btnCrearTripulante.setFont(new Font("Monospaced", Font.BOLD, 12));
        btnCrearTripulante.setFocusPainted(false);

        JPanel panelFormularioTripulante = new JPanel(new BorderLayout(5, 8));
        panelFormularioTripulante.setBackground(fondo);
        panelFormularioTripulante.add(panelTripulante, BorderLayout.CENTER);
        panelFormularioTripulante.add(btnCrearTripulante, BorderLayout.SOUTH);

        // PANEL TRIPULANTES DISPONIBLES

        panelDisponibles = new JPanel(new BorderLayout(5, 10));
        panelDisponibles.setBorder(new TitledBorder("Tripulantes disponibles"));
        ((TitledBorder) panelDisponibles.getBorder()).setTitleColor(celeste);
        ((TitledBorder) panelDisponibles.getBorder()).setTitleFont(
                new Font("Monospaced", Font.BOLD, 12)
        );
        panelDisponibles.setBackground(fondo);

        modeloDisponibles = new DefaultListModel<>();
        listaDisponibles = new JList<>(modeloDisponibles);

        listaDisponibles.setBackground(fondoPanel);
        listaDisponibles.setForeground(blanco);
        listaDisponibles.setSelectionBackground(celeste);
        listaDisponibles.setSelectionForeground(fondo);
        listaDisponibles.setFont(new Font("Monospaced", Font.PLAIN, 12));
        listaDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollDisponibles = new JScrollPane(listaDisponibles);
        scrollDisponibles.setBorder(null);
        scrollDisponibles.getViewport().setBackground(fondoPanel);

        btnAsignar = new JButton("Asignar a nave");
        btnAsignar.setBackground(celeste);
        btnAsignar.setForeground(fondo);
        btnAsignar.setFont(new Font("Monospaced", Font.BOLD, 12));
        btnAsignar.setFocusPainted(false);

        panelDisponibles.add(scrollDisponibles, BorderLayout.CENTER);
        panelDisponibles.add(btnAsignar, BorderLayout.SOUTH);

        // AGREGAR PANELES

        panelPrincipal.add(panelNave);
        panelPrincipal.add(panelFormularioTripulante);
        panelPrincipal.add(panelDisponibles);

        // BOTON INICIAR

        panelComienzo = new JPanel();
        panelComienzo.setBackground(fondo);
        this.panelComienzo.setLayout(new GridLayout(0, 1, 0, 0));
        
        this.panelbtnIniciar = new JPanel();
        this.panelComienzo.add(this.panelbtnIniciar);

        btnIniciar = new JButton("Iniciar");
        this.panelbtnIniciar.add(this.btnIniciar);
        panelbtnIniciar.setBackground(fondo);
        btnIniciar.setBackground(celeste);
        btnIniciar.setForeground(fondo);
        btnIniciar.setFont(new Font("Monospaced", Font.BOLD, 13));
        btnIniciar.setFocusPainted(false);
        contentPane.add(panelComienzo, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            VCreacion ventana = new VCreacion();
            ventana.setVisible(true);
        });
    }
}
