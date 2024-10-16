# Library Management System

## Project Overview
The **Library Management System** is a web application implemented with spring boot. The system is designed to manage book reservations within a library, providing functionalities for different roles: System Administrator, Reservation Manager, and Regular User. The application follows SOLID principles and includes basic authentication and authorization mechanisms to ensure that each user has access to the appropriate functionalities.

## Project Structure
The project is organized into the following files and folders:

```
.
├── HELP.md
├── README.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│ ├── main
│ │ ├── java
│ │ │ └── com
│ │ │     └── example
│ │ │         └── Librarymanagementsystem
│ │ │             ├── LibraryManagementSystemApplication.java
│ │ │             ├── config
│ │ │             │ ├── AppConfig.java
│ │ │             │ └── OpenApiConfig.java
│ │ │             ├── controller
│ │ │             │ ├── AppController.java
│ │ │             │ ├── AuthController.java
│ │ │             │ ├── BookController.java
│ │ │             │ ├── GoogleCalendarController.java
│ │ │             │ ├── ReservationController.java
│ │ │             │ └── UserController.java
│ │ │             ├── data
│ │ │             │ ├── model
│ │ │             │ │ ├── Book.java
│ │ │             │ │ ├── Reservation.java
│ │ │             │ │ ├── User.java
│ │ │             │ │ ├── audit
│ │ │             │ │ │ └── DateAudit.java
│ │ │             │ │ └── enums
│ │ │             │ │     ├── BookAvailability.java
│ │ │             │ │     ├── BookGenre.java
│ │ │             │ │     ├── ReservationStatus.java
│ │ │             │ │     └── UserRole.java
│ │ │             │ └── repository
│ │ │             │     ├── BookRepository.java
│ │ │             │     ├── ReservationRepository.java
│ │ │             │     └── UserRepository.java
│ │ │             ├── exception
│ │ │             │ ├── DuplicateEntryException.java
│ │ │             │ ├── GlobalExceptionHandler.java
│ │ │             │ ├── InvalidReservationStatusException.java
│ │ │             │ └── ResourceNotFoundException.java
│ │ │             ├── librarymanagmentproject-d277fcb44df3.json
│ │ │             ├── payload
│ │ │             │ ├── mapper
│ │ │             │ │ ├── BookMapper.java
│ │ │             │ │ └── UserMapper.java
│ │ │             │ ├── request
│ │ │             │ │ ├── BookRequestDTO.java
│ │ │             │ │ ├── EventRequest.java
│ │ │             │ │ ├── ReservationRequest.java
│ │ │             │ │ ├── SignInRequestDTO.java
│ │ │             │ │ ├── SignUpRequest.java
│ │ │             │ │ └── UserRequestDTO.java
│ │ │             │ └── response
│ │ │             │     ├── ApiResponseSchema.java
│ │ │             │     ├── AuthenticationResponse.java
│ │ │             │     ├── BookResponseDTO.java
│ │ │             │     ├── MessageResponse.java
│ │ │             │     ├── Response.java
│ │ │             │     ├── UserInfoResponse.java
│ │ │             │     └── UserResponseDTO.java
│ │ │             ├── security
│ │ │             │ ├── WebSecurityConfig.java
│ │ │             │ ├── jwt
│ │ │             │ │ ├── AuthEntryPointJwt.java
│ │ │             │ │ ├── AuthTokenFilter.java
│ │ │             │ │ └── JwtUtils.java
│ │ │             │ └── services
│ │ │             │     ├── UserDetailsImpl.java
│ │ │             │     └── UserDetailsServiceImpl.java
│ │ │             └── service
│ │ │                 ├── IBookService.java
│ │ │                 ├── IReservationService.java
│ │ │                 ├── IUserService.java
│ │ │                 └── impl
│ │ │                     ├── BookService.java
│ │ │                     ├── GoogleCalendarService.java
│ │ │                     ├── ReservationService.java
│ │ │                     └── UserService.java
│ │ └── resources
│ │     ├── application.properties
│ │     └── static
│ └── test
│     └── java
│         └── com
│             └── example
│                 └── Librarymanagementsystem
│                     ├── AuthControllerTest.java
│                     ├── BookControllerTest.java
│                     ├── LibraryManagementSystemApplicationTests.java
│                     ├── book
│                     │ └── BookServiceTest.java
│                     ├── reservation
│                     │ └── ReservationServiceTest.java
│                     └── user
│                         └── UserServiceTest.java
```

## How to Run and Use the Project

### Prerequisites
- Java Development Kit (JDK) installed on your machine.
- A terminal or command prompt to run the application.
- Maven

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
The application will start on http://localhost:8080/api/v1<br>
swagger url: http://localhost:8080/api/v1/swagger-ui/index.html#/

### services of Application

1. **System Administrator:**
   - CRUD operations for manage books.
   - CRUD operations for manage users 
   
2. **Reservation Manager:**
   - View book reservation requests.
   - Approve or reject book reservation requests.
   - CRUD operations for manage reservations.

3. **User:**
   - View available books.
   - Request a book reservation.
   - View your requested books.
   - Delete a reservation request.
   - View reserved books.

4. *public:*
   - get profile details
   - register
   - login
   - logout
   - create event on Google calendar

## Key Features
**_JWT Security_**:
Implement JSON Web Token (JWT) for secure authentication and authorization, ensuring that user sessions are managed securely.
<br><br>**_OpenAPI Documentation_**:
Automatically generate API documentation using OpenAPI specifications, providing a clear and comprehensive overview of available endpoints and their usage.
<br><br>**_Swagger UI_**:
Integrate Swagger UI for interactive API documentation, allowing developers to explore and test API endpoints directly from the browser.
![swagger](/src/main/resources/swagger.png)
<br><br>**_Validation on Schema_**:
Enforce data integrity and validation rules on request and response schemas, ensuring that all data adheres to defined formats and constraints.
<br><br>**_Using SLF4J for Logging_**:
Utilize SLF4J (Simple Logging Facade for Java) for logging application events, providing a consistent logging interface and supporting various logging frameworks.
<br><br>**_Testing with MockService_**:
Write unit and integration tests using MockService to simulate external dependencies, ensuring that the application behaves correctly under various scenarios.
<br><br>**_Exception Handling_**:
Implement a centralized exception handling mechanism to manage application errors gracefully, providing meaningful error messages and HTTP status codes to clients.
<br><br>**_Google Calendar Integration_**:
Integrate with Google Calendar API to display reservations, allowing users to view their booked slots directly in their Google Calendar, enhancing user experience and accessibility.
![calendar](/src/main/resources/google-calendar.mp4)


## Version Information
- **Current Version:** 1.0.0

## Changelog
- **1.0.0:**
   - Initial release with core functionalities:
      - Role-based access control.
      - Book management for Admins.
      - Reservation management for Managers.
      - User-specific operations for Regular Users.






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




## References link

- [Podspace folder link](https://podspace.ir/public/folders/IG65XX963DJLJJR1)
