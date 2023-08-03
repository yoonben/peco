package com.peco.controller;

import java.util.HashMap;
import java.util.Map;


public class CommonRestController {
	
	private final String REST_WRITE = "등록";
	private final String REST_EDIT = "수정";
	private final String REST_DELETE = "삭제";
	protected final String REST_SUCCESS = "success";
	protected final String REST_FAIL = "fail";
	/**
	 * 입력, 수정, 삭제의 경우 int 값을 반환합니다.
	 * 결과를 받아서 Map을 생성후 반환 합니다.
	 * @return
	 */
	// map을 생성후 result, msg세팅
	public Map<String, Object> responseMap(int res, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(res > 0) {
			map.put("result", REST_SUCCESS);
			map.put("msg", msg+" 되었습니다.");
		}else {
			map.put("result", REST_FAIL);
			map.put("msg", msg+"중 예외가 발생하였습니다");

		}
		
		return map;
	}
	
	public Map<String, Object> responseWriteMap(int res){
		return responseMap(res,REST_WRITE);
	}
	
	public Map<String, Object> responseEditeMap(int res){
		return responseMap(res,REST_EDIT);
	}
	
	public Map<String, Object> responseDeleteMap(int res){
		return responseMap(res,REST_DELETE);
	}
	
	public Map<String, Object> responseMap(String result, String msg){
			Map<String, Object> map = new HashMap<String, Object>();
		
		
			map.put("result", result);
			map.put("msg", msg);

		
		return map;
	}

}
