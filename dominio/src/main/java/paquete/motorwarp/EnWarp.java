package paquete.motorwarp;

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
    public void prepararSalto(){
        System.out.println("Transicion invalida");
    }

    @Override
    public void iniciarWarp(){
        System.out.println("En Warp...");
    }

    @Override
    public void finalizarWarp(){
        System.out.println("Enfriando motor");
        motor.cambiarEstado(new Enfriamiento(motor));
    }

    @Override
    public void finalizarEnfriamiento(){

        System.out.println("Transicion invalida");
    }
}
