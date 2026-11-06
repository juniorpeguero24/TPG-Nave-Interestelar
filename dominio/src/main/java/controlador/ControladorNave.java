package controlador;

import modelo.excepciones.NaveDestruidaExcepcion;
import modelo.mision.Mision;
import modelo.mision.Mision01;
import modelo.mision.Mision02;
import modelo.mision.Mision03;
import modelo.tablero.Destino;
import modelo.tablero.Tablero;
import modelo.nave.AsistenteComando;
import modelo.universo.Universo;
import vistas.VControlNave;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;


public class ControladorNave extends WindowAdapter implements ActionListener
{
    private final VControlNave controlNave;
    private final AsistenteComando asistenteComando;
    private final Navegador navegador;
    private Destino destino;
    private Tablero tablero;
    private boolean misionEnCurso = false;

    public ControladorNave(Universo universo, VControlNave controlNave, Navegador nav, Destino destino, Tablero tablero) {

        this.controlNave = controlNave;
        this.controlNave.setActionListener(this);
        this.controlNave.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.asistenteComando = universo.getAsistenteEnOperacion();
        this.navegador = nav;
        this.destino = destino;
        this.tablero = tablero;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if  (comando.equalsIgnoreCase("Tripulantes")) {
            vistaTripulantes();
        }
        else if  (comando.equalsIgnoreCase("Visor")) {
            vistaVisorRecondito();
        }
        else if  (comando.equalsIgnoreCase("Iniciar Mision")) {
            iniciarMision();
        }
        else if (comando.equalsIgnoreCase("Volver Creacion")) {
            navegador.volverACreacion();
        }
    }

    public void configuracionInicial() {
        String nombre = this.asistenteComando.getNombreNave();
        String tipo = this.asistenteComando.getTipo();
        String recursos = this.asistenteComando.obtenerEstadoNaveActual();
        controlNave.setDatosNave(nombre, tipo, recursos);
        controlNave.setTablero();
    }

    public void iniciarMision() {

        try {
            String NombreMision = this.controlNave.getMisionSeleccionada();

            Mision mision = null;

            if (NombreMision.equalsIgnoreCase("M-01 (Intercepción y Asistencia)")) {
                mision = new Mision01(destino, tablero);
            } else if (NombreMision.equalsIgnoreCase("M-02 (Recolección)")) {
                mision = new Mision02(destino, tablero);
            } else if (NombreMision.equalsIgnoreCase("M-03 (Retorno Seguro)")) {
                mision = new Mision03(destino, tablero);
            }

            assert mision != null;
            mision.agregarObservador(this.controlNave);
            mision.realizarMision(this.asistenteComando);
        }
        catch (NaveDestruidaExcepcion ex) {
            this.controlNave.mostrarMensaje(ex.getMessage());
            this.controlNave.desactivarMision();
        }

    }

    private void vistaTripulantes() {
        navegador.mostrarTripulantes(this.asistenteComando);
    }

    private void vistaVisorRecondito() {
        navegador.mostrarVisorRecondito();
    }

}
