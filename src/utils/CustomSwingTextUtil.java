package utils;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CustomSwingTextUtil {
	public static boolean isTextEmpty(Component targetComponent, String str) {
		if(str != null) {
			if(!str.isBlank()) {
				return false;
			}
		}
		//위에 해당하지 않으면 null 이거나 blank임
		JOptionPane.showMessageDialog(targetComponent, "내용을 입력하세요", "입력 오류", JOptionPane.ERROR_MESSAGE);
		return true;
	}
	
	public static void clearTextField(JTextField testField) {
		testField.setText("");
	}
}
