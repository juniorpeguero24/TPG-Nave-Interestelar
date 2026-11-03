package controlador;

import modelo.bitacora.Bitacora;
import modelo.concurrencia.PlataformaDeCarga;
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

    public Navegador(VAcceso acceso, VCreacion creacion, Destino destino, Tablero tablero){
        this.acceso = acceso;
        this.creacion = creacion;
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
        }
    }

    public void iniciarJuego(Universo universo) {
        if (control == null) {
            this.control = new VControlNave();
            ControladorNave controladorNave = new ControladorNave(universo, control, this, destino, tablero);

            Nave nave = universo.getAsistenteEnOperacion().getNave();
            Bitacora bitacora = universo.getAsistenteEnOperacion().getBitacora();

            nave.añadirObservador(control);
            bitacora.añadirObservador(control);

            controladorNave.setDatosNave();
            creacion.setVisible(false);
            control.setVisible(true);
        }
    }

    public void mostrarTripulantes() {
        if (tripulantes == null) {
            this.tripulantes = new VTripulantes();
            /*ControladorTripulantes = .... */
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
}
