# 🌍✨ SISTEMA DE AGENCIA DE VIAJES ✨🌍
### 🧳 Gestión de Reservas Turísticas en Java

---

## 📖 Descripción

Este proyecto implementa un sistema básico de gestión de una *agencia de viajes* desarrollado en Java. Permite crear viajeros, paquetes turísticos y reservas, aplicando conceptos clave de programación orientada a objetos, patrones de diseño, programación funcional y manejo de errores.

---

## 🚀 Funcionalidades

- Crear viajeros con diferentes tipos (frecuente u ocasional).
- Generar paquetes turísticos (Playa, Montaña, Ciudad).
- Registrar reservas asociando viajeros con paquetes.
- Calcular el costo total de cada reserva.
- Filtrar reservas según un presupuesto ingresado por el usuario.
- Validar entradas del usuario para evitar errores en ejecución.

---

## 🧱 Estructura del Proyecto

### 🔹 Clases principales

- *Persona*
  - Clase base con atributos: nombre e identificación.

- *Viajero*
  - Hereda de Persona.
  - Añade el atributo tipoViajero.

- *PaqueteTuristico*
  - Representa un destino turístico.
  - Contiene destino y precio.

- *Reserva*
  - Relaciona un viajero con un paquete turístico.
  - Calcula el total según número de personas.

---

## 🧩 Patrones de Diseño Implementados

### 🏭 Factory Method
Clase: PaqueteFactory

Permite crear paquetes turísticos según el tipo:

```java
PaqueteTuristico paquete = PaqueteFactory.crearPaquete("Playa");