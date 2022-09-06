package org.zerock.myapp.interceptor;

import java.sql.Timestamp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.myapp.common.SharedScopeKeys;
import org.zerock.myapp.domain.UserVO;
import org.zerock.myapp.service.UserService;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

// Authentication 수행 interceptor
@Component
public class LoginInterceptor implements HandlerInterceptor {

	private UserService service;
	
	// 1. HTTP request 가 Controller의 Handler method로 위임되기 직전에 콜백

	// Session Scope 에 저장되어 있던 모든 정보 파괴 수행
	// (*주의*) 명시적으로 "로그아웃" 요청을 보내지 않는 한, 세션 자체를 파괴해서는 안됨
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		log.trace("=====================================================================");
		log.trace("1. preHandle(req, res, {}) invoked.", handler);
		log.trace("=====================================================================");
		
		// Step.1 Session Scope에 접근할 수 있는 HttpSession 객체를 획득
		HttpSession session = req.getSession();
		log.info("\t+ 1. session Id: {}", session.getId());
		
		// Step.2 Session Scope에서 UserVO 인증객체를 파괴!!
		session.removeAttribute(SharedScopeKeys.USER_KEY);
		log.info("\t+ 2. UserVO shared object deleted.");
		
		return true;
	} // preHandle

	
	// 2. Controller의 Handler method 가 수행완료된 직후에 콜백
	//    (*주의*) 단, Controller's handler method에서 예외가 발생하면, 아래의 메소드는 호출되지 못함
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
		log.trace("=====================================================================");
		log.trace("2. postHandle(req, res, {}, {}) invoked.", handler, modelAndView);
		log.trace("=====================================================================");
		
		// Step.1 Session Scope에 접근할 수 있는 HttpSession 객체를 획득
		HttpSession session = req.getSession();
		log.info("\t+ 1. session Id: {}", session.getId());
		
		// Step.2 매개변수 modelAndView를 조사해서, UserVO 가 들어 있는지 확인
		//        있으면, UserVO를 인증객체로 Session Scope에 올려 놓자!!!
		ModelMap model = modelAndView.getModelMap();
		UserVO vo = (UserVO) model.get(SharedScopeKeys.LOGIN_KEY);
		
		if(vo != null) { // 로그인 성공
			// ========================================================= //
			// (1) 세션영역에 인증객체인 UserVO를 바인딩 처리 - 인증수행
			// ========================================================= //
			session.setAttribute(SharedScopeKeys.USER_KEY, vo);
			log.info("\t+ 2. Authentication Succeed.");
			// ========================================================= //
			// (2) 자동로그인 옵션체크 -> on되어있으면, 자동로그인용 쿠키 처리
			// ========================================================= //
			
			// Step.1 자동로그인 옵션 체크
			boolean isRememberMe = checkRememberMeOption(req);
			log.info("\t+ inRememberMe: {}", isRememberMe);
			
			// Step.2 자동로그인 쿠키 생성 및 쿠키의 만료기간도 설정
			if(isRememberMe) { // if on
				String sessionId = req.getSession().getId();   // 쿠키값 획득 : 현재 웹브라우저의 이름
				
				// 쿠키값: 키=값 형태로 되어있음
				// 자동로그인용 쿠키값은 현재 인증에 성공한 웹브라우저의 이름(즉, 세션ID)
				Cookie rememberMeCookie = new Cookie(SharedScopeKeys.REMEMBERME_KEY, sessionId);
				
				final int maxAge = 1*60*60*24*7;	// in seconds
				rememberMeCookie.setMaxAge(maxAge);	// 쿠키의 유효기간을 최대 1주일(7일)로 설정
				
				rememberMeCookie.setPath("/");	// 새로운 요청을 서버로 전송시, 그 어떠한 요청URI 발생시에도, 쿠키값을 전송
				
				// Step. 3 (Step.2)에서 생성한 자동로그인 쿠키를 응답메시지의 헤더(`Set-Cookie`) 헤더에 저장
				res.addCookie(rememberMeCookie);
				
				// Step. 4 자동로그인 쿠키의 값 + 만료일자 2개의 값을 tbl_user 테이블에 기록(저장)
				long now = System.currentTimeMillis();
				long expiry = now + (maxAge * 1000L);		// 자동로그인 쿠키의 만료일자
				
				Timestamp expiryTS = new Timestamp(expiry);		// 만료일자 계산
				
				boolean isModifiedWithRememberMe = this.service.modifyUserWithRememberMe(vo.getUserid(), sessionId, expiryTS);
				log.info("\t+ isModifiedWithRememberMe: {}", isModifiedWithRememberMe);
				
			} // if : 자동로그인 옵션이 on시
			
		} // if : 인증 성공시
		
	} // postHandle

	// 자동로그인 옵션이 on/off 되어있는지 체크해서 반환
	private boolean checkRememberMeOption(HttpServletRequest req) {
		log.trace("checkRememberMeOption(req) invoked.");
		
		String rememberMe = req.getParameter("rememberMe");
		
		return rememberMe !=null;
	}//checkRememberMeOption
	
	// 3. Spring MVC의 View가 호출완료된 직후에 콜백
	//    (*주의*) 단, Controller's handler method에서 예외가 발생하든 안하든 무조건 호출됨
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception e) throws Exception {
		log.trace("=====================================================================");
		log.trace("3. afterCompletion(req, res, {}, {}) invoked.", handler, e);
		log.trace("=====================================================================");

		
		
	} // afterCompletion
	
} // end class
