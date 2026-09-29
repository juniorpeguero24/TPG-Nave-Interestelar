package paquete;

import paquete.bitacora.Evento;
import paquete.bitacora.TipoEvento;

import paquete.excepciones.OperacionRecursoInvalidaExcepcion;

public class AsistenteComando {
    private final Nave nave;

    public AsistenteComando(Nave nave){
        this.nave = nave;
    }


    // ----- BITACORA ----- //
    
    private void registrarEvento(Evento evento){
        nave.registrarEvento(evento);
    }

    public String generarInformeBitacora() {
        return nave.generarInformeBitacora();
    }


    // ----- MOTOR WARP ----- //

    public void prepararSalto(){
        Evento evento = nave.prepararSalto();
        this.registrarEvento(evento);
    }

    public void iniciarWarp(){
        Evento evento = nave.iniciarWarp();
        this.registrarEvento(evento);
    }

    public void finalizarWarp(){
        Evento evento = nave.finalizarWarp();
        this.registrarEvento(evento);
    }

    public void finalizarEnfriamiento(){
        Evento evento = nave.finalizarEnfriamiento();
        this.registrarEvento(evento);
    }

    // ----- RECURSOS ----- //
    
    public void cargarCombustible(int cantidad) {
        try {
            nave.cargarCombustible(cantidad);
            this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se cargaron " + cantidad + " unidades de combustible"));

        } catch (OperacionRecursoInvalidaExcepcion e) {
            this.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
        }
    }
    
    public void cargarEnergia(int cantidad) {
        try {
            nave.cargarEnergia(cantidad);
            this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se cargaron " + cantidad + " unidades de energia"));

        } catch (OperacionRecursoInvalidaExcepcion e) {
            this.registrarEvento(new Evento(TipoEvento.ERROR, e.getMessage()));
        }
    }
    
    public void realizarMantenimiento() {
        nave.realizarMantenimiento();
        this.registrarEvento(new Evento(TipoEvento.RECURSOS, "Se realizo el mantenimiento de la nave"));
    }
}
