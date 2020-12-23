package com.test.mysql.test;

import java.util.List;


import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	//총 게시글 수
	public int boardtotal(){
		return sqlSession.selectOne("testBoard.count");
	}
	
	// 게시글 리스트
	public List<BoardVO> list(PagingVO p){
		return sqlSession.selectList("testBoard.list",p);
	}
	
	// 게시글 추가
	public int insertBoard(BoardVO vo) {
		return sqlSession.insert("testBoard.insertBoard",vo);
	}

}
