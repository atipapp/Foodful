package io.foodful.dinnerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dinner extends BaseEntity {

    private OffsetDateTime scheduledTime;

    @OneToMany(mappedBy = "dinner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RSVP> rsvps;

    @OneToMany(mappedBy = "dinner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

}
