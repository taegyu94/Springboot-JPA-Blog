package com.Yoo.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Yoo.blog.model.Board;
import com.Yoo.blog.model.Reply;
import com.Yoo.blog.repository.BoardRepository;
import com.Yoo.blog.repository.ReplyRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@RestController
public class ReplyControllerTest {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable int id) {
		return boardRepository.findById(id).get(); //jackson 라이브러리(오브젝트를 json으로 리턴) => 모델의 getter를 호출해서 json으로 변환
	}
	
	@GetMapping("/test/reply")	// Reply를 다이렉트로 호출하면 Board를 가져오지만, Board를 통해 Reply를 호출하면 Board를 가져오지 않는다. >> @JsonIgnoreProperties({"board"})
	public List<Reply> getReply() {
		return replyRepository.findAll(); //jackson 라이브러리(오브젝트를 json으로 리턴) => 모델의 getter를 호출해서 json으로 변환
	}
}
