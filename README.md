# LIBRARIAN
## Java, Spring Boot, and PostgreSQL Project


[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/) [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)](https://spring.io/projects/spring-boot) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13.4-blue.svg)](https://www.postgresql.org/) [![MAVEN](https://img.shields.io/badge/Maven-3.9.4-brightgreen.svg)](https://gradle.org/)

![Logo](https://github.com/migmm/librarian/blob/media/assets/logo-library.png)

This is a sample project that uses Spring Boot and PostgreSQL to create a Java application. The project uses Maven as a dependency management system and has been developed with Java 17.


### Prerequisites

Before running the application, make sure you have the following components installed:

    Java Development Kit (JDK) 17 or higher
    Apache Maven 3.9.4 or higher
    PostgreSQL 13.4 or higher
    An Integrated Development Environment (IDE) compatible with Java, such as IntelliJ IDEA or Eclipse (optional)


### Database Configuration

Make sure you have a PostgreSQL database set up. You can configure the database connection in the application.properties file located in src/main/resources. There is a provided sample file of a application.properties file. Here is an example configuration:

    spring.datasource.url=jdbc:postgresql://localhost:5432/database_name
    spring.datasource.username=username
    spring.datasource.password=password
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=create

    upload.dir=images

Replace database_name, username, and password with your own values.


### Features

- Global exception handler
- Data validation
- OWASP Security (CSRF and the protections provided by Sping Security)
- Authentication via JWT Token
- Password encryption
- Pagination in book endpoint and possibility to search by ISBN or book name


### Database Relationships

![Logo](https://github.com/migmm/librarian/blob/media/assets/relations.png)
 
I used three types of relationships.


##### Foreign Key Relationship (Using a Junction Table)

In some scenarios, you might need to establish many-to-many relationships between entities. For this, you can use a junction table (also known as a linking or pivot table). In a Spring Data JPA application, you can create entities and repositories to represent this relationship. For example, if you have two entities, Book and author, and you want to establish a many-to-many relationship, you can create a junction table book_author:

    book entity has a @ManyToMany relationship with the book_author entity.
    author entity also has a @ManyToMany relationship with the book_author entity.


##### Direct Relationship
 
In other cases, you may have direct relationships between entities, such as a one-to-one or one-to-many relationship. In this project I made a relationship @OneToOne between books and vendors.


##### Element Collection
 
In this case I used a collection of images, in file helper with the bean @ElementCollection. This is often used when you want to model a one-to-many relationship without creating a separate table for the related entities. Instead, the collection is stored as part of the owning entity's table.


### Running the Project

First create a database named librarian then set a variable in your application.properties file named spring.jpa.hibernate.ddl-auto=create

Clone this repository or download the source code.
Open a terminal and navigate to the project's root directory.


Run the project using Maven:

    mvn spring-boot:run

Stop the running, then change the variable value to spring.jpa.hibernate.ddl-auto=none

Load the dummy data using DBeaver or Heidi, or your preferenced database editor.

Run the project again and you are in!

Alternatively, you can build an executable JAR file and then run it:

    mvn package

    or

    java -jar target/project_name.jar

Replace project_name with the name of your project.

Yoy can also build the project without run tests
    
    mvn clean package -DskipTests
    

The application will be available at http://localhost:8080. You can access it from your web browser.

### Dockerfile

To run the project in local with Docker you can build and run the project with the commands

    docker build -t librarian .

    docker run --rm librarian


### Dependences

The project uses the following Spring Boot dependencies:

    Spring Boot 3.2.1
    Lombok
    Spring Security 6
    Spring Data JPA
    PostgreSQL Driver

You can manage the dependencies in the project's pom.xml file.

This project is under the MIT license. Please refer to the LICENSE file for more details.


### Endpoints documentation

| Endpoint                  | Method     | Description                                                                   |
|---------------------------|------------|-------------------------------------------------------------------------------|
|                           |            |                                                                               |
| `/authors/findall`        | GET        | Get all authors. (view comment below <br/> to see how request with prameters. |
| `/authors/find/{id}`      | GET        | Get author by ID.                                                             |
| `/authors/save`           | POST       | Register new author.                                                          |
| `/authors/update/{id}`    | PUT        | Update a author.                                                              |
| `/authors/setstatus/{id}` | PUT        | Set author status to true o false (logical deletion).                         |
| `/authors/delete/{id}`    | DELETE     | Delete author from database.                                                  |
|                           |            |                                                                               |
| `/books/findall`          | GET        | Get all books.                                                                |
| `/books/find/{id}`        | GET        | Get book by ID.                                                               |
| `/books/save`             | POST       | Register new book.                                                            |
| `/books/update/{id}`      | PUT        | Update a book.                                                                |
| `/books/setstatus/{id}`   | PUT        | Set book status to true o false (logical deletion).                           |
| `/books/borrow/{id}`      | PUT        | Borrow a book                                                                 |
| `/books/return/{id}`      | PUT        | Return a book                                                                 |
| `/books/delete/{id}`      | DELETE     | Delete book from database.                                                    |
|                           |            |                                                                               |
| `/users/findall`          | GET        | Get all users.                                                                |
| `/users/find/{id}`        | GET        | Get users by ID.                                                              |
| `/users/save`             | POST       | Register new user.                                                            |
| `/users/update/{id}`      | PUT        | Update a user.                                                                |
| `/users/setstatus/{id}`   | PUT        | Set user status to true o false (logical deletion).                           |
| `/users/delete/{id}`      | DELETE     | Delete users from database.                                                   |
|                           |            |                                                                               |
| `/auth/register`          | POST       | Register a new user.                                                          |
| `/auth/login`             | POST       | Login a registered user.                                                      |
| `/auth/logout`            | POST       | Log out a logged-in user.                                                     |
|                           |            |                                                                               |
| `/vendor/findall`         | GET        | Get all vendors.                                                              |
| `/vendor/find/{id}`       | GET        | Get vendor by ID.                                                             |
| `/vendor/save`            | POST       | Register new vendor.                                                          |
| `/vendor/update/{id}`     | PUT        | Update a vendor.                                                              |
| `/vendor/setstatus/{id}`  | PUT        | Set vendor status to true o false (logical deletion).                         |
| `/vendor/delete/{id}`     | DELETE     | Delete vendor from database.                                                  |


All endpoints uses a local host url http://localhost:8080/
If you want a more complete description of the endpoints and how to use it go to the section below.


### Pagination and search by ISBN or book name

The book endpoint has the possibility to paginate results by the number of items per page, order them and select which page to view. It's also possible to search by the book's name or ISBN, for example.

    Get all books
    http://localhost:8080/books/findAll?page=0&size=6&sort=title,asc

    Search by name (you can use %20 for spaces)
    http://localhost:8080/books/findAll?page=0&size=6&sort=title,asc&name=Book%20Example

    Search by ISBN
    http://localhost:8080/books/findAll?page=0&size=6&sort=title,asc&ISBN=1234567890123


### Swagger documentation

To access to Swagger API documentation you can go to the links below in your localhost:

[Swagger UI](http://localhost:8080/swagger-ui/index.html)
[Swagger JSON](http://localhost:8080/v3/api-docs)


### Source information

[Swagger Documentation](https://www.baeldung.com/spring-rest-openapi-documentation)

[Validations implementation](https://medium.com/@himani.prasad016/validations-in-spring-boot-e9948aa6286b)

[If validation does not work](https://stackoverflow.com/questions/48614773/spring-boot-validation-annotations-valid-and-notblank-not-working)

[Pagination results](https://howtodoinjava.com/spring-data/pagination-sorting-example/)

[Send multipart in put with mockmvc](https://stackoverflow.com/questions/38571716/how-to-put-multipart-form-data-using-spring-mockmvc)

[Testing with JWT token](https://stackoverflow.com/questions/61500578/how-to-mock-jwt-authentication-in-a-spring-boot-unit-test)

[Testing with JWT token](https://stackoverflow.com/questions/45241566/spring-boot-unit-tests-with-jwt-token-security)

[Testing with CSRF](https://stackoverflow.com/questions/25605373/unit-testing-controllers-with-csrf-protection-enabled-in-spring-security)

[Testing with CSRF](https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html)


### Extra files

I included a [Dummy data](dummy_data.sql) to populate the database and a [Postman file](Librarian.postman_collection.json) to test the routes propertly, you can use in postman o you can import this file in another tester like [Hoppscotch](https://hoppscotch.io/).

I also included a [Dockerfile](Dockerfile) to make a deploy.

### Contact

You can reachme at hi@miguedev.com or go to my website https://www.miguedev.com
