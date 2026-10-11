# Contratos

Este documento describe las responsabilidades, precondiciones, postcondiciones e invariantes de todas las clases.

---

## `Bitacora`

### Responsabilidad

`Bitacora` administra el registro de eventos de una nave. Sus responsabilidades son:

- almacenar eventos válidos;
- ignorar eventos `null`;
- consultar eventos dentro de un intervalo de fechas;
- proporcionar una representación textual de los eventos registrados.

La bitácora no crea ni modifica los eventos que recibe. Tampoco decide qué operación generó un evento.

### Constructor

```java
public Bitacora();
```

#### Precondiciones

- Sin precondiciones.

#### Postcondiciones

- Se crea una bitácora vacía.
- La colección interna de eventos queda inicializada.

#### Invariantes

- La bitácora solo contiene referencias a objetos `Evento` no nulas.

### `registrarEvento`

```java
public void registrarEvento(Evento evento);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si `evento` es válido (`evento.esValido()`), queda agregado a la bitácora.
- Si `evento` es `null` o no es válido, la bitácora no cambia.
- Los eventos ya registrados conservan su orden.

#### Invariantes

- La cantidad de eventos no disminuye.
- Ningún elemento almacenado es `null`.

### `consultarEvento`

```java
public ArrayList<Evento> consultarEvento(LocalDateTime desde, LocalDateTime hasta);
```

#### Precondiciones

- `desde` no debe ser `null`.
- `hasta` no debe ser `null`.
- `desde` debe ser anterior o igual a `hasta`.

#### Postcondiciones

- Devuelve un `ArrayList<Evento>`.
- Incluye todos los eventos cuya fecha está dentro del intervalo inclusivo: `desde <= fecha <= hasta`.
- Devuelve una lista vacía si no hay eventos que cumplan el filtro.
- No modifica los registros de la bitácora.

#### Invariantes

- Consultar la bitácora no elimina ni agrega eventos.
- El orden de los eventos devueltos coincide con el orden en que fueron
  registrados.

---

## `Evento`

### Responsabilidad

`Evento` representa un hecho ocurrido en la nave, con su fecha, tipo y descripción. Sus datos no se modifican después de su creación.

### Constructor

```java
public Evento(TipoEvento tipo, String descripcion);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- `fecha` queda establecida con `LocalDateTime.now()` durante la construcción.
- `tipo` y `descripcion` conservan los valores recibidos.
- Si `tipo` o `descripcion` son `null`, o la descripción está en blanco, `esValido()` devuelve `false`.

#### Invariantes

- Los datos del evento no cambian después de su creación.

### `getFecha` y `getTipo`

```java
public LocalDateTime getFecha();
public TipoEvento getTipo();
```

#### Postcondiciones

- `getFecha()` devuelve la fecha asignada durante la construcción y nunca devuelve `null`.
- `getTipo()` devuelve el tipo recibido en el constructor; puede ser `null`.

### `esValido`

```java
public boolean esValido();
```

#### Postcondiciones

- Devuelve `true` únicamente si `tipo` no es `null`, `descripcion` no es `null` y `descripcion` no está en blanco.
- En cualquier otro caso, devuelve `false`.
- No modifica el evento.

### `toString`

```java
public String toString();
```

#### Postcondiciones

- Devuelve la fecha con formato `yyyy-MM-dd HH:mm`, seguida del tipo, `: `, la descripción y un salto de línea.
- Si `tipo` o `descripcion` son `null`, su representación en el texto es `null`.


---


## `EstadoWarp`

### Responsabilidad

`EstadoWarp` define el contrato común que deben cumplir todos los estados del motor Warp. 
Cada implementación determina qué operaciones son válidas para el estado actual y cuál es el siguiente estado cuando se realiza una transición válida.

Las clases que implementan esta interfaz son `Disponible`,
`PreparandoSalto`, `EnWarp` y `Enfriamiento`.

### Operaciones de transición

```java
Evento prepararSalto();
Evento saltar();
Evento enfriar();

```

#### Precondiciones

- La operación debe ser invocada a través del motor, quien delega a su estado actual.

#### Postcondiciones

- Devuelve un `Evento` no nulo.
- Si la operación es válida para el estado:
  - solicita a `MotorWarp` el cambio al estado siguiente;
  - el evento informa la transición realizada.
- Si la operación es inválida:
  - solicita a `MotorWarp` el registro de un error;
  - el estado actual del motor no cambia;

#### Invariantes

- Cada implementación respeta las reglas de transición correspondientes a su estado.
- Una operación inválida no modifica el estado del motor.
- Todas las operaciones definidas por la interfaz devuelven un evento
  `Evento`.

---

## `MotorWarp`

### Responsabilidad

`MotorWarp` administra el estado actual del motor y delega cada operación al objeto que representa dicho estado. 
También genera un `Evento` utilizado para registrar una transición válida o inválida.

