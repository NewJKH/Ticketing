package com.concert.ticketing.common.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.concert.ticketing.common.exception.CustomErrorCode;
import com.concert.ticketing.common.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisLockAspect {
	private final RedissonClient redissonClient;

	@Around("@annotation(redisLock)")
	public Object applyRedissonLock(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
		String key = parseKey(redisLock.key(), joinPoint);
		RLock lock = redissonClient.getLock(key);

		boolean available = lock.tryLock(
			redisLock.waitTime(),
			redisLock.leaseTime(),
			redisLock.timeUnit()
		);

		if (!available) {
			log.info("lock wait time exceeded");
			throw new CustomException(CustomErrorCode.REDIS_WRONG_TYPE);
		}

		try {
			return joinPoint.proceed();
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	private String parseKey(String key, ProceedingJoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();

		EvaluationContext context = new StandardEvaluationContext();
		String[] paramNames = methodSignature.getParameterNames();
		Object[] paramValues = joinPoint.getArgs();

		for (int i = 0; i < paramNames.length; i++) {
			context.setVariable(paramNames[i], paramValues[i]);
		}

		ExpressionParser parser = new SpelExpressionParser();
		return parser.parseExpression(key).getValue(context, String.class);
	}
}
