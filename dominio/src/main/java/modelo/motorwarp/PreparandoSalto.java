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
        turnosCompletados++;
        if (turnosCompletados >= 2) {
            motor.cambiarEstado(new Disponible(motor));
        }
        return motor.resultado(TipoResultadoSalto.SALTO_NORMAL, 1, "Salto normal durante la preparacion Warp");
    }

    @Override
    public ResultadoSalto saltarWarp(boolean energiaSuficiente) {
        return saltar();
    }

    @Override
    public String toString() {
        return "Preparando salto (" + turnosCompletados + "/2)";
    }
}
