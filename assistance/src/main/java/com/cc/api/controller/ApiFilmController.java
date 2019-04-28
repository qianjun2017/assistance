package com.cc.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.web.Response;
import com.cc.film.service.FilmService;

@Controller
@RequestMapping("/api/film")
public class ApiFilmController {

	@Autowired
	private FilmService filmService;
	
	/**
	 * 查询影片年份、语言、国家枚举值
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/enums", method = RequestMethod.GET)
	public Response<Map<String, Object>> queryEnums(){
		Response<Map<String, Object>> response = new Response<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("countries", filmService.queryFilmCountryList());
		map.put("years", filmService.queryFilmYearList());
		response.setData(map);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