`MotorWarp` no registra los eventos directamente en una `Bitacora`. Devuelve los eventos a su llamador, que se encarga de
registrarlos.

### Constructor

```java
public MotorWarp();
```

#### Precondiciones

- No requiere parámetros.

#### Postcondiciones

- El motor queda inicializado en el estado `Disponible`.
- El objeto de estado actual queda asociado al motor.

#### Invariantes

- El estado actual nunca es `null`.
- El motor siempre tiene exactamente un estado actual.
- El estado actual implementa `EstadoWarp`.

### `getEstado`

```java
public String getEstado();
```

#### Precondiciones

- El motor debe estar correctamente inicializado.

#### Postcondiciones

- Devuelve el nombre del estado actual.

#### Invariantes

- El nombre devuelto corresponde al estado que controla actualmente al
  motor.

### Operaciones de transición

```java
public Evento prepararSalto();
public Evento saltar();
public Evento enfriar();

```

#### Precondiciones

- El motor debe estar inicializado.

#### Postcondiciones generales

- Se delega la operación al estado actual.
- Se devuelve un `Evento` no nulo.
- Si la transición es válida:
  - el motor cambia al estado siguiente;
  - devuelve un evento de tipo `MOTOR_WARP` cuya descripción es `"Cambio de estado a "` seguida del nombre del nuevo estado.
- Si la transición es inválida:
  - el motor conserva su estado actual;
  - devuelve un evento de tipo `MOTOR_WARP` con la descripción `"Transición inválida"`.

#### Invariantes

- El motor siempre permanece en uno de los estados definidos.
- Una transición inválida no cambia el estado.
- Cada invocación devuelve un evento que puede ser registrado en la bitacora.

### `cambiarEstado`

```java
Evento cambiarEstado(EstadoWarp estado);
```

Este método tiene visibilidad de paquete y es utilizado por los estados
concretos para solicitar una transición.

#### Precondiciones

- El nuevo estado debe implementar `EstadoWarp`.

#### Postcondiciones

- El estado recibido pasa a ser el estado actual del motor.
- Se devuelve un evento que describe el nuevo estado.

#### Invariantes

- El estado actual nunca queda en `null`.
- El cambio de estado solo se realiza a través de una transición definida
  por el patrón State.

### `registrarError`

```java
Evento registrarError();
```

Este método tiene visibilidad de paquete y es utilizado por los estados
cuando una operación no está permitida.

#### Precondiciones

- El motor debe estar inicializado.

#### Postcondiciones

- Se devuelve un evento que informa una transición inválida.
- El estado actual no cambia.

#### Invariantes

- Una operación inválida nunca altera el estado del motor.

---

## `Liquidacion`

### Responsabilidad

Define el contrato para la consulta y cálculo de conceptos remunerativos dentro del esquema de liquidación de haberes.

### `calcularTotal`

```java
double calcularTotal();
```

#### Precondiciones

- El componente debe estar inicializado.

#### Postcondiciones

- Devuelve el valor total acumulado en PG (mayor o igual a 0).
- No altera el estado interno de los objetos.

### `obtenerDetalle`

```java
String obtenerDetalle();
```

#### Precondiciones

- El componente debe estar inicializado.

#### Postcondiciones

- Devuelve una cadena no vacía con los conceptos aplicados y sus montos.

---

## `SueldoBase`

### Responsabilidad

Componente base que asigna la remuneración inicial según el cargo del tripulante.

```java
public SueldoBase(String nombre, Cargo cargo);
```

#### Precondiciones

- cargo no debe ser null.
- `nombre` puede ser `null`; en ese caso se representa como `null` en el detalle.

#### Postcondiciones

- Asigna un sueldo base de 1000 PG a `CAPITAN`, 600 PG a `CONSEJERO`, 400 PG a `TENIENTE` y 200 PG a `ALFEREZ`.
- `calcularTotal()` devuelve ese sueldo base.
- `obtenerDetalle()` incluye el cargo, el nombre y el monto base.

#### Invariantes

- El sueldo base nunca es negativo.
- El cargo asociado permanece inmutable.

---

## `LiquidacionDecorator`

### Responsabilidad

Clase abstracta base para componer conceptos remunerativos delegando en el componente envuelto.

```java
public LiquidacionDecorator(Liquidacion envoltorio);
```

#### Precondiciones

- envoltorio no debe ser null.

#### Postcondiciones

- Almacena la referencia al componente interno decorado.
- `calcularTotal()` y `obtenerDetalle()` delegan en el componente envuelto y devuelven su resultado sin agregar conceptos.

#### Invariantes

- La referencia interna envoltorio nunca es null.

---

## `SubsidioOrigen`

### Responsabilidad

Añade el subsidio fijo según el planeta de origen del tripulante.

```java
public SubsidioOrigen(Liquidacion envoltorio, Origen origen);
```

#### Precondiciones

- envoltorio no debe ser null.
- origen no debe ser null.

#### Postcondiciones

