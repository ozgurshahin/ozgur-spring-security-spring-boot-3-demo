package org.example.ozgurspringsecurityspringboot3demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserEntity {
    private Long id;
    private String email;
    @JsonIgnore//this is important to prevent password to be shown in response
    private String password;
    private String role;
    private String extraInfo;
}
