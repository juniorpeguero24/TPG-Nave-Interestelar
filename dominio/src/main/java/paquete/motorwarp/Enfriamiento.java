package paquete.motorwarp;

import paquete.bitacora.Evento;

class Enfriamiento implements EstadoWarp {
    private final MotorWarp motor;

    Enfriamiento(MotorWarp motor) {
        this.motor = motor;
    }

    @Override
    public String toString(){
        return "Enfriamiento";
    }

    @Override
    public Evento prepararSalto(){
        return motor.registrarError();
    }

    @Override
    public Evento saltar(){

        return motor.registrarError();
    }

    @Override
    public Evento enfriar(){

        return motor.registrarError();
    }

}
