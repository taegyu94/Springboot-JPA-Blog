package com.Yoo.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Yoo.blog.model.Board;
import com.Yoo.blog.model.User;
import com.Yoo.blog.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) {	//	title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){	// List<User> users = pagingUsers.getContent(); 와 같이 List 타입을 가져갈 수 있지만, Page 타입의 다양한 data들로 페이징을 위해 Page타입으로 리턴
		return boardRepository.findAll(pageable);	// boardRepository.findAll() >> 리턴하면 List<Board> List타입이지만, Pageable타입의 pageable을 넣어서 실행하면 Page 타입이된다.
	}
	
	@Transactional(readOnly = true)	//selecte 만 하니까!
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new  IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		System.out.println("글삭제하기 : "+id);
		boardRepository.deleteById(id);
	}

	@Transactional	
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new  IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				});	//수정하려면 영속화를 먼저해야함.
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료시 (Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹이 일어나고 자동 업데이트가 됨. db flush
	}
	
}
