# ING-Hubs-HW

I built a Spring Boot product management API with JWT authentication and role-based permissions. It manages products and orders, where each order contains multiple purchases linked to products. It uses an H2 in-memory database with a small SQL script for test data, so it runs quickly and is easy to test.

How to run:
- clone the project
- run as spring application
- after it started, access h2 database with: http://localhost:8080/h2-console (8080 or any other port the app starts on), and use username: ing, password: ing
- import postman collection in postman and you will see a few folders to do almost every action you can in the project (just register and login (you will also have a registered account when you start the app with usernmae: admin@admin.com, password: adminpassword, then the login request will automatically put jwtToken in a variable and is used in every request in the collection)
