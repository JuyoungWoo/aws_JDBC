package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
//import com.mysql.cj.jdbc.CallableStatement;

import config.DBConnectionMgr;
import entity.Product;
import entity.ProductCategory;
import entity.ProductColor;

public class ProductRepository {
	private DBConnectionMgr pool;
	private static ProductRepository instance;
	
	private ProductRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static ProductRepository getInstance() {
		if(instance == null) {
			instance = new ProductRepository();
		}
		return instance;
	}
	public Product findProductByProductId(int productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null; //결과 없으면 null 반환
		
		try {
			con = pool.getConnection();
			String sql = "select \r\n"
					+ "	pt.product_id,\r\n"
					+ "    pt.product_name,\r\n"
					+ "    pt.product_price,\r\n"
					+ "    \r\n"
					+ "    pt.product_color_id,\r\n"
					+ "    pcot.product_color_name,\r\n"
					+ "    \r\n"
					+ "    pt.product_category_id,\r\n"
					+ "    pcat.product_category_name\r\n"
					+ "    \r\n"
					+ "from\r\n"
					+ "	product_tb pt\r\n"
					+ "    left outer join product_color_tb pcot \r\n"
					+ "		on(pcot.product_color_id = pt.product_color_id)\r\n"
					+ "    left outer join product_category_tb pcat\r\n"
					+ "		on(pcat.product_category_id = pt.product_category_id)\r\n"
					+ "where\r\n"
					+ "	pt.product_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = Product.builder()
						.productId(rs.getInt(1))
						.productName(rs.getString(2))
						.productPrice(rs.getInt(3))
						.productColorId(rs.getInt(4))
						.productColor(ProductColor.builder()
								.productColorId(rs.getInt(4))
								.productColorName(rs.getString(5))
								.build())
						.productCategoryId(rs.getInt(6))
						.productCategory(ProductCategory.builder()
								.productCategoryId(rs.getInt(6))
								.productCategoryName(rs.getString(7))
								.build())
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		
		return product;
	}
	
	
	//상품명으로 Product 객체를 찾기
	public Product findProductByProductName(String productName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null; //결과 없으면 null 반환
		
		try {
			con = pool.getConnection();
			String sql = "select \r\n"
					+ "	pt.product_id,\r\n"
					+ "    pt.product_name,\r\n"
					+ "    pt.product_price,\r\n"
					+ "    \r\n"
					+ "    pt.product_color_id,\r\n"
					+ "    pcot.product_color_name,\r\n"
					+ "    \r\n"
					+ "    pt.product_category_id,\r\n"
					+ "    pcat.product_category_name\r\n"
					+ "    \r\n"
					+ "from\r\n"
					+ "	product_tb pt\r\n"
					+ "    left outer join product_color_tb pcot \r\n"
					+ "		on(pcot.product_color_id = pt.product_color_id)\r\n"
					+ "    left outer join product_category_tb pcat\r\n"
					+ "		on(pcat.product_category_id = pt.product_category_id)\r\n"
					+ "where\r\n"
					+ "	pt.product_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = Product.builder()
						.productId(rs.getInt(1))
						.productName(rs.getString(2))
						.productPrice(rs.getInt(3))
						.productColorId(rs.getInt(4))
						.productColor(ProductColor.builder()
								.productColorId(rs.getInt(4))
								.productColorName(rs.getString(5))
								.build())
						.productCategoryId(rs.getInt(6))
						.productCategory(ProductCategory.builder()
								.productCategoryId(rs.getInt(6))
								.productCategoryName(rs.getString(7))
								.build())
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con,pstmt,rs);
		}
		
		return product;
	}
	
