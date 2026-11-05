# Patrón Factory aplicado a `Nave`

## ¿Qué problema resuelve?

El sistema puede operar con distintos tipos de nave, cada uno con recursos
iniciales diferentes. Sin una fábrica, el código que necesita crear una nave
tendría que conocer las clases concretas (`NaveExploradora`, `NaveCarguero` y
`NaveCombate`) y decidir cuál instanciar. Si esa decisión se repitiera en
varios lugares, la selección y la creación quedarían dispersas.

`NaveFactory` centraliza esa decisión: recibe el nombre y la clase solicitada,
y devuelve una instancia del tipo concreto correspondiente.

## Participantes del patrón

### Producto abstracto: `Nave`

`Nave` define el tipo común que heredan el resto de las naves. Reúne el nombre,
los recursos, el motor Warp y la tripulación, además de las operaciones
comunes a todas las naves.

### Productos concretos

Las subclases representan los tipos disponibles y configuran sus recursos
iniciales al llamar al constructor de `Nave`:

| Tipo | Clase concreta | Combustible inicial | Energía inicial |
| --- | --- | ---: | ---: |
| Exploradora | `NaveExploradora` | 60 | 80 |
| Carguero | `NaveCarguero` | 100 | 60 |
| Combate | `NaveCombate` | 80 | 100 |

### Fábrica: `NaveFactory`

`NaveFactory.crearNave(String nombre, String clase)` es un método estático que
interpreta el tipo solicitado y crea el producto concreto. El cliente trabaja
con el tipo abstracto `Nave`, sin tener que instanciar directamente las
subclases:

```java
Nave nave = NaveFactory.crearNave("USS Discovery", "exploradora");
```

### Manejo de tipos no reconocidos

Si `clase` es `null` o no coincide con uno de los tipos admitidos,
`crearNave` devuelve `null`. Por eso, quien llama a la fábrica debe comprobar
el resultado antes de utilizar la nave.

### Ventajas de esta aplicación

- Centraliza la creación y selección del tipo de nave.
- Desacopla al cliente de los constructores concretos; puede conservar la
  referencia como `Nave`.
- Mantiene en cada subtipo la configuración de sus recursos iniciales.

<br><br>

# Patrón State aplicado a `MotorWarp`

## ¿Qué problema resuelve?

El motor Warp tiene un comportamiento diferente según su estado actual.
Por ejemplo, preparar un salto es válido cuando el motor está `Disponible`,
pero no es válido cuando está `En Warp` o en `Enfriamiento`.

El patrón State permite representar cada estado como un objeto separado.
De esta manera, cada estado define qué operaciones acepta y a qué estado
debe pasar el motor después de una operación válida.

Sin este patrón, `MotorWarp` tendría que contener muchos condicionales para
consultar el estado actual.

Con State, la decisión se delega al objeto que representa el estado actual.

## Participantes del patrón

### Contexto: `MotorWarp`

`MotorWarp` es el **Contexto** del patrón. Mantiene una referencia al estado
actual:

```java
private EstadoWarp estado;
```

También expone las operaciones del motor y las delega al estado:

```java
public Evento prepararSalto() {
    return this.estado.prepararSalto();
}
```

`MotorWarp` no implementa directamente las reglas de cada situación. Su
responsabilidad es conservar el estado actual, delegar las operaciones y
permitir el cambio de estado.

### Interfaz de estado: `EstadoWarp`

`EstadoWarp` define el contrato común para todos los estados:

```java
public interface EstadoWarp {
    Evento prepararSalto();
    Evento saltar();
    Evento enfriar();
}
```

Gracias a esta interfaz, `MotorWarp` puede trabajar con cualquier estado sin
conocer los detalles de su implementación.

### Estados concretos

Las clases que implementan `EstadoWarp` son:

- `Disponible`;
- `PreparandoSalto`;
- `EnWarp`;
- `Enfriamiento`.

Cada una define las operaciones válidas y las operaciones inválidas para su
estado.

## Estados y transiciones

El motor comienza en `Disponible`:

