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
    private int gemasObtenidas;

    public Mision02(Destino destino, Tablero tablero) {
        super( "M-02 - Recoleccion", destino, tablero);
    }

    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion, InterruptedException {
        super.ejecutar(asistente);
        if (llegoAlObjetivo(asistente)) {
            agregarAccionPrincipal("Ejecutando maniobra de recolección");
            this.elementoObtenido = true;
        }
    }

    @Override
    protected InformeMision evaluar(AsistenteComando asistente) {
        if (this.elementoObtenido) {
            this.resultado = ResultadoMision.EXITO;
            this.gemasObtenidas = 20 + azar.nextInt(81);   // 20..100
            agregarObservacion("Recolección completada: se obtuvieron " + gemasObtenidas + " gemas");
        }
        return generarInforme(asistente);
    }

    @Override
    protected void cerrar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        if (resultado == ResultadoMision.EXITO) {
            asistente.agregarGemas(gemasObtenidas);
        }
        super.cerrar(asistente);
    }
    
    @Override
    protected int getEnergiaInicial() {
        return CostosMision.ENERGIA_INICIAL_M02;
    }

}
