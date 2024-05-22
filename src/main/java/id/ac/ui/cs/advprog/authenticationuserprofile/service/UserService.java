package id.ac.ui.cs.advprog.authenticationuserprofile.service;

import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationuserprofile.dto.UserProfileDTO;

import java.util.List;

public interface UserService {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    List<UserProfileDTO> getAllProfiles();
    UserProfileDTO getProfileByUsername(String username);
    UserProfileDTO updateProfile(String username, UserProfileDTO profile);
    void deleteProfile(String username);
}