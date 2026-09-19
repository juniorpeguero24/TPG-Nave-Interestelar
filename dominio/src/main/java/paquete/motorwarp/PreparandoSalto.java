package paquete.motorwarp;

class PreparandoSalto implements EstadoWarp {

    PreparandoSalto(){
    }

    public String nombre(){
        return "Preparando salto";
    }

    public void prepararSalto(MotorWarp motor){
        System.out.println("Salto en preparacion...");
    }

    public void iniciarWarp(MotorWarp motor){
        System.out.println("Iniciando Warp");
        motor.cambiarEstado(new EnWarp());
    }
    public void finalizarWarp(MotorWarp motor){

        System.out.println("Transicion invalida");
    }

    public void finalizarEnfriamiento(MotorWarp motor){

        System.out.println("Transicion invalida");
    }

}
