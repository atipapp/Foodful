package io.foodful.commons.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {

    protected BaseEntity(String id) {
        this.id = id;
    }

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2", parameters = {})
    @GeneratedValue(generator = "uuid2")
    @Column(length = 36)
    private String id;

    private OffsetDateTime created;

    private OffsetDateTime lastModified;

    @PrePersist
    public void onPrePersist() {
        this.created = OffsetDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.lastModified = OffsetDateTime.now();
    }
}