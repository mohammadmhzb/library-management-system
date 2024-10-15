# Library Management System

## Project Overview
The **Library Management System** is a console-based application implemented in Java. The system is designed to manage book reservations within a library, providing functionalities for different roles: System Administrator, Reservation Manager, and Regular User. The application follows SOLID principles and includes basic authentication and authorization mechanisms to ensure that each user has access to the appropriate functionalities.

## Project Structure
The project is organized into the following files and folders:

```
library-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── librarymanagementsystem/
│   │   │               ├── LibraryManagementSystemApplication.java
│   │   │               ├── config/
│   │   │               │   └── AppConfig.java
|   |   |               |   └── OpenApiConfig.java
│   │   │               ├── controller/
│   │   │               │   ├── AdminController.java
│   │   │               │   └── ReservationManagerController.java
│   │   │               │   └── UserController.java
│   │   │               ├── service/
│   │   │               │   ├── BookService.java
│   │   │               │   ├── UserService.java
│   │   │               │   └── ReservationService.java
│   │   │               ├── repository/
│   │   │               │   ├── BookRepository.java
│   │   │               │   └── ReservationRepository.java
│   │   │               ├── model/
│   │   │               │   ├── Book.java
│   │   │               │   ├── RegularUser.java
│   │   │               │   ├── Reservation.java
│   │   │               │   ├── SystemAdmin.java
│   │   │               │   └── User.java
│   │   │               ├── enums/
│   │   │               │   ├── BookAvailability.java
│   │   │               │   ├── BookGenre.java
│   │   │               │   ├── ReservationStatus.java
│   │   │               │   └── UserRole.java
│   │   │               └── exception/
│   │   │                   └── ResourceNotFoundException.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           └── (static files like CSS, JS, images)
│   │       └── templates/
│   │           └── (Thymeleaf templates if using)
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── librarymanagementsystem/
│                       ├── LibraryManagementSystemApplicationTests.java
│                       ├── controller/
│                       │   ├── BookControllerTest.java
│                       │   └── UserControllerTest.java
│                       └── service/
│                           ├── BookServiceTest.java
│                           └── UserServiceTest.java
├── README.md
└── .gitignore
```

## How to Run and Use the Project

### Prerequisites
- Java Development Kit (JDK) installed on your machine.
- A terminal or command prompt to run the application.

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/mohammadmhzb/library-management-system
   ```
2. **Navigate to the project directory:**

   ```bash
   cd library-management-system
   ```

3. **Build the project using Maven:**

   ```bash
   mvn clean install
   ```
4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
The application will start on http://localhost:8080/api/v1

### Using the Application

1. **System Administrator:**
   - View all books.
   - Add a new book.
   - Remove an existing book.

2. **Reservation Manager:**
   - View book reservation requests.
   - Approve or reject book reservation requests.

3. **Regular User:**
   - View available books.
   - Request a book reservation.
   - View your requested books.
   - Delete a reservation request.
   - View reserved books.

## Features
- **Book Management:** Admins can add, view, and remove books.
- **Reservation Management:** Managers can view, approve, and reject book reservations.
- **User Reservations:** Users can view available books, request reservations, and manage their reservations.
- **Authentication:** Users must log in with their credentials.
- **Authorization:** Access to functionalities is based on user roles (Admin, Manager, User).

## Version Information
- **Current Version:** 1.0.0

## Changelog
- **1.0.0:**
   - Initial release with core functionalities:
      - Role-based access control.
      - Book management for Admins.
      - Reservation management for Managers.
      - User-specific operations for Regular Users.

## References link

- [Podspace folder link](https://podspace.ir/public/folders/IG65XX963DJLJJR1)





## Git Commit Guidelines

When contributing to this project, please follow these guidelines for writing commit messages:

### Commit Message Structure

A commit message should consist of a **header**, an optional **body**, and an optional **footer**. The general format is as follows:



```
<type>(<scope>): <subject>

<body>

<footer>
```

### Components

- **Header**: A brief summary of the changes (max 72 characters).
   - **Type**: Indicates the type of change. Common types include:
      - `feat`: A new feature
      - `fix`: A bug fix
      - `docs`: Documentation only changes
      - `style`: Changes that do not affect the meaning of the code (white-space, formatting, etc.)
      - `refactor`: A code change that neither fixes a bug nor adds a feature
      - `test`: Adding missing tests or correcting existing tests
      - `chore`: Changes to the build process or auxiliary tools and libraries

- **Scope**: A noun describing a section of the codebase (optional). For example, `user`, `admin`, `api`, etc.

- **Subject**: A short description of the change (imperative mood, e.g., "add", "fix", "update").

- **Body**: A more detailed explanation of the change (optional). This can include the motivation for the change and contrast with previous behavior.

- **Footer**: Any information about breaking changes or issues closed (optional). For example, `BREAKING CHANGE: <description>` or `Closes #123`.

### Example Commit Messages

Here are some examples of well-structured commit messages:

```
feat(user): add user registration feature

This commit introduces a new user registration feature that allows users to create an account. It includes form validation and error handling.

Closes #45
```

```
fix(api): correct user authentication bug

Fixed a bug that prevented users from logging in when using special characters in their passwords.
```

```
docs: update README with contribution guidelines
```

### Best Practices

- Use the imperative mood in the subject line (e.g., "add" instead of "added").
- Keep the subject line concise and to the point.
- If the commit is large or complex, consider breaking it into smaller commits.
- Ensure that your commit messages are clear and descriptive to help others understand the changes made.

By following these guidelines, you help maintain a clean and understandable project history, making it easier for everyone to collaborate effectively.
