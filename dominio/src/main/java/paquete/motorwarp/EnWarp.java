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
        // return motor.cambiarEstado(new Enfriamiento(motor));
        return motor.cambiarEstado(new Disponible(motor));
    }

    // Por requerimiento R3 de la aclaración, vuelve a Disponible directamente
    /*
    @Override
    public Evento finalizarWarp(){
        return motor.cambiarEstado(new Disponible(motor));
    }
    */

    @Override
    public Evento finalizarEnfriamiento(){
       return motor.registrarError();
    }
}
