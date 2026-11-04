package modelo.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO implements IUsuarioDAO {
    @Override
    public void registrar(UsuarioDTO usuario) throws Exception {
        String sql = "INSERT INTO usuario(nombre_usuario, contrasenia) VALUES (?, ?)";
        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {
            sentencia.setString(1, usuario.getNombreUsuario());
            sentencia.setString(2, usuario.getContrasenia());
            sentencia.executeUpdate();
        }
    }

    @Override
    public boolean existeUsuario(String nombreUsuario) throws Exception {
        String sql = "SELECT 1 FROM usuario WHERE nombre_usuario = ?";
        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {
            sentencia.setString(1, nombreUsuario);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }
        }
    }

    @Override
    public boolean validarCredenciales(String nombreUsuario, String contrasenia) throws Exception {
        String sql = "SELECT 1 FROM usuario WHERE nombre_usuario = ? AND contrasenia = ?";
        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {
            sentencia.setString(1, nombreUsuario);
            sentencia.setString(2, contrasenia);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }
        }
    }
}