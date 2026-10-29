package modelo.nave;

public class NaveEnemigaCarguera implements NaveExterna {

    @Override
    public ResultadoEncuentro interactuarConExploradora(NaveExploradora exploradora) {
        return ResultadoEncuentro.GANA;
    }

    @Override
    public ResultadoEncuentro interactuarConCarguero(NaveCarguero carguero) {
        return ResultadoEncuentro.PACTO;
    }

    @Override
    public ResultadoEncuentro interactuarConCombate(NaveCombate combate) {
        return ResultadoEncuentro.PIERDE;
    }
}