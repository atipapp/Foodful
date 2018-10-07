package io.foodful.dinner.core.domain;

import io.foodful.commons.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    private String content;

    private String userId;

    @ManyToOne
    private Dinner dinner;

}
