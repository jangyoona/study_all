package com.demoweb.dto;

public class BoardAttachDto {
	
	private int attachNo;
	private int boardNo;
	private String userFileName; // 사용자가 업로드한 파일 이름
	private String savedFileName; // 서버에 저장한 파일 이름 (고유한 이름) --중복 방지
	private int downloadCount;
	
	// boardattach 테이블과 board 테이블 사이의 1 : 1 관계를 구현하는 필드 ex) 파일 입장에선 1 : 게시물 1개
	private BoardDto board;
	
	public int getAttachNo() {
		return attachNo;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public String getUserFileName() {
		return userFileName;
	}
	public String getSavedFileName() {
		return savedFileName;
	}
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setAttachNo(int attachNo) {
		this.attachNo = attachNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
	}
	public void setSavedFileName(String savedFileName) {
		this.savedFileName = savedFileName;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
	public BoardDto getBoard() {
		return board;
	}
	public void setBoard(BoardDto board) {
		this.board = board;
	}
	

}
