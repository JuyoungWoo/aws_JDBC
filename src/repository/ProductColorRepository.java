package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnectionMgr;
import entity.ProductColor;
import lombok.Builder;

public class ProductColorRepository {
	private DBConnectionMgr pool;
	private static ProductColorRepository instance;
	
	private ProductColorRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static ProductColorRepository getInstance() {
		if(instance == null) {
			instance = new ProductColorRepository();
		}
		return instance;
	}
	
	public List<ProductColor> getProductColorListAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductColor> productColorList = null;
		
		try {
			con = pool.getConnection();
			String sql = "select "
					+ "product_color_id, "
					+ "product_color_name " //띄어쓰기 있어야됨 (주의!)
					+ "from product_color_tb "
					+ "order by "
					+ "product_color_name";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			productColorList = new ArrayList<>();
			
			while(rs.next()) {
				ProductColor productColor = ProductColor.builder()
						.productColorId(rs.getInt(1))
						.productColorName(rs.getString(2))
						.build();
				
				productColorList.add(productColor);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		
		return productColorList;
	}
	
	//찾고자 하는 색상명을 매개변수로 넣고
	public ProductColor findProductColorByProductColorName(String productColorName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductColor productColor = null; //ProductColor 객체를 반환
			
		try {
			con = pool.getConnection();
			
			String sql = "select "
					+ "product_color_id, "
					+ "product_color_name " //띄어쓰기 있어야됨 (주의!)
					+ "from product_color_tb "
					+ "where "
					+ "product_color_name = ?"; //product_color_name 컬럼을 unique로 지정. 색상명을 검색하여 하나라도 안 나오면 false
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, productColorName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				productColor = ProductColor.builder()
						.productColorId(rs.getInt(1))
						.productColorName(rs.getString(2))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return productColor;
	}
	//색상을 객체 형태로 받아옴
	public int saveProductColor(ProductColor productColor) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "insert into product_color_tb values(0, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productColor.getProductColorName());
			successCount = pstmt.executeUpdate(); //int형으로 반환함
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return successCount;
	}
}
