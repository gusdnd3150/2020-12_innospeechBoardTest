package com.test.mysql.test;

import java.util.List;
import java.util.Map;

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
	
	// 검색 조건 게시글 수 
	public int searchboardtotal(Map<String,String> searchParam) {
		return sqlSession.selectOne("testBoard.searchBoardTotal",searchParam);
	}
	
	// 게시글 리스트
	public List<BoardVO> list(PagingVO p){
		return sqlSession.selectList("testBoard.list",p);
	}
	
	// 게시글 추가
	public int insertBoard(BoardVO vo) {
		return sqlSession.insert("testBoard.insertBoard",vo);
	}
	
	// 게시글 상세내용
	public BoardVO boardDetail(BoardVO vo) {
		return sqlSession.selectOne("testBoard.boardDetail",vo);
		
	}
	// 게시글 수정
	public int modBoard(BoardVO vo) {
		return sqlSession.update("testBoard.modBoard",vo);
		
	}
	// 게시글 삭제
	public void deleteBoard(int vo) {
		 sqlSession.delete("testBoard.deleteBoard",vo);
	}

}
