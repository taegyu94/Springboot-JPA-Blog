package com.Yoo.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Yoo.blog.model.Board;


public interface BoardRepository extends JpaRepository<Board, Integer> {	
	
}



//-------------------------------------------전통적인 로그인 방식
//JPA Naming 쿼리 전략
//SELECT * FROM user WHERE username=?1 AND password=?2  쿼리가 동작
//User findByUsernameAndPassword(String username, String password);

//@Query 를통해 직접 설정 가능 둘다 위의 방법과 동일
//@Query(value="SELECT * FROM user WHERE username=?1 AND password=?2", nativeQuery = true)
//User login(String username, String password);
//------------------------------------------------