package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.tablero.Destino;
import modelo.tablero.Tablero;

import java.util.Random;


public class Mision01 extends Mision {
    private boolean asistenciaRealizada = false;
    private final Random azar = new Random();
    private final int auxilioEnergia;
    private final int auxilioCombustible;
    
    public Mision01(Destino destino, Tablero tablero) {
        super("M-01 - Intercepcion y asistencia", destino, tablero);
        this.auxilioEnergia = azar.nextInt(51);
        this.auxilioCombustible = azar.nextInt(51);
    }

    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion, InterruptedException {
        super.ejecutar(asistente);
        
        if (!llegoAlObjetivo(asistente)) {
            return;
        }

        if (asistente.getEnergia() <= auxilioEnergia || asistente.getCombustible() <= auxilioCombustible) {
            agregarObservacion("Recursos insuficientes para asistir a la nave aliada");
            return;
        }

        agregarAccionPrincipal("Contacto con la nave aliada. Auxilio solicitado: "
                + auxilioEnergia + " de energia y "
                + auxilioCombustible + " de combustible");
        this.asistenciaRealizada = true;
    }

    @Override
    protected InformeMision evaluar(AsistenteComando asistente) {
        if (this.asistenciaRealizada) {
            this.resultado = ResultadoMision.EXITO;
            agregarObservacion("Auxilio entregado: " + auxilioEnergia + " de energía y "
                + auxilioCombustible + " de combustible");
        }
        return generarInforme(asistente);
    }
    
    @Override
    protected void cerrar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        if (resultado == ResultadoMision.EXITO) {
            // Entregamos los recursos a la nave aliada.
            if (auxilioCombustible > 0) {
                asistente.consumirCombustible(auxilioCombustible);
                sumarCombustibleConsumido(auxilioCombustible);
            }
            if (auxilioEnergia > 0) {
                asistente.consumirEnergia(auxilioEnergia);
                sumarEnergiaConsumida(auxilioEnergia);
            }
            agregarObservacion("Auxilio entregado: " + auxilioEnergia + " de energía y "
                    + auxilioCombustible + " de combustible");
        }
        super.cerrar(asistente);
    }
    
    @Override
    protected int getEnergiaInicial() {
        return CostosMision.ENERGIA_INICIAL_M01;
    }

    @Override
    protected int getEnergiaInicial() {
        return CostosMision.ENERGIA_INICIAL_M01;
    }
}
