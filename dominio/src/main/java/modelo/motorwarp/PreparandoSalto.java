package modelo.motorwarp;

class PreparandoSalto implements EstadoWarp {
    private final MotorWarp motor;
    private int turnosCompletados;

    PreparandoSalto(MotorWarp motor, int turnosCompletados) {
        this.motor = motor;
        this.turnosCompletados = turnosCompletados;
    }

    @Override
    public ResultadoSalto saltar() {
        return motor.resultado(TipoResultadoSalto.INVALIDO, 0, "No se puede realizar un salto normal mientras el motor prepara un salto Warp");
    }

    @Override
    public ResultadoSalto saltarWarp(boolean energiaSuficiente) {
        if (!energiaSuficiente) {
            return motor.resultado(TipoResultadoSalto.SIN_ENERGIA, 0, "La preparacion del salto Warp no puede continuar por energia insuficiente");
        }
        turnosCompletados++;
        if (turnosCompletados >= 2) {
            motor.cambiarEstado(new EnWarp(motor));
            return motor.resultado(TipoResultadoSalto.WARP_LISTO, 0, "Preparacion completada. El motor esta listo para ejecutar el salto Warp");
        }
        return motor.resultado(TipoResultadoSalto.PREPARANDO, 0, "Continua la preparacion del salto Warp");
    }

    @Override
    public String toString() {
        return "Preparando salto";
    }
}
