package com.demoweb.dto;

import java.util.ArrayList;
import java.util.Date;

public class BoardDto {
	private int boardNo;
	private String writer;
	private String title;
	private String content;
	private Date writeDate;
	private Date modifyDate;
	private int readCount;
	private boolean deleted;
	
	// board 테이블과 boardattach 테이블 사이의 1 : Many(다수) 관계를 구현하는 필드 = DB 관계 ex) 게시물은1개, 파일은 여러개
	private ArrayList<BoardAttachDto> attachments;
	
	// board 테이블과 boardComment 테이블 사이의 1 : Many(다수) 관계를 구현하는 필드 = DB 관계 ex) 게시물은1개, 댓글은 여러개
	private ArrayList<BoardCommentDto> comments;
	
	
	public ArrayList<BoardCommentDto> getComments() {
		return comments;
	}
	public void setComments(ArrayList<BoardCommentDto> comments) {
		this.comments = comments;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public String getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public int getReadCount() {
		return readCount;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public ArrayList<BoardAttachDto> getAttachments() {
		return attachments;
	}
	public void setAttachments(ArrayList<BoardAttachDto> attachments) {
		this.attachments = attachments;
	}
	
	

}
