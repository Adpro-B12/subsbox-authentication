package id.ac.ui.cs.advprog.authenticationuserprofile.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    private UserRole(String role) {
        this.role = role;
    }

    public static boolean contains(String param) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}
