package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import static vistas.Estilo.*;

public class VAcceso extends JFrame {

	private JPanel contentPane;
	private JPanel panelPrincipal;
	private JPanel panelIzquierda;
	private JPanel panelCentral;
	private JPanel panelDerecha;
	private JPanel panelSuperior;
	private JLabel lblTitulo;
	private JButton btnLogin;
	private JButton btnRegistro;
	private JPanel panelNombre;
	private JLabel lblNombre;
	private JTextField tfNombre;
	private JPanel panelContrasenia;
	private JLabel lblContrasenia;
	private JPanel panelSubtitulo;
	private JPasswordField passwordField;
	private JLabel lblSubtitulo;
	private JPanel panelBtnLogin;
	private JPanel panelBtnRegistro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VAcceso frame = new VAcceso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VAcceso()  {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(200, 200, 600, 450);
	    setResizable(false);
		setLocationRelativeTo(null);

	    this.contentPane = new JPanel();
	    this.contentPane.setBackground(fondo);
	    this.contentPane.setLayout(new BorderLayout(10, 20));
	    setContentPane(this.contentPane);

	    // T�tulo
	    this.panelSuperior = new JPanel();
	    this.panelSuperior.setBackground(fondo);
	    this.contentPane.add(this.panelSuperior, BorderLayout.NORTH);

	    this.lblTitulo = new JLabel("Nave Interestelar");
	    this.lblTitulo.setFont(fuenteTitulo);
	    this.lblTitulo.setForeground(celeste);
	    this.panelSuperior.add(this.lblTitulo);

	    // Panel principal
	    this.panelPrincipal = new JPanel(new GridLayout(1, 3, 10, 0));
	    this.panelPrincipal.setBackground(fondo);
	    this.contentPane.add(this.panelPrincipal, BorderLayout.CENTER);

	    this.panelIzquierda = new JPanel();
	    this.panelIzquierda.setBackground(fondo);
	    this.panelPrincipal.add(this.panelIzquierda);

	    // Formulario central
	    this.panelCentral = new JPanel(new GridLayout(6, 1, 0, 8));
	    this.panelCentral.setBackground(fondo);
	    this.panelPrincipal.add(this.panelCentral);

	    this.panelSubtitulo = new JPanel();
	    this.panelSubtitulo.setBackground(fondo);
	    this.panelCentral.add(this.panelSubtitulo);
	    
	    this.lblSubtitulo = new JLabel("Identificacion");
	    this.lblSubtitulo.setFont(fuenteSubtitulo);
	    this.panelSubtitulo.add(this.lblSubtitulo);
	    this.lblSubtitulo.setForeground(celeste);

	    this.panelNombre = new JPanel(new GridLayout(2, 1, 0, 4));
	    this.panelNombre.setBackground(fondo);
	    this.panelCentral.add(this.panelNombre);

	    this.lblNombre = new JLabel("Nombre:");
	    this.lblNombre.setForeground(blanco);
	    this.lblNombre.setFont(fuentePlain);
	    this.panelNombre.add(this.lblNombre);

	    this.tfNombre = new JTextField();
	    this.tfNombre.setColumns(10);
	    this.tfNombre.setFont(fuentePlain);
	    this.panelNombre.add(this.tfNombre);

	    this.panelContrasenia = new JPanel(new GridLayout(2, 1, 0, 4));
	    this.panelContrasenia.setBackground(fondo);
	    this.panelCentral.add(this.panelContrasenia);

	    this.lblContrasenia = new JLabel("Contraseña:");
	    this.lblContrasenia.setForeground(blanco);
	    this.lblContrasenia.setFont(fuentePlain);
	    this.panelContrasenia.add(this.lblContrasenia);

	    this.passwordField = new JPasswordField();
	    this.passwordField.setFont(fuentePlain);
	    this.panelContrasenia.add(this.passwordField);
	    
	    this.panelBtnLogin = new JPanel();
	    this.panelCentral.add(this.panelBtnLogin);

	    this.btnLogin = new JButton("Entrar");
	    this.panelBtnLogin.add(this.btnLogin);
	    this.panelBtnLogin.setBackground(fondo);
	    this.btnLogin.setFont(fuentePlain);
	    this.btnLogin.setBackground(celeste);
	    this.btnLogin.setForeground(Color.BLACK);
	    this.btnLogin.setFocusPainted(false);
	    
	    
	    this.panelBtnRegistro = new JPanel();
	    this.panelBtnRegistro.setBackground(fondo);
	    this.panelCentral.add(this.panelBtnRegistro);

	    this.btnRegistro = new JButton("Registrarse");
	    this.panelBtnRegistro.add(this.btnRegistro);
	    this.btnRegistro.setFont(fuentePlain);
	    this.btnRegistro.setBackground(blanco);
	    this.btnRegistro.setFocusPainted(false);

	    this.panelDerecha = new JPanel();
	    this.panelDerecha.setBackground(fondo);
	    this.panelPrincipal.add(this.panelDerecha);
	}

}
