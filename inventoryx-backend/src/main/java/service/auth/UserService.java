package service.auth;

import dto.auth.SignupRequest;
import dto.auth.UserResponse;
import model.auth.User;

public interface UserService {
    UserResponse registerUser(SignupRequest signupRequest);
    UserResponse getCurrentUser(User user);
    UserResponse updateUser(User user, SignupRequest updateRequest);
}
