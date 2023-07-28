package service;

import java.util.ArrayList;
import java.util.List;

import entity.ProductColor;
import repository.ProductColorRepository;

public class ProductColorService {
	
	private ProductColorRepository productColorRepository;
	private static ProductColorService instance;
	
	private ProductColorService() {
		productColorRepository = ProductColorRepository.getInstance();
		//ProductColorService 객체가 생성될 때 같이 생성-> repo 내의 메소드도 같이 사용 가능해짐
	}
	
	public static ProductColorService getInstance() {
		if(instance == null) {
			instance = new ProductColorService();
		}
		return instance;
	}
	
	//productcolor 객체에서 색상명만 가져와서 문자열로된 리스트로 변환하는 과정
	public List<String> getProductColorNameList(){
		List<String> productColorNameList = new ArrayList<>();
		
		productColorRepository.getProductColorListAll().forEach(productColor -> {
			productColorNameList.add(productColor.getProductColorName());
		});
		
		return productColorNameList;
	}
	
	public boolean isProductColorNameDuplicatedProductColorName(String productColorName) {
		boolean result = false;
		result = productColorRepository.findProductColorByProductColorName(productColorName) != null;
		//색상명을 검색했을 때 결과가 없으면 true: 새로 넣을 색상명이 중복되지 않는다는 의미
		return result;
	}
	
	public boolean registerProductColor(ProductColor productColor) {
		boolean result = false;
		result = productColorRepository.saveProductColor(productColor) > 0; // executeUpdate()한 결과가 0보다 크면 정상적으로 insert 된 거임
		return result;
	}
}
