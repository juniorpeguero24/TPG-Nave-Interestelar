package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.tablero.Destino;
import modelo.tablero.Tablero;

import java.util.Random;

public class Mision02 extends Mision {
    private boolean elementoObtenido = false;
    private final Random azar = new Random();

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

    @Override
    protected void cerrar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        if (resultado == ResultadoMision.EXITO) {
            int gemas = 20 + azar.nextInt(81);   // 20..100
            asistente.agregarGemas(gemas);
            agregarObservacion("Recolección completada: se obtuvieron " + gemas + " gemas");
        }
        super.cerrar(asistente);
    }

    @Override
    protected int getEnergiaInicial() {
        return CostosMision.ENERGIA_INICIAL_M02;
    }
}
