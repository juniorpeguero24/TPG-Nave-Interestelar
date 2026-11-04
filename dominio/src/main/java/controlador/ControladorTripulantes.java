package controlador;

import modelo.nave.AsistenteComando;
import modelo.tripulacion.Tripulante;
import vistas.VTripulantes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

public class ControladorTripulantes extends WindowAdapter implements ActionListener {

    private final VTripulantes vTripulantes;
    private final Navegador navegador;
    private final AsistenteComando asistenteComando;

    public ControladorTripulantes(AsistenteComando asistente, VTripulantes vTripulantes, Navegador nav) {
        this.asistenteComando = asistente;
        this.vTripulantes = vTripulantes;
        this.vTripulantes.setActionListener(this);
        this.navegador = nav;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equalsIgnoreCase("volver")) {
            this.navegador.volverAlControlDesdeTripulantes();
        }
    }

    public void setTablaTripulantes() {
        ArrayList<Tripulante> tripulantes = asistenteComando.getTripulantes();

        String nombre, cargo, origen;
        double salario;
        int antiguedad;

        for (Tripulante tripulante : tripulantes) {
            nombre = tripulante.getNombre();
            cargo = tripulante.getCargo().name();
            origen = tripulante.getPlanetaOrigen().name();
            antiguedad = tripulante.getAntiguedad();
            salario = tripulante.liquidarHaberes().calcularTotal();

            this.vTripulantes.setTablaTripulantes(new Object[]{nombre, cargo, origen, antiguedad, salario});
        }

    }
}
