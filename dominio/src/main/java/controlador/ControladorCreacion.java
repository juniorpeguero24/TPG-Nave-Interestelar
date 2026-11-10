package controlador;

import modelo.excepciones.CapitanExistenteException;
import modelo.excepciones.NaveInvalidaException;
import modelo.excepciones.TripulanteInvalidoException;
import modelo.excepciones.TripulanteRepetidoException;
import modelo.nave.AsistenteComando;
import modelo.nave.Nave;
import modelo.nave.NaveFactory;
import modelo.tripulacion.Cargo;
import modelo.tripulacion.Origen;
import modelo.tripulacion.Tripulante;
import modelo.universo.Universo;
import vistas.VCreacion;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControladorCreacion implements ActionListener
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
        else if (comando.equalsIgnoreCase("asignar a nave")) {
            asignarTripulante();
        }
        else if (comando.equalsIgnoreCase("agregar consejo")) {
            agregarConsejo();
        }

    }

    private void crearNave(){

        try {
            String nombre = this.vCreacion.getNombreNave();
            String tipo = this.vCreacion.getTipoNave();

            Nave nave = NaveFactory.crearNave(nombre, tipo);

            if (nave != null) {

                this.asistenteComando = new AsistenteComando(nave);

                universo.registrarAsistente(this.asistenteComando);
                universo.seleccionarNave(nombre);

                this.vCreacion.setDatosNaveCreada(nombre, tipo);
                this.vCreacion.habilitaAsignarTripulante();
            }
        }
        catch (NaveInvalidaException e){
            this.vCreacion.mostrarMensaje(e.getMessage());
        }
    }

    private void crearTripulante() {

        try{
           String nombre = this.vCreacion.getNombreTripulante();
           int antiguedad = this.vCreacion.getAntiguedadTripulante();
           Origen origen = Origen.valueOf(this.vCreacion.getOrigenTripulante());
           Cargo cargo = Cargo.valueOf(this.vCreacion.getCargoTripulante());

           Tripulante tripulante = new Tripulante(antiguedad, nombre, cargo, origen);

           universo.agregarTripulanteDisponible(tripulante);
           actualizarListaDisponibles();
       }

        catch (NumberFormatException e)  {
            this.vCreacion.mostrarMensaje("Antiguedad invalida");
        }
        catch (TripulanteInvalidoException | TripulanteRepetidoException e) {
           this.vCreacion.mostrarMensaje(e.getMessage());
        }


    }

    private void asignarTripulante(){
        Tripulante tripulante = this.vCreacion.getTripulanteSeleccionado();
        try {
            asistenteComando.agregarTripulante(tripulante);

            universo.quitarTripulanteDisponible(tripulante);
            actualizarListaDisponibles();

            if (asistenteComando.tieneTripulacionValida())
                this.vCreacion.habilitaIniciarJuego();
        }
        catch(CapitanExistenteException | TripulanteRepetidoException | TripulanteInvalidoException e){
            this.vCreacion.mostrarMensaje(e.getMessage());
        }
    }

    public void actualizarListaDisponibles(){
        ArrayList<Tripulante> tripulantes = universo.getTripulanteDisponibles();
        this.vCreacion.actualizarListaDisponibles(tripulantes);
    }

    private void agregarConsejo(){
        Tripulante tripulante = this.vCreacion.getTripulanteSeleccionado();
        try {
           tripulante.registrarConsejo();

           actualizarListaDisponibles();
        }
        catch (TripulanteInvalidoException e) {
            this.vCreacion.mostrarMensaje(e.getMessage());
        }
    }

    private void iniciarJuego(){
        navegador.iniciarJuego(universo);
    }

}
