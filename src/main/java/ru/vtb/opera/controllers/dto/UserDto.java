package ru.vtb.opera.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @JsonProperty
    private String name;
    @JsonProperty
    private String password;
    @JsonProperty
    private Set<String> roles;
}
