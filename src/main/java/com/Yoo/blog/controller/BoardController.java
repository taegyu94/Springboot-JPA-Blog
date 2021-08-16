package com.Yoo.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Yoo.blog.service.BoardService;

@Controller	//데이터가 아니라 파일을 리턴    ,     viewResolver 작동 >> 해당 index (return) 에 model의 정보를 전달해준다.   
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// @AuthenticationPrincipal PrincipalDetail principal   >>  세션에 접근할 수 있다. principal.getUsername()
	//컨트롤러에서 세션을 어떻게 찾는지?
	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {	// model = jsp에서 request 정보 Model에 정보를 담으면 view까지 데이터를 전달할 수 있다.
		// in application.yml
		//prefix : /WEB-INF/views/
		//suffix : .jsp
		//-> retunrn   >>  /WEB-INF/views/index.jsp  가 된다.
		
		//내가 작성한 글 보기 >> 작성한 글을 Select해와야 한다. 따라서 'index" 페이지로 갈때 데이터를 가져가자.  데이터를 가져가기 위한 Model 이 필요하다
		
		model.addAttribute("boards", boardService.글목록(pageable));	//일단 모든 글목록을 가져오기   "boards" 가 "index" 페이지로 전달되어진다.
		
		return "index";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id)); 
		return "board/detail";
		
	}
	
	
	// USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	//update
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {	//model >> 해당 데이터를 가지고 뷰까지 이동
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
}
