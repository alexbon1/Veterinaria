# Veterinaria

# Documentación Técnica - Clínica Veterinaria

## 1. Introducción

La presente documentación técnica tiene como objetivo proporcionar una comprensión integral del sistema de gestión de una clínica veterinaria. Aquí encontrarás detalles sobre la arquitectura del sistema, el modelo de datos, consultas JPQL, así como instrucciones para la configuración y despliegue del proyecto.

## 2. Arquitectura del Sistema

El proyecto sigue una estructura basada en el patrón Modelo-Vista-Controlador (MVC). Se utiliza Hibernate como implementación de JPA para la capa de persistencia. A continuación, se presenta un diagrama de clases que ilustra las entidades y sus relaciones.


Mis disculpas por eso. Aquí está la documentación con formato Markdown:

markdown
Copy code
# Documentación Técnica - Clínica Veterinaria

## 1. Introducción

La presente documentación técnica tiene como objetivo proporcionar una comprensión integral del sistema de gestión de una clínica veterinaria. Aquí encontrarás detalles sobre la arquitectura del sistema, el modelo de datos, consultas JPQL, así como instrucciones para la configuración y despliegue del proyecto.

## 2. Arquitectura del Sistema

El proyecto sigue una estructura basada en el patrón Modelo-Vista-Controlador (MVC). Se utiliza Hibernate como implementación de JPA para la capa de persistencia. A continuación, se presenta un diagrama de clases que ilustra las entidades y sus relaciones.

+-------------------+ +-------------------+ +-------------------+
| Dueño | ----- | Mascota | ----- | Visita |
+-------------------+ +-------------------+ +-------------------+
| id: Long | | id: Long | | id: Long |
| nombre: String | | nombre: String | | fecha: String |
| direccion: String | | tipo: String | | motivo: String |
| telefono: String | | raza: String | | diagnostico: String|
| mascotas: List<> | <--- | dueño: Dueño | <--- | mascota: Mascota |
+-------------------+ +-------------------+ +-------------------+


## 3. Modelo de Datos

### 3.1 Entidad Dueño

La entidad Dueño representa a los propietarios de mascotas en la clínica veterinaria.

```java
@Entity
public class Dueño {
    // Atributos y anotaciones...
}
```
Atributos:

id (Long): Identificador único del dueño (generado automáticamente).
nombre (String): Nombre del dueño.
direccion (String): Dirección del dueño.
telefono (String): Número de teléfono del dueño.
mascotas (List<Mascota>): Lista de mascotas asociadas al dueño.
### 3.2 Entidad Mascota (Continuación)


### 3.2 Entidad Mascota 

La entidad `Mascota` representa a las mascotas atendidas en la clínica.

```java
@Entity
public class Mascota {
    // Atributos y anotaciones...
}
```
Atributos:

id (Long): Identificador único de la mascota (generado automáticamente).
nombre (String): Nombre de la mascota.
tipo (String): Tipo de la mascota (por ejemplo, perro, gato).
raza (String): Raza de la mascota.
dueño (Dueño): Dueño de la mascota.
visitas (List<Visita>): Lista de visitas asociadas a la mascota.

### 3.2 Entidad Visita
La entidad Visita registra las visitas realizadas a las mascotas.
```
@Entity
public class Visita {
    // Atributos y anotaciones...
}
```
Atributos:

id (Long): Identificador único de la visita (generado automáticamente).
fecha (String): Fecha de la visita.
motivo (String): Motivo de la visita.
diagnostico (String): Diagnóstico de la visita.
mascota (Mascota): Mascota asociada a la visita.

## 4. Consultas JPQL en el Proyecto

### 4.1 Consulta de Mascotas por Tipo

En la clase `VeterinariaService`, se encuentra la siguiente consulta que recupera una lista de mascotas según el tipo especificado.

```java
/**
 * Obtiene una lista de mascotas según el tipo especificado.
 *
 * @param tipo Tipo de mascota a buscar.
 * @return Lista de mascotas del tipo especificado.
 */
public List<Mascota> findMascotasByTipo(String tipo) {
    Query query = entityManager.createQuery("SELECT m FROM Mascota m WHERE m.tipo = :tipo");
    query.setParameter("tipo", tipo);
    return query.getResultList();
}
```
### 4.2 Consulta de Dueños con Múltiples Mascotas
En la misma clase VeterinariaService, se encuentra la siguiente consulta que recupera una lista de dueños que tienen más de dos mascotas.

```
/**
 * Obtiene una lista de dueños que tienen más de dos mascotas.
 *
 * @return Lista de dueños con múltiples mascotas.
 */
public List<Dueño> findDueñosWithMultipleMascotas() {
    Query query = entityManager.createQuery("SELECT d FROM Dueño d WHERE SIZE(d.mascotas) > 2");
    return query.getResultList();
}
````
### 4.3 Consulta de Visitas por Nombre de Mascota
En la misma clase VeterinariaService, se encuentra la siguiente consulta que recupera una lista de visitas según el nombre de la mascota especificada.

```
/**
 * Obtiene una lista de visitas según el nombre de la mascota especificada.
 *
 * @param nombreMascota Nombre de la mascota.
 * @return Lista de visitas para la mascota especificada.
 */
