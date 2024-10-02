package com.demoweb.service;

import com.demoweb.common.Util;
import com.demoweb.dao.MemberDao;
import com.demoweb.dto.MemberDto;
import com.demoweb.factory.DemoWebBeanFactory2;

public class AccountService {
	
//	private MySqlMemberDao mySqlDao = new MySqlMemberDao();
//	private OracleMemberDao memberDao = new OracleMemberDao();
	private MemberDao memberDao = DemoWebBeanFactory2.getMemberDao();
	
	// 회원가입 처리
	// ********* UI영역의 메서드명은 register 와 같은 이름을 쓰는게 관례
	public void registerMember(MemberDto member) {
		
		// 업무 규칙(요구사항) 처리 <비밀번호 암호화>
		String hashedPasswd =  Util.getHashedString(member.getPasswd(), "SHA-256");
		member.setPasswd(hashedPasswd);
		
		// 데이터베이스에 데이터 저장 ( DAO 호출 )
		memberDao.insertMember(member);
		
	}
	
	// 로그인 처리
	public MemberDto loginMember(MemberDto member) {
		
		// 업무 규칙(요구사항) 처리 <비밀번호 암호화>
		String hashedPasswd =  Util.getHashedString(member.getPasswd(), "SHA-256");
		// memberId = hashedPasswd;
		member.setPasswd(hashedPasswd);
		
		// 데이터베이스에 데이터 조회
		MemberDto dto = memberDao.selectMember(member);
		
		// 호출한 곳으로 조회 결과 반환
		return dto;
		
	}
	
	// 비밀번호 초기화
	public void resetPasswd(MemberDto member) {
		
		// 업무 규칙(요구사항) 처리 <비밀번호 암호화>
		String hashedPasswd =  Util.getHashedString(member.getPasswd(), "SHA-256");
		member.setPasswd(hashedPasswd);
		
		memberDao.updatePasswd(member);
		
		
		
	}

	public boolean checkDuplication(String memberId) {
		int count = memberDao.selectMemberCountByMemberId(memberId);
		return count == 0;
	}
	
}

