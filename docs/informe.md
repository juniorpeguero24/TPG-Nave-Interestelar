# Informe general de diseño

Este proyecto sigue una organización por dominios para mantener la lógica de negocio clara y extensible. La separación en paquetes como `nave`, `tripulacion`, `mision`, `haberes`, `motorwarp`, `bitacora` y `excepciones` refleja una arquitectura centrada en responsabilidades bien definidas.

## Decisiones de diseño

- Modularización del dominio: cada paquete agrupa conceptos del negocio relacionados, lo que facilita mantenimiento, pruebas y evolución.
- Uso de patrones de diseño: la fábrica `NaveFactory` encapsula la creación de tipos de nave; el patrón `Decorator` en `LiquidacionDecorator` permite sumar conceptos salariales sin alterar la lógica base; el motor warp usa estados para representar transiciones de comportamiento.
- Encapsulamiento y validación: entidades y servicios controlan su estado y rechazan entradas inválidas con excepciones específicas, reduciendo errores en tiempo de ejecución.
- Cohesión en la coordinación: `Universo` actúa como orquestador de asistentes y simulación, evitando acoplar la lógica de decisiones a clases concretas.

## Principios SOLID aplicados

- SRP (Single Responsibility Principle): cada clase tiene una responsabilidad concreta, por ejemplo, gestionar una nave, calcular una liquidación o registrar eventos.
- OCP (Open/Closed Principle): se amplía funcionalidad agregando nuevos tipos de naves, estados del motor o conceptos salariales sin cambiar la estructura base.
- LSP (Liskov Substitution Principle): las implementaciones especializadas de `Nave` y de conceptos del dominio pueden sustituir la abstracción base sin romper el comportamiento esperado.
- ISP (Interface Segregation Principle): las interfaces son específicas y enfocadas en comportamientos mínimos, evitando contratos inflados.
- DIP (Dependency Inversion Principle): el dominio depende de abstracciones y no de implementaciones concretas, favoreciendo la flexibilidad y la prueba unitaria.

En conjunto, el diseño busca un equilibrio entre claridad, extensibilidad y separación de responsabilidades, adecuado para una simulación de dominio con crecimiento incremental.
