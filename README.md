# Stock Control

Stock Control API demo

## Requirements

* Java 11
* Maven >= 3.6.3

## Build and run

After cloning the project run:
```bash
cd api
mvn spring-boot:run
```

## API

Once the project is running go to `http://localhost:8080/swagger-ui/index.html` in order to interact with the API.

## Database

The project uses an H2 in-memory database and it's automatically seeded by means of the `api/src/main/resources/data.sql` file.
