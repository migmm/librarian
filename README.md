# LIBRARIAN
## Java, Spring Boot, and PostgreSQL Project

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/) [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.4-brightgreen.svg)](https://spring.io/projects/spring-boot) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13.4-blue.svg)](https://www.postgresql.org/) [![MAVEN](https://img.shields.io/badge/Maven-3.9.4-brightgreen.svg)](https://gradle.org/)

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

Replace database_name, username, and password with your own values.

### Database Relationships

I used two types of relationships.

#####Foreign Key Relationship (Using a Junction Table)

In some scenarios, you might need to establish many-to-many relationships between entities. For this, you can use a junction table (also known as a linking or pivot table). In a Spring Data JPA application, you can create entities and repositories to represent this relationship. For example, if you have two entities, Book and author, and you want to establish a many-to-many relationship, you can create a junction table book_author:

    book entity has a @ManyToMany relationship with the book_author entity.
    author entity also has a @OneToMany relationship with the book_author entity.

##### Direct Relationship

In other cases, you may have direct relationships between entities, such as a one-to-one or one-to-many relationship. In this project I made a relationship @OneToOne between books and vendors.

### Running the Project

Clone this repository or download the source code.
Open a terminal and navigate to the project's root directory.
Run the project using Maven:

    mvn spring-boot:run

Alternatively, you can build an executable JAR file and then run it:

    mvn package
    java -jar target/project_name.jar

    Replace project_name with the name of your project.

    The application will be available at http://localhost:8080. You can access it from your web browser.

### Using the Application

This is a sample application and can be customized to suit your needs. The application currently includes basic Spring Security configuration for user authentication. You can modify and extend the functionality as per your requirements.
Dependencies

The project uses the following Spring Boot dependencies:

    Spring Boot 2.5.5
    Lombok
    Spring Security
    Spring Data JPA
    PostgreSQL Driver

You can manage the dependencies in the project's pom.xml file.

This project is under the MIT license. Please refer to the LICENSE file for more details.

### Contact

YOu can reachme in hi@miguedev.com.