# Contratos

Este documento describe las responsabilidades, precondiciones, postcondiciones e invariantes de todas las clases.

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

- No requiere parámetros.

#### Postcondiciones

- Se crea una bitácora vacía.
- La colección interna de eventos queda inicializada.

#### Invariantes

- La bitácora solo contiene referencias a objetos `Evento` no nulas.

### `registraEvento`

```java
public void registraEvento(Evento evento);
```

#### Precondiciones

-

#### Postcondiciones

- Si `evento` no es `null`, queda agregado a la bitácora.
- Si `evento` es `null`, la bitácora no cambia.
- Los eventos ya registrados conservan su orden.

#### Invariantes

- La cantidad de eventos no disminuye.
- Ningún elemento almacenado es `null`.

### `consultaEvento`

```java
public ArrayList<Evento> consultaEvento(LocalDateTime desde, LocalDateTime hasta);
```

#### Precondiciones

- `desde` no debe ser `null`.
- `hasta` no debe ser `null`.
- `desde` debe ser anterior o igual a `hasta`.

#### Postcondiciones

- Devuelve un `ArrayList<Evento>`.
- Incluye todos los eventos cuya fecha cumple (Desde =< fecha <= hasta)
- Devuelve una lista vacía si no hay eventos que cumplan el filtro.
- No modifica los registros de la bitácora.

#### Invariantes

- Consultar la bitácora no elimina ni agrega eventos.
- El orden de los eventos devueltos coincide con el orden en que fueron
  registrados.


## `EstadoWarp`

### Responsabilidad

`EstadoWarp` define el contrato común que deben cumplir todos los estados del motor Warp. 
Cada implementación determina qué operaciones son válidas para el estado actual y cuál es el siguiente estado cuando se realiza una transición válida.

Las clases que implementan esta interfaz son `Disponible`,
`PreparandoSalto`, `EnWarp` y `Enfriamiento`.

### `nombre`

```java
String nombre();
```

#### Precondiciones

- El estado debe estar correctamente inicializado.

#### Postcondiciones

- Devuelve un nombre no nulo que identifica el estado actual.

#### Invariantes

- El nombre identifica al estado que implementa la interfaz.
- No modifica el estado del motor.

### Operaciones de transición

```java
EventoMotorWarp prepararSalto();
EventoMotorWarp iniciarWarp();
EventoMotorWarp finalizarWarp();
EventoMotorWarp finalizarEnfriamiento();
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
public EventoMotorWarp prepararSalto();
public EventoMotorWarp iniciarWarp();
public EventoMotorWarp finalizarWarp();
public EventoMotorWarp finalizarEnfriamiento();
```

#### Precondiciones

- El motor debe estar inicializado.

#### Postcondiciones generales

- Se delega la operación al estado actual.
- Se devuelve un `Evento` no nulo.
- Si la transición es válida:
  - el motor cambia al estado siguiente;
  - el evento informa el cambio de estado.
- Si la transición es inválida:
  - el motor conserva su estado actual;
  - el evento informa `"Transicion inválida"`.

#### Invariantes

- El motor siempre permanece en uno de los estados definidos.
- Una transición inválida no cambia el estado.
- Cada invocación devuelve un evento que puede ser registrado por `Nave`.

### `cambiarEstado`

