package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.universo.Posicion;

public class Mision01 extends Mision {
    private boolean asistenciaRealizada = false;
    
    public Mision01() {
        super("M-01 - Intercepcion y asistencia", new Posicion(7, 6));
    }

    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        super.ejecutar(asistente);

        agregarAccionPrincipal("Ejecutando maniobra de asistencia táctica");
        this.asistenciaRealizada=true;
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.asistenciaRealizada)
            this.resultado = ResultadoMision.EXITO;
    }
}
