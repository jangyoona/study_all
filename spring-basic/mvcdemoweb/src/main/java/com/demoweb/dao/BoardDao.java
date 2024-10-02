package com.demoweb.dao;

import java.util.ArrayList;

import com.demoweb.dto.BoardAttachDto;
import com.demoweb.dto.BoardCommentDto;
import com.demoweb.dto.BoardDto;

public interface BoardDao {

	void insertBoard(BoardDto board);

	ArrayList<BoardDto> selectAllBoard();

	// 게시판 페이징 처리 -- 일반 게시판 글 불러오는거랑 똑같고 LIMIT + 조건 추가만 붙음
	ArrayList<BoardDto> selectBoardByRange(int start, int count);

	void insertBoardAttach(BoardAttachDto attach);

	// 글번호를 받아서 게시글 조회 및 반환 ( primary key 검색이므로 단일 객체 반환 )
	BoardDto selectBoardByBoardNo(int boardNo);

	ArrayList<BoardAttachDto> selectBoardAttachByBoardNo(int boardNo);

	BoardAttachDto selectBoardAttachByAttachNo(int attachNo);

	void updateBoardDeleted(int boardNo);

	void deleteBoardAttach(int attachNo);

	void updateBoard(BoardDto board);

	void insertComment(BoardCommentDto comment);

	ArrayList<BoardCommentDto> selectBoardCommentByBoardNo(int boardNo);

	void deleteComment(int commentNo);

	void updateComment(BoardCommentDto comment);

	void insertReComment(BoardCommentDto comment);

	BoardCommentDto selectBoardCommentByCommentNo(int commentNo);

	void updateStep(BoardCommentDto parent);

	// 게시판 페이징 처리를 위한 전체 데이터 갯수 카운트 가져오는 Dao
	int selectBoardCount();

}