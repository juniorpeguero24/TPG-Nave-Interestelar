# Informe general de diseño

Este proyecto sigue una organización por dominios para mantener la lógica de negocio clara y extensible. La separación en paquetes como `nave`, `tripulacion`, `mision`, `haberes`, `motorwarp`, `bitacora` y `excepciones` refleja una arquitectura centrada en responsabilidades bien definidas.

## Decisiones de diseño

- Modularización del dominio: cada paquete agrupa conceptos del negocio relacionados, lo que facilita mantenimiento, pruebas y evolución.
- Uso de patrones de diseño: la fábrica `NaveFactory` encapsula la creación de tipos de nave; el patrón `Decorator` en `LiquidacionDecorator` permite sumar conceptos salariales sin alterar la lógica base; el motor warp con el patron `State` usa estados para representar transiciones de comportamiento. Las misiones utilizan el patron `Template` en la superclase Mision para seguir el paso a paso y luego especifican sus pasos particulares en las subclases.
- Encapsulamiento y validación: entidades y servicios controlan su estado y rechazan entradas inválidas con excepciones específicas, reduciendo errores en tiempo de ejecución.
- Cohesión en la coordinación: `Universo` actúa como orquestador de asistentes y simulación, evitando acoplar la lógica de decisiones a clases concretas.

## Principios SOLID aplicados

- SRP (Single Responsibility Principle): cada clase tiene una responsabilidad concreta, por ejemplo, gestionar una nave, calcular una liquidación o registrar eventos.
- OCP (Open/Closed Principle): se amplía funcionalidad agregando nuevos tipos de naves, estados del motor o conceptos salariales sin cambiar la estructura base.
- LSP (Liskov Substitution Principle): las implementaciones especializadas de `Nave` y de conceptos del dominio pueden sustituir la abstracción base sin romper el comportamiento esperado.
- ISP (Interface Segregation Principle): las interfaces son específicas y enfocadas en comportamientos mínimos, evitando contratos inflados.
- DIP (Dependency Inversion Principle): el dominio depende de abstracciones y no de implementaciones concretas, favoreciendo la flexibilidad y la prueba unitaria.

En conjunto, el diseño busca un equilibrio entre claridad, extensibilidad y separación de responsabilidades.


<br><br>

# Entrega 2: operación dentro de un universo dinámico

En la segunda entrega el núcleo de la Entrega 1 se integra con el tablero y el
`Destino` provistos por la cátedra, se agrega una interfaz gráfica en Swing, un
módulo de concurrencia y la persistencia de usuarios. Se continúa el mismo
proyecto: las clases de la Entrega 1 se amplían, no se reemplazan.

## Arquitectura general

El sistema se organiza en capas con responsabilidades separadas:

| Capa | Paquetes | Responsabilidad |
| --- | --- | --- |
| Vista | `vistas` | Mostrar datos y capturar eventos del usuario (Swing). Sin lógica de negocio. |
| Controlador | `controlador` | Traducir los eventos de la vista en operaciones del modelo y coordinar la respuesta. |
| Modelo | `modelo.nave`, `modelo.mision`, `modelo.tripulacion`, `modelo.universo`, `modelo.tablero`, `modelo.bitacora`, `modelo.haberes`, `modelo.motorwarp` | Lógica de negocio y reglas del dominio. No conoce Swing. |
| Concurrencia | `modelo.concurrencia` | Simulación del universo paralelo (productores y consumidores). |
| Persistencia | `modelo.persistencia` | Acceso a datos de usuarios (DAO y DTO sobre H2). |
| Observadores | `observer` | Interfaces para las actualizaciones automáticas del modelo hacia la vista. |

El punto de entrada es `Main`, que inicializa la base de datos, crea el `Universo`
e inicia la pantalla de acceso. El `Navegador` arma el tablero y el `Destino` y
coordina el paso entre las ventanas.

## Decisiones de diseño incorporadas

- **Separación MVC y Observer.** Las acciones del usuario siguen el recorrido
  vista, `ActionListener`, controlador y modelo. Los cambios que genera el modelo
  por su cuenta (recursos de la nave, bitácora, informes de misión y eventos de
  la plataforma) llegan a la vista mediante Observer, de modo que el dominio solo
  conoce interfaces y no clases de Swing.
