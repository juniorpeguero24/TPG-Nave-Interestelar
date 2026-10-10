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
        int combustibleNecesario = getCombustibleNecesario();
        asistente.consumirCombustible(combustibleNecesario);
        setCombustibleConsumido(combustibleNecesario);

        int energiaNecesaria = getEnergiaNecesaria();
        asistente.consumirEnergia(energiaNecesaria);
        setEnergiaConsumida(energiaNecesaria);

        int desgasteGenerado = getDesgasteNecesario();
        asistente.aumentarDesgaste(desgasteGenerado);
        setDesgasteGenerado(desgasteGenerado);

        agregarAccionPrincipal("Ejecutando maniobra de retorno");
        this.regresoCompletado = true;
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.regresoCompletado)
            this.resultado = ResultadoMision.EXITO;
    }
}
