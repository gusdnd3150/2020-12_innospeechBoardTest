package com.test.mysql.test;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/Test/*")
@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	BoardServices services;

	// 게시판 호출
	@RequestMapping(value = "/mainBoard.do", method = RequestMethod.GET)
	public ModelAndView boardList(@RequestParam Map<String, Object> info, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();

		Map<String,Object> searchParam = new HashMap<String,Object>();
		
		String nowPage = (String) info.get("nowPage");
		String cntPerPage = (String) info.get("cntPerPage");

		// 검색 조건
		String searchType = (String) info.get("searchType");
		String searchContent = (String) info.get("searchContent");

		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "10";
		}
		if (searchType == null || searchType == "") {
			searchType = "all";
		}
		
		searchParam.put("searchType", searchType);
		searchParam.put("searchContent", searchContent);
		
		int total = services.searchboardtotal(searchParam);  // 수정중
		
		
		PagingVO paging = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage),searchType,searchContent);
		System.out.print("페이징값 :" + paging.toString());
		
		List<BoardVO> boarList = new ArrayList<BoardVO>();
		boarList = services.list(paging);

		mav.addObject("paging", paging);
		mav.addObject("boardList", boarList);

		mav.setViewName("board");
		return mav;
	}

	// 게시글작성 form 이동
	@RequestMapping(value = "/writeBoard.do", method = RequestMethod.GET)
	public ModelAndView writeBoard(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("writeBoardForm");
		return mav;
	}

	// 게시글 인설트
	@RequestMapping(value = "/insertBoard.do", method = RequestMethod.POST)
	@ResponseBody
	public String writeBoard(BoardVO vo, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			vo.setBoardSEQ(services.boardtotal() + 1);
			services.insertBoard(vo);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "false";
		}
		return result;
	}

	// 게시판 상세내용
	@RequestMapping(value = "/boardDetail.do", method = RequestMethod.GET)
	public ModelAndView boardDetail(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		// System.out.print("넘어온 값: "+vo.getContent());

		String boardNum = request.getParameter("boardNum");
		BoardVO vo = new BoardVO();

		vo.setBoardNum(Integer.parseInt(boardNum));
		try {
			vo = services.boardDetail(vo);
			mav.addObject("boardDetail", vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("boardDetail");
		return mav;
	}

	// 게시글 수정
	@RequestMapping(value = "/modifyBoard.do", method = RequestMethod.POST)
	@ResponseBody
	public String modifyBoard(BoardVO vo, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			int result1 = services.modBoard(vo);
			System.out.print(result1);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "false";
		}
		return result;
	}

	// 게시글 삭제
	@RequestMapping(value = "/deleteBoard.do", method = RequestMethod.GET)
	@ResponseBody
	public String deleteBoard(HttpServletRequest request, HttpServletResponse response) {

		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		String result = "";
		try {
			// int result1 = services.modBoard(vo);
			services.deleteBoard(boardNum);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "false";
		}
		return result;
	}
}