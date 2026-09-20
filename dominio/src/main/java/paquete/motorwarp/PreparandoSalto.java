package paquete.motorwarp;

import paquete.bitacora.EventoMotorWarp;

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
    public EventoMotorWarp prepararSalto(){

        return motor.registrarError();
    }

    @Override
    public EventoMotorWarp iniciarWarp(){
        return motor.cambiarEstado(new EnWarp(motor));
    }

    @Override
    public EventoMotorWarp finalizarWarp(){

        return motor.registrarError();
    }

    @Override
    public EventoMotorWarp finalizarEnfriamiento(){
        return motor.registrarError();
    }

}
