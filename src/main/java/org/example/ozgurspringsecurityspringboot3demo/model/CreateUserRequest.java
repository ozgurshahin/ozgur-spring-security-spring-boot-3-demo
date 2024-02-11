package org.example.ozgurspringsecurityspringboot3demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
    private String email;
    private String password;
}
