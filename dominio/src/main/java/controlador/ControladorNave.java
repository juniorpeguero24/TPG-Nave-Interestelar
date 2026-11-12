package controlador;

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

import modelo.mision.InformeMision;


public class ControladorNave implements ActionListener
{
    private final VControlNave controlNave;
    private final AsistenteComando asistenteComando;
    private final Navegador navegador;
    private Destino destino;
    private Tablero tablero;
    private Universo universo;

    public ControladorNave(Universo universo, VControlNave controlNave, Navegador nav, Destino destino, Tablero tablero) {
        this.controlNave = controlNave;
        this.controlNave.setActionListener(this);
        this.universo = universo;
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
            volverACreacion();
        }
    }

    public void configuracionInicial() {
        String nombre = this.asistenteComando.getNombreNave();
        String tipo = this.asistenteComando.getTipo();
        String recursos = this.asistenteComando.obtenerEstadoNaveActual();
        controlNave.setDatosNave(nombre, tipo, recursos);
        controlNave.setTablero();
    }

    public void iniciarMision()  {
        String nombreMision = this.controlNave.getMisionSeleccionada();

        Mision mision = null;
        if (nombreMision.equalsIgnoreCase("M-01 (Intercepción y Asistencia)")) {
            mision = new Mision01(destino, tablero);
        } else if (nombreMision.equalsIgnoreCase("M-02 (Recolección)")) {
            mision = new Mision02(destino, tablero);
        } else if (nombreMision.equalsIgnoreCase("M-03 (Retorno Seguro)")) {
            mision = new Mision03(destino, tablero);
        } else {
            throw new IllegalArgumentException("Misión seleccionada desconocida: " + nombreMision);
        }

        controlNave.setMisionEnCurso(true);
        ejecutarMisionenHilo(mision);
    }


    /*La mision debe ejecutarse en un hilo nuevo para que el hilo grafico se libere
     y pueda seguir actualizando la vista paralelamente
     */

    private void ejecutarMisionenHilo(Mision mision) {
        Thread hiloMision = new Thread(new Runnable() {
            @Override
            public void run() {

                    // mision.realizarMision(asistenteComando);
                    InformeMision informe = mision.realizarMision(asistenteComando);
                    controlNave.setMisionEnCurso(false);
                    
                    if (asistenteComando.naveDestruida())
                    {
                        controlNave.desactivarMision();
                        controlNave.mostrarMensaje("NAVE DESTRUIDA");
                    }
                    
            }
        });

        hiloMision.start();
    }

    private void vistaTripulantes() {
        navegador.mostrarTripulantes(this.asistenteComando);
    }

    private void vistaVisorRecondito() {
        navegador.mostrarVisorRecondito();
    }

    private void volverACreacion() {
        navegador.volverACreacion(universo);
    }
}
