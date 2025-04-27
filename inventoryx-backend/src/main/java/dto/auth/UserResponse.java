package dto.auth;

import java.util.List;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private List<String> roles;

    public UserResponse(Long id, String username, String email, String phone, String address, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.roles = roles;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getRoles() {
        return roles;
    }
}