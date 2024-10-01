package org.mufasadev.ecommerce.project.security.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserResponseInfo {
    @Getter
    @Setter
    private Long id;
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private List<String> roles;

    public UserResponseInfo(Long id, String username, List<String> roles) {
        this.id = id;
        this.roles = roles;
        this.username = username;
    }
}