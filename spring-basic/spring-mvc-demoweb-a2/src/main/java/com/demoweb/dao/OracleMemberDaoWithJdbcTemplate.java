package com.demoweb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.demoweb.dto.MemberDto;

import lombok.Setter;

public class OracleMemberDaoWithJdbcTemplate implements MemberDao {

	@Setter
	private JdbcTemplate jdbcTemplate;
	
	// 회원가입 처리 -> 회원정보를 데이터베이스에 저장
	public void insertMember(MemberDto member) {
		
		String sql = "INSERT INTO member (memberid, passwd, email) VALUES(?, ?, ?)";
		jdbcTemplate.update(sql,member.getMemberId(), member.getPasswd(), member.getEmail());
		
	}
	
	
	public MemberDto selectMemberByMemberIdAndPasswd(MemberDto member) {

		String sql = "SELECT memberid, email, usertype, regdate, active FROM member WHERE memberid = ? AND passwd = ? ";
		// 단일 
		MemberDto selectedMember = 
			jdbcTemplate.queryForObject(sql,
										// 1. RowMapper 인터페이스를 구현하는 '익명 클래스' 만들기 + 2. 그 클래스의 인스턴스 만들기
										new RowMapper<MemberDto>() {
											@Override
											public MemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
												MemberDto member = new MemberDto(); // 조회 결과를 저장할 객체임 (set)
												member.setMemberId(rs.getString(1));
												member.setEmail(rs.getString(2));
												member.setUserType(rs.getString(3));
												member.setRegDate(rs.getDate(4));
												member.setActive(rs.getBoolean(5));
												
												return member;
											}
										}, 
										member.getMemberId(), member.getPasswd());
			
		return selectedMember;
	}
	
	
	public void updatePasswd(MemberDto member) {
		
		String sql = "update member set passwd = ? where memberid = ? ";
		jdbcTemplate.update(sql, member.getPasswd(), member.getMemberId());
		
	}


	public int selectMemberCountByMemberId(String memberId) {
		
		String sql = "SELECT COUNT(memberid) FROM member WHERE memberid = ?";
		int count = jdbcTemplate.queryForObject(sql,
									new RowMapper<Integer>() {
										@Override
										public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
											return rs.getInt(1);
										}
										
									},
									memberId);
		return count;
		
		
			
	}
}