```java
public MotorWarp() {
    this.estado = new Disponible(this);
}
```

Las transiciones válidas son:

```text
Disponible
    -- prepararSalto() -->
Preparando salto
    -- saltar() -->
En Warp
    -- enfriar() -->
Enfriamiento
    -- pasa un tiempo -->
Disponible
```

El estado concreto crea el siguiente estado y solicita a `MotorWarp` que lo establezca como estado actual.


## Ventajas de esta aplicación

- Evita condicionales complejos dentro de `MotorWarp`.
- Cada estado tiene su propio comportamiento.
- Agregar un nuevo estado no requiere modificar toda la lógica del motor.

## Consideración de diseño

Las clases concretas de estado tienen visibilidad de paquete y reciben una
referencia al motor:

```java
private final MotorWarp motor;
```

Esto permite que un estado solicite una transición sin exponer las
operaciones internas de cambio de estado fuera del paquete
`motorwarp`. Es una decisión adecuada para mantener encapsulada la
implementación del patrón.

<br><br>

# Patrón Decorator aplicado a Liquidación de Haberes

## ¿Qué problema resuelve?

El cálculo de haberes mensuales involucra conceptos acumulativos (sueldo base por cargo, antigüedad, subsidio por origen y adicionales por consejos).

El patrón Decorator evita la explosión de subclases por combinaciones posibles y permite componer dinámicamente los conceptos remunerativos en tiempo de ejecución envolviendo un componente base.

## Participantes del patrón

### Componente común: `Liquidacion`

Interfaz que define los métodos comunes:

- `calcularTotal()`: devuelve el monto acumulado en PG.
- `obtenerDetalle()`: devuelve el desglose de conceptos aplicados.

### Componente concreto: `SueldoBase`

Punto de partida que asigna el haber según el cargo (Capitán 1000 PG, Consejero 600 PG, Teniente 400 PG, Alférez 200 PG). No envuelve a ningún objeto.

### Decorador abstracto: `LiquidacionDecorator`

Clase abstracta que implementa `Liquidacion` y contiene una referencia (`envoltorio`) al componente que decora, delegándole las llamadas por defecto.

### Decoradores concretos

Añaden su propio concepto al acumulado:

- `SubsidioOrigen`: suma el valor según el planeta de origen (Terrícola 20 PG, Vulcano 30 PG, Marciano 18 PG).
- `Antiguedad`: calcula el porcentaje anual correspondiente al cargo sobre el sueldo base.
- `AdicionalConsejeros`: suma 2 PG por cada consejo registrado al cargo Consejero.

## Ventajas de esta aplicación

- Permite agregar o combinar nuevos conceptos sin modificar las clases existentes.
- Mantiene identificable el aporte individual de cada concepto junto con el total final.

<br><br>

# Patrón Template Method aplicado a `Mision`

## ¿Qué problema resuelve?

Toda misión en el universo sigue una serie estricta de etapas invariantes que deben ejecutarse en un orden predeterminado (preparar la nave y verificar recursos, ejecutar la tarea específica, evaluar el resultado y finalizar asentando el desenlace y ejecutando maniobras warp si hubo éxito).

Sin el patrón Template Method, cada misión concreta implementaría su propio flujo de control, lo que causaría:
- Duplicación de la lógica de validación de recursos y motor.
- Riesgo de alterar el orden de las etapas de ejecución.
- Dispersión de la responsabilidad de asentar los eventos en la bitácora y ordenar el salto warp.

El patrón Template Method permite fijar el esqueleto del algoritmo de ejecución en una clase abstracta (`Mision`), delegando únicamente los pasos variables (parte de `ejecutar` y `evaluar`) en las subclases concretas.

## Participantes del patrón

### Clase Abstracta (Plantilla): `Mision`

Define el método plantilla `realizarMision` marcado como `final` para impedir que las subclases alteren la estructura del proceso:

