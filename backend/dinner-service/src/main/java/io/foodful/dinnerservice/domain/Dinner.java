package io.foodful.dinnerservice.domain;

import lombok.*;

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
@Builder
public class Dinner extends BaseEntity {

    private String title;

    private String location;

    private OffsetDateTime scheduledTime;

    @OneToMany(mappedBy = "dinner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RSVP> rsvps;

    @OneToMany(mappedBy = "dinner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

}