```java
EventoMotorWarp cambiarEstado(EstadoWarp estado);
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
EventoMotorWarp registrarError();
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

## `Liquidacion`

### Responsabilidad

Define el contrato para la consulta y cálculo de conceptos remunerativos dentro del esquema de liquidación de haberes.

### `calcularTotal`

```java
double calcularTotal();
```

### Precondiciones

- El componente debe estar inicializado.

### Postcondiciones

- Devuelve el valor total acumulado en PG (mayor o igual a 0).
- No altera el estado interno de los objetos.

#### `obtenerDetalle`

```java
String obtenerDetalle();
```

### Precondiciones

- El componente debe estar inicializado.

### Postcondiciones

- Devuelve una cadena no vacía con los conceptos aplicados y sus montos.

### `SueldoBase`

### Responsabilidad

Componente base que asigna la remuneración inicial según el cargo del tripulante.

```Java
public SueldoBase(Cargo cargo);
```

### Precondiciones

- cargo no debe ser null.

### Postcondiciones

- Asigna el monto base correspondiente al cargo según el dominio.

### Invariantes

- El sueldo base nunca es negativo.
- El cargo asociado permanece inmutable.

### `LiquidacionDecorator`

### Responsabilidad

Clase abstracta base para componer conceptos remunerativos delegando en el componente envuelto.

```Java
public LiquidacionDecorator(Liquidacion envoltorio);
```

### Precondiciones

- envoltorio no debe ser null.

### Postcondiciones

- Almacena la referencia al componente interno decorado.

### Invariantes

- La referencia interna envoltorio nunca es null.

### `SubsidioOrigen`

### Responsabilidad

Añade el subsidio fijo según el planeta de origen del tripulante.

```Java
public SubsidioOrigen(Liquidacion envoltorio, Origen origen);
```

### Precondiciones

- envoltorio no debe ser null.
- origen no debe ser null.

### Postcondiciones

- Suma el subsidio correspondiente al total acumulado.
- Agrega la descripción del subsidio al detalle.

### `Antiguedad`

### Responsabilidad

Calcula y agrega el adicional por años de servicio según el cargo del tripulante.

```Java
public Antiguedad(Liquidacion envoltorio, Cargo cargo, int antiguedad);
```

### Precondiciones

- envoltorio no debe ser null.
- cargo no debe ser null.
- antiguedad debe ser mayor o igual a cero (antiguedad >= 0).

### Postcondiciones

- Suma el adicional por año correspondiente al cargo sobre su haber base.
- Registra el desglose de años en el detalle.

### `AdicionalConsejeros`

### Responsabilidad

Añade el adicional por consejos brindados durante el período liquidado.

```Java
public AdicionalConsejeros(Liquidacion envoltorio, int cantidadConsejos);
```

### Precondiciones

- envoltorio no debe ser null.
- cantidadConsejos debe ser mayor o igual a cero (cantidadConsejos >= 0).

### Postcondiciones

- Agrega 2 PG por cada consejo registrado al monto total.
- Incorpora la cantidad de consejos computados al detalle.

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

### Postcondiciones
- Inicializa la colección interna de asistentes vacía.   
- naveEnOperacion queda sin asignar (null).   

### Invariantes
- La colección de asistentes no contiene referencias nulas.

### `registrarAsistente`

```Java
public void registrarAsistente(AsistenteComando asistente);
```

### Precondiciones
- asistente no debe ser null.   

### Postcondiciones
- El asistente queda añadido a la colección del universo.   
- Se incrementa la cantidad de naves registradas en 1.   

### Invariantes
- La cantidad de asistentes registrados nunca disminuye.   

### `seleccionarNaveParaOperar`

```Java
public AsistenteComando seleccionarNaveParaOperar(int indice);
```

### Precondiciones
- indice debe ser un valor válido: 0 <= indice < getCantidadNaves().   

### Postcondiciones
- Establece y devuelve el asistente correspondiente al índice como la nave activa en operación.   
- Garantiza que solo un asistente esté en operación en un momento dado.

### Invariantes
- No modifica la cantidad ni el orden de los asistentes almacenados.   

### `AsistenteComando`

### Responsabilidad
Es la entidad inteligente que gobierna una única nave. Actúa como intermediario obligatorio para toda orden o consulta externa (recursos, motor warp y bitácora).   

`Constructor`

```Java
public AsistenteComando(Nave nave);
```

### Precondiciones
- nave no debe ser null.   

### Postcondiciones
- Queda asociado bidireccionalmente a la nave indicada.   

### Invariantes
- Cada asistente opera exactamente una única nave a lo largo de su ciclo de vida.   

### `verificarDisponibilidadParaMision`

```Java
public void verificarDisponibilidadParaMision(int combustibleNecesario, int energiaNecesaria, int desgasteGenerado)
throws RecursoInsuficienteExcepcion, OperacionRecursoInvalidaExcepcion;
```

### Precondiciones
- combustibleNecesario >= 0, energiaNecesaria >= 0, desgasteGenerado >= 0.   

### Postcondiciones
- Si el motor está en estado "Disponible" y la nave cuenta con combustible, energía y margen de desgaste suficiente, la ejecución concluye normalmente.   
- Si el motor no está disponible, lanza OperacionRecursoInvalidaExcepcion.   
- Si alguno de los recursos es insuficiente, lanza RecursoInsuficienteExcepcion.   
- No altera los recursos de la nave.   

### Invariantes
- No modifica el estado del motor ni los consumos de la nave si se produce una falla.   

### `OrdenarSaltoWarp`

```Java
public void ordenarSaltoWarp();
```

### Precondiciones
- El motor de la nave debe encontrarse en estado "Disponible".   

### Postcondiciones
- Ejecuta en secuencia: prepararSalto() -> iniciarWarp() -> finalizarWarp().   
- El motor concluye en estado "Disponible".   
- Registra cada transición como un Evento inmutable en la bitácora.   

### Invariantes
- La bitácora incrementa su tamaño según los eventos generados.   

### `MisionResponsabilidad`

Clase abstracta que define la plantilla y precondiciones generales para cualquier misión encomendada a un asistente.realizarMision (Template Method)
```Java
public final void realizarMision(AsistenteComando asistente)
throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion;
```

### Precondiciones
- asistente no debe ser null.   

### Postcondiciones
- Ejecuta en orden invariable: preparar -> ejecutar -> evaluar -> finalizar.
- Si la verificación de recursos o motor falla, aborta lanzando la excepción correspondiente y no consume recursos de la nave.   
- Si la misión finaliza con éxito, ordena el salto warp al asistente y asienta el evento de éxito en la bitácora.   
- Si la misión falla, asienta el evento de fallo en la bitácora sin ordenar salto warp.

### Invariantes
- El flujo de ejecución permanece inalterable para cualquier subclase.
- Toda misión registra su desenlace en la bitácora del asistente.