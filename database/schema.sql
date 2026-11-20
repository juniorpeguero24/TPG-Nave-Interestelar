-- Esquema de la base de datos H2 (modo embebido).
-- La aplicación crea esta tabla automáticamente al iniciar (InicializadorBD);
-- este archivo documenta el esquema y permite recrearlo manualmente.
--
-- Única entidad persistida: las credenciales de los usuarios.

CREATE TABLE IF NOT EXISTS usuario (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50)  NOT NULL UNIQUE,
    contrasenia    VARCHAR(100) NOT NULL
);
