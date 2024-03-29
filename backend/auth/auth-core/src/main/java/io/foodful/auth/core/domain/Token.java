package io.foodful.auth.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
    @Column(length = 50)
    private String value;

    private OffsetDateTime expirationDate;

    private String userId;

    private OffsetDateTime created;

    @PrePersist
    public void onPrePersist() {
        this.created = OffsetDateTime.now();
    }

}