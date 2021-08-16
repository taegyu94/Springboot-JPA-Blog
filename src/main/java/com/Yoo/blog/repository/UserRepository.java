package com.Yoo.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Yoo.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
//@Repository 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {	//해당 JpaRepository 는 User 테이블을 관리하는 repository,  User테이블의 PK는 integer이다.
	//JPA Naming 쿼리 전략
	//SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);
	
}



//-------------------------------------------전통적인 로그인 방식
//JPA Naming 쿼리 전략
//SELECT * FROM user WHERE username=?1 AND password=?2  쿼리가 동작
//User findByUsernameAndPassword(String username, String password);

//@Query 를통해 직접 설정 가능 둘다 위의 방법과 동일
//@Query(value="SELECT * FROM user WHERE username=?1 AND password=?2", nativeQuery = true)
//User login(String username, String password);
//------------------------------------------------