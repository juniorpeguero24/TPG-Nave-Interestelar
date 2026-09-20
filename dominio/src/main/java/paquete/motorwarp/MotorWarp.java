package paquete.motorwarp;

import paquete.bitacora.EventoMotorWarp;

public class MotorWarp {
    private EstadoWarp estado;

    public MotorWarp(){
        this.estado = new Disponible(this);
    }

    EventoMotorWarp cambiarEstado(EstadoWarp estado){
        this.estado = estado;
        return new EventoMotorWarp("Cambio de estado: " + estado.nombre());
    }

    public String getEstado(){
        return this.estado.nombre();
    }

    EventoMotorWarp registrarError(){
        return new EventoMotorWarp("Transicion inválida");
    }

    public EventoMotorWarp prepararSalto(){
        return this.estado.prepararSalto();
    }

    public EventoMotorWarp iniciarWarp(){
        return this.estado.iniciarWarp();
    }

    public EventoMotorWarp finalizarWarp(){
        return this.estado.finalizarWarp();
    }

    public EventoMotorWarp finalizarEnfriamiento(){
        return this.estado.finalizarEnfriamiento();
    }

}
