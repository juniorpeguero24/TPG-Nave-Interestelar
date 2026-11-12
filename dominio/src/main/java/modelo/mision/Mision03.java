package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.tablero.Destino;
import modelo.tablero.Posicion;
import modelo.tablero.Tablero;
import modelo.tablero.Posicion;

public class Mision03 extends Mision {
    private boolean regresoCompletado = false;
    
    public Mision03(Destino destino, Tablero tablero) {
        super( "M-03 - Retorno seguro", destino, tablero);
    }


    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion, InterruptedException {
        super.ejecutar(asistente);
        if (llegoAlObjetivo(asistente)) {
            agregarAccionPrincipal("Ejecutando maniobra de retorno");
            this.regresoCompletado = true;
        }
    }

    @Override
    protected InformeMision evaluar(AsistenteComando asistente) {
        if (this.regresoCompletado)
            this.resultado = ResultadoMision.EXITO;
        return generarInforme(asistente);
    }
    
    @Override
    protected void cerrar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        if (resultado == ResultadoMision.EXITO) {
            int combustible = asistente.getEspacioCombustible();
            int energia = asistente.getEspacioEnergia();
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
    protected int getEnergiaInicial() {
        return CostosMision.ENERGIA_INICIAL_M03;
    }

}
