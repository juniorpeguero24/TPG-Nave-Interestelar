package controlador;

import modelo.mision.Mision;
import modelo.mision.Mision01;
import modelo.mision.Mision02;
import modelo.mision.Mision03;
import modelo.nave.AsistenteComando;
import modelo.universo.Universo;
import vistas.VControlNave;
import vistas.VTripulantes;
import vistas.VVisorRecondito;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;


public class ControladorNave extends WindowAdapter implements ActionListener
{
    private final VControlNave controlNave;
    private final AsistenteComando asistenteComando;
    private final Navegador navegador;

    public ControladorNave(Universo universo,
                           VControlNave controlNave, Navegador nav) {

        this.controlNave = controlNave;
        this.controlNave.setActionListener(this);
        this.controlNave.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.asistenteComando = universo.getAsistenteEnOperacion();
        this.navegador = nav;
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
    }

    public void setDatosNave() {
        String nombre = this.asistenteComando.getNombreNave();
        String tipo = this.asistenteComando.getTipo();
        String recursos = this.asistenteComando.obtenerEstadoNaveActual();
        controlNave.setDatosNave(nombre, tipo, recursos);
    }

    public void iniciarMision(){
        String NombreMision = this.controlNave.getMisionSeleccionada();

        Mision mision = null;

        if (NombreMision.equalsIgnoreCase("M-01 (Intercepción y Asistencia)")) {
             mision = new Mision01();
        }
        else if (NombreMision.equalsIgnoreCase("M-02 (Recolección)"))
        {
             mision = new Mision02();
        }
        else if (NombreMision.equalsIgnoreCase("M-03 (Retorno Seguro)")){
             mision = new Mision03();
        }

        mision.añadirObservador(this.controlNave);
        mision.realizarMision(this.asistenteComando);
    }

    private void vistaTripulantes() {
        navegador.mostrarTripulantes();
    }

    private void vistaVisorRecondito() {
        navegador.mostrarVisorRecondito();
    }

}
