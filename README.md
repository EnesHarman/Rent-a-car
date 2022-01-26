
# Rent a Car Rest Api
This is a Spring Boot project for a rent a car website. 
## Summary
There are 3 different types of users: System manager, company manager, and customer. I have used JSON web token and Spring Security to provide role-based architecture. 
**System managers** can do CRUD operations on companies, roles, vehicles status, gear types, and fuel types. Also if a company manager wants to register, he/she should be approved by system managers and he/she should use the code sent from the system to his/her email. 
**Company managers** can add and organize their companies' vehicles to the system. When a customer sent a rental request, the company manager can apply or reject that request.
**Customers** can search vehicles with some parameters like city, gear types, and fuel types. When customers select a car for the rental, they can send a rental request to the company manager. When the manager answers the rental request, customers are informed by email.

You can see all endpoints from http://localhost:8080/swagger-iu.html when you run the API.

## Technologies
These are the technologies I have used:

 - Java 8
 - Spring Boot
 - Spring Security
 - Hibernate
 - Lombok
 - MySQL
 - JavaMailSender
 - Bcrypt Password Encoder
 
Note that **Lombok should be installed** on your computer to run this API.

## Configurations
On the database side, you just need to create a schema named **arac_kiralama**. Tables will create **automatically**. Then you need to enter your MySQL **user name** and **password** in the application.properties file. If you wish, you can change your logs direction from the same file.

