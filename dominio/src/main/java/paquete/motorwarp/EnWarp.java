package paquete.motorwarp;

import paquete.bitacora.EventoMotorWarp;

class EnWarp implements EstadoWarp {
    private final MotorWarp motor;

    EnWarp(MotorWarp motor){
        this.motor = motor;
    }

    @Override
    public String nombre(){
        return "En Warp";
    }

    @Override
    public EventoMotorWarp prepararSalto(){

        return motor.registrarError();
    }

    @Override
    public EventoMotorWarp iniciarWarp(){
        return motor.registrarError();
    }

    @Override
    public EventoMotorWarp finalizarWarp(){
        return motor.cambiarEstado(new Enfriamiento(motor));
    }

    @Override
    public EventoMotorWarp finalizarEnfriamiento(){
       return motor.registrarError();
    }
}
