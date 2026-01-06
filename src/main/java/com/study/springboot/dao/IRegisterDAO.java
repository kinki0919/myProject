package com.study.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.RegisterDTO;

@Mapper
public interface IRegisterDAO {
	public List<RegisterDTO> listDao(); // 회원목록 조회
	public RegisterDTO viewDao(int rno); // 회원정보 상세보기
	public int writeDao(RegisterDTO dto); //회원가입
	public int deleteDao(int rno); //회원탈퇴
	public int updateDao(RegisterDTO dto); //회원정보 수정
	public boolean checkPasswordDao(int rno, String rpasswd); //비밀번호 체크(수정/삭제 공통)
	
}
