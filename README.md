# E-Commerce PC Store 

The E-Commerce PC Store is a Java-based console application designed to manage a computer store's inventory, user accounts, and shopping carts. The system allows users to browse and purchase computers, while administrators can manage the store inventory and user accounts.

## Features

- **User Management**: Registration, login, and personalized account management.
- **Computer Inventory**: Browsing and managing available computers.
- **Shopping Cart**: Adding computers to a cart, viewing cart contents, and completing purchases.
- **Admin Controls**: Enhanced capabilities for managing users and inventory.

## User and Admin Capabilities

### User Capabilities

- **Registration and Login**: Users can create accounts and log in to access the store.
- **Browsing Computers**: Users can view  and select a list of available computers with detailed specifications.
- **Shopping Cart**: Users can use add computers to their cart, view the products in cart, clear their cart and deposit money to purchase selected computers.

### Admin Capabilities

- **Admin priviliges**: Admins can do anything that the users do and have elevated rights to control inventory and users management.
- **Enhanced Inventory Management**: Admins can add new computers, update existing computer details, and remove computers from inventory.
- **User Management**: Admins can view all registered users, delete users, or modify user roles and credentials.


## Challenges Faced

During the development of this project, I encoutered several challenges:

***Technical Challenges***: 
 - When to use public and private access modifiers, but also set / getter methods, how to pass data between classes and manage decoupling mechanism.
  
***Design Challenges***: 
- Implementing the Mediator pattern was tricky, had to refactor multiple times. Also had an issue how to manage the project as it grew larger, so I restarted it and created an UML Diagram and spent some time reading about programming concepts and principles to achieve cleaner code base.
- The hardest part was creating the shopping cart, it took me approximately around 2-3 days to finally grasp the logic (tracking Ids between tables), thanks to a dev post I saw that there was a one to many relationship pattern and it finally clicked.
  
***Performance Issues***: 
- It was my first time ever creating SQL Database and manage it, had to practice custom queries to understand the CRUD operations, while doing that I referenced to w3schools for the syntax and the final chapter 15. JDBC of the JAVA 17 OCP Book.
- After I got more familiar started using GitHub Copilot Pro to write faster queries to `add`, `select`, `update`, `delete` in the service package.

## UML Diagram

I created an UML Diagram in order to provide a high-level view of the system architecture, illustrating the relationships between key components, classes and packages.

![UML Diagram](uml/uml-project.png)

## Package Description 

### `main`
- Initializes and starts the application by setting up mediator and store components.

### `mediator`
- Coordinates actions between store, handlers, and services, ensuring decoupled interaction.

### `store`
- Handles user interactions, command processing.

### `service`
- Used for computer, user, and cart operations, interacting with the the SQL database to manage data persistence using CRUD operations.

### `model`
- Includes classes like `User`, `Computer`, and `CartItem`, defining the properties and methods for each type of object.

### `handler`
- Executes the command-line interface operations, handling user inputs and triggering service actions.

### `connection`
- Facilitates connections to different databases using a centralized management class.

### `util`
- Provides common utility methods for tasks like input validation, command processing or printing.