```java
public final InformeMision realizarMision(AsistenteComando asistente) {
        
        try {
            preparar(asistente);
            ejecutar(asistente);
            evaluar(asistente);
        } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion | TripulacionInvalidaExcepcion e) {
            this.resultado = ResultadoMision.FALLO;
            observaciones.add(e.getMessage());
        }

        return finalizar(asistente);
    }
```
- **Pasos concretos (invariantes):**
    - `preparar(AsistenteComando asistente)`: solicita al asistente validar que el motor esté disponible y existan los recursos suficientes (combustible, energía, tolerancia al desgaste) antes de iniciar. Asienta el evento de inicio en la bitácora.
    - `finalizar(AsistenteComando asistente)`: si la evaluación fue exitosa, ordena al asistente ejecutar el salto warp y registra el desenlace; si falló, asienta el evento de fallo.
- **Pasos primitivos (abstractos):**
    - `ejecutar(AsistenteComando asistente)`: consume los recursos asignados y es implementado por sus subclases para realizar su tarea específica.
    - `evaluar(AsistenteComando asistente)`: determina y asigna el estado final (`ResultadoMision.EXITO` o `ResultadoMision.FALLO`).

### Clases Concretas

- `Mision01` (Intercepción y asistencia)
- `Mision02` (Recolección)
- `Mision03` (Retorno seguro)

Cada una define sus propios requisitos de recursos en el constructor y concreta las acciones específicas de su labor mediante las órdenes canalizadas por el `AsistenteComando`.

## Intermediación y Desacoplamiento (`AsistenteComando`)

`Mision` no conoce ni interactúa directamente con `Nave`. Toda consulta de estado, verificación y consumo de combustible/energía se le solicita al `AsistenteComando`, quien gobierna la nave y asienta los sucesos en la bitácora.

## Ventajas de esta aplicación

- **Permite añadir nuevos tipos de misiones sin tocar la estructura del algoritmo ni las misiones existentes.
- **Control centralizado:** Asegura que ninguna misión comience sin recursos ni omita el registro en la bitácora o la transición del motor warp ante un éxito.

<br><br>

# Patrón Observer aplicado a la actualización de las vistas

## ¿Qué problema resuelve?

Durante una misión cambian la nave (recursos, desgaste, estado del motor), la
bitácora y los informes, y esos cambios ocurren en hilos distintos del hilo de
la interfaz. La vista necesita reflejarlos en pantalla sin que el modelo conozca
clases de Swing y sin que la vista consulte al modelo cada cierto tiempo.

El patrón Observer invierte esa dependencia: el modelo (sujeto) mantiene una
lista de observadores expresados como interfaces y los notifica cuando cambia;
la vista se registra como observador y se actualiza sola.

## Participantes del patrón

### Interfaces observador (paquete `observer`)

| Interfaz | Método | Sujeto que notifica | Observador concreto |
| --- | --- | --- | --- |
| `ObservadorNave` | `actualizarNave(Nave)` | `Nave` | `VControlNave` |
| `ObservadorBitacora` | `actualizarBitacora(Bitacora)` | `Bitacora` | `VControlNave` |
| `ObservadorMision` | `actualizarInformes(InformeMision)` | `Mision` | `VControlNave` |
| `ObservadorPlataforma` | `actualizar(String)` | `PlataformaDeCarga` | `VVisorRecondito` |

### Sujetos

Cada sujeto guarda su lista de observadores y expone `añadirObservador`,
`quitarObservador` y `notificarObservadores`:

- `Nave` notifica ante cada cambio de recursos, desgaste o estado.
- `Bitacora` notifica cada vez que registra un evento válido.
- `Mision` notifica al finalizar, entregando el `InformeMision`.
- `PlataformaDeCarga` notifica cada evento del universo paralelo con un mensaje
  de texto.

### Registro de los observadores

El registro lo hace el código de coordinación, no el modelo ni la vista:

- `Navegador` registra `VControlNave` en la nave y en la bitácora a través de
  `AsistenteComando.añadirObservadorNave` y `añadirObservadorBitacora`.
- `ControladorNave` registra `VControlNave` en cada misión antes de iniciarla.
- `Navegador` registra `VVisorRecondito` en la `PlataformaDeCarga`.

