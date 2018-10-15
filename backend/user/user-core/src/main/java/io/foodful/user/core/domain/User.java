package io.foodful.user.core.domain;

import io.foodful.commons.data.BaseEntity;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
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

    private String externalId;

    public enum Role {
        USER, ADMIN
    }

    @ElementCollection(targetClass = Role.class)
    private List<Role> roles = new ArrayList<>();

}
