package com.poscodx.mysite.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String handler(Exception e, Model model) {
		// 1. 로킹(logging)
		System.out.println(e);
		
		// 2. 안내 메시지 페이지
		return "errors/exception";
		
		// 3. 종료
		
	}
}
