package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // H2 es una base de datos relacional embebida en Java.
    // La utilizamos para persistir los usuarios sin depender de un servidor externo.
    // Nos comunicamos con H2 mediante JDBC
    private static final String URL = "jdbc:h2:./data/nave_interestelar";
    private static final String USUARIO = "sa";
    private static final String CONTRASENIA = "";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
    }
}