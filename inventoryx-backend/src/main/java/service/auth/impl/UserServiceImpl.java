package service.auth.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.auth.SignupRequest;
import dto.auth.UserResponse;
import exception.ResourceNotFoundException;
import exception.UsernameAlreadyExistsException;
import exception.EmailAlreadyExistsException;
import model.auth.ERole;
import model.auth.Role;
import model.auth.User;
import repository.auth.RoleRepository;
import repository.auth.UserRepository;
import service.auth.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                         RoleRepository roleRepository,
                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponse registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Error: Email is already in use!");
        }

        User user = new User(signupRequest.getUsername(),
                           signupRequest.getEmail(),
                           passwordEncoder.encode(signupRequest.getPassword()));

        Set<Role> roles = assignRolesToUser(signupRequest.getRoles());
        user.setRoles(roles);
        user.setAddress(signupRequest.getAddress());
        user.setPhone(signupRequest.getPhone());

        userRepository.save(user);
        return convertToUserResponse(user);
    }

    private Set<Role> assignRolesToUser(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Error: ROLE_USER not found."));
            roles.add(userRole);
            return roles;
        }

        strRoles.forEach(role -> {
            switch (role.toLowerCase()) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new ResourceNotFoundException("Error: ROLE_ADMIN not found."));
                    roles.add(adminRole);
                    break;
                default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new ResourceNotFoundException("Error: ROLE_USER not found."));
                    roles.add(userRole);
            }
        });

        return roles;
    }

    @Override
    public UserResponse getCurrentUser(User user) {
        return convertToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(User user, SignupRequest updateRequest) {
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateRequest.getEmail())) {
                throw new EmailAlreadyExistsException("Error: Email is already in use!");
            }
            user.setEmail(updateRequest.getEmail());
        }

        user.setAddress(updateRequest.getAddress());
        user.setPhone(updateRequest.getPhone());
        
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }
        
        userRepository.save(user);
        return convertToUserResponse(user);
    }

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPhone(),
            user.getAddress(),
            user.getRoles() == null ? new ArrayList<>() :
                user.getRoles().stream()
                    .map(role -> role.getName().toString())  // Changed name() to toString()
                    .collect(Collectors.toList())
        );
    }
}