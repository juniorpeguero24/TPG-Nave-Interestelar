package paquete.motorwarp;

import paquete.bitacora.Evento;

class PreparandoSalto implements EstadoWarp {

    private final MotorWarp motor;

    PreparandoSalto(MotorWarp motor){
        this.motor = motor;
    }


    @Override
    public String nombre(){
        return "Preparando salto";
    }

    @Override
    public Evento prepararSalto(){

        return motor.registrarError();
    }

    @Override
    public Evento iniciarWarp(){
        return motor.cambiarEstado(new EnWarp(motor));
    }

    @Override
    public Evento finalizarWarp(){

        return motor.registrarError();
    }

    @Override
    public Evento finalizarEnfriamiento(){
        return motor.registrarError();
    }

}
