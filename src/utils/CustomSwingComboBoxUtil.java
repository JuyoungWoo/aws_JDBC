package utils;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class CustomSwingComboBoxUtil {
	
	//frame에서 호출. comboBox에 매개변수에 해당하는 모델리스트로 세팅
	public static void setComboBoxModel(JComboBox<?> comboBox, List<String> modelList) {
		if(comboBox == null || modelList == null) {
			return;
		}
		String[] modelArray = new String[modelList.size()];
		
		//매개변수로 받아온 모델리스트를 배열에 담는다.
		for(int i = 0; i < modelList.size(); i++) {
			modelArray[i] = modelList.get(i);
		}
		
		comboBox.setModel(new DefaultComboBoxModel(modelArray));
	}
}
