package org.zerock.myapp.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@NoArgsConstructor

// To register manually this interceptor as a bean in the servlet-context.xml

//@Component		// Auto-registration as a bean
public class SampleInterceptor implements HandlerInterceptor {
	
	
	// 1. HTTP request 가 Controller의 Handler method로 위임되기 직전에 콜백
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("=====================================================================");
		log.trace("1. preHandle(req, res, {}) invoked.", handler);
		log.trace("=====================================================================");
		
		log.info("\t+ 1. handler: {}", handler);
		log.info("\t+ 2. type: {}", handler.getClass().getName());
		
//		-----------------------
		
		HandlerMethod requestHandler = (HandlerMethod) handler;
		
		Object controller = requestHandler.getBean();
		Method method = requestHandler.getMethod();
		
		log.info("\t+ 3. requestHandler: {}", requestHandler);
		log.info("\t+ 4. controller: {}", controller);
		log.info("\t+ 5. method: {}", method);
				
		
		// 예제: 요청을 보낸 클라이언트의 IP주소를 체크하여, 위험한 소스에서 온 요청이면,
		//       그 즉시 요청 처리를 끝내버림(through returning false)
//		String clientAddr = req.getRemoteAddr();
//		log.info("\t+ 6. clientAddr: {}", clientAddr);
//		
//		return ("192.168.10.4".equals(clientAddr) ? false : true);	// 지정된 IP주소는 강사PC의 주소임.

		// ------------------------------------------------------------------------
		// If returning false, Incoming request를 뒤로 넘기지 않음(체인이 있든 없든)
		// If returning true,  Incoming request를 뒤로 넘김       (체인이 있든 없든)
		// ------------------------------------------------------------------------
		return true;
	} // preHandle

	
	// 2. Controller의 Handler method 가 수행완료된 직후에 콜백
	//    (*주의*) 단, Controller's handler method에서 예외가 발생하면, 아래의 메소드는 호출되지 못함
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
		log.trace("=====================================================================");
		log.trace("2. postHandle(req, res, {}, {}) invoked.", handler, modelAndView);
		log.trace("=====================================================================");
		
		log.info("\t+ 1. modelAndView: {}", modelAndView);
		
		ModelMap model = modelAndView.getModelMap();		// Model 얻기
		String viewName = modelAndView.getViewName();		// 뷰이름 얻기
		HttpStatus status = modelAndView.getStatus();		// 응답 상태코드 얻기
		
		log.info("\t+ 2. model: {}", model);
		log.info("\t+ 3. viewName: {}", viewName);
		log.info("\t+ 4. status: {}", status);
		
//		---
		
		modelAndView.setViewName("redirect:/login");		// 필요하면, 특수한 경우에, 뷰이름을 변경하여 다른 화면으로 이동시킴
		modelAndView.setStatus(HttpStatus.OK);				// 필요하면, 응답 상태코드마저 변경하여, 브라우저로 전송
		model.put("serverTime", "^^;;");					// 필요하면, 모델에 추가 데이터를 넣어서, 뷰에 전달가능

		log.info("\t+ 5. modelAndView: {}", modelAndView);

	} // postHandle

	
	// 3. Spring MVC의 View가 호출완료된 직후에 콜백
	//    (*주의*) 단, Controller's handler method에서 예외가 발생하든 안하든 무조건 호출됨
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception e) throws Exception {
		log.trace("=====================================================================");
		log.trace("3. afterCompletion(req, res, {}, {}) invoked.", handler, e);
		log.trace("=====================================================================");

		log.info(e);
		
	} // afterCompletion
	
} // end class
