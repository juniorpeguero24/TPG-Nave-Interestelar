package paquete.motorwarp;

import paquete.bitacora.EventoMotorWarp;

class Disponible implements EstadoWarp {
    private final MotorWarp motor;

    Disponible(MotorWarp motor){
        this.motor = motor;
    }

    @Override
    public String nombre(){
        return "Disponible";
    }

    @Override
    public EventoMotorWarp prepararSalto(){
        return motor.cambiarEstado(new PreparandoSalto(motor));
    }

    @Override
    public EventoMotorWarp iniciarWarp(){

        return motor.registrarError();
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
