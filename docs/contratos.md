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
