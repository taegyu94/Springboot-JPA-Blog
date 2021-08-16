package com.Yoo.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML)파일 로 응답을 하고 싶으면
//@Controlller  

//사용자가 요청 -> 응답(Data)를 응답하고 싶음면  @RestController

@RestController
public class HttpControllerTest {
	
	private static final String TAG="HttpControllerTest";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//Member m = new Member(1,"hong","1234","email");
		Member m = Member.builder().username("cos").password("12222").email("asd222@gmail.com").build();
		System.out.println(TAG+"getter: "+m.getUsername());
		m.setUsername("ssar");
		System.out.println(TAG+"setter: "+m.getUsername());
		return "lombok test 완료";
	}
	
	
	//인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
	//http://localhost:8080/http/get  (select)
	//@RequestParam int id    :       
	//http://localhost:8080/http/get?id=1 처럼 쿼리 스트링을 이용하여 데이터를 전송 할때 값을 받아올 수 있다.
	//여러개 ?  http://localhost:8080/http/get?id=1&username=hong .. &연산자사용.
	//(@RequestParam int id, @RequestParam String username  ....)  
	//--------------------------------------------------------------------------------------Get
	/*    --------->  따로 따로 데이터를 받을 시 @RequestParam + type value
	@GetMapping("/http/get")
	public String getTest(@RequestParam int id, @RequestParam String username) {
		return "get 요청: "+id+","+username;
	}*/
	
	//------------한꺼번에 받고 싶을 때,  (Member m)  Member 라는 클래스에 변수값의 getter,setter,constructor  존재
 	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	//--------------------------------------------------------------------------------------------
 	
 	//------------------------------------------------------------------------------------post
	//http://localhost:8080/http/post  (insert)
 	//----------------------- form태그처럼 x-www-form-urlencoded 타입을 활용한 방법
 	/*
	//post요청은 일반적으로 form태그를 활용하여 정보를 보냄
	//mime 타입 : x-www-form-urlencoded
	@PostMapping("/http/post")
	public String postTest(Member m) {
		return "post 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}*/
	//-----------------------
 	//---------------------text로 post 요청	in postman  raw 형  ... body 데이터는 @RequestBody  ..  mime타입 : text/plain
 	/*
	@PostMapping("/http/post")
	public String postTest(@RequestBody String text) {
		return "post 요청 : "+text;
	}*/
 	//---------------------
 	//-------------------------json post요청  in postman  raw-json 형 선택   mime타입 : application/json
	//spring이 mime타입이 application/json 인형태로 요청을 받으면 (@RequestBody Member m ) 을 통해 자동 Mapping
 	//but, text/plain 형태를 보내게 되면 Mapping이 되지 않으므로, (@RequestBody String text) 로 받아야함.
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {  // MessageConverter (스프링부트) 가 mapping을 함.
		return "post 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	//--------------------------
	//------------------------------------------------------------------------------------------------
	
	//----------------------------------------------------------------put
	//put , delete 도 post와 마찬가지로 body데이터를 object(Member)에 mapping해서 데이터를 받을 수 있다. @RequestBody 어노테이션을 통해!
	//http://localhost:8080/http/put  (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}

	//http://localhost:8080/http/delete  (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
