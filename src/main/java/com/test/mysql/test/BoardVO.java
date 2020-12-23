package com.test.mysql.test;

import org.springframework.stereotype.Component;

@Component
public class BoardVO {
	
	private int boardNum;   //게시판 번호 pk
	private String writer;     //작성자
	private String title;     //제목
	private String content;    //내용
	private String updateD;    //작성일
	
	private int rowN;    //게시글 순번
	private int boardSEQ;   // 시퀀스값
	
	
	public int getBoardSEQ() {
		return boardSEQ;
	}
	public void setBoardSEQ(int boardSEQ) {
		this.boardSEQ = boardSEQ;
	}
	public int getRowN() {
		return rowN;
	}
	public void setRowN(int rowN) {
		this.rowN = rowN;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpdateD() {
		return updateD;
	}
	public void setUpdateD(String updateD) {
		this.updateD = updateD;
	}
	
}
