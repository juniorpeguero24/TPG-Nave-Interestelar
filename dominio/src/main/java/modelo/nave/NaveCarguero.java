package modelo.nave;

import modelo.universo.Posicion;

public class NaveCarguero extends Nave {
    public NaveCarguero(String nombre, Posicion posicion) {
        super(nombre, 100, 60, posicion);
    }

    @Override
    public String getTipo() {
        return "carguero";
    }
    
    // Enfrentamientos
    @Override
    public ResultadoEncuentro enfrentar(Nave otraNave) {
        return otraNave.enfrentarConCarguero(this);
    }

    @Override
    protected ResultadoEncuentro enfrentarConExploradora(NaveExploradora exploradora) {
        return ResultadoEncuentro.GANA;
    }

    @Override
    protected ResultadoEncuentro enfrentarConCarguero(NaveCarguero carguero) {
        return ResultadoEncuentro.PACTO;
    }

    @Override
    protected ResultadoEncuentro enfrentarConCombate(NaveCombate combate) {
        return ResultadoEncuentro.PIERDE;
    }
}
