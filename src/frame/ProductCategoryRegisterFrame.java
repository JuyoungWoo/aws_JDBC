package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.ProductCategory;
import entity.ProductColor;
import service.ProductCategoryService;
import service.ProductColorService;
import utils.CustomSwingTextUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductCategoryRegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productCategoryTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductCategoryRegisterFrame frame = new ProductCategoryRegisterFrame();
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
	public ProductCategoryRegisterFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 카테고리 등록");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 410, 54);
		contentPane.add(titleLabel);
		
		JLabel productCategoryLabel = new JLabel("카테고리명");
		productCategoryLabel.setBounds(12, 74, 74, 25);
		contentPane.add(productCategoryLabel);
		
		productCategoryTextField = new JTextField();
		productCategoryTextField.setColumns(10);
		productCategoryTextField.setBounds(103, 74, 319, 25);
		contentPane.add(productCategoryTextField);
		
		JButton registerSubmitBtn = new JButton("등록하기");
		registerSubmitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String productCategoryName = productCategoryTextField.getText();
				
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productCategoryName)) { return; }//빈 값이면 true 반환
				
				if(ProductCategoryService
						.getInstance()
						.isProductCategoryNameDuplicatedProductCategoryName(productCategoryName)) {
					JOptionPane.showMessageDialog(contentPane,"이미 존재하는 분류명입니다", "중복오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				ProductCategory productCategory = ProductCategory.builder()
						.productCategoryName(productCategoryName)
						.build();
				//카테고리가 insert되지 않았을 때
				if(!ProductCategoryService.getInstance().registerProductCategory(productCategory)) {
					JOptionPane.showMessageDialog(contentPane,"색상 등록 중 오류가 발생했습니다.", "등록오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				JOptionPane.showMessageDialog(contentPane,"새로운 색상을 등록하였습니다.", "등록 성공", JOptionPane.PLAIN_MESSAGE);
				CustomSwingTextUtil.clearTextField(productCategoryTextField); //매개변수에 해당되는 텍스트필드를 비워줌
				
			}
		});
		registerSubmitBtn.setBounds(12, 121, 410, 44);
		contentPane.add(registerSubmitBtn);
	}
}
