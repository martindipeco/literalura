# Liter-Alura

## Descripción

Liter-Alura es una aplicación Java que permite gestionar libros y autores utilizando JPA (Java Persistence API) con Hibernate y una base de datos PostgreSQL. La aplicación ofrece diversas funcionalidades para buscar y listar libros y autores, así como obtener estadísticas y datos curiosos.

## Funcionalidades

1. **Buscar libro por título y/o autor (y agregar a base propia)**
2. **Buscar por nombre Autor en el repositorio**
3. **Listar libros registrados**
4. **Listar autores registrados**
5. **Listar autores vivos en un determinado año**
6. **Listar libros por idioma**
7. **Estadísticas basadas en API**
8. **Estadísticas basadas en repositorio**
9. **Datos curiosos con años**

## Estructura del Proyecto

El proyecto está estructurado de la siguiente manera:

- **Modelos**: Definición de las entidades `Autor` y `Libro`.
- **Repositorios**: Interfaces que definen los métodos de acceso a la base de datos para las entidades `Autor` y `Libro`.
- **Servicios**: Clase `LibroService` que proporciona métodos para la búsqueda de libros en una API externa.
- **Principal**: Clase `Principal` que contiene el método `main` y la lógica de la aplicación.

## Requisitos

- Java 8 o superior
- Maven
- PostgreSQL
- Dependencias de Maven para JPA, Hibernate y PostgreSQL

## Configuración

1. **Clonar el repositorio:**

```bash
git clone <url_del_repositorio>
cd liter-alura

2. **Configurar la base de datos:**

Asegúrate de tener PostgreSQL instalado y ejecutándose. Crea una base de datos para la aplicación.

```bash
CREATE DATABASE liter_alura;

3. **Configurar las propiedades de la aplicación:**

Edita el archivo src/main/resources/application.properties con los detalles de tu base de datos

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/liter_alura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

4. **Construir y ejecutar la aplicación:**

```bash
mvn clean install
mvn spring-boot:run


## Uso

Al ejecutar la aplicación, se mostrará un menú en la consola con las siguientes opciones:

```bash
Bienvenidos a Liter-Alura

1 - Buscar libro por título y/o autor (y agregar a base propia)
2 - Buscar por nombre Autor en Repo
3 - Listar libros registrados
4 - Listar autores registrados
5 - Listar autores vivos en un determinado año
6 - Listar libros por idioma
7 - Estadísticas (API)
8 - Estadísticas (Repo)
9 - Datos curiosos con años

0 - Salir

### Ejemplos de uso

- Buscar libro por título:
Ingresa la opción 1 y proporciona el título o parte del título del libro. Si se encuentran resultados, se agregarán a la base de datos.

- Buscar autor por nombre:
Ingresa la opción 2 y proporciona el nombre del autor. Se mostrarán los autores que coincidan con la búsqueda.

- Listar libros registrados:
Ingresa la opción 3 para listar todos los libros en la base de datos.

- Listar autores registrados:
Ingresa la opción 4 para listar todos los autores en la base de datos.

- Listar autores vivos en un determinado año:
Ingresa la opción 5 y proporciona el año. Se mostrarán los autores que estaban vivos en ese año.

- Listar libros por idioma:
Ingresa la opción 6 y selecciona el idioma deseado.

- Mostrar estadísticas:
Ingresa la opción 7 para mostrar estadísticas basadas en los datos de la API o la opción 8 para estadísticas basadas en el repositorio.

- Mostrar datos curiosos:
Ingresa la opción 9 para mostrar datos curiosos, como el autor más longevo.


## Contribuciones son bienvenidas!

Las contribuciones son bienvenidas. Por favor, sigue los siguientes pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama:
```bash
 git checkout -b feature/nueva-funcionalidad:
3. Realiza los cambios necesarios y realiza commits:
```bash
git commit -m 'Agregar nueva funcionalidad'
4. Empuja los cambios a tu fork:
```bash
git push origin feature/nueva-funcionalidad
5. Abre un Pull Request.


[Link a video - demo (13 min)](https://youtu.be/S_Wtcx2KEOc)
