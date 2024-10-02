package com.demoweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.jdbc.support.JdbcUtils;

import com.demoweb.dto.MemberDto;

import lombok.Setter;

public class OracleMemberDaoWithConnectionPool implements MemberDao {
	
	@Setter
	private DataSource dataSource;

	
	// 회원가입 처리 -> 회원정보를 데이터베이스에 저장
	public void insertMember(MemberDto member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			// 1. 드라이버 준비
			// 2. 연결 객체 만들기
			conn = dataSource.getConnection();
			
			// 3. 명령 객체 만들기 >> SQL문 주입
			String sql = "INSERT INTO member (memberid, passwd, email) VALUES(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getEmail());
			
			// 4. 명령 실행 // 보내는 명령이 insert, update, delete 는 -> executeUpdate
			pstmt.executeUpdate();
			
			// 5. 결과가 있으면 결과 처리
			
			
			// 6. 연결 종료
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeStatement(pstmt);
			JdbcUtils.closeConnection(conn);
		}
	}
	
	
	public MemberDto selectMemberByMemberIdAndPasswd(MemberDto member) {
		
		// memberId와 passwd로 회원정보(모든 컬럼) 조회하고 결과를 console에 출력하는 JDBC 코드 구현
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //SELECT문에서 쓰이는 놈.
		MemberDto selectedMember = null; // 조회 결과를 저장할 변수
		
		try {
			// 1. 드라이버 준비
			// 2. 연결 객체 만들기
			conn = dataSource.getConnection();
			
			// 3. 명령 객체 만들기 >> SQL문 주입
			String sql = "SELECT memberid, email, usertype, regdate, active FROM member WHERE memberid = ? AND passwd = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPasswd());
			
			
			// 4. 명령 실행 (결과가 있으면 결과 저장 - SELECT인 경우)
			rs = pstmt.executeQuery();
			
			// 5. 결과가 있으면 결과 처리
			if (rs.next()) {
				selectedMember = new MemberDto(); // 조회 결과를 저장할 객체임 (set)
				selectedMember.setMemberId(rs.getString(1));
				selectedMember.setEmail(rs.getString(2));
				selectedMember.setUserType(rs.getString(3));
				selectedMember.setRegDate(rs.getDate(4));
				selectedMember.setActive(rs.getBoolean(5));
			}
			
			// 6. 연결 종료
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(pstmt);
			JdbcUtils.closeConnection(conn);

		}
		return selectedMember; //조회 결과 반환
	}
	
	
	public void updatePasswd(MemberDto member) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try { 
			
		// 1. 드라이버 준비
		// 2. 연결 객체 만들기
		conn = dataSource.getConnection();
		
		// 3. 명령 객체 만들기 >> SQL문 주입
		String sql = "update member set passwd = ? where memberid = ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, member.getPasswd());
		pstmt.setString(2, member.getMemberId());
		
		pstmt.executeUpdate();
		
		int affectedCount = pstmt.executeUpdate();
		System.out.println("수정된 행의 갯수: " + affectedCount);
		// DML구문 executeupdate 사용 시 => 반환값이 사실 있었음.
		// 반환값(int) = 영향받은 행의 갯수 => 0 이면 실패 / 0 이상이면 명령 성공
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeStatement(pstmt);
			JdbcUtils.closeConnection(conn);
		}
		
	}


	public int selectMemberCountByMemberId(String memberId) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null; //SELECT문에서 쓰이는 놈.
			int count = 0; // 조회 결과를 저장할 변수
			
			try {
				// 1. 드라이버 준비
				// 2. 연결 객체 만들기
				conn = dataSource.getConnection();
				
				// 3. 명령 객체 만들기 >> SQL문 주입
				String sql = "SELECT COUNT(memberid) FROM member WHERE memberid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberId);
				
				
				// 4. 명령 실행 (결과가 있으면 결과 저장 - SELECT인 경우)
				rs = pstmt.executeQuery();
				
				// 5. 결과가 있으면 결과 처리
				rs.next();
				count = rs.getInt(1);
				
					
				// 6. 연결 종료
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(pstmt);
				JdbcUtils.closeConnection(conn);

			}
			return count;
	}
}
