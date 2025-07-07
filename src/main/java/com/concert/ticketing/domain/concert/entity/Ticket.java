package com.concert.ticketing.domain.concert.entity;

import com.concert.ticketing.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "ticket")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email")
    private Member member;

    @Enumerated
    private Sector sector;

    private LocalDate purchaseDate;
}