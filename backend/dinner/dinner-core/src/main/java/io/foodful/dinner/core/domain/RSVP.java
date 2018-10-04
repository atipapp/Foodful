package io.foodful.dinner.core.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static io.foodful.dinner.core.domain.RSVP.Status.PENDING;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RSVP extends BaseEntity {

    private String userId;

    private Status status = PENDING;

    @ManyToOne
    private Dinner dinner;

    public enum Status {
        PENDING, ACCEPTED, DENIED
    }

}
