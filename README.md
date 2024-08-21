# Library Management System

## Project Overview
The **Library Management System** is a console-based application implemented in Java. The system is designed to manage book reservations within a library, providing functionalities for different roles: System Administrator, Reservation Manager, and Regular User. The application follows SOLID principles and includes basic authentication and authorization mechanisms to ensure that each user has access to the appropriate functionalities.

## Project Structure
The project is organized into the following files and folders:

```
library-management-system/
├── src/
│   ├── main/
│       ├── LibraryManagementSystem.java
│       ├── AuthManager.java
│   ├── enums/
│       ├── BookAvailability.java
│       ├── BookGenre.java
│       ├── ReservationStatus.java
│       ├── UserRol.java
│   ├── model/
│       ├── Book.java
│       ├── RegularUser.java
│       ├── Reservation.java
│       ├── SystemAdmin.java
│       ├── User.java
│       ├── ReservationManager.java
│   ├── repository/
│       ├── BookRepository.java
│       ├── ReservationRepository.java
    ├── test/
│       ├── TestObjects.java
├── README.md
└── .gitignore
```

## How to Run and Use the Project

### Prerequisites
- Java Development Kit (JDK) installed on your machine.
- A terminal or command prompt to run the application.

### Running the Application
1. Clone the repository or download the project files.
2. Navigate to the project directory.
3. Compile the Java files using the following command:
   ```bash
   javac src/*.java
   ```
4. Run the compiled application:
   ```bash
   java src/main/LibraryManagementSystem
   ```

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