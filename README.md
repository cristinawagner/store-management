# store-management

Store Management Tool

This is a store management tool with basic functions needed for an online store to keep an inventory, sell items and
deliver them.

System requirements to run the application:

- Java 17
- maven 3
- Spring Boot 3
- Spring Boot Web, Spring Data JPA,
- Embedded H2 database(The initial data is loaded from data.sql)

I created a Postman collection with the available endpoints: Store Management Tool.postman_collection.json

TODO and alternatives to current implementation
- authentication and role based access for endpoints. 
  Eg: only ADMIN can add/update/delete items/products etc. from DB, STANDARD accounts can query the data
- Create an error handling advice to handle the exceptions thrown by the application.
- Implement orders/transactions/dispatching of data.