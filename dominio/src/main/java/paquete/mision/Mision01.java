package paquete.mision;

import paquete.AsistenteComando;
import paquete.bitacora.Evento;
import paquete.bitacora.TipoEvento;
import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

public class Mision01 extends Mision {
    private boolean asistenciaRealizada = false;
    
    public Mision01() {
        super("M-01 - Intercepcion y asistencia", 4, 5, 4);
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

        agregarAccionPrincipal("Ejecutando maniobra de asistencia táctica");
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
