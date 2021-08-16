package com.Yoo.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.Yoo.blog.model.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
//static 이하에 있는 /js/**, /css/**, /image/** 허용
//회원가입해야하는데 인증이 되어있을 필요가 없으므로
//인증이 필요 없는 곳에는 /auth 를 붙일 것임.

@Controller
public class UserController {
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	@GetMapping("auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) {	//Data를 리턴해주는 컨트롤러 함수
		
		// POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
		//POST 요청을 도와주는 라이브러리   (HttpsURLConnection, Retrofit2, OkHttp , RestTemplate ....  라이브러리 등등)
		
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "7a0a529aa9ab672db37a8514596f60b5");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers);
		
		// Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
		);
		
		// 응답받은 Json data를 OAuthTokken 에 저장
		// 여러가지 라이브러리 있다. (Gson, Json Simple, ObjectMapper. ...)
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
		
		return response.getBody();
	}
}
