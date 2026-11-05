package modelo.motorwarp;

public class MotorWarp {
    private EstadoWarp estado;

    public MotorWarp(){
        this.estado = new Disponible(this);
    }

    void cambiarEstado(EstadoWarp estado) {
        this.estado = estado;
    }

    ResultadoSalto resultado(TipoResultadoSalto tipo, int casilleros, String descripcion) {
        return new ResultadoSalto(tipo, casilleros, descripcion);
    }

    public ResultadoSalto saltar() {
        return estado.saltar();
    }

    public boolean puedeSaltarWarp() {
        return estado instanceof Disponible;
    }

    public ResultadoSalto saltarWarp(boolean energiaSuficiente) {
        return estado.saltarWarp(energiaSuficiente);
    }

    @Override
    public String toString() {
        return estado.toString();
    }
}
