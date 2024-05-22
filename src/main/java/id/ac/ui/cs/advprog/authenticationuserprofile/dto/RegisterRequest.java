package id.ac.ui.cs.advprog.authenticationuserprofile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String role;
    private String phoneNumber;
    private String address;
}
