package modelo.nave;

public class NaveCombate extends Nave {
    public NaveCombate(String nombre) {
        super(nombre, 80, 100);
    }

    @Override
    public String getTipo() {
        return "combate";
    }
    
    // Enfrentamientos
    @Override
    public ResultadoEncuentro enfrentar(Nave otraNave) {
        return otraNave.enfrentarConCombate(this);
    }

    @Override
    protected ResultadoEncuentro enfrentarConExploradora(NaveExploradora exploradora) {
        return ResultadoEncuentro.PIERDE;
    }

    @Override
    protected ResultadoEncuentro enfrentarConCarguero(NaveCarguero carguero) {
        return ResultadoEncuentro.GANA;
    }

    @Override
    protected ResultadoEncuentro enfrentarConCombate(NaveCombate combate) {
        return ResultadoEncuentro.PACTO;
    }
}
