package com.Yoo.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController 는 data 즉 문자 자체를 리턴
@Controller	//data를 리턴할게 아니라 파일을 리턴  // @Controller 어노테이션을 사용하면, tempHome()이라는 
//메소드는 파일을 리턴한다.
public class TempControllerTest {
	
	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//파일리턴 기본경로 : src/main/resources/static    <<   기본경로  
		//but, return "home.html" 이라고 하면   src/main/resources/statichome.html  << 이렇게 경로가 잡힘
		//따라서, 리턴명 : /home.html  이라고 해야한다.
		//풀경로 : src/main/resources/static/home.html    가 된다.
		return "/home.html";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//application.yml   의 prefix  :  Controller가 리턴할때 앞에 붙여주는 경로명
		// suffix  :  뒤에 붙여주는 경로명.
		//prefix : /WEB-INF/views/    ,  suffix  :  .jsp
		//풀 경로는 /WEB-INF/views//test.jsp.jsp 와 같이 리턴명 앞 뒤에 붙는다.
		//따라서, 리턴명을  html과 다르게  "/test.jsp" 가 아니라 "test" 라고 붙여준다.
		return "test" ;    
	}
}
/*
 src/main/resources/static 이라는 디렉토리는 정적인 파일을 저장하는 공간.(브라우저가 바로 인식할수 있는)
html파일 , .png 등 
그러나, .jsp라는 템플릿은 찾을수 없다.  왜냐하면 jsp 파일은 정적인 파일이 아니라 동적인 파일  컴파일이 일어나야하는 파일
원래 스프링부트는 jsp를 지원하지 않아서 사용하려면 의존성을 추가해야한다. in porm.xml
또한, jsp파일을 다른 곳에 저장해야한다.
*/
