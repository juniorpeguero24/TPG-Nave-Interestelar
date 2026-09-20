package paquete.motorwarp;

import paquete.bitacora.EventoMotorWarp;

class Enfriamiento implements EstadoWarp {
    private final MotorWarp motor;

    Enfriamiento(MotorWarp motor) {
        this.motor = motor;
    }

    @Override
    public String nombre(){
        return "Enfriamiento";
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

        return motor.registrarError();
    }

    @Override
    public EventoMotorWarp finalizarEnfriamiento() {

        return motor.cambiarEstado(new Disponible(motor));
    }

}
