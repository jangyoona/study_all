package com.demoweb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.demoweb.dto.BoardAttachDto;
import com.demoweb.dto.BoardDto;

// MyBatis MApper.xml DAO 객체 생성할 때 적용할 인터페이스
@Mapper // root-context.xml 파일의 <bean id="boardMapper" ...을 대신하는 annotation
public interface BoardMapper {

	@Insert("INSERT INTO board (boardno, title, writer, content) " +
			"VALUES (BOARD_SEQUENCE.NEXTVAL, #{ title }, #{ writer }, #{ content })")
	@Options(useGeneratedKeys = true, keyColumn = "boardNo", keyProperty = "boardNo")
	void insertBoard(BoardDto board);
	
	@Insert("INSERT INTO board (boardno, title, writer, content) " +
			"VALUES (BOARD_SEQUENCE.NEXTVAL,#{ title }, #{ writer }, #{ content })")
	@SelectKey(statement = "SELECT BOARD_SEQUENCE.CURRVAL FROM DUAL",
			   resultType = Integer.class, keyProperty = "boardNo", before = false)
	void insertBoard2(BoardDto board);
	
	@Insert("INSERT INTO board (boardno, title, writer, content) " +
			"VALUES (#{ boardNo }, #{ title }, #{ writer }, #{ content })")
	@SelectKey(statement = "SELECT BOARD_SEQUENCE.NEXTVAL FROM DUAL",
			   resultType = Integer.class, keyProperty = "boardNo", before = true)
	void insertBoard3(BoardDto board);
	
	@Select("SELECT boardNo, title, writer, readCount, writeDate, modifyDate, deleted " +
			"FROM board " +
			"ORDER BY boardNo DESC ")
	List<BoardDto> selectAllBoard();

	@Select(  "	    SELECT * "
			+ "		FROM "
			+ "			( "
			+ "				SELECT ROWNUM idx, a.* "
			+ "				FROM "
			+ "				( "
			+ "					SELECT boardNo, title, writer, readCount, writeDate, modifyDate, deleted "
			+ "					FROM board "
			+ "					ORDER BY boardNo DESC "
			+ "			) a "
			+ "				WHERE ROWNUM < #{ to } "
			+ "		) b "
			+ "		WHERE b.idx >= #{ from }")
	List<BoardDto> selectBoardByRange(@Param("from") int from, @Param("to") int to); // start, count
	
	
	@Select("SELECT boardNo, title, writer, content, readCount, writeDate, modifyDate "
			+ "FROM board "
			+ "WHERE boardNo = #{ boardNo } AND deleted = '0'")
	BoardDto selectBoardByBoardNo(int boardNo); // 게시글 + 첨부파일 따로따로 조회
	
	@Select("SELECT b.boardNo, b.title, b.writer, b.content, b.readCount, b.writeDate, b.modifyDate, "
			+ "		ba.attachNo, ba.userFileName, ba.savedFileName, ba.downloadCount "
			+ "FROM board b "
			+ "LEFT OUTER JOIN boardattach ba "
			+ "ON b.boardno = ba.boardno "
			+ "WHERE b.boardNo = #{ boardNo } AND b.deleted = '0'")
	@Results(id = "boardResultMap",
			 value = {
					 @Result(column = "boardNo", property = "boardNo", id = true),
					 @Result(column = "title", property = "title"),
					 @Result(column = "writer", property = "writer"),
					 @Result(column = "content", property = "content"),
					 @Result(column = "readCount", property = "readCount"),
					 @Result(column = "writeDate", property = "writeDate"),
					 @Result(column = "modifyDate", property = "modifyDate"),
					 @Result(column = "boardNo", property = "attachments",
					 		 many = @Many(resultMap = "boardAttachResultMap"))
			 })
	BoardDto selectBoardByBoardNo2(int boardNo); // resultMap을 사용하여 join하는 방식
	
	@Select("SELECT b.boardNo, b.title, b.writer, b.content, b.readCount, b.writeDate, b.modifyDate "
			+ "FROM board b "
			+ "WHERE b.boardNo = #{ boardNo } AND b.deleted = '0'")
	@Results(id = "boardResultMap2",
			 value = {
					 @Result(column = "boardNo", property = "boardNo", id = true),
					 @Result(column = "title", property = "title"),
					 @Result(column = "writer", property = "writer"),
					 @Result(column = "content", property = "content"),
					 @Result(column = "readCount", property = "readCount"),
					 @Result(column = "writeDate", property = "writeDate"),
					 @Result(column = "modifyDate", property = "modifyDate"),
					 @Result(column = "boardNo", property = "attachments",
					 		 many = @Many(select = "selectBoardAttachByBoardNo"))
			 })
	BoardDto selectBoardByBoardNo3(int boardNo); // join없이 select 속성으로 참조(?)하여 한번에 가져오는 방식

	@Insert("INSERT INTO boardattach (attachNo, boardNo, userfilename, savedfilename)"
		  + "VALUES (BOARDATTACH_SEQUENCE.NEXTVAL, #{ boardNo }, #{ userFileName }, #{ savedFileName })")
	void insertBoardAttach(BoardAttachDto attach);
	
	@Select("SELECT attachNo, boardNo, userFileName, savedFileName, downloadCount "
		  + "FROM boardAttach "
	  	  + "WHERE boardNo = #{ boardNo } ")
	@Results(id = "boardAttachResultMap",
			 value = {
					 @Result(column = "attachNo", property = "attachNo", id = true),
					 @Result(column = "boardNo", property = "boardNo"),
					 @Result(column = "userFileName", property = "userFileName"),
					 @Result(column = "savedFileName", property = "savedFileName"),
					 @Result(column = "downloadCount", property = "downloadCount"),
			 })
	List<BoardAttachDto> selectBoardAttachByBoardNo(int boardNo);
	
	@Select("SELECT attachNo, boardNo, userFileName, savedFileName, downloadCount "
		  + "FROM boardAttach "
		  + "WHERE attachNo = #{ attachNo }")
	BoardAttachDto selectBoardAttachByAttachNo(int attachNo);
	
	@Select("SELECT COUNT(*) FROM board")
	int selectBoardCount();
	
	
}
