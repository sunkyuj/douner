package com.sunkyuj.douner.user.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("V")
@Getter
@Setter
public class Volunteer extends User{
}
