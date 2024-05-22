package id.ac.ui.cs.advprog.authenticationuserprofile.model;

import id.ac.ui.cs.advprog.authenticationuserprofile.enums.UserRole;

import java.util.regex.Pattern;

public class UserBuilder {
    private final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private final Pattern usernamePattern = Pattern.compile("^[a-z0-9._]+$");
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private String address;

    public UserBuilder username(String username) {
        if (username.isEmpty() || !usernamePattern.matcher(username).matches()) {
            throw new IllegalArgumentException("Username is empty or contains invalid characters.");
        }
        this.username = username;
        return this;
    }

    public UserBuilder fullName(String fullName) {
        this.fullName = fullName.isEmpty() ? "Pengguna" : fullName;
        return this;
    }

    public UserBuilder password(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        this.password = password;
        return this;
    }

    public UserBuilder email(String email) {
        if (!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid or empty email.");
        }
        this.email = email;
        return this;
    }

    public UserBuilder phoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone Number cannot be empty.");
        }
        this.phoneNumber = phoneNumber;
        return this;
    }
    public UserBuilder role(String role){
        if (!UserRole.contains(role)) {
            throw new IllegalArgumentException("Invalid role.");
        }
        this.role = UserRole.valueOf(role);
        return this;
    }

    public UserBuilder address(String address) {
        if (address.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.address = address;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUsername(this.username);
        user.setFullName(this.fullName);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setPhoneNumber(this.phoneNumber);
        user.setRole(this.role);
        user.setAddress(this.address);
        return user;
    }
}
