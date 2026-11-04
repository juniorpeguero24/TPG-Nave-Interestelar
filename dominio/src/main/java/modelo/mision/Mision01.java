package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.tablero.Destino;
import modelo.tablero.Tablero;


public class Mision01 extends Mision {
    private boolean asistenciaRealizada = false;
    
    public Mision01(Destino destino, Tablero tablero) {
        super("M-01 - Intercepcion y asistencia", destino, tablero);
    }

    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion, InterruptedException {
        super.ejecutar(asistente);
        if (llegoAlObjetivo(asistente)) {
            agregarAccionPrincipal("Ejecutando maniobra de asistencia táctica");
            this.asistenciaRealizada=true;
        }
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.asistenciaRealizada)
            this.resultado = ResultadoMision.EXITO;
    }
}
