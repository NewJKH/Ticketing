package com.concert.ticketing.domain.ticket.repository;

import static com.concert.ticketing.domain.concert.entity.QConcert.*;
import static com.concert.ticketing.domain.member.entity.QMember.*;
import static com.concert.ticketing.domain.ticket.entity.QTicket.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.concert.ticketing.domain.ticket.dto.TicketResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
public class TicketCustomRepositoryImpl implements TicketCustomRepository {

	private final JPAQueryFactory queryFactory;

	public TicketCustomRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public void deleteTickets(String email) {
		queryFactory
			.delete(ticket)
			.where(ticket.member.email.eq(email))
			.execute();
	}

	@Override
	public List<TicketResponse> getMyTickets(String email) {
		return queryFactory
			.select(Projections.constructor(TicketResponse.class,
				ticket.number,
				ticket.sector,
				ticket.member.name,
				ticket.concert.title,
				ticket.concert.date,
				ticket.purchaseDate
			))
			.from(ticket)
			.join(ticket.member, member)
			.join(ticket.concert, concert)
			.where(ticket.member.email.eq(email))
			.fetch();
	}
}
