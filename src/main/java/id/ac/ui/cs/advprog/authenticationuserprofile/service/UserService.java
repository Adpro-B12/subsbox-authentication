package id.ac.ui.cs.advprog.authenticationuserprofile.service;

import id.ac.ui.cs.advprog.authenticationuserprofile.model.User;

import java.util.UUID;
public interface UserService {
    User registerNewUser(User user);
    User updateUser(User user);
    void deleteUser(UUID userId);
    User getUserByEmail(String email);
}
