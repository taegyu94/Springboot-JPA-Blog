package com.Yoo.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Yoo.blog.config.auth.PrincipalDetail;
import com.Yoo.blog.dto.ReplySaveRequestDto;
import com.Yoo.blog.dto.ResponseDto;
import com.Yoo.blog.model.Board;
import com.Yoo.blog.model.Reply;
import com.Yoo.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {	
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value() , 1);
	
	}
	
	
	// 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다
	// dto 를 사용하지 않은 이유는 현재 프로젝트가 작기때문에 구지...
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave( @RequestBody ReplySaveRequestDto replySaveRequestDto) {	
	
		boardService.댓글쓰기(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value() , 1);
	
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value() , 1);
		
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		boardService.글수정하기(id , board);
		return new ResponseDto<Integer>(HttpStatus.OK.value() , 1);
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value() , 1);
		
	}
	
	
	//전통적인 로그인 방식.
	/*
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user , HttpSession session){
	 * System.out.println("UserApiController : login 호출됨"); User principal =
	 * userService.로그인(user);
	 * 
	 * if(principal != null) { session.setAttribute("principal", principal); }
	 * 
	 * return new ResponseDto<Integer>(HttpStatus.OK.value() , 1); }
	 */
}
