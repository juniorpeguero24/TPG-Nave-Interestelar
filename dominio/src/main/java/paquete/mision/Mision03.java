package paquete.mision;

import paquete.Nave;

import paquete.excepciones.OperacionRecursoInvalidaExcepcion;
import paquete.excepciones.RecursoInsuficienteExcepcion;

public class Mision03 extends Mision {
    private boolean regresoCompletado = false;
    
    public Mision03(Nave nave) {
        super(nave, "M-03 - Retorno seguro", 4, 0, 4);
    }
    
    @Override
    protected void ejecutar() throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        nave.consumirCombustible(getCombustibleNecesario());         
        nave.aumentarDesgaste(getDesgasteGenerado());
        
        // ejecutar acciones
        regresoCompletado = true;
        nave.consumirEnergia(getEnergiaNecesaria());
    }
    @Override
    protected void evaluar() {
        if (regresoCompletado) {
            resultado = ResultadoMision.EXITO;
        } else {
            resultado = ResultadoMision.FALLO;
        }
    }
}
