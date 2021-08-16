package com.Yoo.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Yoo.blog.model.User;

import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
@Getter
public class PrincipalDetail implements UserDetails{
	
	private User user;	//콤포지션 (객체를 품고있다?)
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴한다. (true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있지 않았는지 리턴한다. (true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴한다. (true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화(사용가능)인지 리턴한다. (true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 계정의 어떤 권한을 가졌는지 리턴한다.
	//	계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 우리는 한개만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();	// ArrayList는 Collection 타입.
		
		/*		add함수안에 들어올수 있는 파라미터는 GrantedAuthority 밖에 없고 GrantedAuthority 는 getAuthority 함수만 들고 있어서 람다식으로 표현 가능.
		collectors.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return "ROLE_"+user.getRole();		//ROLE_USER or ROLE_ADMIN 이 리턴 .  role을 리턴해줄때 ROLE_ 를 붙이는 것이 약속. prefix
			}
		});*/	
		
		collectors.add(()->{return "ROLE_"+user.getRole();});
		
		return collectors;
	}
	
}
