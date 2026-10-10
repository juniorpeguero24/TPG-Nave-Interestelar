package modelo.mision;

import modelo.AsistenteComando;
import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

public class Mision02 extends Mision {
    private boolean elementoObtenido = false;
    
    public Mision02() {
        super( "M-02 - Recoleccion", 4, 5, 4);
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

        agregarAccionPrincipal("Ejecutando maniobra de recolección");
        this.elementoObtenido = true;
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.elementoObtenido)
            this.resultado = ResultadoMision.EXITO;
    }
}
