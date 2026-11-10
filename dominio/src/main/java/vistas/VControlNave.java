package vistas;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import modelo.bitacora.Bitacora;
import modelo.mision.InformeMision;
import modelo.nave.AsistenteComando;
import modelo.nave.Nave;

import modelo.tablero.Posicion;
import modelo.tablero.Tablero;
import modelo.universo.Asteroide;
import modelo.universo.ElementoDelUniverso;
import modelo.universo.EstacionCarga;
import observer.ObservadorBitacora;
import observer.ObservadorMision;
import observer.ObservadorNave;
import observer.ObservadorTablero;

import static vistas.Estilo.*;

public class VControlNave extends JFrame implements IVista, ObservadorNave, ObservadorBitacora, ObservadorMision, ObservadorTablero {

    private JLabel lblTituloBitacora;
    private JPanel panelSeleccion;
    private JLabel lblMisiones;
    private JLabel lblHistorial;
    private JScrollPane scrollInformes;
    private JPanel panelDatosTablero;
    private JPanel panelCoordenadas;
    private JLabel lblTituloTablero;
    private JPanel panelInferior;
    private JPanel panelDatos;
    private JPanel contentPane;
    private JPanel panelPrincipal;

    private JPanel panelInformacionNave;
    private JPanel panelBitacora;

    private JPanel panelMision;
    private JPanel panelGestionMision;
    private JPanel panelHistorial;

    private JPanel panelTablero;
    private JPanel panelMapa;
    private JTable tablaTablero;
    private DefaultTableModel modeloTablero;

    private JLabel lblTitulo;

    private JComboBox<String> cbMisiones;
    private JButton btnIniciarMision;

    private JTextArea listaInformes;

    private JLabel lblCoordenadas;
    private JLabel lblEstado;

    private JButton btnTripulantes;
    private JButton btnVisorRecondito;
    private JButton btnVolverCreacion;
    private JPanel panelRecursos;
    private JPanel panelNave;
    private JLabel lblNave;
    private JLabel lblTipo;
    private JPanel panelRelleno;
    private JPanel panelRelleno2;
    private JPanel panelOrganizaRecursos;
    private JTextArea txtRecursos;
    private JScrollPane scrollBitacora;
    private JTextArea listaBitacora;

    private ImageIcon iconoNave;
    private ImageIcon iconoNaveEnemiga;
    private ImageIcon iconoEstacion;
    private ImageIcon iconoAsteroide;
    private ImageIcon iconoNaveVsAsteroide;
    private ImageIcon iconoNaveEstacion;
    private ImageIcon iconoNaveVsNaveEnemiga;
    private ImageIcon iconoDestino;


    private Tablero tablero;
    private Posicion objetivo;

