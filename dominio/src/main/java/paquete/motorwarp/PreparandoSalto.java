package paquete.motorwarp;

import paquete.bitacora.Evento;

class PreparandoSalto implements EstadoWarp {

    private final MotorWarp motor;

    PreparandoSalto(MotorWarp motor){
        this.motor = motor;
    }


    @Override
    public String toString(){
        return "Preparando salto";
    }

    @Override
    public Evento prepararSalto(){

        return motor.registrarError();
    }

    @Override
    public Evento saltar(){
        return motor.cambiarEstado(new EnWarp(motor));
    }

    @Override
    public Evento enfriar(){

        return motor.registrarError();
    }

}
