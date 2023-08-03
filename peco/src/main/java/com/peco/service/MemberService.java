package com.peco.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.peco.vo.MemberVO;

@Service
public interface MemberService {
	public MemberVO login(MemberVO member);
	public int insert(MemberVO member);
	public int idCheck(MemberVO member);
	public int nicknameCheck(MemberVO member);
	
	public Map<String, String> naverLogin(HttpServletRequest request, Model model);
	public MemberVO apiLogin(MemberVO apiMember);
}
