package modelo.motorwarp;

class Enfriamiento implements EstadoWarp {
    private final MotorWarp motor;

    Enfriamiento(MotorWarp motor) {
        this.motor = motor;
    }

    private ResultadoSalto completarEnfriamiento() {
        motor.cambiarEstado(new Disponible(motor));
        return motor.resultado(TipoResultadoSalto.ENFRIAMIENTO, 0, "Enfriamiento completado. Motor nuevamente disponible");
    }

    @Override
    public ResultadoSalto saltar() {
        return completarEnfriamiento();
    }

    @Override
    public ResultadoSalto saltarWarp(boolean energiaSuficiente) {
        return completarEnfriamiento();
    }

    @Override
    public String toString() {
        return "Enfriamiento";
    }
}