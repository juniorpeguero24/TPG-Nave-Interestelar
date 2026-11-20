package modelo.persistencia;

import java.sql.SQLException;

public interface IUsuarioDAO {
    void registrar(UsuarioDTO usuario) throws SQLException; // INSERT
    boolean existeUsuario(String nombreUsuario) throws SQLException; // SELECT por nombre
    boolean validarCredenciales(String nombreUsuario, String contrasenia) throws SQLException; // SELECT por nombre + constrasenia
}