import controlador.ControladorCreacion;
import controlador.ControladorNave;
import controlador.ControladorPlataforma;
import controlador.Navegador;
import modelo.concurrencia.PlataformaDeCarga;
import modelo.nave.AsistenteComando;
import modelo.nave.Nave;
import modelo.nave.NaveFactory;
import modelo.universo.Posicion;
import modelo.universo.Universo;
import vistas.VControlNave;
import vistas.VCreacion;
import vistas.VTripulantes;
import vistas.VVisorRecondito;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // VISTAS

        VCreacion vCreacion = new VCreacion();

        // MODELOS

        Universo universo = new Universo();

        // CONTROLADORES

        Navegador navegador = new Navegador(null, vCreacion);
        ControladorCreacion controladorCreacion = new ControladorCreacion(universo, vCreacion,navegador);


        // OBSERVADORES
        
        vCreacion.setVisible(true);





    }
}
