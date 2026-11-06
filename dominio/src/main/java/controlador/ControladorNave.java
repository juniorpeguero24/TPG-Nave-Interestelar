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
import java.util.concurrent.ExecutionException;

import modelo.mision.InformeMision;


public class ControladorNave extends WindowAdapter implements ActionListener
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
        this.controlNave.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            this.controlNave.setMisionEnCurso(true);

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

            ejecutarMisionenHilo(mision);

            this.controlNave.setMisionEnCurso(false);
    }


    /*La mision debe ejecutarse en un hilo nuevo para que el hilo grafico se libere
     y pueda seguir actualizando la vista paralelamente
     */

    private void ejecutarMisionenHilo(Mision mision) {
        Thread hiloMision = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // mision.realizarMision(asistenteComando);
                    InformeMision informe =
                            mision.realizarMision(asistenteComando);

                    System.out.println("Informe generado: " + informe);

                    System.out.println(
                            "Cantidad de informes: "
                            + asistenteComando.getHistorialInformes().size()
                    );
                } catch (NaveDestruidaExcepcion e) {
                    controlNave.mostrarMensaje(e.getMessage());
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
