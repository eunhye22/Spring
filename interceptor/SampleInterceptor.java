package org.zerock.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
public class SampleInterceptor implements HandlerInterceptor {
	
	// 1. HTTP request가 Controller의 Handler method로 위임되기 직전에 콜백
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, 
								Object handler) throws Exception {
		
		log.trace("preHandle(req, res, {}, {} invoked.", handler);
		
		return true;
	} // postHandle
	
	// 2. Controller의 Handler method가 수행완료된 직후에 콜백
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, 
								Object handler, ModelAndView modelAndView) throws Exception {
		
		log.trace("postHandle(req, res, {}, {} invoked.", handler);
		
	} // postHandle
	
	
   //-- 3. Srping MVC의 View가 호출완료된 직후에 콜백
   @Override
   public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)
         throws Exception {
      log.trace("afterCompletion(req, res, {}, {}) invoked.", req, res, handler, ex);
      
   } // afterCompletion


} // end
