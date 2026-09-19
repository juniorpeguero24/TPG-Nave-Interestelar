package paquete.motorwarp;

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
    public void prepararSalto(){
        System.out.println("Preparando salto");
        motor.cambiarEstado(new PreparandoSalto(motor));
    }

    @Override
    public void iniciarWarp(){

        System.out.println("Transicion invalida");
    }

    @Override
    public void finalizarWarp(){

        System.out.println("Transicion invalida");
    }

    @Override
    public void finalizarEnfriamiento(){

        System.out.println("Transicion invalida");
    }

}
