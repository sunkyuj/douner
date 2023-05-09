package com.sunkyuj.douner.domain.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("R")
@Getter
@Setter
public class Requester extends User{
}
