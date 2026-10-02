package modelo.mision;

import modelo.AsistenteComando;

import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

public class Mision03 extends Mision {
    private boolean regresoCompletado = false;
    
    public Mision03() {
        super( "M-03 - Retorno seguro", 4, 0, 4);
    }


    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        asistente.consumirCombustible(getCombustibleNecesario());
        asistente.consumirEnergia(getEnergiaNecesaria());
        asistente.aumentarDesgaste(getDesgasteGenerado());

        asistente.registrarEvento(new Evento(TipoEvento.MISION,"Ejecutando maniobra de retorno..."));
        this.regresoCompletado = true;
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.regresoCompletado)
            this.resultado = ResultadoMision.EXITO;
        else
            this.resultado = ResultadoMision.FALLO;
    }
}
