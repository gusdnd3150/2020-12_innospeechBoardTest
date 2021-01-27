package com.test.mysql.test;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

@RequestMapping("/Test/*")
@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	BoardServices services;

	// 게시판 호출
	@RequestMapping(value = "/mainBoard.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView boardList(@RequestParam Map<String, Object> info, HttpServletRequest request,
			HttpServletResponse response) {
		
		logger.info("mainBoard.do");
		logger.trace("trace");
		ModelAndView mav = new ModelAndView();

		Map<String, String> searchParam = new HashMap<String, String>();

		String nowPage = (String) request.getParameter("nowPage");
		String cntPerPage = (String) request.getParameter("cntPerPage");

		// 검색 조건
		String searchType = (String) request.getParameter("searchType");
		String searchContent = (String) request.getParameter("searchContent");
        
		System.out.print(searchContent);
		
		nowPage = nowPage == null ? "1" : nowPage;
		cntPerPage = cntPerPage == null ? "10" : cntPerPage;
		searchType = searchType == null ? "10" : searchType;

		searchParam.put("searchType", searchType);
		searchParam.put("searchContent", searchContent);
		int total = services.searchboardtotal(searchParam); 
		PagingVO paging = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage),
				Integer.parseInt(searchType), searchContent);
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

	public static String getRandomString() { // 파일업로드 시 랜덤값을 만들어줌
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	@ResponseBody         // 
	@RequestMapping(value = "/upload.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String addAfter(@RequestParam Map<String,Object> info, MultipartHttpServletRequest upfile,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String resEnt = null;
		
		List<MultipartFile> files = upfile.getFiles("upload");  //photo라는 이름으로 넘어오는 파일을 전부 받는다
		try {

			JsonObject json = new JsonObject();
			PrintWriter printWriter = null;
			OutputStream out = null;
			
		String imagePath = "/resources/img/"; 
		String path = request.getSession().getServletContext().getRealPath("/");// locallhost8080/
		String savePath = path + imagePath;   //
            String originalFileName = null;
            String originalFileExtension = null;
            String storedFileName = null;
        
		File file = new File(savePath);    //지정한 url에 대한 파일 객체를 생성한다

        if(file.exists() == false){
            file.mkdirs(); // 지정한 경로에 폴더가 없으면 생성한다
        }
		for (MultipartFile mf : files) {   //넘어온 파일 개수만큼 돌리고
	        originalFileName = mf.getOriginalFilename();
	        originalFileExtension = mf.getOriginalFilename().substring(originalFileName.lastIndexOf("."));
	        storedFileName = getRandomString() + originalFileExtension;   //위에 랜덤값을 뽑아주는 매소드 + 

			System.out.println("originFileName : " + originalFileName);
			System.out.println("originalFileExtension : " + originalFileExtension);
			System.out.println("storedFileName : " + storedFileName);

			file = new File(savePath + storedFileName);
	        System.out.println(file.getAbsolutePath()); //파일 절대 경로
	        mf.transferTo(file); // 파일들을 file 경로로 넣	는다
		}

		
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		return resEnt;
	}

	
	/*
	@RequestMapping(value = "fileupload.do", method = RequestMethod.POST)
	@ResponseBody
	public String fileUpload(HttpServletRequest req, HttpServletResponse resp, MultipartHttpServletRequest multiFile)
			throws Exception {
		JsonObject json = new JsonObject();
		PrintWriter printWriter = null;
		OutputStream out = null;
		MultipartFile file = multiFile.getFile("upload");
		
		if (file != null) {
			if (file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {
				if (file.getContentType().toLowerCase().startsWith("image/")) {
					try {
						String fileName = file.getName();
						byte[] bytes = file.getBytes();
						String uploadPath = req.getServletContext().getRealPath("/img");
						File uploadFile = new File(uploadPath);
						if (!uploadFile.exists()) {
							uploadFile.mkdirs();
						}
						fileName = UUID.randomUUID().toString();
						uploadPath = uploadPath + "/" + fileName;
						out = new FileOutputStream(new File(uploadPath));
						out.write(bytes);

						printWriter = resp.getWriter();
						resp.setContentType("text/html");
						String fileUrl = req.getContextPath() + "/img/" + fileName;

						// json 데이터로 등록
						// {"uploaded" : 1, "fileName" : "test.jpg", "url" : "/img/test.jpg"}
						// 이런 형태로 리턴이 나가야함.
						json.addProperty("uploaded", 1);
						json.addProperty("fileName", fileName);
						json.addProperty("url", fileUrl);

						printWriter.println(json);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (out != null) {
							out.close();
						}
						if (printWriter != null) {
							printWriter.close();
						}
					}
				}
			}
		}
		return null;
	}
	*/

}