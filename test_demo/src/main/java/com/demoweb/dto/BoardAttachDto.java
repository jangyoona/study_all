package com.demoweb.dto;

import com.demoweb.entity.BoardAttachEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardAttachDto {
	
	private int attachNo;
	private int boardNo;
	private String userFileName;	// 사용자가 업로드한 파일 이름
	private String savedFileName;	// 서버에 저장한 파일 이름, 고유한 이름
	private int downloadCount;

	public BoardAttachEntity toEntity(){
		return BoardAttachEntity.builder()
//				.attachNo(attachNo) // 처음에는 없는 컬럼이라 의미가 없다고 함?
//				.boardNo(boardNo)
				.userFileName(userFileName)
				.savedFileName(savedFileName)
				.downloadCount(downloadCount).build();
	}

	public static BoardAttachDto of(BoardAttachEntity entity){
		return BoardAttachDto.builder()
				.attachNo(entity.getAttachNo())
				.userFileName(entity.getUserFileName())
				.savedFileName(entity.getSavedFileName())
				.downloadCount(entity.getDownloadCount()).build();
	}
	

}
