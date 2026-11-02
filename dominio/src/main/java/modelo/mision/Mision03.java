package modelo.mision;

import modelo.nave.AsistenteComando;

import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.tablero.Destino;
import modelo.tablero.Tablero;

public class Mision03 extends Mision {
    private boolean regresoCompletado = false;
    
    public Mision03(Destino destino, Tablero tablero) {
        super( "M-03 - Retorno seguro", destino, tablero);
    }


    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        super.ejecutar(asistente);

        agregarAccionPrincipal("Ejecutando maniobra de retorno");
        this.regresoCompletado = true;
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.regresoCompletado)
            this.resultado = ResultadoMision.EXITO;
    }
}
