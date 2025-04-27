package controller.auth;

import jakarta.validation.Valid;  // Changed from javax to jakarta
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import dto.auth.SignupRequest;
import dto.auth.UserResponse;
import model.auth.User;
import service.auth.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserResponse getCurrentUser(@AuthenticationPrincipal User user) {
        return userService.getCurrentUser(user);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserResponse updateUser(@AuthenticationPrincipal User user, 
                                 @Valid @RequestBody SignupRequest updateRequest) {
        return userService.updateUser(user, updateRequest);
    }
}