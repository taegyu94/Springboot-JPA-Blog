package com.Yoo.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Yoo.blog.dto.ReplySaveRequestDto;
import com.Yoo.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	@Modifying
	@Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())",nativeQuery = true)
	int mSave(int userId , int boardId, String content);	
	// JDBC가 update, insert등을 실행하게되면, 리턴값으로 업데이트된 행의 개수를 리턴해줌. >> int값으로 리턴
}
