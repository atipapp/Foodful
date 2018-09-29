package io.foodful.authservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class RefreshToken extends Token{

    @OneToOne
    private AccessToken accessToken;

}
