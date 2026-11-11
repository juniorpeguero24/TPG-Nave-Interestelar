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

El motor Warp se comporta de manera diferente según la fase operativa en la que
se encuentra. Un salto Warp solo puede ejecutarse cuando el motor está
`Disponible`; después del salto el motor necesita un turno de enfriamiento y dos
turnos de preparación antes de volver a estarlo.

Sin el patrón State, `MotorWarp` tendría que consultar con condicionales qué
fase está atravesando y qué debe hacer en cada caso. Con State, esa decisión se
delega en el objeto que representa la fase actual.

## Participantes del patrón

### Contexto: `MotorWarp`

`MotorWarp` es el **Contexto**. Mantiene una referencia al estado actual y
expone hacia el exterior las operaciones `saltar()` y `saltarWarp(...)`, que
delega en ese estado:

```java
private EstadoWarp estado;

public ResultadoSalto saltar() {
    return estado.saltar();
}

public ResultadoSalto saltarWarp(boolean energiaSuficiente) {
    return estado.saltarWarp(energiaSuficiente);
}

public boolean puedeSaltarWarp() {
    return estado instanceof Disponible;
}
```

`MotorWarp` no decide el comportamiento de cada fase: conserva el estado actual,
delega las operaciones y permite cambiarlo.

### Interfaz de estado: `EstadoWarp`

```java
public interface EstadoWarp {
    ResultadoSalto saltar();
    ResultadoSalto saltarWarp(boolean energiaSuficiente);
}
```

### Estados concretos

- `Disponible`: el motor puede ejecutar un salto Warp.
- `Enfriamiento`: fase posterior al salto Warp.
- `PreparandoSalto`: recuperación posterior al enfriamiento; cuenta turnos.

### Resultado de un salto: `ResultadoSalto`

Cada operación devuelve un `ResultadoSalto`, con el tipo de salto
(`TipoResultadoSalto`: `SALTO_NORMAL`, `SALTO_WARP`, `ENFRIAMIENTO`,
`INVALIDO`), la cantidad de casilleros que avanza la nave y una descripción.
Los estados deciden el movimiento y el próximo estado; no consumen recursos.

## Estados y transiciones

El motor comienza en `Disponible`:

```java
public MotorWarp() {
    this.estado = new Disponible(this);
}
```

| Estado | `saltar()` | `saltarWarp(energiaSuficiente)` |
| --- | --- | --- |
| `Disponible` | Salto normal de 1 casillero. No cambia de estado. | Con energía suficiente: salto Warp de 2 casilleros y pasa a `Enfriamiento`. Sin energía suficiente: salto normal. |
| `Enfriamiento` | Salto de 1 casillero (resultado `ENFRIAMIENTO`) y pasa a `PreparandoSalto` con 0 turnos. | Igual que `saltar()`. |
| `PreparandoSalto` | Salto normal de 1 casillero y cuenta un turno; al completar 2 turnos pasa a `Disponible`. | Igual que `saltar()`. |

```text
Disponible
    -- saltarWarp (con energía) -->
Enfriamiento
    -- siguiente salto -->
Preparando salto (0/2)
    -- 2 saltos -->
Disponible
```

## División de responsabilidades

- Los **estados** deciden qué tipo de salto se produce, cuántos casilleros avanza
  la nave y cuál es el estado siguiente.
- `Nave` verifica antes de delegar que existan los recursos necesarios y aplica
  después los costos según el resultado: 2 de combustible por salto normal,
  5 de energía por salto Warp y 5 de desgaste en el enfriamiento.
- `AsistenteComando` registra cada resultado como evento de la bitácora.
- `Mision` decide cuándo intentar un salto Warp (ante un elemento en la ruta) y
  mueve la nave en el tablero según los casilleros del resultado.

## Ventajas de esta aplicación

- Evita condicionales sobre el estado dentro de `MotorWarp`.
- Cada fase tiene su propio comportamiento y su propia transición.
- Agregar una fase nueva no obliga a modificar la lógica del motor ni de la
  misión.

## Consideración de diseño

Las clases concretas de estado tienen visibilidad de paquete y reciben una
referencia al motor, lo que les permite pedir una transición sin exponer
`cambiarEstado` fuera del paquete `motorwarp`.

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

Toda misión sigue las mismas cuatro fases, en el mismo orden: preparar, ejecutar,
evaluar y cerrar. Sin Template Method cada misión implementaría su propio flujo,
con riesgo de duplicar la validación de recursos, de alterar el orden de las
fases o de olvidar el registro en la bitácora.

El patrón fija el esqueleto del algoritmo en una clase abstracta (`Mision`) y
delega en las subclases solo los pasos que varían.

## Participantes del patrón

### Clase abstracta (plantilla): `Mision`

El método plantilla `realizarMision` es `final`, de modo que las subclases no
pueden alterar el orden de las fases:

```java
public final InformeMision realizarMision(AsistenteComando asistente) {
    InformeMision informe = null;
    try {
        preparar(asistente);
        ejecutar(asistente);
        if (asistente.naveDestruida()) {
            this.resultado = ResultadoMision.FALLO;
            agregarObservacion("La nave fue destruida durante la mision");
        }
        informe = evaluar(asistente);
        cerrar(asistente);
    } catch (OperacionRecursoInvalidaExcepcion | RecursoInsuficienteExcepcion
             | TripulacionInvalidaExcepcion | InterruptedException e) {
        this.resultado = ResultadoMision.FALLO;
        observaciones.add(e.getMessage());
        if (informe == null) informe = generarInforme(asistente);
    } catch (NaveDestruidaExcepcion e) {
        // ... mismo tratamiento, registrando el error en la bitácora
    }
    return informe;
}
```

