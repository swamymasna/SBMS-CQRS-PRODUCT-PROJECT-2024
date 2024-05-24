package com.kes.ip.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@AllArgsConstructor
public class LoggingAspect {

	private ObjectMapper mapper;

	@Pointcut(value = "execution(* com.kes.ip.service.*.*(..))")
	public void servicePointcut() {

	}

	@Pointcut(value = "execution(* com.kes.ip.controller.*.*(..))")
	public void controllerPointcut() {

	}

	@Around(value = "servicePointcut() || controllerPointcut()")
	public Object loggingAdvice(ProceedingJoinPoint pjp) throws Throwable {

		String methodName = pjp.getSignature().getName();

		String clzName = pjp.getTarget().getClass().getName();

		Object[] args = pjp.getArgs();

		log.info("Entered into : " + clzName + " : " + methodName + "() " + "Arguments : "
				+ mapper.writeValueAsString(args));

		Object object = pjp.proceed();

		log.info("Returning Back From : " + clzName + " : " + methodName + "() " + "Response : "
				+ mapper.writeValueAsString(object));

		return object;
	}
}