```
java
asistente.añadirObservadorNave(control);
asistente.añadirObservadorBitacora(control);
```

### Mecanismo provisto por la cátedra

El `Tablero` hereda de `Observable` y notifica a objetos `Observador`
(`actualizar()` sin parámetros) cuando cambia el estado del universo. Es un
mecanismo independiente del paquete `observer`, que se usa para el tablero.

## Consideración de diseño

Los avisos de `PlataformaDeCarga` se emiten desde los hilos de generadoras y
naves. Como Swing solo debe modificarse desde su propio hilo, `VVisorRecondito`
delega la escritura en `SwingUtilities.invokeLater`.

## Ventajas de esta aplicación

- El modelo no depende de la vista: solo conoce interfaces.
- Se pueden agregar nuevas vistas sin modificar los sujetos.
- La interfaz se actualiza automáticamente durante la misión, sin consultas
  periódicas.

<br><br>

# Patrón Double Dispatch aplicado a los encuentros entre naves

## ¿Qué problema resuelve?

El resultado de un enfrentamiento depende del tipo de las dos naves
involucradas (la propia y la enemiga) y no de sus recursos. Resolverlo con
`instanceof` o con condicionales anidados obligaría a modificar el código cada
vez que se agrega un tipo de nave.

Un despacho simple elige el método según el tipo de un solo objeto. El Double
Dispatch encadena dos llamadas polimórficas para que el método ejecutado
dependa del tipo de ambos.

## Participantes del patrón

### Clase abstracta: `Nave`

Declara las operaciones del despacho:

```java
public abstract ResultadoEncuentro enfrentar(Nave otraNave);

protected abstract ResultadoEncuentro enfrentarConExploradora(NaveExploradora exploradora);
protected abstract ResultadoEncuentro enfrentarConCarguero(NaveCarguero carguero);
protected abstract ResultadoEncuentro enfrentarConCombate(NaveCombate combate);
```

### Clases concretas: `NaveExploradora`, `NaveCarguero`, `NaveCombate`

**Primer despacho.** Cada clase implementa `enfrentar` invocando el método que
identifica su propio tipo sobre la otra nave:

```java
// En NaveExploradora
public ResultadoEncuentro enfrentar(Nave otraNave) {
    return otraNave.enfrentarConExploradora(this);
}
```

**Segundo despacho.** Cada clase implementa los tres métodos `enfrentarCon...`
y decide el resultado según el tipo recibido. El resultado se expresa desde la
perspectiva de la nave propia:

```java
// En NaveCarguero
protected ResultadoEncuentro enfrentarConExploradora(NaveExploradora e) { return ResultadoEncuentro.GANA; }
protected ResultadoEncuentro enfrentarConCarguero(NaveCarguero c)       { return ResultadoEncuentro.PACTO; }
protected ResultadoEncuentro enfrentarConCombate(NaveCombate c)         { return ResultadoEncuentro.PIERDE; }
```

### Tabla de resultados (desde la nave propia)

| Nave propia \ Nave enemiga | Exploradora | Carguero | Combate |
| --- | --- | --- | --- |
| Exploradora | Pacto de paz | Gana | Pierde |
| Carguero | Pierde | Pacto de paz | Gana |
| Combate | Gana | Pierde | Pacto de paz |

### Enumerado: `ResultadoEncuentro`

Toma los valores `GANA`, `PIERDE` y `PACTO`.

### Punto de uso: `Nave.interactuarCon`

`Nave` implementa `ElementoDelUniverso`. Cuando la nave propia coincide con una
nave enemiga en una casilla, se invoca `interactuarCon` y, si el resultado es
`PIERDE`, la nave propia se destruye y la misión falla:

```java
public void interactuarCon(Nave nave) {
    ResultadoEncuentro resultado = nave.enfrentar(this);
    if (resultado == ResultadoEncuentro.PIERDE) {
        nave.destruir();
    }
}
```

## Ventajas de esta aplicación

- Elimina `instanceof` y los condicionales por tipo.
- Cada combinación de tipos queda resuelta en un único método.
- Agregar un tipo de nave exige implementar los métodos de despacho, y el
  compilador señala lo que falta.

