package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.tablero.Destino;
import modelo.tablero.Posicion;
import modelo.tablero.Tablero;

public class Mision03 extends Mision {
    private boolean regresoCompletado = false;
    
    public Mision03(Destino destino, Tablero tablero) {
        super( "M-03 - Retorno seguro", destino, tablero);
    }


    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        super.ejecutar(asistente);
        if (llegoAlObjetivo(asistente)) {
            agregarAccionPrincipal("Ejecutando maniobra de retorno");
            this.regresoCompletado = true;
        }
    }

    @Override
    protected void cerrar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        if (resultado == ResultadoMision.EXITO) {
            int combustible = asistente.getNave().getEspacioCombustible();
            int energia = asistente.getNave().getEspacioEnergia();
            if (combustible > 0) {
                asistente.cargarCombustible(combustible);
            }
            if (energia > 0) {
                asistente.cargarEnergia(energia);
            }
            asistente.realizarMantenimiento();
            agregarObservacion("Recarga total: +" + combustible + " de combustible, +" + energia
                    + " de energía. Desgaste reiniciado a 0");
        }
        super.cerrar(asistente);
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.regresoCompletado)
            this.resultado = ResultadoMision.EXITO;
    }

    // La base de origen es la posicion inicial de la nave: el centro del tablero.
    @Override
    protected Posicion elegirObjetivo(Posicion origen) {
        return new Posicion(getTablero().getX() / 2, getTablero().getY() / 2);
    }

    @Override
    protected int getEnergiaInicial() {
        return CostosMision.ENERGIA_INICIAL_M03;
    }
}
