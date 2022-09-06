package org.zerock.myapp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.zerock.myapp.common.SharedScopeKeys;
import org.zerock.myapp.domain.UserVO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

// 로그아웃 요청을 처리하는 역할 수행
@Component
public class LogoutInterceptor implements HandlerInterceptor {
	
	
	// 1. HTTP request 가 Controller의 Handler method로 위임되기 직전에 콜백
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("=====================================================================");
		log.trace("1. preHandle(req, res, {}) invoked.", handler);
		log.trace("=====================================================================");
		
		// Step.1 Session Scope에 접근할 수 있는 HttpSession 객체를 획득
		HttpSession session = req.getSession();
		log.info("\t+ 1. Session Id: {}", session.getId());
		
		// Step.2 Session 객체를 파괴 -> 인증정보(UserVO)객체도 파괴하자!
		UserVO vo = (UserVO) session.getAttribute(SharedScopeKeys.USER_KEY);
		
		session.removeAttribute(SharedScopeKeys.USER_KEY);
		session.invalidate();

		log.info("\t+ 2. Session Destroyed: {}", vo);
		
		// Step.3 명시적으로 로그아웃을 수행시켜서 여기까지 왔다면, 더이상 현재 웹브라우저에 설정된
		//			자동로그인 기능도 무력화 시켜야 함
		
		Cookie rememberMeCookie = WebUtils.getCookie(req, SharedScopeKeys.REMEMBERME_KEY);
		if(rememberMeCookie != null) {		// 자동로그인 기능이 여전히 유효한 웹브라우저인 경우
			log.info("\t+ 3. Cookie to be destroyed - name: {}, value: {}", 
						rememberMeCookie.getName(), rememberMeCookie.getValue());
			
			rememberMeCookie.setMaxAge(0);	// 유효시간 0초 : 그 즉시 파괴하라 (to 웹브라우저)
			rememberMeCookie.setPath("/");
			
			res.addCookie(rememberMeCookie);	// 응답메시지의 헤더(Set-Cookie)에 쿠키 설정
			
		} // if
		
		
		// 로그인창으로 이동 (re-direct)
		req.getSession().setAttribute(SharedScopeKeys.LOGIN_RESULT, "Signed Out Successfully.");
		res.sendRedirect("/user/login");
		
		return false;	// 컨트롤러로 요청을 위임하지 않음
	} // preHandle

	
	// 2. Controller의 Handler method 가 수행완료된 직후에 콜백
	//    (*주의*) 단, Controller's handler method에서 예외가 발생하면, 아래의 메소드는 호출되지 못함
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
		log.trace("=====================================================================");
		log.trace("2. postHandle(req, res, {}, {}) invoked.", handler, modelAndView);
		log.trace("=====================================================================");
		
		
	} // postHandle

	
	// 3. Spring MVC의 View가 호출완료된 직후에 콜백
	//    (*주의*) 단, Controller's handler method에서 예외가 발생하든 안하든 무조건 호출됨
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception e) throws Exception {
		log.trace("=====================================================================");
		log.trace("3. afterCompletion(req, res, {}, {}) invoked.", handler, e);
		log.trace("=====================================================================");

		
		
	} // afterCompletion
	
} // end class
