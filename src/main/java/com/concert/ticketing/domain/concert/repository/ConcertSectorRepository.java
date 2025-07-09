package com.concert.ticketing.domain.concert.repository;

import com.concert.ticketing.domain.concert.entity.ConcertSector;
import com.concert.ticketing.domain.concert.entity.Sector;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertSectorRepository extends JpaRepository<ConcertSector, Sector> {

  List<ConcertSector> findByConcert_Id(Long concertId);
}
