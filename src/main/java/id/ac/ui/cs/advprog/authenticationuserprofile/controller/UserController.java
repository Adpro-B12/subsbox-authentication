package id.ac.ui.cs.advprog.authenticationuserprofile.controller;

import id.ac.ui.cs.advprog.authenticationuserprofile.model.User;
import id.ac.ui.cs.advprog.authenticationuserprofile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<User>> registerUser(@RequestBody User user) {
        return userService.registerNewUser(user)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/update")
    public CompletableFuture<ResponseEntity<User>> updateUser(@RequestBody User user) {
        return userService.updateUser(user)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{userId}")
    public CompletableFuture<ResponseEntity<Void>> deleteUser(@PathVariable UUID userId) {
        return userService.deleteUser(userId)
                .thenApply(v -> ResponseEntity.ok().<Void>build());
    }

    @GetMapping("/{email}")
    public CompletableFuture<ResponseEntity<User>> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .thenApply(ResponseEntity::ok);
    }
}
