package com.study.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.dao.IRegisterDAO;
import com.study.springboot.dto.RegisterDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
	@Autowired
	IRegisterDAO dao; //의존성 자동주입(DI,약한결합)
	
	@RequestMapping("/") //localhost:8080/
	public String root() {
		return "redirect:list"; //페이지 강제이동 //WEB-INF/views/list.jsp
	}
	
	@RequestMapping("/writeForm") //localhost:8080/writeForm
	public String writeForm() {
		return "writeForm"; //WEB-INF/views/writeForm.jsp
	}
	
	@RequestMapping("/write") //localhost:8080/write
	public String write(HttpServletRequest request, RegisterDTO dto) {
		//연락처 합쳐서 저장
		String rtel1 = request.getParameter("rtel1");
		String rtel2 = request.getParameter("rtel2");
		String rtel3 = request.getParameter("rtel3");
		dto.setRtel(rtel1+"-"+rtel2+"-"+rtel3);
		
		// 취미를 배열 처리해서 저장
		String[] hobbies = request.getParameterValues("rhobby");
		String chk = "";
		
		if(hobbies != null) { // 체크박스를 체크했다면 실행
			for(int i=0; i<hobbies.length; i++) {
				chk = chk + hobbies[i] + " ";
			}
			dto.setRhobby(chk);
		} else { // 체크박스를 체크를 안했다면 출력안함
			dto.setRhobby("");
		}
		
		dao.writeDao(dto); // insert 실행
		
		return "redirect:list"; //WEB-INF/views/list.jsp
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("list", dao.listDao());
		return "list";
	}
	
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, Model model) {
		int rno = Integer.parseInt(request.getParameter("rno"));
		model.addAttribute("detail", dao.viewDao(rno));
		return "detail";
	}
	
	// 비밀번호 확인폼 (수정/탈퇴 공용)
	@RequestMapping("/passwordCheckForm")
	public String passwordCheckForm(HttpServletRequest request, Model model) {
		int rno = Integer.parseInt(request.getParameter("rno"));
		String mode = request.getParameter("mode"); // update, delete
		
		model.addAttribute("rno", rno);
		model.addAttribute("mode", mode);
		
		return "passwordCheckForm";
	}
	
	// 비밀번호 확인 처리
	@RequestMapping("/passwordCheck")
	public String passwordCheck(HttpServletRequest request, Model model) {
		int rno = Integer.parseInt(request.getParameter("rno"));
		String mode = request.getParameter("mode"); // update, delete
		String rpasswd = request.getParameter("rpasswd");
		
		if(dao.checkPasswordDao(rno, rpasswd)) {//비밀번호 같다면
			if(mode.equals("update")) { //수정처리
				model.addAttribute("edit", dao.viewDao(rno));
				return "updateForm";
			}
			else if(mode.equals("delete")) { //탈퇴처리
				dao.deleteDao(rno);
				return "redirect:list";
			}
		}
		//비밀번호 같지 않다면
		model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
		model.addAttribute("rno", rno);
		model.addAttribute("mode", mode);
		return "passwordCheckForm";
	}
	
	//회원정보 수정폼
	@RequestMapping("/update")
	public String update(HttpServletRequest request, RegisterDTO dto) {
		//연락처 합쳐서 저장
		String rtel1 = request.getParameter("rtel1");
		String rtel2 = request.getParameter("rtel2");
		String rtel3 = request.getParameter("rtel3");
		dto.setRtel(rtel1+"-"+rtel2+"-"+rtel3);
		
		// 취미를 배열 처리해서 저장
		String[] hobbies = request.getParameterValues("rhobby");
		String chk = "";
		
		if(hobbies != null) { // 체크박스를 체크했다면 실행
			for(int i=0; i<hobbies.length; i++) {
				chk = chk + hobbies[i] + " ";
			}
			dto.setRhobby(chk);
		} else { // 체크박스를 체크를 안했다면 출력안함
			dto.setRhobby("");
		}
		
		dao.updateDao(dto); // update 실행
		
		return "redirect:list"; //WEB-INF/views/list.jsp
	}
	
}
