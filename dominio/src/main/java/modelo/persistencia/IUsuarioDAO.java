package modelo.persistencia;

public interface IUsuarioDAO {
    void registrar(UsuarioDTO usuario) throws Exception; // INSERT
    boolean existeUsuario(String nombreUsuario) throws Exception; // SELECT por nombre
    boolean validarCredenciales(String nombreUsuario, String contrasenia) throws Exception; // SELECT por nombre + constrasenia
}