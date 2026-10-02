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
`motorwarp`. Es una decisión adecuada para mantener encapsulada la
implementación del patrón.

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

# Patrón Template Method aplicado a `Mision`

## ¿Qué problema resuelve?

Toda misión en el universo sigue una serie estricta de etapas invariantes que deben ejecutarse en un orden predeterminado (preparar la nave y verificar recursos, ejecutar la tarea específica, evaluar el resultado y finalizar asentando el desenlace y ejecutando maniobras warp si hubo éxito).

Sin el patrón Template Method, cada misión concreta implementaría su propio flujo de control, lo que causaría:
- Duplicación de la lógica de validación de recursos y motor.
- Riesgo de alterar el orden de las etapas de ejecución.
- Dispersión de la responsabilidad de asentar los eventos en la bitácora y ordenar el salto warp.

El patrón Template Method permite fijar el esqueleto del algoritmo de ejecución en una clase abstracta (`Mision`), delegando únicamente los pasos variables (`ejecutar` y `evaluar`) en las subclases concretas.

## Participantes del patrón

### Clase Abstracta (Plantilla): `Mision`

Define el método plantilla `realizarMision` marcado como `final` para impedir que las subclases alteren la estructura del proceso:

```java
public void realizarMision(AsistenteComando asistente) 
        throws OperacionRecursoInvalidaExcepcion, RecursoInsuficienteExcepcion {
    preparar(asistente);
    ejecutar(asistente);
    evaluar(asistente);
    finalizar(asistente);
}
```
- **Pasos concretos (invariantes):**
    - `preparar(AsistenteComando asistente)`: solicita al asistente validar que el motor esté disponible y existan los recursos suficientes (combustible, energía, tolerancia al desgaste) antes de iniciar. Asienta el evento de inicio en la bitácora.
    - `finalizar(AsistenteComando asistente)`: si la evaluación fue exitosa, ordena al asistente ejecutar el salto warp y registra el desenlace; si falló, asienta el evento de fallo.
- **Pasos primitivos (abstractos):**
    - `ejecutar(AsistenteComando asistente)`: implementado por cada misión concreta para consumir los recursos asignados y realizar su tarea específica.
    - `evaluar(AsistenteComando asistente)`: determina y asigna el estado final (`ResultadoMision.EXITO` o `ResultadoMision.FALLO`).

### Clases Concretas

- `Mision01` (Intercepción y asistencia)
- `Mision02` (Recolección)
- `Mision03` (Retorno seguro)

Cada una define sus propios requisitos de recursos en el constructor y concreta las acciones específicas de su labor mediante las órdenes canalizadas por el `AsistenteComando`.

## Intermediación y Desacoplamiento (`AsistenteComando`)

El diseño cumple estrictamente con el principio de mínima sorpresa y bajo acoplamiento: `Mision` no conoce ni interactúa directamente con `Nave`. Toda consulta de estado, verificación y consumo de combustible/energía se le solicita al `AsistenteComando`, quien gobierna la nave y asienta los sucesos en la bitácora.

## Ventajas de esta aplicación

- **Principio Abierto/Cerrado (OCP):** Permite añadir nuevos tipos de misiones sin tocar la estructura del algoritmo ni las misiones existentes.
- **Control centralizado:** Asegura que ninguna misión comience sin recursos ni omita el registro en la bitácora o la transición del motor warp ante un éxito.