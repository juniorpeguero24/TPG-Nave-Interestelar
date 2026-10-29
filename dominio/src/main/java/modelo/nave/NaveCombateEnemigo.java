package modelo.nave;

public class NaveEnemigaCombate implements NaveExterna {

    @Override
    public ResultadoEncuentro interactuarConExploradora(NaveExploradora exploradora) {
        return ResultadoEncuentro.PIERDE;
    }

    @Override
    public ResultadoEncuentro interactuarConCarguero(NaveCarguero carguero) {
        return ResultadoEncuentro.GANA;
    }

    @Override
    public ResultadoEncuentro interactuarConCombate(NaveCombate combate) {
        return ResultadoEncuentro.PACTO;
    }
}