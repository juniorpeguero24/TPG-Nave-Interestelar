import controlador.ControladorCreacion;
import controlador.Navegador;
import modelo.universo.Universo;
import vistas.VCreacion;
import modelo.tablero.Tablero;
import modelo.tablero.Destino;
import modelo.universo.ElementoDelUniverso;
import modelo.universo.Asteroide;
import modelo.universo.EstacionCarga;
import modelo.nave.NaveFactory;
import java.util.ArrayList;
import modelo.excepciones.CantidadDeElementosSuperaCasillaExcepcion;
import modelo.excepciones.DimensionesDeTableroInconsistentesExcepcion;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Creacion del tablero
        int cantFilas = 10;
        int cantColumnas = 10;
        ArrayList<ElementoDelUniverso> elementos = new ArrayList<>();

        elementos.add(new Asteroide(5));
        elementos.add(new Asteroide(7));
        elementos.add(new Asteroide(2));
        elementos.add(new EstacionCarga(5, 5));
        elementos.add(new EstacionCarga(5, 3));
        elementos.add(NaveFactory.crearNave("Enemiga 1", "exploradora"));
        elementos.add(NaveFactory.crearNave("Enemiga 2", "combate")); 
        elementos.add(NaveFactory.crearNave("Enemiga 3", "carguero"));  

        Tablero tablero;
        try {
            tablero = new Tablero (cantFilas, cantColumnas, elementos);
        }
        catch(DimensionesDeTableroInconsistentesExcepcion | CantidadDeElementosSuperaCasillaExcepcion e) {
            System.out.println("Error al crear el tablero: " + e.getMessage());
            return;
        }
        Destino destino = new Destino(tablero);

        // VISTAS

        VCreacion vCreacion = new VCreacion();

        // MODELOS

        Universo universo = new Universo();

        // CONTROLADORES

        Navegador navegador = new Navegador(null, vCreacion, destino, tablero);
        ControladorCreacion controladorCreacion = new ControladorCreacion(universo, vCreacion,navegador);


        // OBSERVADORES
        
        vCreacion.setVisible(true);


    }
}
