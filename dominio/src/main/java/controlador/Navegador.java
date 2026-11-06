package controlador;

import modelo.bitacora.Bitacora;
import modelo.concurrencia.PlataformaDeCarga;
import modelo.excepciones.CantidadDeElementosSuperaCasillaExcepcion;
import modelo.excepciones.DimensionesDeTableroInconsistentesExcepcion;
import modelo.excepciones.NaveInvalidaException;
import modelo.nave.AsistenteComando;
import modelo.nave.Nave;
import modelo.nave.NaveFactory;
import modelo.universo.Asteroide;
import modelo.universo.ElementoDelUniverso;
import modelo.universo.EstacionCarga;
import modelo.universo.Universo;
import vistas.*;
import modelo.tablero.Tablero;
import modelo.tablero.Destino;

import java.util.ArrayList;

public class Navegador {
    private VAcceso acceso;
    private VCreacion creacion;
    private VControlNave control;
    private VTripulantes tripulantes;
    private VVisorRecondito visor;
    private Destino destino;
    private Tablero tablero;
    private AsistenteComando asistente;

    public Navegador(VAcceso acceso){
        this.acceso = acceso;
        this.creacion = null;
        this.control = null;
        this.tripulantes = null;
        this.visor = null;
    }


    public void mostrarCreacion(Universo universo) {
        if  (this.creacion == null) {
            this.creacion = new VCreacion();
            ControladorCreacion controladorCreacion = new ControladorCreacion(universo, creacion, this);

            acceso.setVisible(false);
            creacion.setVisible(true);
        }
        acceso.setVisible(false);
        creacion.setVisible(true);
    }

    public void iniciarJuego(Universo universo) {
        asistente = universo.getAsistenteEnOperacion();

        if (control == null) {
            asistente = universo.getAsistenteEnOperacion();

            crearTablero();

            this.control = new VControlNave(tablero, asistente);
            ControladorNave controladorNave = new ControladorNave(universo, control, this, destino, tablero);

            controladorNave.configuracionInicial();

            creacion.setVisible(false);
            control.setVisible(true);
        }
        else {
            creacion.setVisible(false);
            control.setVisible(true);
        }
    }

    public void mostrarTripulantes(AsistenteComando asistenteComando) {
        if (tripulantes == null) {
            this.tripulantes = new VTripulantes();
            ControladorTripulantes controladorTripulantes = new ControladorTripulantes(asistenteComando, tripulantes, this);

            controladorTripulantes.setTablaTripulantes();

            control.setVisible(false);
            tripulantes.setVisible(true);
        } else {
            control.setVisible(false);
            tripulantes.setVisible(true);
        }
    }

    public void volverAlControlDesdeTripulantes() {
        tripulantes.setVisible(false);
        control.setVisible(true);
    }

    public void mostrarVisorRecondito() {
        if (visor == null) {
            this.visor = new VVisorRecondito();
            PlataformaDeCarga plataforma = new  PlataformaDeCarga();
            plataforma.añadirObservador(visor);
            ControladorPlataforma controladorPlataforma = new ControladorPlataforma(plataforma, visor, this);

            control.setVisible(false);
            visor.setVisible(true);

        } else {
            control.setVisible(false);
            visor.setVisible(true);
        }
    }

    public void volverAlControlDesdeVisorRecondito() {
        visor.setVisible(false);
        control.setVisible(true);
    }

    public void volverACreacion(Universo universo) {
        this.control.setVisible(false);

        this.creacion = null;
        this.creacion = new VCreacion();

        ControladorCreacion controladorCreacion = new ControladorCreacion(universo, creacion, this);
        this.creacion.setVisible(true);

        this.control = null;
        this.tripulantes = null;
        this.visor = null;
    }

    private void crearTablero(){
        // Creacion del tablero
        int cantFilas = 10;
        int cantColumnas = 10;
        ArrayList<ElementoDelUniverso> elementos = new ArrayList<>();

        elementos.add(new Asteroide(5));
        elementos.add(new Asteroide(7));
        elementos.add(new Asteroide(2));
        elementos.add(new Asteroide(5));
        elementos.add(new EstacionCarga(5, 5));
        elementos.add(new EstacionCarga(5, 3));
        elementos.add(new EstacionCarga(5, 5));
        elementos.add(new EstacionCarga(5, 3));
        elementos.add(new EstacionCarga(5, 5));
        elementos.add(new EstacionCarga(5, 3));
        elementos.add(new EstacionCarga(5, 5));
        elementos.add(new EstacionCarga(5, 3));
        try {
            elementos.add(NaveFactory.crearNave("Enemiga 1", "exploradora"));
            elementos.add(NaveFactory.crearNave("Enemiga 2", "combate"));
            elementos.add(NaveFactory.crearNave("Enemiga 3", "carguero"));
            elementos.add(NaveFactory.crearNave("Enemiga 1", "exploradora"));
            elementos.add(NaveFactory.crearNave("Enemiga 2", "combate"));
            elementos.add(NaveFactory.crearNave("Enemiga 3", "carguero"));
            elementos.add(NaveFactory.crearNave("Enemiga 1", "exploradora"));
            elementos.add(NaveFactory.crearNave("Enemiga 2", "combate"));
            elementos.add(NaveFactory.crearNave("Enemiga 3", "carguero"));
        }
        catch (NaveInvalidaException e){
        }

        Tablero tablero;
        try {
            tablero = new Tablero (cantFilas, cantColumnas, elementos);
        }
        catch(DimensionesDeTableroInconsistentesExcepcion | CantidadDeElementosSuperaCasillaExcepcion e) {
            System.out.println("Error al crear el tablero: " + e.getMessage());
            return;
        }
        Destino destino = new Destino(tablero);

        this.tablero = tablero;
        this.destino = destino;
    }
}
