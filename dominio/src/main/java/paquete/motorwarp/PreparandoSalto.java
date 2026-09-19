package paquete.motorwarp;

class PreparandoSalto implements EstadoWarp {

    private final MotorWarp motor;

    PreparandoSalto(MotorWarp motor){
        this.motor = motor;
    }


    @Override
    public String nombre(){
        return "Preparando salto";
    }

    @Override
    public void prepararSalto(){
        System.out.println("Salto en preparacion...");
    }

    @Override
    public void iniciarWarp(){
        System.out.println("Iniciando Warp");
        motor.cambiarEstado(new EnWarp(motor));
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
