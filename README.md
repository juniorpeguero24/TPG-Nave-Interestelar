# Nave Interestelar

Proyecto Maven en Java. Programacion C.

## Organización del proyecto

```text
.
├── pom.xml                         # POM raíz; agrega el módulo dominio
├── dominio/
│   ├── pom.xml                     # Configuración Maven del módulo Java
│   └── src/main/java/modelo/
│       ├── Prueba.java             # Punto de entrada para ejecutar la simulación
│       ├── Universo.java           # Coordinación del universo simulado
│       ├── Recursos.java           # Recursos de la nave
│       ├── AsistenteComando.java   # Asistente de comandos
│       ├── bitacora/               # Eventos y registro de operaciones
│       ├── excepciones/            # Excepciones del dominio
│       ├── haberes/                # Liquidaciones y conceptos salariales
│       ├── mision/                 # Misiones y sus resultados
│       ├── motorwarp/              # Estados y funcionamiento del motor warp
│       ├── nave/                   # Tipos de nave y fábrica
│       └── tripulacion/            # Tripulantes, cargos y origen
├── docs/                           # Documentación de contratos, patrones y uso de IA

```



## Requisitos

- JDK instalado y disponible en el `PATH` (`java` y `javac`).
- Maven instalado y disponible en el `PATH` (`mvn`).

### Instalación

Instala un JDK (se recomienda JDK 17 o superior) y Maven según tu sistema operativo:

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

Después de la instalación, abre una terminal nueva y comprueba que las herramientas estén disponibles:

```bash
java -version
javac -version
mvn -version
```


## Compilar

Desde la raíz del repositorio, ejecuta:

```bash
mvn clean package
```

El POM raíz agrega el módulo `dominio`, por lo que el comando construye el proyecto completo. Para compilar sin borrar primero los archivos generados, también puedes usar `mvn package`.

## Ejecutar

Después de compilar, desde la raíz ejecuta la clase principal con:

```bash
java -cp dominio/target/classes modelo.Prueba
```

La simulación ejecuta e imprime los escenarios de prueba propuestos en el enunciado.


Los archivos `.jpr`, `.jws` e `.iml` son configuraciones de proyecto del IDE; 
Maven no los necesita para compilar ni ejecutar. La salida de compilación se genera en `dominio/target/`.
