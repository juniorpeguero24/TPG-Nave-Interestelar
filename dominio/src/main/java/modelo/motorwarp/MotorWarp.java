package modelo.motorwarp;

import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;

public class MotorWarp {
    private EstadoWarp estado;

    public MotorWarp(){
        this.estado = new Disponible(this);
    }

    Evento cambiarEstado(EstadoWarp estado){
        this.estado = estado;
        return new Evento(TipoEvento.MOTOR_WARP, "Cambio de estado a " + estado);
    }

    Evento registrarError(){
        return new Evento(TipoEvento.MOTOR_WARP, "Transición inválida");
    }

    public String getEstado(){
        return this.estado.toString();
    }

    public Evento prepararSalto(){
        return this.estado.prepararSalto();
    }

    public Evento saltar(){
        return this.estado.saltar();
    }

    public Evento enfriar(){
        return this.estado.enfriar();
    }

}
