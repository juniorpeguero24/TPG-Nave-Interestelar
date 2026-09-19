package paquete.motorwarp;

class Enfriamiento implements EstadoWarp {
    private final MotorWarp motor;

    Enfriamiento(MotorWarp motor) {
        this.motor = motor;
    }

    @Override
    public String nombre(){
        return "Enfriamiento";
    }

    @Override
    public void prepararSalto(){

        System.out.println("Transicion invalida");
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
    public void finalizarEnfriamiento() {

        System.out.println("Enfriamiento finalizado");
        motor.cambiarEstado(new Disponible(motor));
    }

}
