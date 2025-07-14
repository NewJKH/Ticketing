package com.concert.ticketing.domain.concert.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.concert.ticketing.domain.concert.entity.ConcertSector;
import com.concert.ticketing.domain.concert.entity.Sector;

import jakarta.persistence.LockModeType;

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

	Optional<ConcertSector> findByConcert_IdAndName(Long concertId, Sector name);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT cs FROM ConcertSector cs WHERE cs.concert.id = :concertId AND cs.name = :sectorName")
	Optional<ConcertSector> findWithPessimisticLock(@Param("concertId") Long concertId,
		@Param("sectorName") Sector sectorName);
}