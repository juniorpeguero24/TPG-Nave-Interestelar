package modelo.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializadorBD {
    public static void inicializar() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuario (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre_usuario VARCHAR(50) NOT NULL UNIQUE, " +
                    "contrasenia VARCHAR(100) NOT NULL" +
                    ")";
        try (
            Connection conexion = ConexionBD.obtenerConexion();
            Statement sentencia = conexion.createStatement()
        ) {
            sentencia.execute(sql);
        }
    }
}