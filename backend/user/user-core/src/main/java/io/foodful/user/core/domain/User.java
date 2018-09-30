package io.foodful.user.core.domain;

import io.foodful.commons.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    private String email;

    private boolean enabled;

    public enum Role {
        USER, ADMIN
    }

    @ElementCollection(targetClass = Role.class)
    private List<Role> roles = new ArrayList<>();

}
