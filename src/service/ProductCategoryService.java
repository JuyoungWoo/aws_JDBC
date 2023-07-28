package service;

import java.util.ArrayList;
import java.util.List;

import entity.ProductCategory;
import repository.ProductCategoryRepository;

public class ProductCategoryService {

	private ProductCategoryRepository productCategoryRepository;
	private static ProductCategoryService instance;
	
	private ProductCategoryService() {
		productCategoryRepository = ProductCategoryRepository.getInstance();
		//ProductCategoryService 객체가 생성될 때 같이 생성-> Repo 내의 메소드도 같이 사용 가능해짐
	}
	
	public static ProductCategoryService getInstance() {
		if(instance == null) {
			instance = new ProductCategoryService();
		}
		return instance;
	}
	
	//productCategory 객체에서 색상명만 가져와서 문자열로된 리스트로 변환하는 과정
	public List<String> getProductCategoryNameList(){
		List<String> productCategoryNameList = new ArrayList<>();
		
		productCategoryRepository.getProductCategoryListAll().forEach(productCategory -> {
			productCategoryNameList.add(productCategory.getProductCategoryName());
		});
		
		return productCategoryNameList;
	}
	
	public boolean isProductCategoryNameDuplicatedProductCategoryName(String productCategoryName) {
		boolean result = false;
		result = productCategoryRepository.findProductCategoryByProductCategoryName(productCategoryName) != null;
		//카테고리명을 검색했을 때 결과가 없으면 true: 새로 넣을 카테고리명이 중복되지 않는다는 의미
		return result;
	}
	public boolean registerProductCategory(ProductCategory productCategory) {
		boolean result = false;
		result = productCategoryRepository.saveProductCategory(productCategory) > 0; 
		// executeUpdate()한 결과가 0보다 크면 정상적으로 insert 된 거임
		return result;
	}
}
