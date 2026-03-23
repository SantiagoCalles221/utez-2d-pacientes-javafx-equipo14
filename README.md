# Integradora-Stanle-SantiagoC

Este proyecto es una aplicación de escritorio desarrollada para la integradora de Programacion/POO.
El sistema permite administrar un directorio de pacientes con persistencia de datos en archivos locales, 
diseñada específicamente para un entorno de consultorio médico pequeño.

El sistema no utiliza bases de datos externas; toda la información se almacena y recupera de un archivo local

Alta: Registro de pacientes con validaciones en tiempo real.\
Consulta: Visualización dinámica mediante un TableView vinculado a una ObservableList.
Actualización: Modificación de datos existentes.\
Borrado Lógico: Implementación de estatus ACTIVO / INACTIVO para conservar la integridad de los datos históricos.

Validaciones de SeguridadCURP: Identificador único para evitar registros duplicados.\
Nombre: Mínimo 5 caracteres.\
Edad: Validación numérica en rango lógico (0 a 120 años).\
Teléfono: Formato de 10 dígitos.\
Campos Obligatorios: Prevención de valores nulos o vacíos mediante Alerts.

Tecnologías Utilizadas
Lenguaje: Java 17+\
Framework UI: JavaFX con FXML y Scene Builder.\
Entorno de Desarrollo: IntelliJ IDEA.\
Version de JDK: temurin 21+

Integrantes
Calles y Carranza Santiago
Reyes Casimiro Stanle