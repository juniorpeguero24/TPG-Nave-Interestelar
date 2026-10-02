package modelo.mision;

import modelo.AsistenteComando;
import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

public class Mision01 extends Mision {
    private boolean asistenciaRealizada = false;
    
    public Mision01() {
        super("M-01 - Intercepcion y asistencia", 4, 5, 4);
    }

    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        asistente.consumirCombustible(getCombustibleNecesario());
        asistente.consumirCombustible(getEnergiaNecesaria());
        asistente.aumentarDesgaste(getDesgasteGenerado());

        asistente.registrarEvento(new Evento(TipoEvento.MISION,"Ejecutando maniobra de asistencia táctica..."));
        this.asistenciaRealizada=true;
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.asistenciaRealizada)
            this.resultado = ResultadoMision.EXITO;
        else
            this.resultado = ResultadoMision.FALLO;
    }
}
