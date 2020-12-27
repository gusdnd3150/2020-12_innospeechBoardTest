package com.test.mysql.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServices {
	
	@Autowired 
	private BoardDAO dao;
	
	public int boardtotal() {
		return dao.boardtotal();
	}
	public List<BoardVO> list(PagingVO p){
		return dao.list(p);
	}
	
	public int insertBoard(BoardVO vo) {
		return dao.insertBoard(vo);
		
	}
	
	public BoardVO boardDetail(BoardVO vo) {
		return dao.boardDetail(vo);
		
	}
	
	public int modBoard(BoardVO vo) {
		return dao.modBoard(vo);
		
	}
	
	public void deleteBoard(int vo) {
		 dao.deleteBoard(vo);
	}
	
	public int searchboardtotal(Map<String,String> searchParam) {
		return dao.searchboardtotal(searchParam);
	}

}
