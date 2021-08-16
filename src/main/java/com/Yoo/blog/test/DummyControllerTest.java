package com.Yoo.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Yoo.blog.model.RoleType;
import com.Yoo.blog.model.User;
import com.Yoo.blog.repository.UserRepository;

//html파일이 아니라 data를 리턴해주는 controller
@RestController
public class DummyControllerTest {

	@Autowired	//의존성 주입(DI) 
	private UserRepository userRepository;	
	//spring이 @RestController 를 읽어서 DummyControllerTest 를 멤모리에 저장할 때, private UserRepository userRepository; 는 null
	//그래서 @Autowired 를 붙여주면 자동으로 new 해서 메모리에 저장해준다.
	
	//----------------------------------------------------delete
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		//userRepository.deleteById(id);  그냥 하면 오류가 생길 수 있으니, try-catch로 묶어준다.ex) id가 DB에 없을때.
		
		return "삭제되었습니다. id : "+id;
	}
	
	
	//-----------------------------------------------------
	
	//----------------------------------------------------------  save방식와 더티체킹 방식으로 update 하기  update{
	
	//email, password 정보 수정
	@Transactional	//함수종료시 자동 commit이 됨
	@PutMapping("dummy/user/{id}")	//update에는 PutMapping  ,  주소가 중복되어도, Mapping 방식이 다르면 구분 가능.
	//form태그(post)방식으로 보낸 데이터는 User requestUser  처럼 객체로 받아올 수 있지만,
	//json 데이터를 받아오고 싶다면, @RequestBody 어노테이션이 필요
	//json 데이터를 요청 => Java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아줘요. 이때 필요한 어노테이션이 @RequestBody.
	
	public User updateUser(@PathVariable int id, @RequestBody User requestUser ) {	
		System.out.println("id : "+ id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		/*
		//save 함수는 id를 전달하지 않으면 insert해주고
		//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
		//save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
		//json데이터로 받아온 데이터에는 password,email 만 있는 상황.  >>  받아온 데이터는 requestUser(User(Entity)형)에 저장. password,email 만.
		// requestUser.setId(id); 를 통해 id값을 설정했고, username은 null일 수 없기에 강제 설정. 그 후 save 하게되면 나머지 데이터는 null 값이 된다.
		
		requestUser.setId(id);
		requestUser.setUsername("ssar");	//없으면 username이 null이어서 에러발생. username은 nullable=false라고 했다. 그러나, 여전히 createDate나role은 null 값이므로 실행시 두 값은 null 값이 저장된다.
		userRepository.save(requestUser);
		
		*/
		/*
		//                                                   save를 통해 update 하고자 할때는
		// 이 방식은 id를 통해 DB의 데이터를 찾아 없으면 "수정에 실패하였습니다." 가 출력되고, id값이 있다면 그 행의 data들을 user 객체에 담아 저장
		// -> 완전한 data.  수정전. >> 저장되어있는 data
		// 그 후, user.setPassword(requestUser.getPassword()); 를 통해 
		// 입력 받은 password와 email 값으로 재설정 후, save를 통해 DB에 저장. 
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		userRepository.save(user);
		
		*/
		//----------------------------더티체킹방식   @Transactional   =>  userRepository.save(user); 저장을 날리는 것이 없는 상황
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		return user;
	}
	//----------------------------------------------------------------------------------------------}
	
	//---------------------------------------------------------------여러개의 data를 db에서 select 하기  select{
	//하나의 유저 정보를 가져오는게 아니라 여러개의 유저정보를 가져오자.
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//모든 정보를 가져올때 >> findAll() ,  pageable  >>  findAll(pageable)
	//@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable  
	// 페이징 기본전략 >> 2건씩 들고오고(size=2), id로 sort하고(sort="id"), id를 최신순으로 가져온다(direction=Sort.Direction.DESC)
	//한 페이지당 2건의 데이터를 리턴받을 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		//Page<User> users = userRepository.findAll(pageable);		//findAll(pageable) >> Page 타입을 리턴한다.
		//List<User> users = userRepository.findAll(pageable).getContent();		//findAll(pageable).getContent() >> List 타입을 리턴. content 부분만 필요하다면.
		//추천방법
		Page<User> pagingUsers = userRepository.findAll(pageable);
		List<User> users = pagingUsers.getContent();
		
		return users;
	}
	//-----------------------------------------------------------------------}
	
	//-------------------------------------------------id를 이용하여 하나의 data를 db에서 select 하기   select{
	//{id} 주소로 파라미터를 전달받을 수 있다.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public	 User detail(@PathVariable int id) {
		// user/4를 찾으면(없는값) 내가 DB에서 못찾아오면 user가 null이 될 것 아냐?
		//그럼 return null이 되잖아 그럼 프로그램에 문제가 생기지 않겠니?
		//그래서 Optional로 너의 User 객체를 감싸서 가져올태니 null인지 아닌지 판단해서 return해!!
		
		//User user = userRepository.findById(id).get(); //null이 리턴될 리 없으니까 그냥 User 객체를 뽑아서 저장해.
		/*User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
			@Override
			public User get() {
				//TODO Auto-generated method stub
				return new User();
			}
		});	//null이 리턴되면 객체 하나는 만들어서 저장해.*/
		
		/* 람다식
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
		});
		*/
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. ");
			}
		});
		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//따라서 요청자인 웹브라우저는 자바오브젝트를 이해하지 못한다.
		//변환 ( 웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리 과거엔)
		//스프링부트는 MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		//그래서 브라우저에 http://localhost:8000/blog/dummy/user/3 요청을 하면 json형식으로 보인다.
		return user;
	}
	//---------------------------------------------------------------------------------}
	
	// Post방식으로 데이터 받아와 DB에 저장(insert)하기----------------------------------방법1      insert{
	//http://localhost:8000/blog/dummy/join (요청)
	//http의 body에 username,password,email 데이터를 가지고 요청하면 join 메소드의 파라미터에 전달이 된다.
	//단, 파라미터의 이름이 body의 이름과 일치한다면!
	/*
	@PostMapping("/dummy/join")
	public String join(String username, String password, String email) {	// key=value(form data)(약속된 규칙) 형태의 data를 받아준다.
		System.out.println("username : "+username);
		System.out.println("password : "+password);
		System.out.println("email : "+email);
		return "회원가입이 완료 되었습니다.";
	}*/
	//------------------------------------------------------------------
	//form 태그 방식(Post방식)으로 데이터 받아와 DB에 저장(insert)하기 ---------방법2 object로 값을 가져올 수 있다.
	@PostMapping("/dummy/join")
	public String join(User user) {	// key=value(form data)(약속된 규칙) 형태의 data를 받아준다.
		System.out.println("id : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER);	
		userRepository.save(user);
		 
		return "회원가입이 완료 되었습니다.";
	}
	//--------------------------------------------------------------}
	
}
