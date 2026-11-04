package modelo.motorwarp;

import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;
import modelo.mision.CostosMision;
import modelo.nave.Nave;

public class MotorWarp {
    private EstadoWarp estado;

    public MotorWarp(){
        this.estado = new Disponible(this);
    }

    public EstadoWarp getEstado() {
        return estado;
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

    public ResultadoSalto saltarWarp(boolean energiaSuficiente) {
        return estado.saltarWarp(energiaSuficiente);
    }

    @Override
    public String toString() {
        return estado.toString();
    }
}
