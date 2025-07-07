package com.concert.ticketing.domain.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "concert_sector")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertSector {
    @Id
    @Enumerated
    private Sector name;

    private int remain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;
}
