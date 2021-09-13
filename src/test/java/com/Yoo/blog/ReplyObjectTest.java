package com.Yoo.blog;

import org.junit.jupiter.api.Test;

import com.Yoo.blog.model.Reply;

public class ReplyObjectTest {

	@Test
	public void 투스트링테스트() {
		Reply reply = Reply.builder()
				.id(1)
				.user(null)
				.board(null)
				.content("안녕")
				.build();
		
		System.out.println(reply); //오브젝트를 출력시에 toString()이 자동 호출됨.
	}
}
