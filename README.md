﻿# Quartz Task Scheduler 🕒

Un Scheduler sencillo hecho en Java (con Spring y Quartz) que consume una API pública.

## Tabla de contenidos 📑

- [Quartz Task Scheduler](#quartz-task-scheduler-)
  - [Tabla de contenidos 📑](#tabla-de-contenidos-)
  - [Descripción 📝](#descripción-)
  - [Diagramas 📊](#diagramas-)
  - [Imágenes 📷](#imágenes-)
  - [Requisitos 📋](#requisitos-)
  - [Uso 🚀](#uso-)
  - [Recursos Adicionales 🔗](#recursos-adicionales-)
  - [Licencia 📄](#licencia-)

## Descripción 📝

Un simple y práctico Scheduler hecho en Java.<br>
El mismo permie programar y administrar tareas de forma automática.<br>
Está hecho con la librería Quartz. Utiliza Java 17 (con el framework Spring)y Maven para el manejo de dependencias. <br>
Se puede utilizar para programar tareas de forma automática, como por ejemplo: enviar mails, generar reportes, etc.<br>
En este caso, consulta una API pública y devuelve el resultado en un log.<br>
Todo el proyecto se encuentra documentado, método a método, ya sea con comentarios en el código o en este mismo README.<br>
En caso de necesitar más información, dejo a su disposición diferentes fuentes de las que pueden guiarse para entender mejor el funcionamiento de Quartz junto con Spring.

## Diagramas 📊
Nota: Por si no se ven, estan codeados en Mermaid, pueden ejecutarlos desde la siguiente [página](https://mermaid.live/edit).

Metodología de trabajo de procesos en Quartz

```mermaid
flowchart LR
    style A fill:#504945,stroke:#504945,stroke-width:2px,color:#EBDBB2
    style B fill:#504945,stroke:#504945,stroke-width:2px,color:#EBDBB2
    style C fill:#504945,stroke:#504945,stroke-width:2px,color:#EBDBB2
    style D fill:#504945,stroke:#504945,stroke-width:2px,color:#EBDBB2
    style E fill:#504945,stroke:#504945,stroke-width:2px,color:#EBDBB2
    style F fill:#504945,stroke:#504945,stroke-width:2px,color:#EBDBB2
    style G fill:#504945,stroke:#504945,stroke-width:2px,color:#EBDBB2

    A[Scheduler Factory] -- Crea los --> B[Scheduler] -- Programa --> C[Jobs]
    C -- Se realizan/activan por medio de --> D[Trigger]
    C -- Se guardan por medio de --> E[Job Store]
    B -- Registra --> F[Job Listeners]
    B -- Registra --> G[Trigger Listener]

```

Clases, métodos y como interactuan entre sí

```mermaid
flowchart TB
    subgraph "QuartzConfig.java"
        style A fill:#504945,stroke:#504945,stroke-width:2px,color:#EBDBB2
        A[QuartzConfig] --> B[SpringBeanJobFactory]
    end

    subgraph "QuartzSchedulerJobs.java"
        style C fill:#3C3836,stroke:#3C3836,stroke-width:2px,color:#EBDBB2
        C[QuartzSchedulerJobs] --> D[jobMemberClassPet]
        C[QuartzSchedulerJobs] --> E[triggerMemberClassPet]
    end

    subgraph "PetJob.java"
        style F fill:#665C54,stroke:#665C54,stroke-width:2px,color:#EBDBB2
        F[PetJob] --> G[PetService]
    end

    subgraph "PetService.java"
        style G fill:#9D0006,stroke:#9D0006,stroke-width:2px,color:#EBDBB2
        G[PetService] --> H[getPets]
    end


```

## Imágenes 📷

Para darle algo de color al repositorio :D
[![Screenshot-4.png](https://i.postimg.cc/yNGMkbst/Screenshot-4.png)](https://postimg.cc/2q4ctTq7)
[![Screenshot-3.png](https://i.postimg.cc/4xWkT3MJ/Screenshot-3.png)](https://postimg.cc/RWJb7v48)
[![Screenshot-5.png](https://i.postimg.cc/5tjc9Vd8/Screenshot-5.png)](https://postimg.cc/gw9t4100)

## Requisitos 📋

- [Java](https://www.java.com/es/) (De preferencia, siempre la opción más actalizada)

  Nota: En teoría, debería funcionar con cualquier versión de Java, así como con cualquier JDK, siempre y cuando realicen la build correctamente. <br>
  El código no posee nada particular de una de las nuevas versiones, es más, se basó en tutoriales y guías de hace años. Comencé programandolo en Java 8 y no me generó ningún problema al actualizarlo de versión. De todas formas recomiendo usar la misma que utiliza el proyecto para evitar rompederos de cabeza.<br>

- [OpenJDK](https://openjdk.org/) (Java Development Kit) versión 17 o superior.

  Versión de OpenJDK utilizada: 17.0.7<br>

  ```
  openjdk version "17.0.7" 2023-04-18
  OpenJDK Runtime Environment Temurin-17.0.7+7 (build 17.0.7+7)
  OpenJDK 64-Bit Server VM Temurin-17.0.7+7 (build 17.0.7+7, mixed mode, sharing)
  ```

## Uso 🚀

Dejo por acá una serie de pasos para poder ejecutarlo en su máquina local (más unas consideraciones extra):<br>

1. Clonar este repositorio
2. Importar el proyecto en un IDE (Eclipse, IntelliJ, etc)<br><i>PD: Se utilizó VSCode con el profile default de Microsoft "Java Spring", que contiene las extensiones de el "Java Extension Pack" y el "Spring Boot Extension Pack".</i>
3. Asegurarse de que el JDK se encuentre actualizado.
4. Hacer una build y ejecutarla.

Ante cualquier inconveniente, no duden en hacermelo saber!

## Recursos Adicionales 🔗

- [Página de Quartz Scheduler](http://www.quartz-scheduler.org/)
- [Documentación de Spring Boot sobre Quartz](https://docs.spring.io/spring-boot/docs/2.0.0.M3/reference/html/boot-features-quartz.html)
- [Generador de expresiones Cron](https://crontab.cronhub.io/)
- [Guía extra para comprender el código de Quartz en Spring](https://www.baeldung.com/spring-quartz-schedule)
- [Github Gist del AutoWiring que usé para la inyección de dependencias de Quartz en Spring (Créditos a @jelies)](https://gist.github.com/jelies/5085593)

## Licencia 📄

Este proyecto está licenciado bajo GPL-3.0, lo que significa que podés usarlo, modificarlo y compartirlo según los términos de la licencia. Para más detalles, podés consultar el archivo LICENSE.md.

¡Siéntanse libres de aprovechar el código y hacer sus propias contribuciones! Valoro cualquier aporte y te animamo a explorar el proyecto. Si encontás algún problema o tenés ideas para mejorarlo, no dudes en hacérmelo saber!
