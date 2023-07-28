package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.DBConnectionMgr;

public class Main2 {

	public static void main(String[] args) {
		// Study3의 product_tb에 (20230706 상품6) insert 하고 결과 출력
		
		System.out.println(getProductByProductCode(20230701));
		insertProduct(20230707, "상품7");
		System.out.println(getProductByProductCode(20230707));
		deleteProduct(20230707);
		
	}

	public static void printProduct() {
	      DBConnectionMgr pool = DBConnectionMgr.getInstance();
	      
	      Connection con = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      try {
	         con = pool.getConnection();
	         
	         String sql = "select * from product_tb";
	         pstmt = con.prepareStatement(sql);
	         
	         rs = pstmt.executeQuery();
	         
	         System.out.println("\n상품 번호\t|상품 이름");
	         while(rs.next()) {
	            System.out.println(rs.getInt(1) + "\t\t|" + rs.getString(2));
	         }
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         pool.freeConnection(con, pstmt, rs);
	      }
	   }
	
	public static void insertProduct(int productCode, String productName) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 데이터베이스 연결
			con = pool.getConnection();
			//쿼리문 작성
			String sql = "insert into product_tb values(?, ?)";
			//쿼리문 가공 준비
			pstmt = con.prepareStatement(sql);
			//쿼리문 가공
			pstmt.setInt(1, productCode); 
			pstmt.setString(2, productName);
			pstmt.executeUpdate();
			
			System.out.println("Inserted successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 생성된 rs, pstmt, con 객체를 순서대로 소멸(db 연결 해제)
			pool.freeConnection(con, pstmt);
		}
	}
	public static void deleteProduct(int productCode) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 데이터베이스 연결
			con = pool.getConnection();
			//쿼리문 작성
			String sql = "delete from product_tb where productCode = ?";
			//쿼리문 가공 준비
			pstmt = con.prepareStatement(sql);
			//쿼리문 가공
			pstmt.setInt(1, productCode); 
			pstmt.executeUpdate();
			
			System.out.println("Deleted successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 생성된 rs, pstmt, con 객체를 순서대로 소멸(db 연결 해제)
			pool.freeConnection(con, pstmt);
		}
	}
	
	public static Map<String, Object> getProductByProductCode(int productCode) {
		Map<String, Object> resultMap = new HashMap<>();
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// Java와 DB를 연결
			con = pool.getConnection();
			//실행할 쿼리문 선언
			String sql = "select product_code, product_name from product_tb where product_code = ?";
			// 작성한 쿼리문을 가공
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productCode);
			//가공된 쿼리문을 실행 후 그 결과를 ResultSet형태로 반환
			rs = pstmt.executeQuery(); //쿼리문 실행한 결과를 ResultSet형태로 반환

			if(rs.next()) { //테이블에 데이터가 있는지 확인
				resultMap.put("product_code", rs.getInt(1));
				resultMap.put("product_name", rs.getString(2));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return resultMap;
	}
}