- Suma el subsidio correspondiente al total acumulado.
- Agrega la descripción del subsidio al detalle.

## `Antiguedad`

### Responsabilidad

Calcula y agrega el adicional por años de servicio según el cargo del tripulante.

```java
public Antiguedad(Liquidacion envoltorio, Cargo cargo, int antiguedad);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Suma el adicional por año correspondiente al cargo sobre su haber base.
- Registra el desglose de años en el detalle.
- Si `envoltorio` o `cargo` son `null`, o `antiguedad` es negativa, lanza `IllegalArgumentException`.

---

## `AdicionalConsejeros`

### Responsabilidad

Añade el adicional por consejos brindados durante el período liquidado.

```java
public AdicionalConsejeros(Liquidacion envoltorio, int cantidadConsejos);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Agrega 2 PG por cada consejo registrado al monto total.
- Agrega al detalle una línea con el monto extra calculado; no incluye la cantidad de consejos.
- Si `envoltorio` es `null` o `cantidadConsejos` es negativa, lanza `IllegalArgumentException`.

---

## `Universo`

### Responsabilidad

`Universo` actúa como el centro de control del sistema. Sus responsabilidades son:

- Mantener el registro de asistentes de comando listos para operar.
- Seleccionar y proveer la nave activa para su operación, asegurando el uso de una única nave a la vez.
- Servir como punto de entrada de alto nivel sin depender de cómo se construyen las naves ni sus asistentes.

### Constructor

```java
public Universo();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Inicializa la colección interna de asistentes vacía.
- `asistenteEnOperacion` queda sin asignar (`null`).

#### Invariantes de la clase

- La colección de asistentes no contiene referencias `null`.
- `asistenteEnOperacion` es `null` o referencia a uno de los asistentes registrados.
- La colección conserva el orden en que se registraron los asistentes.

### `registrarAsistente`

```java
public void registrarAsistente(AsistenteComando asistente);
```

#### Precondiciones
- Ninguna.

#### Postcondiciones
- Si `asistente` no es `null`, queda añadido al final de la colección y la cantidad aumenta en uno.
- Si `asistente` es `null`, lanza `IllegalArgumentException` y la colección no cambia.

### `seleccionarNave`

```java
public AsistenteComando seleccionarNave(String nombreNave);
```

#### Precondiciones
- Ningún asistente registrado debe tener una nave cuyo nombre sea `null`, porque la búsqueda invoca `equals` sobre ese nombre.

#### Postcondiciones
- Si encuentra una nave cuyo nombre coincide exactamente con `nombreNave` (sensible a mayúsculas y minúsculas), la establece como asistente en operación y la devuelve.
- Si no encuentra coincidencia, incluso cuando `nombreNave` es `null`, devuelve `null` y no cambia el asistente en operación.

### `getAsistenteEnOperacion`

```java
public AsistenteComando getAsistenteEnOperacion();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el asistente seleccionado, o `null` si todavía no se seleccionó ninguno.
- No modifica el estado del universo.

### `getCantidadAsistentes`

```java
public int getCantidadAsistentes();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la cantidad actual de asistentes registrados sin modificar el universo.

---

## `AsistenteComando`

### Responsabilidad
Es la entidad inteligente que gobierna una única nave y registra eventos en la Bitacora. Actúa como intermediario obligatorio para toda orden o consulta externa (recursos, motor warp y bitácora).   

### Constructor

```java
public AsistenteComando(Nave nave);
```

#### Precondiciones
- nave no debe ser null.   

#### Postcondiciones
- Queda asociado unidireccionalmente a la nave indicada. 
- Contiene una Bitacora inicialmente vacia.  

#### Invariantes
- Cada asistente opera exactamente una única nave a lo largo de su ciclo de vida.   

### `verificarDisponibilidadParaMision`

```java
public void verificarDisponibilidadParaMision(int combustibleNecesario, int energiaNecesaria, int desgasteGenerado)
throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones
- Ninguna.

#### Postcondiciones
- Si el motor está en estado "Disponible" y la nave cuenta con combustible, energía y margen de desgaste suficiente, la ejecución concluye normalmente.   
- Si el motor no está disponible, lanza OperacionRecursoInvalidaExcepcion.   
- Si alguno de los recursos es insuficiente, lanza RecursoInsuficienteExcepcion.   
Las excepciones son registradas en la bitacora.
- No altera los recursos de la nave.   

#### Invariantes
- No modifica el estado del motor ni los consumos de la nave si se produce una falla.   

### `OrdenarSaltoWarp`

```java
public void ordenarSaltoWarp();
```

#### Precondiciones
- El motor de la nave debe encontrarse en estado "Disponible".   

#### Postcondiciones
- Ejecuta en secuencia: prepararSalto() -> saltar() -> enfriar().   
- El motor concluye en estado "Disponible".   
- Registra cada transición como un Evento en la bitácora.   

#### Invariantes
- La bitácora incrementa su tamaño según los eventos generados.   

### `getNave`

