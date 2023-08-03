package com.peco.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.peco.service.FileuploadService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/peco/*")
@Log4j
public class FileuploadController extends CommonRestController{
	
	public static final String ATTACHES_DIR = "c:\\upload\\";
	
	@Autowired
	FileuploadService service;
	
	/**
	 * -전달된 파일이 없는경우
	 * 
	 * -enctype="multipart/foem-data" 오타
	 * 
	 * -설정이 안되었을때
	 * 		라이브러리 추가(commons-filupload)
	 * 		bean추가
	 * 
	 * @param files
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/ProfileloadActionFetch")
	public @ResponseBody Map<String, Object> fileuploadActionFetch(List<MultipartFile> files, String m_id, RedirectAttributes rttr) throws Exception {
		log.info("fileuploadActionFetch");
		int insertRes = service.Profileupload(files, m_id);
		log.info("업로드 건수 : " + insertRes);
		return responseMap("success", "회원가입되었습니다");

	}
	
}
