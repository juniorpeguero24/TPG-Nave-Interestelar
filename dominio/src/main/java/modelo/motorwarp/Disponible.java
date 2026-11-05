package modelo.motorwarp;

class Disponible implements EstadoWarp {
    private final MotorWarp motor;

    Disponible(MotorWarp motor){
        this.motor = motor;
    }

    @Override
    public String toString(){
        return "Disponible";
    }

    @Override
    public ResultadoSalto saltar() {
        return motor.resultado(TipoResultadoSalto.SALTO_NORMAL, 1, "Salto normal ejecutado");
    }

    @Override
    public ResultadoSalto saltarWarp(boolean energiaSuficiente) {
        if (!energiaSuficiente) {
            return saltar();
        }
        motor.cambiarEstado(new Enfriamiento(motor));
        return motor.resultado(TipoResultadoSalto.SALTO_WARP, 2, "Salto Warp ejecutado");
    }
}