```java
public Nave getNave();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la nave manejada por el asistente.

### `getNombreNave`

```java
public String getNombreNave();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el nombre de la nave asociada.

### `obtenerEstadoNaveActual`

```java
public String obtenerEstadoNaveActual();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la representación textual del estado actual de la nave.

### `agregarTripulante`

```java
public void agregarTripulante(Tripulante tripulante);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Delega la operación a la nave.
- Si el tripulante es `null`, ya está agregado o intenta agregarse un segundo capitán, propaga `IllegalArgumentException`.

### `tieneTripulacionValida`

```java
public boolean tieneTripulacionValida();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve true si la tripulación de la nave tiene al menos cinco miembros y un capitán, sin modificarla.

### `registrarEvento`

```java
public void registrarEvento(Evento evento);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Delega el registro del evento a la bitácora; si es `null` o inválido, la bitácora no cambia.

### `generarInformeBitacora`

```java
public String generarInformeBitacora();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la representación textual de la bitácora sin modificarla.

### `getEstadoMotorWarp`

```java
public String getEstadoMotorWarp();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el nombre del estado actual del motor Warp.

### `prepararSalto`

```java
public void prepararSalto();
```

#### Precondiciones

- Ninguna; la validez de la operación depende del estado actual del motor Warp.

#### Postcondiciones

- Delega la operación al motor Warp y registra en la bitácora el evento devuelto, incluso si la transición es inválida.

### `saltar`

```java
public void saltar();
```

#### Precondiciones

- Ninguna; la validez de la operación depende del estado actual del motor Warp.

#### Postcondiciones

- Delega la operación al motor Warp y registra en la bitácora el evento devuelto, incluso si la transición es inválida.

### `enfriar`

```java
public void enfriar();
```

#### Precondiciones

- Ninguna; la validez de la operación depende del estado actual del motor Warp.

#### Postcondiciones

- Delega la operación al motor Warp y registra en la bitácora el evento devuelto, incluso si la transición es inválida.

### `getCombustible`

```java
public int getCombustible();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el combustible actual de la nave sin modificarla.

### `getEnergia`

```java
public int getEnergia();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la energía actual de la nave sin modificarla.

### `getDesgaste`

```java
public int getDesgaste();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el desgaste actual de la nave sin modificarla.

### `requiereMantenimiento`

```java
public boolean requiereMantenimiento();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve si la nave requiere mantenimiento, sin modificarla.

### `cargarCombustible`

```java
public void cargarCombustible(int cantidad) throws OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si la carga es válida, aumenta el combustible y registra un evento `RECURSOS`.
- Si `cantidad` no es positiva o la carga supera la capacidad máxima, registra un evento `ERROR`, propaga `OperacionRecursoInvalidaExcepcion` y no modifica el combustible.

### `cargarEnergia`

```java
public void cargarEnergia(int cantidad) throws OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si la carga es válida, aumenta la energía y registra un evento `RECURSOS`.
- Si `cantidad` no es positiva o la carga supera la capacidad máxima, registra un evento `ERROR`, propaga `OperacionRecursoInvalidaExcepcion` y no modifica la energía.

### `realizarMantenimiento`

```java
public void realizarMantenimiento();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Reinicia a cero el desgaste de la nave y registra un evento `RECURSOS`.

### `estaDisponibleParaSalto`

```java
public boolean estaDisponibleParaSalto();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve `true` si el estado Warp es `Disponible`, sin distinguir mayúsculas y minúsculas; en otro caso devuelve `false`.

### `verificarDisponibilidadRecursos`

```java
public void verificarDisponibilidadRecursos(int combustible, int energia, int desgaste)
  throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si la verificación tiene éxito, no consume recursos.
- Si algún valor es negativo, los recursos son insuficientes o el desgaste supera el máximo, registra un evento `ERROR` y propaga `OperacionRecursoInvalidaExcepcion` o `RecursoInsuficienteExcepcion`, según corresponda.

### `consumirCombustible` y 'consumirEnergia'

```java
public void consumirCombustible(int cantidad)
  throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna

#### Postcondiciones

- Si el consumo tiene éxito, reduce el combustible/energia y registra un evento `RECURSOS`.
- Si se produce `OperacionRecursoInvalidaExcepcion` o `RecursoInsuficienteExcepcion`, registra un evento `ERROR`, propaga la excepción y no modifica el recurso.


### `aumentarDesgaste`

