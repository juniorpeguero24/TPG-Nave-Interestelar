package paquete.motorwarp;

import paquete.bitacora.Evento;

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
    public Evento prepararSalto(){

        return motor.registrarError();
    }

    @Override
    public Evento iniciarWarp(){
        return motor.registrarError();
    }

    @Override
    public Evento finalizarWarp(){
        return motor.cambiarEstado(new Enfriamiento(motor));
    }

    @Override
    public Evento finalizarEnfriamiento(){
       return motor.registrarError();
    }
}
