package paquete.motorwarp;

class Disponible implements EstadoWarp {

    Disponible(){

    }

    public String nombre(){
        return "Disponible";
    }

    public void prepararSalto(MotorWarp motor){
        System.out.println("Preparando salto");
        motor.cambiarEstado(new PreparandoSalto());
    }

    public void iniciarWarp(MotorWarp motor){

        System.out.println("Transicion invalida");
    }
    public void finalizarWarp(MotorWarp motor){

        System.out.println("Transicion invalida");
    }

    public void finalizarEnfriamiento(MotorWarp motor){

        System.out.println("Transicion invalida");
    }

}