```java
public void aumentarDesgaste(int cantidad) throws OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna

#### Postcondiciones

- Si el aumento tiene éxito, actualiza el desgaste sin registrar evento.
- Si se produce `OperacionRecursoInvalidaExcepcion`, registra un evento `ERROR`, propaga la excepción y no modifica el desgaste.

---


## `Tripulacion`

### Responsabilidad

Administra la colección ordenada de tripulantes de una nave y permite comprobar si cumple los requisitos mínimos de composición.

### Constructor

```java
public Tripulacion();
```

#### Precondiciones

- No requiere parámetros.

#### Postcondiciones

- Inicializa una colección vacía de tripulantes.

#### Invariantes de la clase

- La colección interna está inicializada y no contiene elementos `null`.
- No se almacena dos veces la misma instancia de `Tripulante`.
- Hay como máximo un tripulante con cargo `CAPITAN`.
- La colección puede estar vacía o no satisfacer los requisitos de una tripulación válida.

### `agregarTripulante`

```java
public void agregarTripulante(Tripulante tripulante);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si el tripulante no es `null`, no está ya agregado y no introduce un segundo capitán, lo agrega al final de la colección.
- Si el tripulante es `null`, ya está agregado o introduce un segundo capitán, lanza `IllegalArgumentException` y la colección no cambia.

### `tieneCapitan`

```java
public boolean tieneCapitan();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve `true` si la colección contiene un tripulante cuyo cargo es `CAPITAN`; de lo contrario, devuelve `false`.
- No modifica la colección.

### `esValida`

```java
public boolean esValida();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve `true` si la colección contiene al menos cinco tripulantes y un capitán; de lo contrario, devuelve `false`.
- No modifica la colección.

### `getTripulantes`

```java
public ArrayList<Tripulante> getTripulantes();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve una nueva lista con los mismos tripulantes y en el mismo orden.

### `getCantidadTripulantes`

```java
public int getCantidadTripulantes();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la cantidad de tripulantes almacenados sin modificar la colección.

### `liquidarHaberes`

```java
public ArrayList<Liquidacion> liquidarHaberes();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve una liquidación por cada tripulante, en el mismo orden de la colección.
- Si la colección está vacía, devuelve una lista vacía.
- No modifica la colección de tripulantes.

---

## `Tripulante`

### Responsabilidad

Representa a un miembro de la tripulación con identificador, antigüedad, nombre, cargo, origen y cantidad de consejos registrados.

### Constructor

```java
public Tripulante(int id, int antiguedad, String nombre, Cargo cargo, Origen planetaOrigen);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Almacena los valores recibidos e inicializa la cantidad de consejos en cero.
- Si `antiguedad` es negativa, `nombre` es `null` o está en blanco, o `cargo` o `planetaOrigen` son `null`, lanza `IllegalArgumentException`.

#### Invariantes de la clase

- `id`, antigüedad, nombre, cargo y origen permanecen inmutables después de construir el objeto.
- La antigüedad no es negativa; el nombre no es nulo ni está en blanco; el cargo y el origen no son nulos.
- La cantidad de consejos nunca es negativa y solo puede incrementarse para un `CONSEJERO`.

### `getId`

```java
public int getId();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el identificador recibido en el constructor, sin modificar el objeto.

### `getAntiguedad`

```java
public int getAntiguedad();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la antigüedad recibida en el constructor, sin modificar el objeto.

### `getNombre`

```java
public String getNombre();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el nombre recibido en el constructor, sin modificar el objeto.

### `getCargo`

```java
public Cargo getCargo();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el cargo recibido en el constructor, sin modificar el objeto.

### `getPlanetaOrigen`

```java
public Origen getPlanetaOrigen();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el origen recibido en el constructor, sin modificar el objeto.

### `getCantidadConsejos`

```java
public int getCantidadConsejos();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la cantidad de consejos registrada hasta el momento, sin modificar el objeto.

### `registrarConsejo`

```java
public void registrarConsejo();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si el cargo es `CONSEJERO`, incrementa en uno la cantidad de consejos.
- Para cualquier otro cargo, no modifica el objeto.

### `liquidarHaberes`

```java
public Liquidacion liquidarHaberes();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve una liquidación compuesta por sueldo base, antigüedad y subsidio de origen.
- Si el cargo es `CONSEJERO`, añade el decorador de adicional por consejos.
- No modifica los datos del tripulante ni su cantidad de consejos.

### `toString`

```java
public String toString();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el nombre, cargo, planeta de origen y antigüedad en una cadena.
- No modifica el objeto.

---

## `Recursos`

### Responsabilidad

Administra combustible, energía y desgaste de una nave, validando sus límites y disponibilidad.

### Constructor

```java
public Recursos(int combustible, int energia);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si `combustible` o `energia` están fuera del rango de 0 a 100 inclusive, lanza `IllegalArgumentException`.
- Si los valores son válidos, los almacena y establece el desgaste inicial en 0.

#### Invariantes de la clase

- Combustible y energía se mantienen entre 0 y 100 inclusive.
- El desgaste se mantiene entre 0 y 100 inclusive.

### `cargarCombustible`

