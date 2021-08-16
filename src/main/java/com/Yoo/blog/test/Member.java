package com.Yoo.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter		//getter 생성
//@Setter		//setter 생성		
@Data			//getter,setter 둘다 생성
//@AllArgsConstructor		//모든 필드의 생성자 생성
@RequiredArgsConstructor		//final이 붙은 필드의 생성자 생성*/

@Data
//@AllArgsConstructor
@NoArgsConstructor //빈 생성자 생성
public class Member {
	private  int id;
	private  String username;
	private  String password;
	private  String email;
	
	@Builder   // @Builder 를 생성자에 걸어주면, Member m = Member.builder().username("cos").password("12222").email("asd222@gmail.com").build(); 와 같이 순서와 상관없이 필드값을 초기화할 수 있다.
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	

}
