package com.demoweb.dto;

import java.util.Date;

import lombok.Data;

@Data // Getter, Setter, ToString 등등 자동으로 생성해줌 - 롬북
public class MemberDtoWithoutLombok {
	private String memberId;
	private String passwd;
	private String email;
	private String userType;
	private Date regDate;
	private boolean active;
	
	
}
