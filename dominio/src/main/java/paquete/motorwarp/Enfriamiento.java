package paquete.motorwarp;

class Enfriamiento implements EstadoWarp {
    Enfriamiento(){
    }

    public String nombre(){
        return "Enfriamiento";
    }

    public void prepararSalto(MotorWarp motor){

        System.out.println("Transicion invalida");
    }

    public void iniciarWarp(MotorWarp motor){

        System.out.println("Transicion invalida");
    }

    public void finalizarWarp(MotorWarp motor){
        System.out.println("Transicion invalida");
    }

    public void finalizarEnfriamiento(MotorWarp motor) {

        System.out.println("Enfriamiento finalizado");
        motor.cambiarEstado(new Disponible());
    }

}