**Fase 1, `preparar`** (común): valida la tripulación y que la nave no esté
destruida, verifica de una sola vez que los recursos alcancen para el consumo
inicial y el radar de largo alcance (si falta algo no se modifica nada), elige el
objetivo, calcula la ruta con el radar de largo alcance y la registra.

**Fase 2, `ejecutar`** (común, ampliable): consume los recursos iniciales de la
misión (4 de combustible, 4 de desgaste y la energía propia de cada misión) y
ejecuta el ciclo de travesía por turnos: la nave inspecciona su casilla e
interactúa con el elemento que encuentre, escanea con el radar de corto alcance
y decide el salto (Warp si hay un elemento en la ruta y el motor puede saltar;
normal en caso contrario). Después el `Destino` mueve los elementos del tablero.
Cada turno incluye una pausa para que la vista muestre el avance.

**Fase 3, `evaluar`** (abstracta): cada misión decide su resultado, calcula el
balance de recursos ganados o entregados y genera el `InformeMision`.

**Fase 4, `cerrar`** (con comportamiento por defecto, redefinible): aplica de
forma definitiva sobre la nave los recursos ganados o entregados.

**Ganchos de la plantilla:** `getEnergiaInicial()` (abstracto) y
`elegirObjetivo(Posicion)` (por defecto, el objetivo que sortea el tablero).

### Clases concretas

| Misión | Energía inicial | Verificación al llegar (`ejecutar`) | Balance (`evaluar`) y aplicación (`cerrar`) |
| --- | --- | --- | --- |
| `Mision01` (Intercepción y asistencia) | 5 | La nave debe conservar más energía y combustible que el auxilio solicitado (0 a 50 de cada uno). | Suma el auxilio a los consumos; en `cerrar` lo descuenta de la nave. |
| `Mision02` (Recolección) | 10 | Haber llegado al objetivo. | Sortea de 20 a 100 gemas; en `cerrar` las suma a la nave. |
| `Mision03` (Retorno seguro) | 0 | Haber llegado a la base (el centro del tablero, redefiniendo `elegirObjetivo`). | Calcula la recarga total; en `cerrar` recarga combustible y energía y reinicia el desgaste. |

## Intermediación y desacoplamiento (`AsistenteComando`)

`Mision` no interactúa directamente con `Nave`. Toda consulta, verificación,
consumo, salto y registro se canaliza por `AsistenteComando`, que gobierna la
nave y asienta los sucesos en la bitácora.

## Ventajas de esta aplicación

- Se pueden agregar misiones nuevas sin tocar el algoritmo ni las existentes.
- Ninguna misión puede omitir el registro del informe ni cambiar el orden de las
  fases.
- Separa decidir el resultado (`evaluar`) de aplicarlo sobre la nave (`cerrar`).

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
| `ObservadorMision` | `actualizarInformes(ArrayList<InformeMision>)` | `AsistenteComando` | `VControlNave` |
| `ObservadorTablero` | `actualizar()` | `Tablero` | `VControlNave` |
| `ObservadorPlataforma` | `actualizar(String)` | `PlataformaDeCarga` | `VVisorRecondito` |

### Sujetos

Cada sujeto guarda su lista de observadores y los notifica cuando cambia:

- `Nave` notifica ante cada cambio de recursos, desgaste, gemas, estado del motor
  o destrucción.
- `Bitacora` notifica cada vez que registra un evento válido.
- `AsistenteComando` notifica el historial completo de informes cada vez que una
  misión registra el suyo.
- `Tablero` notifica cuando se mueve la nave o los elementos del universo.
- `PlataformaDeCarga` notifica cada evento del universo paralelo con un mensaje
  de texto.

### Registro de los observadores

`VControlNave` se registra a sí misma en su constructor ante el tablero, la nave,
la bitácora y el asistente (`agregarObservador`). `Navegador` registra
`VVisorRecondito` en la `PlataformaDeCarga`.

```java
tablero.agregarObservador(this);
nave.agregarObservador(this);
bitacora.agregarObservador(this);
asistente.agregarObservador(this);
```

### Mecanismo provisto por la cátedra

`Tablero` hereda de `Observable`, que notifica a objetos `ObservadorTablero`
(`actualizar()` sin parámetros). La interfaz está en el paquete `observer`, junto
con las demás.

## Consideración de diseño

Los avisos de `PlataformaDeCarga` se emiten desde los hilos de generadoras y
naves. Como Swing solo debe modificarse desde su propio hilo, `VVisorRecondito`
delega la escritura en `SwingUtilities.invokeLater`. La misión se ejecuta en un hilo propio, por lo que `VControlNave` también delega la actualización del tablero en `SwingUtilities.invokeLater`.

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
nave enemiga en una casilla, se invoca `interactuarCon`. Si el resultado es
`PIERDE`, la nave propia se destruye y la misión falla. El método devuelve un
`ResultadoInteraccion` con una descripción, que `AsistenteComando` registra en
la bitácora:

```java
public ResultadoInteraccion interactuarCon(Nave nave) {
    ResultadoEncuentro resultado = nave.enfrentar(this);
    if (resultado == ResultadoEncuentro.PIERDE) {
        nave.destruir();
    }
    return new ResultadoInteraccion("Encuentro con " + getNombre()
            + " (" + getTipo() + "). Resultado: " + resultado);
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

```java
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