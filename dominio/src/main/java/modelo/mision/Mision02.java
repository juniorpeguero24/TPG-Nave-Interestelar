package modelo.mision;

import modelo.nave.AsistenteComando;
import modelo.bitacora.Evento;
import modelo.bitacora.TipoEvento;
import modelo.excepciones.OperacionRecursoInvalidaExcepcion;
import modelo.excepciones.RecursoInsuficienteExcepcion;

import modelo.universo.Posicion;

public class Mision02 extends Mision {
    private boolean elementoObtenido = false;
    
    public Mision02() {
        super( "M-02 - Recoleccion", new Posicion(3, 6));
    }

    @Override
    protected void ejecutar(AsistenteComando asistente) throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
        super.ejecutar(asistente);

        agregarAccionPrincipal("Ejecutando maniobra de recolección");
        this.elementoObtenido = true;
    }

    @Override
    protected void evaluar(AsistenteComando asistente) {
        if (this.elementoObtenido)
            this.resultado = ResultadoMision.EXITO;
    }
}