<br><br>

# Patrón Productor-Consumidor con Monitores aplicado a la Plataforma de Carga

## ¿Qué problema resuelve?

En el universo paralelo, varias generadoras de energía depositan baterías en una
única plataforma y varias naves las retiran. La plataforma tiene capacidad
limitada y solo una entidad puede operar sobre ella a la vez. Hay que garantizar
exclusión mutua y hacer esperar a quien no puede continuar: a las generadoras
cuando la plataforma está llena y a las naves cuando está vacía.

## Participantes del patrón

### Productores: `GeneradoraDeEnergia`

Implementa `Runnable` y se ejecuta en su propio hilo. Repite el ciclo: producir
una batería (pausa de `tiempoProduccionMs`) y depositarla con
`plataforma.depositarBateria(id)`.

### Consumidores: `NaveConsumidora`

Implementa `Runnable` y se ejecuta en su propio hilo. Repite el ciclo: retirar
una batería con `plataforma.retirarBateria(id)` y consumirla (pausa de
`tiempoConsumoMs`).

### Recurso compartido y monitor: `PlataformaDeCarga`

Guarda la cantidad de baterías y su capacidad máxima. Sus operaciones son
métodos `synchronized`, por lo que solo un hilo las ejecuta a la vez:

```java
public synchronized void depositarBateria(String idGeneradora) throws InterruptedException {
    while (this.cantBaterias >= this.capacidadMaxima) {
        wait();                       // lleno: la generadora espera
    }
    this.cantBaterias++;
    notifyAll();                      // avisa a los hilos en espera
}

public synchronized void retirarBateria(String idNave) throws InterruptedException {
    while (this.cantBaterias == 0) {
        wait();                       // vacío: la nave espera
    }
    this.cantBaterias--;
    notifyAll();
}
```

## Decisiones de diseño

- **Condición de guarda dentro de un `while`:** al despertar, el hilo vuelve a
  comprobar la condición, porque otro hilo pudo haber modificado el estado
  antes de que obtuviera el monitor.
- **`notifyAll` en lugar de `notify`:** en la misma cola de espera conviven
  productores y consumidores. Con `notifyAll` se despiertan todos y cada uno
  reevalúa su condición, lo que evita que se despierte a un hilo que no puede
  avanzar.
- **Las pausas están en los hilos, no en la plataforma:** dormir dentro de un
  método `synchronized` mantendría tomado el monitor y bloquearía a todos los
  demás hilos.
- **Creación dinámica:** `ControladorPlataforma` crea cada generadora o nave en
  un hilo nuevo (daemon) mientras la simulación está activa, y los detiene con
  `interrupt` al volver al panel de control.
- **Eventos hacia la vista:** la plataforma notifica cada acontecimiento
  (producción, retiro, esperas, ingreso de nuevas entidades) mediante el patrón
  Observer.

## Ventajas de esta aplicación

- Garantiza exclusión mutua sin otros mecanismos de sincronización.
- Productores y consumidores quedan desacoplados: solo conocen la plataforma.
- Permite agregar entidades en caliente sin modificar la plataforma.

<br><br>

# Patrón MVC aplicado a la interfaz de usuario

## ¿Qué problema resuelve?

Si la vista contuviera la lógica de negocio, no se podrían reutilizar los
objetos del dominio ni cambiar la interfaz sin tocar las reglas. MVC separa
quién muestra, quién coordina y quién decide.

## Participantes del patrón

### Modelo

Paquete `modelo`: `Universo`, `AsistenteComando`, `Nave`, `Mision`,
`PlataformaDeCarga`, entre otros. No conoce ninguna clase de Swing.

### Vista

Paquete `vistas`: `VAcceso`, `VCreacion`, `VTripulantes`, `VControlNave` y
`VVisorRecondito`, que extienden `JFrame` e implementan `IVista`. Muestran
datos, capturan eventos y devuelven los valores ingresados mediante getters. No
contienen lógica de negocio.