	public int saveProduct(Product product) {
		Connection con = null;
		CallableStatement cstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql ="{ call p_insert_product(?, ?, ?, ?) }";
			cstmt = con.prepareCall(sql);
			cstmt.setString(1, product.getProductName());
			cstmt.setInt(2, product.getProductPrice());
			cstmt.setString(3, product.getProductColor().getProductColorName());
			cstmt.setString(4, product.getProductCategory().getProductCategoryName());
			successCount = cstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(cstmt != null) {
				try {
					cstmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			pool.freeConnection(con);
		}
		
		return successCount;
	}
	//select
	public List<Product> getSearchProductList(String searchOption, String searchValue){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> productList = null;
		
		try {
			con = pool.getConnection();
			//검색어가 없으면 이 쿼리를 수행
			String sql = "select \r\n"
					+ "	pt.product_id,\r\n"
					+ "    pt.product_name,\r\n"
					+ "    pt.product_price,\r\n"
					+ "    \r\n"
					+ "    pt.product_color_id,\r\n"
					+ "    pcot.product_color_name,\r\n"
					+ "    pt.product_category_id,\r\n"
					+ "    pcat.product_category_name\r\n"
					+ "    \r\n"
					+ "from\r\n"
					+ "	product_tb pt\r\n"
					+ "    left outer join product_color_tb pcot \r\n"
					+ "		on(pcot.product_color_id = pt.product_color_id)\r\n"
					+ "    left outer join product_category_tb pcat\r\n"
					+ "		on(pcat.product_category_id = pt.product_category_id)\r\n"
					+ "where "
					+ "1 = 1 ";
					// 1 = 1 뒤에 and 조건을 붙여서 검색어 입력 시의 쿼리를 완성
			// #입력 창에 아무것도 입력 안하고 엔터 칠 수도 있기 때문에 그 상황은 전체결과가 나와야함. where 절에 무조건 참인 조건을 작성
			
			
			
			//아래 if문은 입력된 검색어가 있을 때만 수행
			if(searchValue != null) {
				if(!searchValue.isBlank()) {
					String whereSql = null;
					
					switch(searchOption) {
					case "전체":
						whereSql ="and "
							+ "(pt.product_name like concat('%', ?, '%') "
							+ "or pcot.product_color_name like concat('%', ?, '%') "
							+ "or pcat.product_category_name like concat('%', ?, '%'))";
						break;
					case "상품명":
						whereSql = "and pt.product_name like concat('%', ?, '%')";
						break;
					case "색상":
						whereSql = "and pcot.product_color_name like concat('%', ?, '%')";
						break;
					case "카테고리":
						whereSql = "and pcat.product_category_name like concat('%', ?, '%')";
						break;
					
					}
					sql += whereSql; //검색어가 있으면 기존 sql에 조건을 추가
					
				}
			}
			pstmt = con.prepareStatement(sql);
			
			if(searchValue != null) { //null이면 주소 자체가 할당이 안된 상태임. -> isBlack() 메소드가 없다는 의미 이므로 내부 if문을 따로 걸어서 isBlank() 해줘야 됨
				if(!searchValue.isBlank()) {
					if(searchOption.equals("전체")) {
						pstmt.setString(1, searchValue);
						pstmt.setString(2, searchValue);
						pstmt.setString(3, searchValue);
					}else {
						pstmt.setString(1, searchValue);
					}
				}
			}

			rs = pstmt.executeQuery();
			productList = new ArrayList<>();
			
			while(rs.next()) {
				Product product = Product.builder()
						.productId(rs.getInt(1))
						.productName(rs.getString(2))
						.productPrice(rs.getInt(3))
						
						.productColorId(rs.getInt(4))
						.productColor(ProductColor.builder()
								.productColorId(rs.getInt(4))
								.productColorName(rs.getString(5))
								.build())
						
						.productCategoryId(rs.getInt(6))
						.productCategory(ProductCategory.builder()
								.productCategoryId(rs.getInt(6))
								.productCategoryName(rs.getString(7))
								.build())
						.build();
				
				productList.add(product);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return productList;
	}
	//delete
	public int deleteProduct(int productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "delete from product_tb where product_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productId);
			successCount = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return successCount;
	}
	//update
	public int updateProduct(Product product) {
		Connection con = null;
		CallableStatement cstmt = null;
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "{ call p_update_product(?, ?, ?, ?, ?) }";
			
			cstmt = con.prepareCall(sql);
			
			cstmt.setInt(1, product.getProductId());
			cstmt.setString(2, product.getProductName());
			cstmt.setInt(3, product.getProductPrice());
			cstmt.setString(4, product.getProductColor().getProductColorName());
			cstmt.setString(5, product.getProductCategory().getProductCategoryName());
			
			successCount = cstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, cstmt);
		}
		
		return successCount;
	}
}
