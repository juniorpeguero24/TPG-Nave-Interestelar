package modelo.motorwarp;

import modelo.bitacora.Evento;

class EnWarp implements EstadoWarp {
    private final MotorWarp motor;

    EnWarp(MotorWarp motor){
        this.motor = motor;
    }

    @Override
    public String toString(){
        return "En Warp";
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
       /* return motor.cambiarEstado(new Enfriamiento(motor));
        Por requerimiento R3 de la aclaración, vuelve a Disponible directamente */

       return motor.cambiarEstado(new Disponible(motor)); 
    }

}
