package modelo.nave;

public class NaveCarguero extends Nave {
    public NaveCarguero(String nombre) {
        super(nombre, 100, 60);
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
