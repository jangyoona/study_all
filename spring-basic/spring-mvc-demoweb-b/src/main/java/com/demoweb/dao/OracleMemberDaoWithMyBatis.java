package com.demoweb.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.demoweb.dto.MemberDto;

import lombok.Setter;

public class OracleMemberDaoWithMyBatis implements MemberDao {

	@Setter
	private SqlSessionTemplate sqlSessionTemplate;
	
	private final String MEMBER_MAPPER = "com.demoweb.mapper.MemberMapper.";
	
	
	// 회원가입 처리 -> 회원정보를 데이터베이스에 저장
	public void insertMember(MemberDto member) {
		
//		sqlSessionTemplate.insert("com.demoweb.mapper.MemberMapper.insertMember", member);
		sqlSessionTemplate.insert(MEMBER_MAPPER + "insertMember", member);
	}
	
	
	// public MemberDto selectMemberByMemberIdAndPasswd(String memberId, String passwd) {
	public MemberDto selectMemberByMemberIdAndPasswd(MemberDto member) {
		MemberDto selectedMember = 
				sqlSessionTemplate.selectOne(MEMBER_MAPPER + "selectMemberByIdAndPasswd", member);
		
		return selectedMember; // 조회 결과를 저장한 객체 반환
		
	}
	
	// 파라미터가 Dto 타입이 아닌, 개별 여러개일 때 = hash map 사용
	public MemberDto selectMemberByMemberIdAndPasswd2(String memberId, String passwd) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("memberId", memberId);
		params.put("passwd", passwd);
		
		MemberDto selectedMember = sqlSessionTemplate.selectOne(MEMBER_MAPPER + "selectMemberByIdAndPasswd2", params);
		
		return selectedMember; // 조회 결과를 저장한 객체 반환
		
	}
	
	
	public void updatePasswd(MemberDto member) {
		sqlSessionTemplate.update(MEMBER_MAPPER + "updatePasswd", member);
		
	}


	public int selectMemberCountByMemberId(String memberId) {
		int count = sqlSessionTemplate.selectOne(MEMBER_MAPPER + "selectMemberCountByMemberId", memberId);
		return count;
			
	}
}