```java
public void cargarCombustible(int cantidad) throws OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si `cantidad` es positiva y el combustible resultante no supera 100, incrementa el combustible en esa cantidad.
- Si `cantidad` no es positiva o la carga supera la capacidad máxima, lanza `OperacionRecursoInvalidaExcepcion` y no modifica el combustible.

### `cargarEnergia`

```java
public void cargarEnergia(int cantidad) throws OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si `cantidad` es positiva y la energía resultante no supera 100, incrementa la energía en esa cantidad.
- Si `cantidad` no es positiva o la carga supera la capacidad máxima, lanza `OperacionRecursoInvalidaExcepcion` y no modifica la energía.

### `realizarMantenimiento`

```java
public void realizarMantenimiento();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Restablece el desgaste a 0; no modifica combustible ni energía.

### `requiereMantenimiento`

```java
public boolean requiereMantenimiento();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve `true` si el desgaste es mayor o igual a 80; de lo contrario, devuelve `false`.
- No modifica los recursos.

### `consumirCombustible`

```java
public void consumirCombustible(int cantidad)
  throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si `cantidad` es mayor o igual a cero y no supera el combustible disponible, lo resta del combustible.
- Si `cantidad` es negativa, lanza `OperacionRecursoInvalidaExcepcion`; si supera el combustible disponible, lanza `RecursoInsuficienteExcepcion`.
- Si lanza una excepción, el combustible no cambia.

### `consumirEnergia`

```java
public void consumirEnergia(int cantidad)
  throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si `cantidad` es mayor o igual a cero y no supera la energía disponible, la resta de la energía.
- Si `cantidad` es negativa, lanza `OperacionRecursoInvalidaExcepcion`; si supera la energía disponible, lanza `RecursoInsuficienteExcepcion`.
- Si lanza una excepción, la energía no cambia.

### `aumentarDesgaste`

```java
public void aumentarDesgaste(int cantidad) throws OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si `cantidad` es mayor o igual a cero y el desgaste resultante no supera 100, incrementa el desgaste en esa cantidad.
- Si `cantidad` es negativa o el desgaste resultante supera 100, lanza `OperacionRecursoInvalidaExcepcion` y no modifica el desgaste.

### `getCombustible`

```java
public int getCombustible();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el combustible actual sin modificar los recursos.

### `getEnergia`

```java
public int getEnergia();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la energía actual sin modificar los recursos.

### `getDesgaste`

```java
public int getDesgaste();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el desgaste actual sin modificar los recursos.

### `verificarDisponibilidadRecursos`

```java
public void verificarDisponibilidadRecursos(int combustibleNecesario, int energiaNecesaria, int desgasteGenerado)
  throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si algún requerimiento es negativo, lanza `OperacionRecursoInvalidaExcepcion`.
- Si no hay suficiente combustible o energía, lanza `RecursoInsuficienteExcepcion`.
- Si el desgaste actual más `desgasteGenerado` supera 100, lanza `OperacionRecursoInvalidaExcepcion`.
- Si todos los requisitos se cumplen, termina normalmente sin modificar los recursos.

---

## `Nave`

### Responsabilidad

Clase abstracta que agrupa los recursos, el motor Warp y la tripulación de una nave. Delega sus operaciones en los componentes correspondientes.

### Constructor

```java
public Nave(String nombre, int combustible, int energia);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- El nombre recibido queda almacenado en la nave.
- Se crea un `MotorWarp` asociado a la nave y se inicializa en el estado `Disponible`.
- Se crea una `Tripulacion` vacía.
- Se crea un `Recursos` con el combustible y la energía iniciales recibidos.
- Si `combustible` o `energia` quedan fuera del rango de 0 a 100, la construcción lanza `IllegalArgumentException`.

#### Invariantes

- La nave siempre mantiene referencias no nulas a `Recursos`, `Tripulacion` y `MotorWarp`.
- El desgaste actual de la nave nunca es negativo y queda controlado por `Recursos`.
- La tripulación y el motor Warp no pueden quedar en un estado no inicializado.

### `getNombre`

```java
public String getNombre();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el nombre asignado a la nave en el constructor.
- No modifica el estado interno de la nave.

### `getEstadoActual`

```java
public String getEstadoActual();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve una representación textual con el combustible, la energía, el desgaste, si requiere mantenimiento y el estado actual del motor Warp.
- No altera el estado de la nave.

#### Invariantes

- El texto devuelto refleja el estado actual de la nave en el momento de la consulta.

### `agregarTripulante`

```java
public void agregarTripulante(Tripulante tripulante);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Delega la acción en la tripulación.
- Si `tripulante` es `null`, ya está agregado o intenta agregar un segundo capitán, lanza `IllegalArgumentException`.
- En caso contrario, el tripulante queda incorporado a la nave.

#### Invariantes

- La tripulación no contiene referencias `null`.
- No puede existir más de un capitán en la tripulación.

### `getTripulantes`

```java
public ArrayList<Tripulante> getTripulantes();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la lista de tripulantes actual de la nave.
- No modifica la tripulación interna.

### `tieneTripulacionValida`

