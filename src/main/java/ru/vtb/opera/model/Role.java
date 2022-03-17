package ru.vtb.opera.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    private Long id;
    private String name;
    private Set<User> users;
}
