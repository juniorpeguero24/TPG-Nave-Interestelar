package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.tablero.Destino;
import modelo.tablero.Tablero;

import java.util.Random;

import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;


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
            sumarCombustibleConsumido(auxilioCombustible);
            sumarEnergiaConsumida(auxilioEnergia);
            agregarObservacion("Auxilio entregado a la nave aliada: " + auxilioEnergia
                    + " de energia y " + auxilioCombustible + " de combustible");
        }
        return generarInforme(asistente);
    }

    @Override
    protected void cerrar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        if (resultado == ResultadoMision.EXITO) {
            if (auxilioCombustible > 0) {
                asistente.consumirCombustible(auxilioCombustible);
            }
            if (auxilioEnergia > 0) {
                asistente.consumirEnergia(auxilioEnergia);
            }
            asistente.registrarEvento(new Evento(TipoEvento.MISION,
                    "Auxilio entregado a la nave aliada: " + auxilioCombustible + " de combustible y "
                            + auxilioEnergia + " de energia"));
        }
        super.cerrar(asistente);
    }
    
    @Override
    protected int getEnergiaInicial() {
        return CostosMision.ENERGIA_INICIAL_M01;
    }

}
