package controlador;

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
    private final Universo universo;
    private final AsistenteComando asistenteComando;
    private final VTripulantes vTripulantes;
    private final VVisorRecondito vVisor;

    public ControladorNave(Universo universo,
                           VControlNave controlNave, VTripulantes vTripulantes, VVisorRecondito vVisor) {

        this.controlNave = controlNave;
        this.controlNave.setActionListener(this);
        this.controlNave.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.universo = universo;
        this.vTripulantes = vTripulantes;
        this.vVisor = vVisor;
        this.asistenteComando = universo.getAsistenteEnOperacion();
        setDatosNave();
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
    }

    private void setDatosNave() {
        String nombre = this.asistenteComando.getNombreNave();
        String tipo = this.asistenteComando.getTipo();
        String recursos = this.asistenteComando.obtenerEstadoNaveActual();
        controlNave.setDatosNave(nombre, tipo, recursos);
    }


    private void vistaTripulantes() {
        this.controlNave.setVisible(false);
        this.vTripulantes.setVisible(true);
    }

    private void vistaVisorRecondito() {
        this.controlNave.setVisible(false);
        this.vVisor.setVisible(true);
    }

}
