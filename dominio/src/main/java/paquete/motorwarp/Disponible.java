package paquete.motorwarp;

import paquete.bitacora.Evento;

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
    public Evento prepararSalto(){
        return motor.cambiarEstado(new PreparandoSalto(motor));
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
    public Evento finalizarEnfriamiento(){
        return motor.registrarError();
    }

}
