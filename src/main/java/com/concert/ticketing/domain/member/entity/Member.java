package com.concert.ticketing.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "member")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private String email;

    private String password;
    private String name;

}
