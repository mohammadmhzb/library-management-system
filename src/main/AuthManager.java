package main;

import enums.UserRole;
import model.*;
import java.util.List;
import java.util.Scanner;

public class AuthManager {
    private final List<User> users;
    private final Scanner scanner;

    public AuthManager(List<User> users, Scanner scanner) {
        this.users = users;
        this.scanner = scanner;
    }

    public void Login() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = authenticate(username, password);
        if (user != null) {
            System.out.println("Log In successful!\n");
            LibraryManagementSystem.setCurrentUser(user);
            routeUserMenu(user);
        } else {
            System.out.println("Invalid username or password.\n");
        }
    }

    private User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                return user;
            }
        }
        return null;
    }

    public void register() {
        System.out.println("Register New User:");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Role (1 for Regular User, 2 for Admin, 3 for Manager ): ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        UserRole role;
        if (roleChoice == 1) {
            role = UserRole.REGULAR_USER;
        } else if (roleChoice == 2) {
            role = UserRole.ADMIN;
        }else if (roleChoice == 3) {
            role = UserRole.MANAGER;
        }else {
            System.out.println("Invalid role choice.\n");
            return;
        }

        if (findUserByUsername(username) != null) {
            System.out.println("Username already exists. Please choose a different username.\n");
            return;
        }

        User newUser;
        if (role == UserRole.REGULAR_USER) {
            newUser = new RegularUser(firstName, lastName, username, password);
        }else if(role == UserRole.ADMIN){
            newUser = new SystemAdmin(firstName, lastName, username, password);
        }else{
            newUser = new ReservationManager(firstName, lastName, username, password);
        }
        users.add(newUser);
        System.out.println("Registration successful!\n");
    }

    private User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }



    private void routeUserMenu(User user) {
        if (user instanceof SystemAdmin) {
            LibraryManagementSystem.systemAdminMenu();
        } else if (user instanceof RegularUser) {
            LibraryManagementSystem.regularUserMenu();
        }
        else if (user instanceof ReservationManager) {
            LibraryManagementSystem.reservationManagerMenu();
        }
    }
}
