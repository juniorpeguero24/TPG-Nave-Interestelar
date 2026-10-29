package vistas;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static vistas.Estilo.*;


public class VVisorRecondito extends JFrame {

	private JPanel contentPane;
	private JPanel panelBtn;
	private JPanel panelBtnNaves;
	private JButton btnNaveConsu;
	private JButton btnNaveGen;
	private JPanel panelBtnVolver;
	private JButton btnVolver;
	private JPanel panelCentral;
	private JScrollPane scrollConcurrencia;
	private JTextArea textAreaConcurrencia;
	private JPanel panelTitulo;
	private JLabel lblTitulo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VVisorRecondito frame = new VVisorRecondito();
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
	public VVisorRecondito() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 250, 500, 700);
		setResizable(false);
		setLocationRelativeTo(null);

		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.contentPane.setBackground(fondo);
		setContentPane(this.contentPane);

		this.panelCentral = new JPanel();
		this.contentPane.add(this.panelCentral, BorderLayout.CENTER);
		this.panelCentral.setLayout(new BorderLayout(0, 0));
		this.panelCentral.setBackground(fondo);

		this.scrollConcurrencia = new JScrollPane();
		this.panelCentral.add(this.scrollConcurrencia);
		this.scrollConcurrencia.getViewport().setBackground(fondoPanel);
		this.scrollConcurrencia.getVerticalScrollBar().setBackground(fondoPanel);
		this.scrollConcurrencia.getVerticalScrollBar().setForeground(celeste);

		this.textAreaConcurrencia = new JTextArea();
		this.textAreaConcurrencia.setEditable(false);
		this.scrollConcurrencia.setViewportView(this.textAreaConcurrencia);
		this.textAreaConcurrencia.setFont(fuentePlain);
		this.textAreaConcurrencia.setForeground(blanco);
		this.textAreaConcurrencia.setBackground(fondoPanel);

		/* se agrega texto con:
		 textAreaConcurrencia("cadena \n" ;
		 */

		this.panelBtn = new JPanel();
		this.contentPane.add(this.panelBtn, BorderLayout.SOUTH);
		this.panelBtn.setLayout(new GridLayout(2, 1, 0, 0));
		this.panelBtn.setBackground(fondo);

		this.panelBtnNaves = new JPanel();
		this.panelBtn.add(this.panelBtnNaves);
		this.panelBtnNaves.setBackground(fondo);

		this.btnNaveConsu = new JButton("Agregar Nave Consumidora");
		this.panelBtnNaves.add(this.btnNaveConsu);
		this.btnNaveConsu.setFont(fuenteBold);
		this.btnNaveConsu.setForeground(celeste);
		this.btnNaveConsu.setBackground(fondoPanel);

		this.btnNaveGen = new JButton("Agregar Nave Generadora");
		this.panelBtnNaves.add(this.btnNaveGen);
		this.btnNaveGen.setFont(fuenteBold);
		this.btnNaveGen.setForeground(celeste);
		this.btnNaveGen.setBackground(fondoPanel);

		this.panelBtnVolver = new JPanel();
		this.panelBtn.add(this.panelBtnVolver);
		this.panelBtnVolver.setBackground(fondo);

		this.btnVolver = new JButton("Volver");
		this.panelBtnVolver.add(this.btnVolver);
		this.btnVolver.setFont(fuenteBold);
		this.btnVolver.setForeground(blanco);
		this.btnVolver.setBackground(fondoPanel);

		btnNaveConsu.setFocusPainted(false);
		btnNaveGen.setFocusPainted(false);
		btnVolver.setFocusPainted(false);

		this.panelTitulo = new JPanel();
		this.contentPane.add(this.panelTitulo, BorderLayout.NORTH);
		this.panelTitulo.setBackground(fondo);

		this.lblTitulo = new JLabel("Plataforma de Carga");
		this.panelTitulo.add(this.lblTitulo);
		this.lblTitulo.setFont(fuenteTitulo);
		this.lblTitulo.setForeground(celeste);
	}

}

