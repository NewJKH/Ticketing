package com.concert.ticketing.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.concert.ticketing.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);
}
