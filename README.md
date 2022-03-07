# VegeShop

**Note**: Since this is a university project, I try to finish it as quickly as possible and ignore some security issues.

## What is VegeShop?

- It's a shopping cart application for selling vegetables.
- This web application also contains Admin role for manage stock, category, etc...
- This application is also my assignment project for my school subject `Java Web Application Development`.
- Work well on `Desktop` only.

## Tech stack

- Frontend
  - JSTL - Standard Tag Library for JSP
  - Bootstrap 5 - A popular CSS Framework
- Backend
  - Servlets - A Java class that extends the capabilities of the servers
  - Log4j - A separate implementation with powerful logging features
  - JDBC - A Java API to connect and execute the query with the database
  - MS SQL - A relational database management system developed by Microsoft
  - Javax Mail - A library in Java for sending email

## How to run

- I built this application with Netbean 8.2 so maybe you can use Netbean IDE to make it more easily to import project.
- If you use other IDE such as Eclipse or Intellij IDEA, you will need to find the way to config this project before use.
- After import this project to IDE, adding JSTL support (almost all IDE support adding JSTL lib) and all external library in `lib`.
- Then, you change some information in `DBUtils` and `EmailSenderUtils`.
- Finally, setup database with my `database.sql` scripts.
- That's all, enjoy VegeShop!.
