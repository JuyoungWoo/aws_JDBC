package event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class AddUserButtonMouseListener extends MouseAdapter {
	//마우스 어댑터는 추상 클래스
	/*
	 * 일반메소드도 가질 수 있음 -> 일반 메소드도 오버라이딩할 수 있음
	 * */
	@Override
	public void mouseClicked(MouseEvent e) {
		JOptionPane.showMessageDialog(null, "test", "test2", JOptionPane.PLAIN_MESSAGE);
	}
}
