package paquete.motorwarp;

import paquete.bitacora.Evento;

class Disponible implements EstadoWarp {
    private final MotorWarp motor;

    Disponible(MotorWarp motor){
        this.motor = motor;
    }

    @Override
    public String toString(){
        return "Disponible";
    }

    @Override
    public Evento prepararSalto(){
        return motor.cambiarEstado(new PreparandoSalto(motor));
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
