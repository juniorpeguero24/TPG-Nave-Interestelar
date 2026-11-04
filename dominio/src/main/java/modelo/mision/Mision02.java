package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.tablero.Destino;
import modelo.tablero.Tablero;

public class Mision02 extends Mision {
    private boolean elementoObtenido = false;
    
    public Mision02(Destino destino, Tablero tablero) {
        super( "M-02 - Recoleccion", destino, tablero);
    }

    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        super.ejecutar(asistente);
        if (llegoAlObjetivo(asistente)) {
            agregarAccionPrincipal("Ejecutando maniobra de recolección");
            this.elementoObtenido = true;
        }
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.elementoObtenido)
            this.resultado = ResultadoMision.EXITO;
    }
}
