package id.ac.ui.cs.advprog.authenticationuserprofile.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class User {
    private UUID id;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
}
