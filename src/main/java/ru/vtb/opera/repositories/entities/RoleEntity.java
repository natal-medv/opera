package ru.vtb.opera.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import ru.vtb.opera.model.ERole;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ERole name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    @Override
    public String getAuthority() {
        return getName().name();
    }

}