```java
public boolean tieneTripulacionValida();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve `true` si la tripulación tiene al menos cinco tripulantes y posee un capitán.
- Devuelve `false` en caso contrario.
- No modifica la tripulación.

### `liquidarHaberesTripulacion`

```java
public ArrayList<Liquidacion> liquidarHaberesTripulacion();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve una liquidación por cada tripulante, en el mismo orden en que se registran.
- Si la tripulación está vacía, devuelve una lista vacía.
- No altera la tripulación ni los datos de los tripulantes.

### `getCombustible`

```java
public int getCombustible();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el combustible actual de la nave.
- No modifica los recursos de la nave.

### `cargarCombustible`

```java
public void cargarCombustible(int cantidad) throws OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si la cantidad es positiva y el combustible final no supera el máximo, aumenta el combustible de la nave.
- Si la cantidad es negativa o excede la capacidad máxima, lanza `OperacionRecursoInvalidaExcepcion` y la nave no cambia.

### `consumirCombustible`

```java
public void consumirCombustible(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si la cantidad es válida y no supera el combustible disponible, reduce el combustible actual.
- Si la cantidad es negativa, lanza `OperacionRecursoInvalidaExcepcion`.
- Si la cantidad supera el combustible disponible, lanza `RecursoInsuficienteExcepcion`.
- En ambos casos de error, el combustible no se modifica.

### `getEnergia`

```java
public int getEnergia();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la energía actual de la nave.
- No modifica el estado de los recursos.

### `cargarEnergia`

```java
public void cargarEnergia(int cantidad) throws OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si la cantidad es positiva y la energía final no supera el máximo, aumenta la energía de la nave.
- Si la cantidad es negativa o excede la capacidad máxima, lanza `OperacionRecursoInvalidaExcepcion` y la nave no cambia.

### `consumirEnergia`

```java
public void consumirEnergia(int cantidad) throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si la cantidad es válida y no supera la energía disponible, reduce la energía actual.
- Si la cantidad es negativa, lanza `OperacionRecursoInvalidaExcepcion`.
- Si la cantidad supera la energía disponible, lanza `RecursoInsuficienteExcepcion`.
- En ambos casos de error, la energía no se modifica.

### `getDesgaste`

```java
public int getDesgaste();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el desgaste acumulado actual de la nave.
- No modifica el estado de la nave.

### `aumentarDesgaste`

```java
public void aumentarDesgaste(int cantidad) throws OperacionRecursoInvalidaExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si la cantidad es positiva y el desgaste resultante no supera el máximo, aumenta el desgaste de la nave.
- Si la cantidad es negativa o el desgaste resultante supera el máximo, lanza `OperacionRecursoInvalidaExcepcion` y no modifica el desgaste.

### `realizarMantenimiento`

```java
public void realizarMantenimiento();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Reinicia el desgaste de la nave a 0.
- No modifica el combustible ni la energía.

### `requiereMantenimiento`

```java
public boolean requiereMantenimiento();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve `true` si el desgaste es mayor o igual a 80.
- Devuelve `false` en caso contrario.
- No modifica el estado de la nave.

### `verificarDisponibilidadRecursos`

```java
public void verificarDisponibilidadRecursos(int combustibleNecesario, int energiaNecesaria, int desgasteGenerado)
  throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion;
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Si algún valor requerido es negativo, lanza `OperacionRecursoInvalidaExcepcion`.
- Si el combustible o la energía disponibles no alcanzan los mínimos, lanza `RecursoInsuficienteExcepcion`.
- Si el desgaste futuro supera el máximo permitido, lanza `OperacionRecursoInvalidaExcepcion`.
- Si todo es válido, finaliza sin modificar los recursos de la nave.

### `getEstadoMotorWarp`

```java
public String getEstadoMotorWarp();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el nombre del estado actual del motor Warp.
- No modifica el estado del motor ni de la nave.

### `prepararSalto`

```java
public Evento prepararSalto();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Delegada en `MotorWarp` la operación de preparación del salto.
- Devuelve el `Evento` producido por la transición del motor.
- No registra el evento en una `Bitacora`.

### `saltar`

```java
public Evento saltar();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Delegada en `MotorWarp` la operación de salto.
- Devuelve el `Evento` generado por la transición del motor.
- No modifica los recursos de la nave de forma directa.

### `enfriar`

