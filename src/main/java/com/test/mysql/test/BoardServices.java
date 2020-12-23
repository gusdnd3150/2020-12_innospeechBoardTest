package com.test.mysql.test;

import java.util.List;

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
	
	public BoardVO boardDetail() {
		return null;
		
	}

}
