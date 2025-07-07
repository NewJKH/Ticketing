package com.concert.ticketing.domain.member.repository;

import com.concert.ticketing.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
