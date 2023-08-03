package com.peco.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peco.service.ApiExamMemberProfile;
import com.peco.controller.CommonRestController;
import com.peco.mapper.MemberMapper;
import com.peco.service.MemberService;
import com.peco.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberMapper memberMapper;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public MemberVO login(MemberVO paramMember) {
		MemberVO member = memberMapper.login(paramMember);

		if (member != null) {
			// 사용자가 입력한 비밀번호가 일치하는지 확인
			// 사용자가 입력한 비밀번호, 데이터베이스에 암호돠되어 저장된 비밀번호
			boolean res = encoder.matches(paramMember.getPw(), member.getPw());

			return member;
		}

		return null;
	}
	
	@Override
	public MemberVO apiLogin(MemberVO apiMember) {
		MemberVO member = memberMapper.login(apiMember);

		return member;
	}
	
	@Override
	public int insert(MemberVO member) {

		member.setPw(encoder.encode(member.getPw()));

		return memberMapper.insert(member);
	}

	@Override
	public int idCheck(MemberVO member) {

		return memberMapper.idCheck(member);
	}

	@Override
	public int nicknameCheck(MemberVO member) {
		// TODO Auto-generated method stub
		return memberMapper.nicknameCheck(member);
	}
	
	@Autowired
	ApiExamMemberProfile apiExam;
	
	@Override
	public Map<String, String> naverLogin(HttpServletRequest request, Model model) {
		// callback처리 -> access_token
		try {
			Map<String, String> callbackRes = callback(request);

			String access_token = callbackRes.get("access_token");

			System.out.println("=========================================================" + access_token);

			// access_token -> 사용자 정보 조회
			Map<String, Object> responseBody = apiExam.getMemberProfile(access_token);

			Map<String, String> response = (Map<String, String>) responseBody.get("response");
			System.out.println("===========================naverLogin");
			System.out.println("id : " + response.get("id"));
			System.out.println("name : " + response.get("name"));
			System.out.println("gender : " + response.get("gender"));
			System.out.println("=======================================");

			model.addAttribute("apiId", response.get("id"));
			model.addAttribute("apiName", response.get("name"));
			model.addAttribute("apiEmail", response.get("email"));
			model.addAttribute("apiMobile", response.get("mobile"));
			
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}

	}
	
	public Map<String, String> callback(HttpServletRequest request) throws Exception {

		String clientId = "vThUa8bHb7IlAn52GUi5";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "fFkU3qH9K1";// 애플리케이션 클라이언트 시크릿값";
		String code = request.getParameter("code");
		String state = request.getParameter("state");

		try {
			String redirectURI = URLEncoder.encode("http://localhost:8080/peco/naver_callback", "UTF-8");
			String apiURL;
			apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
			apiURL += "client_id=" + clientId;
			apiURL += "&client_secret=" + clientSecret;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&code=" + code;
			apiURL += "&state=" + state;
			String access_token = "";
			String refresh_token = "";
			System.out.println("apiURL=" + apiURL);
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			if (responseCode == 200) {
				System.out.println("token요청" + res.toString());

				// json문자열을 Map으로 변환
				Map<String, String> map = new HashMap<String, String>();

				// jackson라이브러리 사용
				ObjectMapper objectMapper = new ObjectMapper();

				map = objectMapper.readValue(res.toString(), Map.class);

				return map;
			} else {
				throw new Exception("callback 반환 코드 : " + responseCode);
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception("callback 처리중 예외가 발생하였습니다.");
		}

	}
}