### Controlador

Paquete `controlador`: `ControladorAcceso`, `ControladorCreacion`,
`ControladorNave`, `ControladorPlataforma`, entre otros. Cada uno implementa
`ActionListener`, se registra en su vista con `setActionListener` y traduce
los eventos en operaciones sobre el modelo. `Navegador` administra qué ventana
se muestra en cada momento.

## Flujo de una acción iniciada por el usuario

1. El usuario interactúa con un componente de la vista (por ejemplo, presiona un
   botón).
2. El `ActionListener` registrado captura el evento y delega la acción en el
   controlador.
3. El controlador invoca la operación correspondiente en el modelo.
4. El modelo procesa la lógica de negocio y devuelve un resultado o cambia su
   estado.
5. El controlador evalúa el resultado y coordina la respuesta con la vista (por
   ejemplo, mostrar un mensaje o abrir otra ventana).

```
java
// ControladorCreacion
public void actionPerformed(ActionEvent e) {
    String comando = e.getActionCommand();
    if (comando.equalsIgnoreCase("crear nave")) {
        crearNave();
    }
    ...
}
```

## Actualizaciones iniciadas por el modelo

Los cambios que ocurren sin que el usuario actúe (por ejemplo, el consumo de
recursos durante una misión) llegan a la vista mediante el patrón Observer; ver
la sección correspondiente.

## Ventajas de esta aplicación

- Se puede modificar la interfaz sin tocar el dominio.
- El dominio puede probarse sin interfaz gráfica.
- Cada clase tiene una sola responsabilidad.

<br><br>

# Patrones DAO y DTO aplicados al registro de usuarios

## ¿Qué problema resuelve?

El sistema debe identificar al operador mediante registro e inicio de sesión, y
recordar sus credenciales entre ejecuciones. Esa persistencia no debe mezclarse
con la lógica de la aplicación ni con la interfaz.

- **DAO (Data Access Object):** encapsula todo el acceso a la base de datos
  detrás de una interfaz.
- **DTO (Data Transfer Object):** objeto simple que transporta los datos entre
  capas.

## Participantes del patrón

### DTO: `UsuarioDTO`

Transporta `nombreUsuario` y `contrasenia` entre el controlador y el DAO. No
contiene lógica.

### Interfaz DAO: `IUsuarioDAO`

Define las operaciones disponibles sin revelar cómo se implementan:

```java
void registrar(UsuarioDTO usuario) throws Exception;
boolean existeUsuario(String nombreUsuario) throws Exception;
boolean validarCredenciales(String nombreUsuario, String contrasenia) throws Exception;
```

### DAO concreto: `UsuarioDAO`

Implementa la interfaz con JDBC. Usa `PreparedStatement` con parámetros, lo que
evita la inyección SQL, y try-with-resources para cerrar la conexión y las
sentencias.

### Infraestructura

- `ConexionBD`: entrega la conexión JDBC a una base H2 en archivo
  (`jdbc:h2:./data/nave_interestelar`), que no exige instalar un servidor ni
  configuraciones distintas por integrante.
- `InicializadorBD`: crea la tabla `usuario` si no existe
  (`CREATE TABLE IF NOT EXISTS`) al iniciar la aplicación desde `Main`.

### Quién los usa

`ControladorAcceso` mantiene una referencia a `IUsuarioDAO`, no a la clase
concreta. Valida los campos vacíos, consulta al DAO y, según el resultado,
muestra un mensaje en la vista o abre la ventana de creación.

## Alcance de la persistencia

Solo se persisten las credenciales de los usuarios (nombre y contraseña). Naves,
tripulantes y misiones no se almacenan.

## Consideraciones

En esta entrega la contraseña se guarda tal como la ingresa el usuario, sin
aplicar un hash. Queda como mejora futura.

## Ventajas de esta aplicación

- El controlador depende de una interfaz: se puede reemplazar H2 por otra base
  cambiando solo el DAO concreto.
- Todo el SQL queda en un único lugar.
- El DTO evita que las capas superiores conozcan la estructura de la tabla.
