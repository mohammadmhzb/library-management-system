package model;

import enums.UserRole;

public abstract class User {
    private int id;
    private String firstName;
    private String LastName;
    private String email;
    private String username;
    private String phoneNumber;
    private String password;
    private UserRole role;


    public User(String firstName, String lastName, String username, String password, UserRole role) {
        this.firstName = firstName;
        LastName = lastName;
        this.username = username;
        setPassword(password);
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = Utils.numberToHash(password);
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //    authenticate
    public boolean authenticate(String password) {
        return this.password.equals(Utils.numberToHash(password));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public abstract void performAction();


}
