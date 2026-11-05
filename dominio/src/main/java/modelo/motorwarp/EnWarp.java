package modelo.motorwarp;

class EnWarp implements EstadoWarp {
    private final MotorWarp motor;

    EnWarp(MotorWarp motor) {
        this.motor = motor;
    }

    @Override
    public ResultadoSalto saltar() {
        return motor.resultado(TipoResultadoSalto.INVALIDO, 0, "El motor esta listo para Warp y no puede realizar un salto normal");
    }

    @Override
    public ResultadoSalto saltarWarp(boolean energiaSuficiente) {
        if (!energiaSuficiente) {
            motor.cambiarEstado(new PreparandoSalto(motor, 0));
            return motor.resultado(TipoResultadoSalto.SIN_ENERGIA, 0, "El salto Warp fue abortado por energia insuficiente y el motor vuelve a preparacion");
        }
        motor.cambiarEstado(new Enfriamiento(motor));
        return motor.resultado(TipoResultadoSalto.SALTO_WARP, 2, "Salto Warp ejecutado");
    }

    @Override
    public String toString() {
        return "En Warp";
    }
}
