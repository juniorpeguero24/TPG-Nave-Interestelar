import controlador.ControladorNave;
import controlador.ControladorPlataforma;
import modelo.concurrencia.PlataformaDeCarga;
import modelo.nave.AsistenteComando;
import modelo.nave.Nave;
import modelo.nave.NaveFactory;
import modelo.universo.Posicion;
import modelo.universo.Universo;
import vistas.VControlNave;
import vistas.VTripulantes;
import vistas.VVisorRecondito;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // VISTAS
        VControlNave vControl =  new VControlNave();
        VVisorRecondito vVisor = new VVisorRecondito();
        VTripulantes vTripulantes = new VTripulantes();

        // MODELOS
        PlataformaDeCarga plataforma = new PlataformaDeCarga(5, 0);
        Universo universo = new Universo();
        Nave nave = NaveFactory.crearNave("Star-X", "combate", new Posicion(5,5));
        AsistenteComando asistente = new AsistenteComando(nave);
        universo.registrarAsistente(asistente);
        universo.seleccionarNave("Star-X");

        // CONTROLADORES
        ControladorPlataforma controladorPlataforma = new ControladorPlataforma(plataforma, vVisor, vControl);
        ControladorNave controladorNave = new ControladorNave(universo, vControl, vTripulantes, vVisor);


        // OBSERVADORES
        plataforma.añadirObservador(vVisor);
        nave.añadirObservador(vControl);


        vControl.setVisible(true);

    }
}
