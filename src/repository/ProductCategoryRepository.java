package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnectionMgr;
import entity.ProductCategory;
import entity.ProductColor;

public class ProductCategoryRepository {
	private static ProductCategoryRepository instance;
	private DBConnectionMgr pool;
	
	private ProductCategoryRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static ProductCategoryRepository getInstance() {
		if(instance == null) {
			instance = new ProductCategoryRepository();
		}
		return instance;
	}
	
	//DB에 있는 카테고리 테이블 목록을 ProductCategory 타입의 리스트로 가져옴
	public List<ProductCategory> getProductCategoryListAll(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductCategory> productCategoryList = null;
		
		try {
			con = pool.getConnection();
			String sql = "select "
					+ "product_category_id, "
					+ "product_category_name " //띄어쓰기 있어야됨 (주의!)
					+ "from product_category_tb "
					+ "order by "
					+ "product_category_name";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			productCategoryList = new ArrayList<>();
			
			while(rs.next()) {
				ProductCategory productCategory = ProductCategory.builder()
						.productCategoryId(rs.getInt(1))
						.productCategoryName(rs.getString(2))
						.build();
				
				productCategoryList.add(productCategory);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return productCategoryList;
	}
	
	public ProductCategory findProductCategoryByProductCategoryName(String productCategoryName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductCategory productCategory = null; //ProductCategory 객체를 반환
			
		try {
			con = pool.getConnection();
			
			String sql = "select "
					+ "product_category_id, "
					+ "product_category_name " //띄어쓰기 있어야됨 (주의!)
					+ "from product_category_tb "
					+ "where "
					+ "product_category_name = ?"; //product_category_name 컬럼을 unique로 지정. 색상명을 검색하여 하나라도 안 나오면 false
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, productCategoryName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				productCategory = ProductCategory.builder()
						.productCategoryId(rs.getInt(1))
						.productCategoryName(rs.getString(2))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return productCategory;
	}
	
	//카테고리를 객체 형태로 받아옴
		public int saveProductCategory(ProductCategory productCategory) {
			Connection con = null;
			PreparedStatement pstmt = null;
			int successCount = 0;
			
			try {
				con = pool.getConnection();
				String sql = "insert into product_category_tb values(0, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, productCategory.getProductCategoryName());
				successCount = pstmt.executeUpdate(); //int형으로 반환함
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt);
			}
			return successCount;
		}
}
