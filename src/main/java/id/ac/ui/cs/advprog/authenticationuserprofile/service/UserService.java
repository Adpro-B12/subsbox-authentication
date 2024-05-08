package id.ac.ui.cs.advprog.authenticationuserprofile.service;

import id.ac.ui.cs.advprog.authenticationuserprofile.model.User;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<User> registerNewUser(User user);
    CompletableFuture<User> updateUser(User user);
    CompletableFuture<Void> deleteUser(UUID userId);
    CompletableFuture<User> getUserByEmail(String email);
}
