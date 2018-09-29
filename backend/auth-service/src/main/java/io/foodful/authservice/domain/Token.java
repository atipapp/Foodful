package io.foodful.authservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    private String value;

    private OffsetDateTime expirationDate;

    private String userId;

    private OffsetDateTime created;

    @PrePersist
    public void onPrePersist() {
        this.created = OffsetDateTime.now();
    }

}