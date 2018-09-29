package io.foodful.authservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class AccessToken extends Token {
}
