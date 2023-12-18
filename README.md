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
Para configurar el proyecto desde cero, sigue estos pasos:

Clonar el Repositorio:

```
git clone https://tu-repositorio.git
cd nombre-del-proyecto
```

### 5.2 Configurar la Base de Datos:

Asegúrate de tener un servidor MySQL en ejecución.
Crea una base de datos con el nombre "clinica" (o el nombre que prefieras).
Actualiza las credenciales de la base de datos en el archivo persistence.xml ubicado en la carpeta src/main/resources/META-INF/.
Configurar Dependencias de Maven:

Asegúrate de tener Maven instalado.
Actualiza la configuración del archivo pom.xml con las dependencias correctas y las versiones de las bibliotecas utilizadas en el proyecto.
Compilar y Construir el Proyecto:

```
mvn clean install
```

5.2 Despliegue y Ejecución del Proyecto
Una vez que el proyecto está configurado, sigue estos pasos para desplegar y ejecutar:

Desplegar en un Servidor de Aplicaciones:

Despliega el archivo JAR generado en el servidor de aplicaciones de tu elección.
Ejecutar desde la Línea de Comandos:

```
java -jar target/nombre-del-proyecto.jar
```

Acceder a la Aplicación:

Abre un navegador web y visita http://localhost:puerto (reemplaza "puerto" con el puerto configurado en tu aplicación).
Interactuar con la Aplicación:

Utiliza la interfaz de usuario para gestionar dueños, mascotas, visitas, etc.
Con estos pasos, deberías tener el proyecto desplegado y funcionando correctamente. Asegúrate de cumplir con los requisitos del sistema y las configuraciones específicas de tu entorno.





