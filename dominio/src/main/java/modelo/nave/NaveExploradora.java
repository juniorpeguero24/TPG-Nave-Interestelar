package modelo.nave;

public class NaveExploradora extends Nave {
    public NaveExploradora(String nombre) {
        super(nombre, 60, 80);
    }

    @Override
    public String getTipo() {
        return "exploradora";
    }
    
    // Enfrentamientos
    @Override
    public ResultadoEncuentro enfrentar(Nave otraNave) {
        return otraNave.enfrentarConExploradora(this);
    }

    @Override
    protected ResultadoEncuentro enfrentarConExploradora(NaveExploradora exploradora) {
        return ResultadoEncuentro.PACTO;
    }

    @Override
    protected ResultadoEncuentro enfrentarConCarguero(NaveCarguero carguero) {
        return ResultadoEncuentro.PIERDE;
    }

    @Override
    protected ResultadoEncuentro enfrentarConCombate(NaveCombate combate) {
        return ResultadoEncuentro.GANA;
    }
}
