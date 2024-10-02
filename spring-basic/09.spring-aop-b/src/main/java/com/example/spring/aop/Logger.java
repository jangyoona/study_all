package com.example.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Logger {
	
	@Pointcut("execution(* *..*.*(..))")
	public void pointcutAll() { } // pointcut? : 지정할 범위 어디에 적용할건데. 이 메서드는 의미가 없지만 Annotation을 이용하여 범위를 적용하고, 저장할 곳이 메서드밖에 없어서 어쩔 수 없음.
	
	@Pointcut("execution(* com.example..*.*1(..))")
	public void pointcut1() { }
	
	
	
	@Before("pointcutAll()") // 만들어져 있는 포인트컷 호출 (재사용)
	public void logBefore(JoinPoint joinpoint) { // JoinPoint : 현재 호출되고 있는 메서드 정보 저장
		System.out.printf("==========>Logger.logBefore : %s.%s<==========\n",
				joinpoint.getSignature().getDeclaringTypeName(),
				joinpoint.getSignature().getName());
		
	}
	
	@After("execution(* *..*.*(..))") // 현재 어드바이스에 적용되는 포인트컷 직접 집어넣 (재사용은 안되겠지)
	public void logAfter(JoinPoint joinpoint) { // JoinPoint : 현재 호출되고 있는 메서드 정보 저장
		System.out.printf("==========>Logger.logAfter : %s.%s<==========\n",
				joinpoint.getSignature().getDeclaringTypeName(),
				joinpoint.getSignature().getName());
		
	}
	
	@Around("pointcut1()")
	public Object logAround(ProceedingJoinPoint joinPoint) {
		
		long start = System.currentTimeMillis();
		Object returnValue = null;
		
		try {
			// before area
			returnValue = joinPoint.proceed(); // 실제 메서드 호출 (필수)
			// after returning area
		} catch (Throwable ex) {
			// after throwing area
			
		} finally {
			// after area
			long stop = System.currentTimeMillis();
			System.out.printf("==========> Execution Lap : %d (%s.%s) <==========\n",
					stop-start,
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName());
		}
		
		return returnValue;
		
	}

}