    public VControlNave(Tablero tablero, AsistenteComando asistente) {

        this.tablero = tablero;
        this.tablero.agregarObservador(this);

        Nave nave = asistente.getNave();
        nave.agregarObservador(this);

        Bitacora bitacora = asistente.getBitacora();
        bitacora.agregarObservador(this);

        asistente.agregarObservador(this);

        iconoNave = cargarIcono("nave.png");
        iconoNaveEnemiga = cargarIcono("naveEnemiga.png");
        iconoEstacion = cargarIcono("estacion.png");
        iconoAsteroide = cargarIcono("asteroide.png");
        iconoNaveVsNaveEnemiga = cargarIcono("naveVsnaveEnemiga.png");
        iconoNaveVsAsteroide = cargarIcono("naveVsAsteroide.png");
        iconoNaveEstacion = cargarIcono("naveEstacion.png");
        iconoDestino = cargarIcono("destino.png");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(true);
        setLocationRelativeTo(null);

        this.contentPane = new JPanel();
        this.contentPane.setBackground(fondo);
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
        this.panelPrincipal.setBackground(fondo);
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

        this.panelDatos = new JPanel();
        panelDatos.setBackground(Estilo.fondoPanel);

        this.panelInformacionNave.add(panelDatos);
        panelDatos.setLayout(new BorderLayout(0, 0));
        
        this.panelNave = new JPanel();
        panelDatos.add(this.panelNave, BorderLayout.NORTH);
        this.panelNave.setLayout(new GridLayout(1, 3, 0, 0));
        
        this.lblNave = new JLabel("Nave: ");
        this.lblNave.setForeground(Estilo.celeste);
        this.lblNave.setFont(Estilo.fuenteBold);
        this.panelNave.add(this.lblNave);
        
        this.lblTipo = new JLabel("Tipo: ");
        this.lblTipo.setForeground(Estilo.blanco);
        this.lblTipo.setFont(Estilo.fuentePlain);
        this.panelNave.add(this.lblTipo);
        this.panelNave.setBackground(Estilo.fondoPanel);
        
        this.panelRelleno = new JPanel();
        this.panelNave.add(this.panelRelleno);
        this.panelRelleno.setBackground(Estilo.fondoPanel);
        
        this.panelRecursos = new JPanel();
        this.panelRecursos.setBackground(Estilo.fondoPanel);
        panelDatos.add(this.panelRecursos);
        this.panelRecursos.setLayout(new GridLayout(2, 1, 0, 0));

        this.panelRelleno2 = new JPanel();
        this.panelRecursos.add(this.panelRelleno2);
        this.panelRelleno2.setBackground(Estilo.fondoPanel);
        
        this.panelOrganizaRecursos = new JPanel();
        this.panelRecursos.add(this.panelOrganizaRecursos);
        this.panelOrganizaRecursos.setLayout(new GridLayout(1, 1, 0, 0));
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
        
        this.txtRecursos = new JTextArea();
        this.txtRecursos.setBackground(Estilo.fondoPanel);
        this.txtRecursos.setForeground(Estilo.blanco);
        this.txtRecursos.setFont(Estilo.fuenteBold);
        this.panelOrganizaRecursos.add(this.txtRecursos);

        // BITACORA

        this.panelBitacora = new JPanel();
        this.panelBitacora.setBackground(Estilo.fondoPanel);
        this.panelBitacora.setBorder(new EmptyBorder(12, 12, 12, 12));
        this.panelBitacora.setLayout(new BorderLayout(5, 8));
        this.panelBitacora.setBorder(null);

        this.panelInformacionNave.add(this.panelBitacora);

        this.lblTituloBitacora = new JLabel("BITACORA DE EVENTOS");
        lblTituloBitacora.setForeground(Estilo.celeste);
        lblTituloBitacora.setFont(Estilo.fuenteBold);

        this.panelBitacora.add(lblTituloBitacora, BorderLayout.NORTH);


        this.listaBitacora = new JTextArea();
        this.listaBitacora.setBackground(Estilo.fondoPanel);
        this.listaBitacora.setFont(Estilo.fuentePlain);
        this.listaBitacora.setForeground(Estilo.blanco);
        this.listaBitacora.setFocusable(false);
        this.listaBitacora.setEditable(false);
        this.listaBitacora.setText("Sin eventos registrados.");

        this.scrollBitacora = new JScrollPane(this.listaBitacora);
        scrollBitacora.getViewport().setBackground(Estilo.fondoPanel);

        this.panelBitacora.add(scrollBitacora, BorderLayout.CENTER);

        // PANEL MISION

        this.panelMision = new JPanel();
        panelMision.setBackground(fondo);
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

        this.panelSeleccion = new JPanel();
        panelSeleccion.setBackground(Estilo.fondoPanel);
        panelSeleccion.setLayout(new GridLayout(3, 1, 5, 0));

        this.panelGestionMision.add(panelSeleccion);

        this.lblMisiones = new JLabel("GESTION DE MISIONES");
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
        this.btnIniciarMision.setActionCommand("Iniciar Mision");


        panelSeleccion.add(this.btnIniciarMision);

        // HISTORIAL DE INFORMES

        this.panelHistorial = new JPanel();
        this.panelHistorial.setBackground(Estilo.fondoPanel);
        this.panelHistorial.setBorder(new EmptyBorder(12, 12, 12, 12));
        this.panelHistorial.setLayout(new BorderLayout(5, 8));
        this.panelHistorial.setBorder(null);

        panelMision.add(this.panelHistorial);

        this.lblHistorial = new JLabel("HISTORIAL DE INFORMES");
        lblHistorial.setForeground(Estilo.celeste);
        lblHistorial.setFont(Estilo.fuenteBold);

        this.panelHistorial.add(lblHistorial, BorderLayout.NORTH);

        this.listaInformes = new JTextArea();
        this.listaInformes.setBackground(Estilo.fondoPanel);
        this.listaInformes.setForeground(Estilo.blanco);
        this.listaInformes.setFont(Estilo.fuentePlain);
        this.listaInformes.setFocusable(false);
        this.listaInformes.setEditable(false);
        this.listaInformes.setText("Sin informes registrados.");

        this.scrollInformes = new JScrollPane(this.listaInformes);
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

        this.panelDatosTablero = new JPanel();
        panelDatosTablero.setBackground(Estilo.fondoPanel);
        panelDatosTablero.setLayout(new GridLayout(2, 1, 5, 5));

        this.panelTablero.add(panelDatosTablero, BorderLayout.NORTH);

        this.lblTituloTablero = new JLabel("TABLERO DE NAVEGACION");
        lblTituloTablero.setForeground(Estilo.celeste);
        lblTituloTablero.setFont(Estilo.fuenteBold);

        panelDatosTablero.add(lblTituloTablero);

        this.panelCoordenadas = new JPanel();
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
        this.panelMapa.setLayout(new BorderLayout());

        this.panelTablero.add(this.panelMapa, BorderLayout.CENTER);

        this.modeloTablero = new DefaultTableModel() {

            @Override
            public Class getColumnClass(int columna) {
                return ImageIcon.class;
            }

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        this.tablaTablero = new JTable();
        JScrollPane scrollTablero = new JScrollPane(this.tablaTablero);
        scrollTablero.setBorder(null);
        scrollTablero.getViewport().setBackground(Estilo.fondoPanel);

        this.tablaTablero.setTableHeader(null);
        this.tablaTablero.setBackground(Estilo.fondoPanel);
        this.tablaTablero.setForeground(Estilo.blanco);
        this.tablaTablero.setModel(this.modeloTablero);
        this.tablaTablero.setFillsViewportHeight(true);
        this.tablaTablero.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.tablaTablero.setCellSelectionEnabled(false);
        this.tablaTablero.setRowSelectionAllowed(false);
        this.tablaTablero.setColumnSelectionAllowed(false);
        this.tablaTablero.setFocusable(false);
        scrollTablero.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.panelMapa.add(scrollTablero, BorderLayout.CENTER);


        // PANEL INFERIOR

        this.panelInferior = new JPanel();
        panelInferior.setBackground(fondo);
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
        this.btnVisorRecondito.setActionCommand("VISOR");

        panelInferior.add(this.btnVisorRecondito);

        this.btnVolverCreacion = new JButton("VOLVER A CREACION");
        this.btnVolverCreacion.setBackground(Estilo.fondoPanel);
        this.btnVolverCreacion.setForeground(Estilo.celeste);
        this.btnVolverCreacion.setFont(Estilo.fuenteBold);
        this.btnVolverCreacion.setFocusPainted(false);
        this.btnVolverCreacion.setActionCommand("Volver Creacion");

        panelInferior.add(this.btnVolverCreacion);
    }

    @Override
    public void setActionListener(ActionListener c) {
        this.btnVisorRecondito.addActionListener(c);
        this.btnTripulantes.addActionListener(c);
        this.btnIniciarMision.addActionListener(c);
        this.btnVolverCreacion.addActionListener(c);
    }

    public void setDatosNave(String nombre, String tipo, String recursos) {
        this.lblNave.setText("Nombre: " + nombre);
        this.lblTipo.setText("Tipo: " + tipo);
        this.txtRecursos.setText(recursos);
    }

    public String getMisionSeleccionada(){
        return this.cbMisiones.getSelectedItem().toString();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void desactivarMision() {
        btnIniciarMision.setEnabled(false);
    }

    public void setMisionEnCurso(boolean enCurso) {
        btnIniciarMision.setEnabled(!enCurso);
    }

    // OBSERVADOR DE NAVE BITACORA E INFORMES


    @Override
    public void actualizarNave(Nave nave) {
        String estadoActual = nave.getEstadoActual();
        SwingUtilities.invokeLater(
            () -> txtRecursos.setText(estadoActual)
        );
    }

    @Override
    public void actualizarBitacora(Bitacora bitacora) {
        String eventos = bitacora.toString();
        SwingUtilities.invokeLater(
            () -> listaBitacora.setText(eventos)
        );
    }

    @Override
    public void actualizarInformes(ArrayList<InformeMision> historial) {
        ArrayList<InformeMision> copia = new ArrayList<>(historial);
        String texto = "";
        for (InformeMision informe : copia) {
            texto += "====================================\n";
            texto += informe.toString();
            texto += "\n\n";
        }
        final String textoFinal = texto;
        SwingUtilities.invokeLater(() -> {
            listaInformes.setText(textoFinal);
        });
    }

    //SETEO Y ACTUALIZACION (OBSERVADOR) DE TABLERO

    /* SwingUtilities.invokeLater(...) le pide a Swing que ejecute una tarea en su hilo de interfaz gráfica (EDT), apenas pueda.
    La misión se ejecuta en otro hilo. En vez de modificar la tabla directamente desde ese hilo,
    invokeLater pone la actualización en la cola del EDT, que es el hilo encargado de dibujar y actualizar los componentes Swing.
    Así se evitan modificaciones simultáneas de la interfaz y del modelo.*/

    @Override
    public void actualizar() {
        ElementoDelUniverso[][] matriz = this.tablero.verTablero();

        int PosXnave = tablero.getPosicionNave().getX();
        int PosYnave = tablero.getPosicionNave().getY();

        javax.swing.SwingUtilities.invokeLater(
                () -> actualizarTablero(matriz, PosXnave, PosYnave)
        );
    }

    private void actualizarTablero(ElementoDelUniverso[][] matriz, int PosXnave, int PosYnave) {
        modeloTablero.setRowCount(0);

        setPosicionNave(PosXnave, PosYnave);
        Posicion objetivo = tablero.getObjetivo();

        for(int i = 0; i < matriz.length; i++){
            ImageIcon[] fila = new ImageIcon[matriz[i].length];
            for(int j = 0; j < tablero.getY(); j++){
                boolean esPosicionNave = i == PosXnave && j == PosYnave;
                
                if (esPosicionNave) {
                    fila[j] = iconoNave;
                }
                if (matriz[i][j] instanceof Asteroide) {
                    fila[j] = esPosicionNave ? iconoNaveVsAsteroide : iconoAsteroide;
                }
                else if (matriz[i][j] instanceof EstacionCarga) {
                    fila[j] = esPosicionNave ? iconoNaveEstacion : iconoEstacion;
                }
                else if (matriz[i][j] instanceof Nave) {
                    fila[j] = esPosicionNave ? iconoNaveVsNaveEnemiga : iconoNaveEnemiga;
                }
                
                boolean esDestino = objetivo != null  && i == objetivo.getX()  && j == objetivo.getY();
                if (esDestino && !esPosicionNave) {
                    fila[j] = iconoDestino;
                }
            }
            modeloTablero.addRow(fila);
        }
    }
    
    public void mostrarObjetivo(Posicion objetivo) {
        SwingUtilities.invokeLater(() -> {
            this.objetivo = objetivo;
            actualizar();
        });
    }
    
    public void setTablero() {
        ajustarTablero(tablero.getX(), tablero.getY());
        actualizar();
    }

    public void ajustarTablero(int filas, int columnas) {
        int tamañoCelda = 40;

        modeloTablero.setColumnCount(columnas);

        this.tablaTablero.setRowHeight(tamañoCelda);

        int ancho = columnas * tamañoCelda;
        int alto = filas * tamañoCelda;

        this.tablaTablero.setPreferredSize(new Dimension(ancho,alto));

        for (int i = 0; i < columnas; i++) {
            this.tablaTablero.getColumnModel()
                    .getColumn(i).setPreferredWidth(tamañoCelda);
        }
    }

    public void setPosicionNave(int x, int y){

        this.lblCoordenadas.setText("X: " + x + " Y: " + y);
    }

    //IMAGENES

    
    private ImageIcon cargarIcono(String nombreArchivo) {
        java.net.URL recurso = getClass().getResource("/vistas/recursos/" + nombreArchivo);
        if (recurso == null) {
            throw new IllegalStateException("No se encontró la imagen /vistas/recursos/" + nombreArchivo);
        }
        Image imagen = new ImageIcon(recurso).getImage();
        imagen = imagen.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
    
}
