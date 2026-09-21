package paquete.motorwarp;

import paquete.bitacora.Evento;
import paquete.bitacora.TipoEvento;

public class MotorWarp {
    private EstadoWarp estado;

    public MotorWarp(){
        this.estado = new Disponible(this);
    }

    Evento cambiarEstado(EstadoWarp estado){
        this.estado = estado;
        return new Evento(TipoEvento.MOTOR_WARP, "Cambio de estado a " + estado.nombre());
    }

    Evento registrarError(){
        return new Evento(TipoEvento.MOTOR_WARP, "Transición inválida");
    }

    public String getEstado(){
        return this.estado.nombre();
    }

    public Evento prepararSalto(){
        return this.estado.prepararSalto();
    }

    public Evento iniciarWarp(){
        return this.estado.iniciarWarp();
    }

    public Evento finalizarWarp(){
        return this.estado.finalizarWarp();
    }

    public Evento finalizarEnfriamiento(){
        return this.estado.finalizarEnfriamiento();
    }

}
