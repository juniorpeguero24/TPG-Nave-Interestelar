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
public EventoMotorWarp prepararSalto() {
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
    String nombre();
    EventoMotorWarp prepararSalto();
    EventoMotorWarp iniciarWarp();
    EventoMotorWarp finalizarWarp();
    EventoMotorWarp finalizarEnfriamiento();
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
    -- iniciarWarp() -->
En Warp
    -- finalizarWarp() -->
Enfriamiento
    -- finalizarEnfriamiento() -->
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
`paquete.motorwarp`. Es una decisión adecuada para mantener encapsulada la
implementación del patrón.
