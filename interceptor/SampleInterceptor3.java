package org.zerock.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@NoArgsConstructor

// To register manually this interceptor as a bean in the servlet-context.xml

//@Component		// Auto-registration as a bean
public class SampleInterceptor3 implements HandlerInterceptor {
	
	
	// 1. HTTP request 가 Controller의 Handler method로 위임되기 직전에 콜백
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("preHandle(req, res, {}) invoked.", handler);
		
		return true;
	} // preHandle

	
	// 2. Controller의 Handler method 가 수행완료된 직후에 콜백
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
		log.trace("postHandle(req, res, {}, {}) invoked.", handler, modelAndView);

	} // postHandle

	
	// 3. Spring MVC의 View가 호출완료된 직후에 콜백
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception e) throws Exception {
		log.trace("afterCompletion(req, res, {}, {}) invoked.", handler, e);

		
	} // afterCompletion
	
} // end class
