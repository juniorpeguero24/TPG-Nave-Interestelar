package paquete.motorwarp;

import paquete.bitacora.Evento;

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
    public Evento prepararSalto(){
        return motor.registrarError();
    }

    @Override
    public Evento iniciarWarp(){

        return motor.registrarError();
    }

    @Override
    public Evento finalizarWarp(){

        return motor.registrarError();
    }

    @Override
    public Evento finalizarEnfriamiento() {

        return motor.cambiarEstado(new Disponible(motor));
    }

}
