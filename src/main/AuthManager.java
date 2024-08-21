package main;

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
            System.out.println("Log In successful!");
            LibraryManagementSystem.setCurrentUser(user);
            routeUserMenu(user);
        } else {
            System.out.println("Invalid username or password.");
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
