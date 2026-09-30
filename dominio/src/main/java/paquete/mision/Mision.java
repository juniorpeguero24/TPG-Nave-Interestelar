package paquete.mision;

import paquete.AsistenteComando;
import paquete.bitacora.Evento;
import paquete.bitacora.TipoEvento;
import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

public abstract class Mision {
    private final String nombre;
    private final int combustibleNecesario, energiaNecesaria, desgasteGenerado;
    protected ResultadoMision resultado;

    public Mision(String nombre, int combustibleNecesario, int energiaNecesaria, int desgasteGenerado) {
        this.nombre = nombre;
        this.combustibleNecesario = combustibleNecesario;
        this.energiaNecesaria = energiaNecesaria;
        this.desgasteGenerado = desgasteGenerado;
    }

    public final void realizarMision(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        preparar(asistente);
        ejecutar(asistente);
        evaluar(asistente);
        finalizar(asistente);
    }

    protected void preparar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        asistente.verificarDisponibilidadParaMision(combustibleNecesario,energiaNecesaria,desgasteGenerado);
        asistente.registrarEvento(new Evento(TipoEvento.MISION, "Iniciando preparativos de misión: " + nombre));
    }
    
    protected abstract void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion;

    protected abstract void evaluar(AsistenteComando asistente);

    protected void finalizar(AsistenteComando asistente) {
        if (this.resultado == ResultadoMision.EXITO) {
            asistente.ordenarSaltoWarp();
            asistente.registrarEvento(new Evento(TipoEvento.MISION, "Mision " + nombre + " completada con ÉXITO."));
        } else {
            asistente.registrarEvento(new Evento(TipoEvento.MISION, "Mision " + nombre + " finalizada con FALLO."));
        }
    }

    public String getNombre() {
        return nombre;
    }
    public int getCombustibleNecesario() {
        return combustibleNecesario;
    }
    public int getEnergiaNecesaria() {
        return energiaNecesaria;
    }
    public int getDesgasteGenerado() {
        return desgasteGenerado;
    }
}