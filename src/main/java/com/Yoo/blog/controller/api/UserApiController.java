package com.Yoo.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Yoo.blog.config.auth.PrincipalDetail;
import com.Yoo.blog.dto.ResponseDto;
import com.Yoo.blog.model.RoleType;
import com.Yoo.blog.model.User;
import com.Yoo.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//@Autowired		//이와 같이 HttpSession session 를 @Autowired DI 해서 session을 사용할 수 있다.
	//private HttpSession session;
	
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {	// username, password, email
		System.out.println("UserApiController : save 호출됨");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 되요.
		userService.회원가입(user);	//1이면 성공, -1 실패
		return new ResponseDto<Integer>(HttpStatus.OK.value() , 1);	//200 : HTTP에서 통신에 정상적으로 성공했다.
		//자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB값은 변경이 됐음.
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 바로 적용되지 않음. 직접 세션값을 변경해야함(로그아웃하고 다시 로그인해야함)
		
		//세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value() , 1);
	}
}



//전통적인 로그인 방식.
/*
 * @PostMapping("/api/user/login") public ResponseDto<Integer>
 * login(@RequestBody User user , HttpSession session){
 * System.out.println("UserApiController : login 호출됨"); User principal =
 * userService.로그인(user);
 * 
 * if(principal != null) { session.setAttribute("principal", principal); }
 * 
 * return new ResponseDto<Integer>(HttpStatus.OK.value() , 1); }
 */
