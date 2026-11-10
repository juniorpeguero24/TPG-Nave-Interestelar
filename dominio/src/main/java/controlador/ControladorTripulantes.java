package controlador;

import modelo.nave.AsistenteComando;
import modelo.tripulacion.Tripulante;
import modelo.tripulacion.Cargo;
import vistas.VTripulantes;
import modelo.excepciones.TripulanteInvalidoException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ControladorTripulantes extends WindowAdapter implements ActionListener {

    private final VTripulantes vTripulantes;
    private final Navegador navegador;
    private final AsistenteComando asistenteComando;

    public ControladorTripulantes(AsistenteComando asistente, VTripulantes vTripulantes, Navegador nav) {
        this.asistenteComando = asistente;
        this.vTripulantes = vTripulantes;
        this.vTripulantes.setActionListener(this);
        this.vTripulantes.addWindowListener(this);
        this.vTripulantes.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.navegador = nav;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equalsIgnoreCase("volver")) {
            this.navegador.volverAlControlDesdeTripulantes();
        }
        else if (comando.equalsIgnoreCase("Agregar Consejo")) {
            agregarConsejo();
        }
    }

    public void setTablaTripulantes() {
        vTripulantes.limpiarTablaTripulantes();
        ArrayList<Tripulante> tripulantes = asistenteComando.getTripulantes();

        String nombre, cargo, origen;
        double salario;
        int antiguedad;

        for (Tripulante tripulante : tripulantes) {
            nombre = tripulante.getNombre();
            cargo = tripulante.getCargo().name();
            if (tripulante.getCargo() == Cargo.CONSEJERO)
                cargo += " (" + tripulante.getCantidadConsejos() + " consejos)";
            origen = tripulante.getPlanetaOrigen().name();
            antiguedad = tripulante.getAntiguedad();
            salario = tripulante.liquidarHaberes().calcularTotal();

            this.vTripulantes.setTablaTripulantes(new Object[]{nombre, cargo, origen, antiguedad, salario});
        }

    }

    @Override
    public void windowClosing(WindowEvent e) {
        navegador.volverAlControlDesdeTripulantes();
    }
    
    private void agregarConsejo() {
        int fila = vTripulantes.getFilaSeleccionada();
        if (fila == -1) {
            vTripulantes.mostrarMensaje("Seleccione un tripulante");
            return;
        }
        ArrayList<Tripulante> tripulantes = asistenteComando.getTripulantes();
        Tripulante tripulante = tripulantes.get(fila);
        try {
            tripulante.registrarConsejo();
            setTablaTripulantes();
        } catch (TripulanteInvalidoException e) {
            vTripulantes.mostrarMensaje(e.getMessage());
        }
    }
}
