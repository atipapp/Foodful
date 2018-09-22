package io.foodful.dinnerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static io.foodful.dinnerservice.domain.RSVP.Status.PENDING;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RSVP extends BaseEntity {

    private String userId;

    private Status status = PENDING;

    @ManyToOne
    private Dinner dinner;

    public enum Status {
        PENDING, ACCEPTED, DENIED
    }

}
