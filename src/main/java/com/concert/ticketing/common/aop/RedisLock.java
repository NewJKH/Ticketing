package com.concert.ticketing.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {
	String key();

	long waitTime() default 5L;

	long leaseTime() default 3L;

	TimeUnit timeUnit() default TimeUnit.SECONDS;
}
