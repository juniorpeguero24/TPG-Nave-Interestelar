package paquete.motorwarp;

class EnWarp implements EstadoWarp {

    EnWarp(){
    }

    public String nombre(){
        return "En Warp";
    }

    public void prepararSalto(MotorWarp motor){
        System.out.println("Transicion invalida");
    }

    public void iniciarWarp(MotorWarp motor){
        System.out.println("En Warp...");
    }

    public void finalizarWarp(MotorWarp motor){
        System.out.println("Enfriando motor");
        motor.cambiarEstado(new Enfriamiento());
    }

    public void finalizarEnfriamiento(MotorWarp motor){

        System.out.println("Transicion invalida");
    }
}
