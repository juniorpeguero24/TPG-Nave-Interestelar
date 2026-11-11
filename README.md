# Nave Interestelar

Proyecto Maven en Java. Programación C.

Software embarcado de una nave interestelar tripulada. La Entrega 1 modela la
nave, su tripulación y las misiones; la Entrega 2 las integra con un tablero
dinámico, una interfaz gráfica en Swing (MVC y Observer), un módulo de
concurrencia (Productor-Consumidor) y el registro de usuarios con persistencia
(DAO y DTO sobre H2).

## Organización del proyecto

```text
.
├── pom.xml                         # POM raíz; agrega el módulo dominio
├── database/
│   └── schema.sql                  # Esquema de la base de datos H2
├── docs/                           # Contratos, patrones, informe, diagrama y uso de IA
└── dominio/
    ├── pom.xml                     # Configuración Maven del módulo Java
    └── src/main/java/
        ├── Main.java               # Punto de entrada de la aplicación
        ├── controlador/            # Controladores (MVC) y navegación entre ventanas
        ├── vistas/                 # Ventanas Swing (MVC)
        ├── observer/               # Interfaces del patrón Observer
        └── modelo/
            ├── bitacora/           # Eventos y registro de operaciones
            ├── concurrencia/       # Plataforma de carga, generadoras y naves consumidoras
            ├── excepciones/        # Excepciones del dominio
            ├── haberes/            # Liquidaciones y conceptos salariales
            ├── mision/             # Misiones y sus informes
            ├── motorwarp/          # Estados y funcionamiento del motor warp
            ├── nave/               # Tipos de nave, fábrica, recursos y radares
            ├── persistencia/       # DAO, DTO y acceso a la base de datos
            ├── tablero/            # Tablero, posiciones y Destino (provistos por la cátedra)
            ├── tripulacion/        # Tripulantes, cargos y origen
            └── universo/           # Universo, elementos del espacio y rutas
```

## Entregas

Cada entrega se identifica mediante una etiqueta Git:

- Entrega 1: `entrega-e1`
- Entrega 2: `entrega-e2`

## Documentación

- Patrones: [docs/patrones.md](./docs/patrones.md)
- Contratos: [docs/contratos.md](./docs/contratos.md)
- Informe de diseño: [docs/informe.md](./docs/informe.md)
- Uso de IA: [docs/uso-ia.md](./docs/uso-ia.md)
- Diagrama de clases: [docs/diagrama.svg](./docs/diagrama.svg)
- Esquema de la base de datos: [database/schema.sql](./database/schema.sql)

## Requisitos de ejecución

- JDK instalado y disponible en el `PATH` (`java` y `javac`). El proyecto
  compila con nivel de lenguaje 11; se recomienda JDK 17 o superior.
- Maven instalado y disponible en el `PATH` (`mvn`).
- Entorno gráfico, porque la aplicación abre ventanas Swing.
- Conexión a internet la primera vez, para que Maven descargue las
  dependencias (H2 y anotaciones de JetBrains).

### Instalación

**Ubuntu / Debian**

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk maven
```

**Windows** (desde PowerShell)

```powershell
winget install --id EclipseAdoptium.Temurin.17.JDK -e
winget install --id Apache.Maven -e
```

Después de instalar, abrir una terminal nueva y comprobar:

```bash
java -version
javac -version
mvn -version
```

## Comandos Maven

Desde la raíz del repositorio:

```bash
mvn clean package
```

El POM raíz agrega el módulo `dominio`, por lo que el comando construye el
proyecto completo. La salida se genera en `dominio/target/`.

## Iniciar el sistema

Desde la raíz del repositorio, después de compilar:

```bash
mvn -pl dominio compile exec:java -Dexec.mainClass=Main
```

También se puede ejecutar la clase `Main` desde el IDE (en IntelliJ, clic
derecho sobre `Main.java` y **Run 'Main.main()'**).

Al iniciar, la aplicación crea la base de datos si no existe y abre la pantalla
de acceso. Flujo de uso:

1. Registrarse (nombre de usuario y contraseña) e iniciar sesión.
2. Crear una nave, crear tripulantes y asignarlos a la nave.
3. Desde el panel de control: iniciar una misión y observar su avance, o abrir
   el visor del universo paralelo para agregar naves consumidoras y
   generadoras de energía.

## Ejecutar las verificaciones

[COMPLETAR: indicar cómo se ejecutan las pruebas o demostraciones reproducibles
del grupo y qué resultado esperado muestra cada una.]

Verificación disponible de la persistencia (registra un usuario de prueba y
muestra el resultado por consola):

```bash
mvn -pl dominio compile exec:java -Dexec.mainClass=modelo.persistencia.PruebaPersistencia
```

## Configuración local

- **Base de datos:** H2 en modo embebido, sin servidor ni credenciales
  personales. El archivo se crea en la carpeta `data/` del directorio desde el
  que se ejecuta la aplicación (`jdbc:h2:./data/nave_interestelar`). Esos
  archivos son datos locales y no se versionan (ver `.gitignore`).
- **Esquema:** la tabla `usuario` se crea automáticamente al iniciar. El mismo
  esquema se documenta en [database/schema.sql](./database/schema.sql).
- Para empezar con la base vacía, cerrar la aplicación y borrar la carpeta
  `data/`.

Los archivos `.jpr`, `.jws` e `.iml` son configuraciones de proyecto del IDE;
Maven no los necesita para compilar ni ejecutar.