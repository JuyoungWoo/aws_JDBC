package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.ProductColor;
import service.ProductColorService;
import utils.CustomSwingTextUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductColorRegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productColorNameTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductColorRegisterFrame frame = new ProductColorRegisterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductColorRegisterFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 색상 등록");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 410, 54);
		contentPane.add(titleLabel);
		
		JLabel productNameLabel = new JLabel("색상명");
		productNameLabel.setBounds(12, 74, 74, 25);
		contentPane.add(productNameLabel);
		
		productColorNameTextField = new JTextField();
		productColorNameTextField.setBounds(103, 74, 319, 25);
		contentPane.add(productColorNameTextField);
		productColorNameTextField.setColumns(10);
		
		JButton registerSubmitBtn = new JButton("등록하기");
		registerSubmitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String productColorName = productColorNameTextField.getText();
				
				if(CustomSwingTextUtil.isTextEmpty(registerSubmitBtn, productColorName)) { return; }//빈 값이면 true 반환
				//isTextEmpty() 호출되고, 빈 값이면 입력오류창 뜬 뒤 해당 메소드를 종료
				
				//service를 통해 입력한 색상명을 통해 나온 Product 결과가 중복됐는지 확인
				if(ProductColorService.getInstance().isProductColorNameDuplicatedProductColorName(productColorName)) {
					JOptionPane.showMessageDialog(contentPane,"이미 존재하는 색상명입니다", "중복오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				ProductColor productColor = ProductColor.builder()
						.productColorName(productColorName)
						.build();
				//색상이 insert되지 않았을 때
				if(!ProductColorService.getInstance().registerProductColor(productColor)) {
					JOptionPane.showMessageDialog(contentPane,"색상 등록 중 오류가 발생했습니다.", "등록오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				JOptionPane.showMessageDialog(contentPane,"새로운 색상을 등록하였습니다.", "등록 성공", JOptionPane.PLAIN_MESSAGE);
				CustomSwingTextUtil.clearTextField(productColorNameTextField); //매개변수에 해당되는 텍스트필드를 비워줌
			}
		});
		registerSubmitBtn.setBounds(12, 109, 410, 50);
		contentPane.add(registerSubmitBtn);
	}
	
	
}
