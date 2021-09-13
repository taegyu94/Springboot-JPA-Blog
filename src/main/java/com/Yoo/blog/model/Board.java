package com.Yoo.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity		//@Controller와 같이 클래스의 역할을 적어주는 어노테이션은 가장 아래에 두어 구분을 쉽게.
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto_increment
	private int id;
	
	@Column(nullable = false, length=100)
	private String title;
	
	@Lob	//대용량 데이터 
	private String content;		//섬머노트 라이브러리 (글을 쓰면 디자인이 된다.) <html> 태그가 섞여서 디자인 됨.

	
	//@ColumnDefault("0")		//문자가 아니라 숫자이기 때문에 작은따옴표 필요 X
	private int count;	//조회수
	
	@ManyToOne(fetch = FetchType.EAGER)		// Many = Board , User = one    =>   한명의 유저가 여러개의 게시글을 작성할 수 있다.  연관관계
	@JoinColumn(name="userId")		// 실제 DB에 저장될 때는 userId 로 만들어진다.
	private User user;		//DB는 오브젝트를 저장할 수 없다. 그렇기 때문에 FK 를 사용 . 자바는 오브젝트를 저장할 수 있다.
	
	// board를 삭제할시, 게시글에 달려있는 댓글까지 모두 지울지 남길지 정해야한다.
	// cascade = CascadeType.REMOVE >> 게시글을 삭제할 시 모두 지울수 있게 한다.
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) 	//mappedBy 연관관계의 주인이 아니다.(난 FK가 아니에요.) DB에 컬럼을 만들지 마세요.
	//fetch = FetchType.EAGER  >> @OneToMany의 기본값은 fetch = FetchType.LAZY  이다. 그러나 LAZY는 필요하면 가져오는 방식.
	//댓긋을 무조건 가져오기를 원하기 때문에 EAGER 로 변경
	@JsonIgnoreProperties({"board"}) //Reply를 호출할때, Reply 안에 Board가 있어서 다시 Board를 호출하는 무한 참조 현상이 발생하는데 이를 해결하기 위한 어노테이션
	@OrderBy("id desc") // id값을 기준으로 내림차순으로 정열
	private List<Reply> replys;
	
	@CreationTimestamp	//데이터가 insert 혹은 update 될때 값이 자동으로 저장
	private Timestamp createDate;
	
	
	
}
