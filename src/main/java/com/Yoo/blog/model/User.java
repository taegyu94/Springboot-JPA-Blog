package com.Yoo.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data	//lombok  ,  getter,setter 생성
@NoArgsConstructor		//빈 생성자 생성
@AllArgsConstructor		//모든 생성자 생성
@Builder							//빌더 패턴
//@DynamicInsert  //insert시 값이 null인 필드를 제외해주는 어노테이션
//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity		//User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {

	@Id	//Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 
	private int id;	//시퀀스,  in mysql   auto_increment
	
	@Column(nullable=false, length=100, unique = true)
	private String username;		//아이디
	
	@Column(nullable=false, length=100)	// 123456 => 해쉬 (비밀번호 암호화)
	private String password; 		//비밀번호
	
	@Column(nullable=false, length=50)
	private String email;				//이메일
	
	//@ColumnDefault("'user'")		//"user" 가 아니라 " 'user' " 작은따옴표로 해야한다. 문자라는 것을 알려줄 필요가 있다.
	//private String role;	//Enum을 쓰는게 좋다.  => Enum을 쓰면 어떤 데이터의 도메인(범위)을 만들수 있다. 
	//DB는 RoleType이라는게 없다.
	@Enumerated(EnumType.STRING) //따라서 알려줄 필요가 있다.
	private RoleType role;
	//만약에 회원가입시 role이 admin, user, manager 가 있는데 이 3개말고 오타라던지 다른것을 입력하게 되면,
	//String 형이라면 입력한 그대로 DB에 저장되지만, Enum을 사용하면 3개중에 1개만 들어갈수 있도록 해줄 수 있다.(도메인, 범위가 정해졌다.)
	
	private String oauth; //kakao , google
	
	
	@CreationTimestamp	//시간이 자동으로 입력
	private Timestamp createDate;			//회원가입했을 시 시간
	
	
}
