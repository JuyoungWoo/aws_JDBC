package service;

import java.util.List;

import entity.Product;
import repository.ProductRepository;

public class ProductService { //싱글톤
	
	private ProductRepository productRepository;
	private static ProductService instance;
	
	private ProductService() {
		productRepository = ProductRepository.getInstance(); //서비스 객체가 생성될 때 레퍼지토리 객체도 생성
	}
	
	public static ProductService getInstance() {
		if(instance == null) {
			instance = new ProductService();
		}
		return instance;
	}
	//상품명 중복 검사
	public boolean isProductNameDuplicated(String productName) {
		return productRepository.findProductByProductName(productName) != null; //name으로 검색한 결과(Product로 반환된 결과)가 있으면 중복임
	}
	//상품 등록
	public boolean registerProduct(Product product) {
		return productRepository.saveProduct(product) > 0;
	}
	//상품 검색
	public List<Product> searchProduct(String searchOption, String searchValue){
		return productRepository.getSearchProductList(searchOption, searchValue);
	}
	
	public boolean removeProduct(int productId) {
		return productRepository.deleteProduct(productId) > 0; //삭제된 게 없을 경우 false
	}
	
	public Product getProductByProductId(int productId) {
		return productRepository.findProductByProductId(productId);
	}
	
	public boolean modifyProduct(Product product) {
		return productRepository.updateProduct(product) > 0;
	}
}
