package com.example.spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Setter;

@Component("serviceConsumer2")
@Scope("prototype") // <bean ... scope="prototype"> 과 같은 효과
public class MyServiceConsumer2 implements ServiceConsumer {

	@Setter(onMethod_ = {@Autowired}) // setter() 자동 생성 + @Autowired 설정하는 방식
	private TimeService timeService;
	@Autowired @Qualifier("messageService") // 변수에 직접 의존성 주입을 설정하는 방식 << 간단하지만, 권장하지 않음.
	private MessageService messageService;
	
	public void doSomething() {
		String message = messageService.getMessage();
		System.out.println(message);
		message = timeService.getTimeString();
		System.out.println(message);
	}
	
	
	

}
