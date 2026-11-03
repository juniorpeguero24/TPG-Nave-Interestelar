package controlador;

import modelo.nave.AsistenteComando;
import modelo.nave.Nave;
import modelo.nave.NaveFactory;
import modelo.tripulacion.Cargo;
import modelo.tripulacion.Origen;
import modelo.tripulacion.Tripulante;
import modelo.universo.Posicion;
import modelo.universo.Universo;
import vistas.VControlNave;
import vistas.VCreacion;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public class ControladorCreacion extends WindowAdapter implements ActionListener
{
    private final Universo universo;
    private final VCreacion vCreacion;
    private AsistenteComando asistenteComando;
    private final Navegador navegador;

    public ControladorCreacion(Universo universo, VCreacion vCreacion, Navegador nav) {
        this.universo = universo;
        this.vCreacion = vCreacion;
        this.vCreacion.setActionListener(this);
        this.navegador = nav;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if  (comando.equalsIgnoreCase("crear nave")) {
            crearNave();
        }
        else if  (comando.equalsIgnoreCase("crear tripulante")) {
            crearTripulante();
        }
        else if  (comando.equalsIgnoreCase("iniciar")) {
            iniciarJuego();
        }
     /*   else if (comando.equalsIgnoreCase("asignar")) {
            asignarTripulante();
        }*/
    }

    private void crearNave(){
        String nombre = this.vCreacion.getNombreNave();
        String tipo = this.vCreacion.getTipoNave();

        Nave nave = NaveFactory.crearNave(nombre, tipo, new Posicion(0,0));

        if (nave!=null) {

            this.asistenteComando = new AsistenteComando(nave);

            universo.registrarAsistente(this.asistenteComando);
            universo.seleccionarNave(nombre);

            this.vCreacion.setDatosNaveCreada(nombre, tipo);
        }

    }


    private void crearTripulante() {
        String nombre = this.vCreacion.getNombreTripulante();
        Origen origen = Origen.valueOf(this.vCreacion.getOrigenTripulante());
        int antiguedad = this.vCreacion.getAntiguedadTripulante();
        Cargo cargo =  Cargo.valueOf(this.vCreacion.getCargoTripulante());

        Tripulante tripulante = new Tripulante(antiguedad,nombre, cargo,origen);

        asistenteComando.agregarTripulante(tripulante);
        /* falta agregarlo en algun arraylist del modelo y actualizar la lista de la vista */
    }

    /*private void asignarTripulante(){
        Tripulante tripulante = this.vCreacion.getTripulanteSeleccionado();
        asistenteComando.agregarTripulante(tripulante);
    }*/

    private void iniciarJuego(){
        AsistenteComando asistente = universo.getAsistenteEnOperacion();

        navegador.iniciarJuego(universo);
    }

}