public List<Visita> findVisitasByMascotaNombre(String nombreMascota) {
    Query query = entityManager.createQuery("SELECT v FROM Visita v WHERE v.mascota.nombre = :nombre");
    query.setParameter("nombre", nombreMascota);
    return query.getResultList();
}
```
4.4 Consulta de Visitas por Motivo
En la misma clase VeterinariaService, se encuentra la siguiente consulta que recupera una lista de visitas según el motivo especificado.
```
/**
 * Obtiene una lista de visitas según el motivo especificado.
 *
 * @param motivo Motivo de la visita.
 * @return Lista de visitas para el motivo especificado.
 */
public List<Visita> findVisitasByMotivo(String motivo) {
    Query query = entityManager.createQuery("SELECT v FROM Visita v WHERE v.motivo = :motivo");
    query.setParameter("motivo", motivo);
    return query.getResultList();
}
```

# 5. Configuración y Despliegue
### 5.1 Configuración del Proyecto desde Cero
Clonar el Repositorio:

bash
```
git clone https://tu-repositorio.git
cd nombre-del-proyecto
```
### Importar el Proyecto en Eclipse:

Abre Eclipse.
Selecciona File -> Import.
En la ventana de importación, elige Existing Maven Projects.
Selecciona la carpeta del proyecto clonado y haz clic en Finish.

### Configurar la Base de Datos:

Asegúrate de tener un servidor MySQL en ejecución.
Crea una base de datos con el nombre "clinica" (o el nombre que prefieras).
Actualiza las credenciales de la base de datos en el archivo persistence.xml ubicado en la carpeta src/main/resources/META-INF/.

### Configurar Dependencias de Maven:

Asegúrate de tener Maven instalado.
Actualiza la configuración del archivo pom.xml con las dependencias correctas y las versiones de las bibliotecas utilizadas en el proyecto.

### Compilar y Construir el Proyecto:

Haz clic derecho en el proyecto en Eclipse.
Selecciona Run As -> Maven Clean.
Después, selecciona Run As -> Maven Install.
### 5.2 Ejecutar el Proyecto desde Eclipse
 ### Configurar el Entorno de Ejecución:

Abre el archivo VeterinariaApp.java ubicado en src/main/java/pack1/.
Haz clic derecho dentro del código y selecciona Run As -> Java Application.

### Acceder a la Aplicación:

Abre un navegador web y visita http://localhost:puerto (reemplaza "puerto" con el puerto configurado en tu aplicación).

### Interactuar con la Aplicación:

Utiliza la interfaz de usuario para gestionar dueños, mascotas, visitas, etc.
Estos pasos deberían ayudarte a abrir y ejecutar el proyecto desde Eclipse. Asegúrate de tener Eclipse configurado correctamente con las herramientas de Maven y la perspectiva de desarrollo de Java.

# 7. Conclusiones y Posibles Mejoras

## 7.1 Reflexiones sobre el Proceso de Desarrollo

Durante el desarrollo de este proyecto, se han enfrentado diversos desafíos y se ha adquirido experiencia valiosa. Algunas reflexiones sobre el proceso incluyen:

- **Aprendizaje de Tecnologías:**
  - Se ha ganado familiaridad con tecnologías como <link>Hibernate</link>, <link>JPA</link> y <link>Maven</link>, contribuyendo al crecimiento en el conocimiento de desarrollo de aplicaciones Java.
- **Gestión de Relaciones en la Base de Datos:**
  - La implementación de relaciones entre entidades, como la relación entre Dueño, Mascota y Visita, ha proporcionado una comprensión más profunda de la gestión de bases de datos relacionales.
- **Desarrollo de Interfaz Gráfica:**
  - La creación de una interfaz gráfica utilizando <link>Swing</link> ha permitido explorar el desarrollo de aplicaciones de escritorio en Java.

## 7.2 Posibles Mejoras y Características Futuras

El proyecto actual sienta una base sólida, pero existen oportunidades para mejoras y expansiones futuras:

- **Mejora de la Interfaz de Usuario:**
  - Realizar mejoras en la interfaz gráfica para hacerla más intuitiva y atractiva para los usuarios.
- **Implementación de Seguridad:**
  - Añadir capas de seguridad, como autenticación y autorización, para proteger la información sensible y restringir el acceso a funciones específicas.
- **Funcionalidades Adicionales:**
  - Explorar la adición de nuevas funciones, como el registro de usuarios, un historial médico detallado para mascotas, o la capacidad de programar citas.
- **Optimización de Consultas:**
  - Optimizar las consultas <link>JPQL</link> para mejorar el rendimiento, especialmente en escenarios con grandes conjuntos de datos.
- **Despliegue en la Nube:**
  - Considerar la posibilidad de desplegar la aplicación en la nube para facilitar el acceso y la escalabilidad.

Estas mejoras potenciales pueden contribuir a la evolución y expansión continua del proyecto en el futuro.