```java
public Evento enfriar();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Delegada en `MotorWarp` la operación de enfriamiento.
- Devuelve el `Evento` generado por la transición del motor.
- No registra ni persiste el evento en una bitácora.

#### Invariantes

- La nave siempre conserva un `MotorWarp` válido.
- Las operaciones del motor solo pueden cambiar su estado a través de la lógica definida por `MotorWarp`.
- El estado de la tripulación y los recursos de la nave solo cambian cuando se ejecutan operaciones explícitas sobre ellos.

### Subclases

- `NaveExploradora` inicia con 60 unidades de combustible y 80 de energía.
- `NaveCarguero` inicia con 100 unidades de combustible y 60 de energía.
- `NaveCombate` inicia con 80 unidades de combustible y 100 de energía.

---

## `Mision`

### Responsabilidad

Clase abstracta que define el flujo común de una misión mediante el patrón Template Method. Las subclases implementan la ejecución y la evaluación del resultado.

### Constructor

```java
public Mision(String nombre, int combustibleNecesario, int energiaNecesaria, int desgasteNecesario);
```
#### Precondiciones

- Sin precondiciones.

#### Postcondiciones

- Se crea una mision con el nombre y los recursos indicados.
- Inicializa el resultado como `FALLO`, los consumos en cero y las listas de acciones y observaciones vacías.


### `realizarMision`

```java
public final InformeMision realizarMision(AsistenteComando asistente);
```

#### Precondiciones

- `asistente` no debe ser `null`.

#### Postcondiciones

- Ejecuta, en orden, `preparar`, `ejecutar`, `evaluar` y `finalizar`.
- Si la tripulación o los recursos no cumplen los requisitos, o si ocurre una `OperacionRecursoInvalidaExcepcion`, `RecursoInsuficienteExcepcion` o `TripulacionInvalidaExcepcion`, establece el resultado `FALLO` y agrega el mensaje de la excepción a las observaciones. Estas excepciones no se propagan al llamador.
- Para las excepciones anteriores, continúa con `finalizar` y devuelve un `InformeMision`.
- Si el resultado es `EXITO`, ordena el salto Warp antes de obtener el estado final de la nave para el informe.
- Registra en la bitácora el informe como evento de tipo `INFORME`. Al superar la preparación, también registra el inicio como evento de tipo `MISION`.

#### Invariantes

- El orden del flujo de ejecución no puede ser alterado por las subclases porque `realizarMision` es `final`.

### Misiones concretas

- `Mision01` ("M-01 - Intercepcion y asistencia"): requiere 4 unidades de combustible, 5 de energía y 4 de desgaste.
- `Mision02` ("M-02 - Recoleccion"): requiere 4 unidades de combustible, 5 de energía y 4 de desgaste.
- `Mision03` ("M-03 - Retorno seguro"): requiere 4 unidades de combustible, 0 de energía y 4 de desgaste.
- Cada misión concreta registra una acción principal y establece `EXITO` si su ejecución termina y la evaluación correspondiente se completa; ante las excepciones de dominio capturadas, el resultado es `FALLO`.

---

## `InformeMision`

### Responsabilidad

Contiene el resultado, las acciones, los recursos consumidos, el estado final de la nave y las observaciones de una misión.

### Constructor

```java
public InformeMision(
  String misionEjecutada,
  ResultadoMision resultado,
  ArrayList<String> accionesPrincipales,
  int combustibleConsumido,
  int energiaConsumida,
  int desgasteGenerado,
  String estadoFinalNave,
  ArrayList<String> observaciones);
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Almacena los valores recibidos, incluidas las referencias a las listas, sin crear copias.

### `getMisionEjecutada`

```java
public String getMisionEjecutada();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el nombre de misión almacenado.

### `getResultado`

```java
public ResultadoMision getResultado();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el resultado almacenado.

### `getAccionesPrincipales`

```java
public ArrayList<String> getAccionesPrincipales();
```

#### Precondiciones

- La lista de acciones almacenada debe ser no nula.

#### Postcondiciones

- Devuelve una copia superficial de la lista de acciones.
- Modificar la lista devuelta no modifica la lista almacenada.

### `getCombustibleConsumido`

```java
public int getCombustibleConsumido();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el combustible consumido almacenado.

### `getEnergiaConsumida`

```java
public int getEnergiaConsumida();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve la energía consumida almacenada.

### `getDesgasteGenerado`

```java
public int getDesgasteGenerado();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el desgaste generado almacenado.

### `getEstadoFinalNave`

```java
public String getEstadoFinalNave();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve el estado final almacenado, que puede ser `null`.

### `getObservaciones`

```java
public ArrayList<String> getObservaciones();
```

#### Precondiciones

- La lista de observaciones almacenada debe ser no nula.

#### Postcondiciones

- Devuelve una copia superficial de la lista de observaciones.
- Modificar la lista devuelta no modifica la lista almacenada.

### `agregarAccionPrincipal`

```java
protected void agregarAccionPrincipal(String accion);
```

#### Precondiciones

- La lista de acciones almacenada debe ser no nula.

#### Postcondiciones

- Agrega `accion` al final de la lista almacenada; admite `null`.

### `agregarObservacion`

```java
protected void agregarObservacion(String observacion);
```

#### Precondiciones

- La lista de observaciones almacenada debe ser no nula.

#### Postcondiciones

- Agrega `observacion` al final de la lista almacenada; admite `null`.

### `toString`

```java
public String toString();
```

#### Precondiciones

- Ninguna.

#### Postcondiciones

- Devuelve una representación textual de la misión, resultado, acciones, recursos consumidos, estado final y observaciones.
- No modifica el informe.
