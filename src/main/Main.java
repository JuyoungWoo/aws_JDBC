package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnectionMgr;

public class Main {
	

	public static void main(String[] args) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// finally 블록에 freeConnection을 넣어줬으니 변수를 바깥으로 빼내야 함
		try {
			// Java와 DB를 연결
			con = pool.getConnection();
			//실행할 쿼리문 선언
			String sql = "select * from user_tb";
			// 작성한 쿼리문을 가공
			pstmt = con.prepareStatement(sql);
			// pstmt
			
			//가공된 쿼리문을 실행 후 그 결과를 ResultSet형태로 반환
			rs = pstmt.executeQuery(); //쿼리문 실행한 결과를 ResultSet형태로 반환
			
			System.out.println("user_id\t|\tusername|\tpassword");
			// 결과가 담긴 resultSet을 반복 작업을 통해 데이터 조회
			// rs 자체는 테이블 하나임
			while(rs.next()) { //next() 한 행을 가져옴
				//커서 처음은 컬럼명이 적혀있는 행을 가리킴
				// getInt() -> 정수
				// getString() -> 문자열
				System.out.println( rs.getInt(1) + "\t|\t" + rs.getString(2) + "\t|\t" + rs.getString(3));
//				System.out.println(rs.getString(2)); //1번째 부터 시작. 2번째 인덱스 컬럼 출력
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 생성된 rs, pstmt, con 객체를 순서대로 소멸(db 연결 해제)
			// con, pstmt, rs에서 sqlException이 발생하여 실행이 종료 되어도 연결 해제는 반드시
			pool.freeConnection(con, pstmt, rs);
			
		}
/////////////////////////////////////////////////////////////////////////////////////		
		try {
			// 데이터베이스 연결
			con = pool.getConnection();
			
			//쿼리문 작성
			String sql = "insert into user_tb values(0, ?, ?)";
//			String sql = "insert into user_tb values(0, \'ttt\', \'1234\')"; ? 값에 직접 넣고 싶으면 이렇게
			
			//쿼리문 가공 준비
			pstmt = con.prepareStatement(sql);
			
			//쿼리문 가공
			pstmt.setString(1, "ttt"); //1번째 ? 자리에 'ttt' 를 넣음
			pstmt.setString(2, "1234");
			
			int successCount = pstmt.executeUpdate();
			System.out.println("insert 성공 횟수: " + successCount);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 생성된 rs, pstmt, con 객체를 순서대로 소멸(db 연결 해제)
			pool.freeConnection(con, pstmt);
		}
////////////////////////////////////////////////////////////////////////
		try {
			// Java와 DB를 연결
			con = pool.getConnection();
			//실행할 쿼리문 선언
			String sql = "select * from user_tb";
			// 작성한 쿼리문을 가공
			pstmt = con.prepareStatement(sql);
			// pstmt
			
			//가공된 쿼리문을 실행 후 그 결과를 ResultSet형태로 반환
			rs = pstmt.executeQuery(); //쿼리문 실행한 결과를 ResultSet형태로 반환
			
			System.out.println("user_id\t|\tusername|\tpassword");
			// 결과가 담긴 resultSet을 반복 작업을 통해 데이터 조회
			// rs 자체는 테이블 하나임
			while(rs.next()) { //next() 한 행을 가져옴
				//커서 처음은 컬럼명이 적혀있는 행을 가리킴
				// getInt() -> 정수
				// getString() -> 문자열
				System.out.println( rs.getInt(1) + "\t|\t" + rs.getString(2) + "\t|\t" + rs.getString(3));
//				System.out.println(rs.getString(2)); //1번째 부터 시작. 2번째 인덱스 컬럼 출력
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 생성된 rs, pstmt, con 객체를 순서대로 소멸(db 연결 해제)
			// con, pstmt, rs에서 sqlException이 발생하여 실행이 종료 되어도 연결 해제는 반드시
			pool.freeConnection(con, pstmt, rs);
			
		}
	}

}
