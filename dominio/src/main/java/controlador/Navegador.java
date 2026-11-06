package controlador;

import modelo.bitacora.Bitacora;
import modelo.concurrencia.PlataformaDeCarga;
import modelo.nave.AsistenteComando;
import modelo.nave.Nave;
import modelo.universo.Universo;
import vistas.*;
import modelo.tablero.Tablero;
import modelo.tablero.Destino;

public class Navegador {
    private VAcceso acceso;
    private VCreacion creacion;
    private VControlNave control;
    private VTripulantes tripulantes;
    private VVisorRecondito visor;
    private Destino destino;
    private Tablero tablero;
    private AsistenteComando asistente;

    public Navegador(VAcceso acceso, Destino destino, Tablero tablero){
        this.acceso = acceso;
        this.creacion = null;
        this.control = null;
        this.tripulantes = null;
        this.visor = null;
        this.destino = destino;
        this.tablero = tablero;
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
}
