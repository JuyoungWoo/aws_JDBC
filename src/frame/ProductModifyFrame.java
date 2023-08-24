package frame;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Product;
import entity.ProductCategory;
import entity.ProductColor;
import service.ProductCategoryService;
import service.ProductColorService;
import service.ProductService;
import utils.CustomSwingComboBoxUtil;
import utils.CustomSwingTextUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ProductModifyFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productNameTextField;
	private JTextField productPriceTextField;
	private JTextField productIdTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductModifyFrame frame = new ProductModifyFrame(1);
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
	public ProductModifyFrame(int productId) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//EXIT_ON_CLOSE로 하면 상품 등록창 닫을 때 메인 창도 같이 닫힘 
		setBounds(100, 100, 450, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 수정");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 410, 25);
		contentPane.add(titleLabel);
		
		JLabel productIdLabel = new JLabel("상품번호");
		productIdLabel.setBounds(12, 65, 74, 25);
		contentPane.add(productIdLabel);
		
		productIdTextField = new JTextField();
		productIdTextField.setColumns(10);
		productIdTextField.setBounds(103, 65, 319, 25);
		productIdTextField.setEnabled(false); //수정이 불가하게끔
		contentPane.add(productIdTextField);
		
		JLabel productNameLabel = new JLabel("상품명");
		productNameLabel.setBounds(12, 100, 74, 25);
		contentPane.add(productNameLabel);
		
		productNameTextField = new JTextField();
		productNameTextField.setBounds(103, 100, 319, 25);
		contentPane.add(productNameTextField);
		productNameTextField.setColumns(10);
		
		JLabel productPriceLabel = new JLabel("가격");
		productPriceLabel.setBounds(12, 135, 74, 25);
		contentPane.add(productPriceLabel);
		
		productPriceTextField = new JTextField();
		productPriceTextField.setColumns(10);
		productPriceTextField.setBounds(103, 135, 319, 25);
		contentPane.add(productPriceTextField);
		//
		JLabel productColorLabel = new JLabel("색상");
		productColorLabel.setBounds(12, 170, 74, 25);
		contentPane.add(productColorLabel);
		
		JComboBox colorComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setComboBoxModel(colorComboBox, ProductColorService.getInstance().getProductColorNameList());
//		colorComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"})); //이 리스트는 창이 떴을 때 기존의 db 결과가 떠야 됨
		colorComboBox.setBounds(103, 170, 319, 25);
		contentPane.add(colorComboBox);
		//
		JLabel productCategoryLabel = new JLabel("카테고리");
		productCategoryLabel.setBounds(12, 205, 74, 25);
		contentPane.add(productCategoryLabel);
		
		JComboBox categoryComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setComboBoxModel(categoryComboBox, ProductCategoryService.getInstance().getProductCategoryNameList());
		categoryComboBox.setBounds(103, 206, 319, 24);
		contentPane.add(categoryComboBox);
		//
		JButton modifySubmitBtn = new JButton("수정하기");
		modifySubmitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String productName = productNameTextField.getText();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productName)) { return; }
				
				String productPrice = productPriceTextField.getText();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productPrice)) { return; }
				
				String productColorName = (String) colorComboBox.getSelectedItem();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productColorName)) { return; }
				
				String productCategoryName = (String) categoryComboBox.getSelectedItem();
				if(CustomSwingTextUtil.isTextEmpty(contentPane, productCategoryName)) { return; }
				
				if(ProductService.getInstance().isProductNameDuplicated(productName)) {
					JOptionPane.showMessageDialog(contentPane, "이미 존재하는 상품명입니다.", "중복 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Product product = Product.builder()
						.productId(productId)
						.productName(productName)
						.productPrice(Integer.parseInt(productPrice))
						.productColor(ProductColor.builder().productColorName(productColorName).build())
						.productCategory(ProductCategory.builder().productCategoryName(productCategoryName).build())
						.build();
				
				if(!ProductService.getInstance().modifyProduct(product)) {
					JOptionPane.showMessageDialog(contentPane, "상품 수정 중 오류가 발생하셨습니다.", "수정 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
			
				JOptionPane.showMessageDialog(contentPane, "상품을 수정하였습니다.","수정 성공", JOptionPane.PLAIN_MESSAGE);
				//창이 닫히기 전에
				ProductSearchFrame.getInstance().setSearchProductTableModel();
				dispose(); //해당 창만 꺼짐
			}
		});
		modifySubmitBtn.setBounds(12, 240, 410, 50);
		contentPane.add(modifySubmitBtn);
		
		Product product = ProductService.getInstance().getProductByProductId(productId);
		if(product != null) {
			//기존에 있는 상품의 정보를 불러옴
			productIdTextField.setText(Integer.toString(product.getProductId()) );
			productNameTextField.setText(product.getProductName());
			productPriceTextField.setText(Integer.toString(product.getProductPrice()));
			colorComboBox.setSelectedItem(product.getProductColor().getProductColorName());
			categoryComboBox.setSelectedItem(product.getProductCategory().getProductCategoryName());
		}
		
	}
}
