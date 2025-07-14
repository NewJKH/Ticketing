package com.concert.ticketing.domain.concert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.concert.ticketing.domain.concert.entity.ConcertSector;
import com.concert.ticketing.domain.concert.entity.Sector;

public interface ConcertSectorRepository extends JpaRepository<ConcertSector, Sector> {

	List<ConcertSector> findByConcert_Id(Long concertId);

	@Modifying
	@Query("""
		    UPDATE ConcertSector cs
		    SET cs.remain = cs.remain - 1
		    WHERE cs.name = :sectorName AND cs.concert.id = :concertId AND cs.remain > 0
		""")
	void decrementRemain(@Param("concertId") Long concertId, @Param("sectorName") Sector sectorName);

	@Modifying
	@Query("""
		    UPDATE ConcertSector cs
		    SET cs.remain = cs.remain + 1
		    WHERE cs.name = :sectorName AND cs.concert.id = :concertId
		""")
	void incrementRemain(@Param("concertId") Long concertId, @Param("sectorName") Sector sectorName);

	ConcertSector findByConcert_IdAndName(Long concertId, Sector name);
}
