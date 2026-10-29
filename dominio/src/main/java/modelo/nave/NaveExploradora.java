package modelo.nave;

public class NaveExploradora extends Nave {
    public NaveExploradora(String nombre) {
        super(nombre, 60, 80);
    }

    @Override
    public String getTipo() {
        return "exploradora";
    }
    
    @Override
    public ResultadoEncuentro aceptarInteraccion(NaveExterna externa) {
        return externa.interactuarConExploradora(this);
}
}


 