- **Misiones con cuatro fases sobre el tablero de la cátedra.** `Mision` conserva
  el método plantilla del Template Method con las fases preparar, ejecutar,
  evaluar y cerrar. En `preparar` se verifican todos los recursos antes de
  consumir nada y se calcula la ruta con el radar de largo alcance. En `ejecutar`
  se consumen los recursos iniciales (4 de combustible, 4 de desgaste y la energía
  propia de cada misión) y se recorre la ruta por turnos: la nave inspecciona su
  casilla e interactúa con el elemento que encuentre, escanea con el radar de
  corto alcance, salta y luego el `Destino` mueve los elementos del universo.
  `evaluar` decide el resultado, calcula el balance de lo ganado o entregado y
  genera el informe; `cerrar` aplica ese balance sobre la nave. La nave, los
  asteroides y las estaciones de carga implementan `ElementoDelUniverso`, por lo
  que el tablero los trata de forma uniforme.
- **Motor Warp con State y resultado de salto.** Las operaciones del motor son
  `saltar()` y `saltarWarp(...)`. Los estados deciden el tipo de salto, los
  casilleros que avanza la nave y el estado siguiente; `Nave` aplica los costos
  (2 de combustible por salto normal, 5 de energía por salto Warp y 5 de desgaste
  en el enfriamiento). Ante un elemento en la ruta, la misión intenta un salto
  Warp, que avanza dos casilleros.
- **Encuentros entre naves con Double Dispatch.** El resultado depende solo del
  tipo de cada nave. Se eliminan los condicionales por tipo y agregar un tipo de
  nave obliga a resolver su comportamiento frente a los demás.
- **Concurrencia con monitores.** La `PlataformaDeCarga` es el monitor del
  problema Productor-Consumidor. Las pausas de la simulación se ubican en los
  hilos y no dentro de los métodos sincronizados, para no mantener tomado el
  monitor mientras un hilo duerme.
- **Persistencia con DAO y DTO.** Solo se persisten las credenciales de los
  usuarios. El controlador depende de la interfaz `IUsuarioDAO`, de modo que la
  base de datos puede reemplazarse sin modificarlo.
- **Manejo de errores.** Además de las excepciones de la Entrega 1 se agregaron
  excepciones propias (`NaveInvalidaException`, `TripulanteInvalidoException`,
  `TripulanteRepetidoException`, `CapitanExistenteException`). Las validaciones
  de formato se hacen en la vista y el controlador, las reglas de negocio en el
  dominio y las consultas en el DAO.

## Principios SOLID en la Entrega 2

- **SRP:** cada capa tiene una responsabilidad; los controladores coordinan, las
  vistas muestran y el modelo decide.
- **OCP:** se pueden agregar nuevos elementos del universo implementando
  `ElementoDelUniverso`, nuevos observadores implementando una interfaz de
  `observer`, o nuevas vistas, sin modificar los sujetos.
- **LSP:** `Nave`, `Asteroide` y `EstacionCarga` pueden usarse indistintamente
  donde se espera un `ElementoDelUniverso`.
- **ISP:** las interfaces de observador declaran un único método, específico de
  lo que cada sujeto notifica.
- **DIP:** `ControladorAcceso` depende de `IUsuarioDAO` y el modelo depende de
  interfaces de observador, no de clases concretas de la interfaz gráfica.

## Bibliotecas externas

- **H2 (`com.h2database:h2` 2.3.232):** base de datos embebida en archivo. Se
  eligió para que todos los integrantes trabajen sin instalar un servidor ni
  usar credenciales personales.
- **`org.jetbrains:annotations`:** anotaciones de uso en el IDE.

## Limitaciones conocidas y trabajo futuro

- Las contraseñas se almacenan sin hash.
- La nave aliada de la misión M-01 no existe como objeto del tablero: se modela
  como el casillero objetivo, que el tablero mantiene libre de elementos. El
  auxilio solicitado se sortea entre 0 y 50 de energía y de combustible.
- Las gemas de M-02 se acumulan en la nave y se informan en su estado; no se
  persisten.
- El informe de una misión refleja el estado de la nave previo a `cerrar`. Lo que
  se gana o entrega al cerrar figura en el balance (consumos y observaciones).
- El tablero de la aplicación es de 10 por 10, definido en `Navegador`.
- En el motor Warp, el enfriamiento y los dos turnos de preparación se cumplen
  después del salto Warp.