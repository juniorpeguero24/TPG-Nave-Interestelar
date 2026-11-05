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
        int turnosCompletados = energiaSuficiente ? 1 : 0;
        motor.cambiarEstado(new PreparandoSalto(motor, turnosCompletados));
        if (!energiaSuficiente) {
            return motor.resultado(TipoResultadoSalto.SIN_ENERGIA, 0, "El motor entra en preparacion, pero no puede avanzar por energia insuficiente");
        }
        return motor.resultado(TipoResultadoSalto.PREPARANDO, 0, "Primer turno de preparacion del salto Warp completado");
    }
}
