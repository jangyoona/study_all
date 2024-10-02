package com.demoweb.dao;

import com.demoweb.dto.MemberDto;

public interface MemberDao {

	// 회원가입 처리 -> 회원정보를 데이터베이스에 저장
	void insertMember(MemberDto member);

	MemberDto selectMemberByMemberIdAndPasswd(MemberDto member);

	void updatePasswd(MemberDto member);

	int selectMemberCountByMemberId(String memberId);

}