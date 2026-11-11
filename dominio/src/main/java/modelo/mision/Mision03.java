package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.tablero.Destino;
import modelo.tablero.Posicion;
import modelo.tablero.Tablero;
import modelo.universo.ElementoDelUniverso;

public class Mision03 extends Mision {
    private boolean regresoCompletado = false;
    private int recargaCombustible, recargaEnergia;

    public Mision03(Destino destino, Tablero tablero) {
        super( "M-03 - Retorno seguro", destino, tablero);
    }


    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion, InterruptedException {
        super.ejecutar(asistente);
        if (!asistente.naveDestruida()) {
            ElementoDelUniverso elementoFinal = getTablero().inspeccionarPosicion();
            if (elementoFinal != null) {
                asistente.interactuarCon(elementoFinal);
            }
        }
        if (llegoAlObjetivo(asistente)) {
            agregarAccionPrincipal("Ejecutando maniobra de retorno");
            this.regresoCompletado = true;
        }
    }

    @Override
    protected InformeMision evaluar(AsistenteComando asistente) {
        if (this.regresoCompletado) {
            this.resultado = ResultadoMision.EXITO;
            this.recargaCombustible = asistente.getNave().getEspacioCombustible();
            this.recargaEnergia = asistente.getNave().getEspacioEnergia();
            agregarObservacion("Recarga total: +" + recargaCombustible + " de combustible, +"
                    + recargaEnergia + " de energía. Desgaste reiniciado a 0");
        }
        return generarInforme(asistente);
    }

    @Override
    protected void cerrar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        if (resultado == ResultadoMision.EXITO) {
            if (recargaCombustible > 0) {
                asistente.cargarCombustible(recargaCombustible);
            }
            if (recargaEnergia > 0) {
                asistente.cargarEnergia(recargaEnergia);
            }
            asistente.realizarMantenimiento();
        }
        super.cerrar(asistente);
    }

    // La base de origen es la posición inicial de la nave: el centro del tablero.
    @Override
    protected Posicion elegirObjetivo(Posicion origen) {
        return new Posicion(getTablero().getX() / 2, getTablero().getY() / 2);
    }
    
    @Override
    protected int getEnergiaInicial() {
        return CostosMision.ENERGIA_INICIAL_M03;
    }

}
