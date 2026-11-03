import controlador.ControladorCreacion;
import controlador.Navegador;
import modelo.universo.Universo;
import vistas.VCreacion;


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
