package io.foodful.auth.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class AccessToken extends Token {
}
