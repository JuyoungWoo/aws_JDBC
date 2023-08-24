package frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import entity.Product;
import service.ProductService;
import utils.CustomSwingTableUtil;
import utils.CustomSwingTextUtil;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductSearchFrame extends JFrame {

	private JPanel contentPane;
	private JButton productModifyBtn;
	private JButton productRemoveBtn;
	
	private JComboBox comboBox;
	private JTextField searchTextField;
	private DefaultTableModel searchProductTableModel;
	private JTable productTable;

	private static ProductSearchFrame instance;
	
	private ProductSearchFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 조회");
		titleLabel.setForeground(new Color(0, 64, 128));
		titleLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 14));
		titleLabel.setBounds(12, 10, 205, 29);
		contentPane.add(titleLabel);
		
		productModifyBtn = new JButton("수정");
		productModifyBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				if(!productModifyBtn.isEnabled()) { return; } //수정버튼이 비활성화 됐을때 클릭을 할수 없게(setEnabled(true)로 해도 클릭은 먹음)
				int productId = Integer.parseInt((String) searchProductTableModel.getValueAt(productTable.getSelectedRow(), 0));
				 //테이블에서 선택한 인덱스 값을 수정할 수 있게 id를 추출해서 수정 프레임 생성 시 인자로 넘겨줌
				ProductModifyFrame productModifyFrame = 
						new ProductModifyFrame(productId);
				productModifyFrame.setVisible(true);

			}
		});
		productModifyBtn.setBounds(766, 10, 97, 23);
		contentPane.add(productModifyBtn);
		
		productRemoveBtn = new JButton("삭제");
		productRemoveBtn.setEnabled(false);
		productRemoveBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!productRemoveBtn.isEnabled()) { return;	} //disabled 일 때에도 클릭은 작동됨 -> 클릭하면 에러 발생되는 문제 해결 위해 함수 종료 시킴
				int productId = Integer.parseInt((String)searchProductTableModel.getValueAt(productTable.getSelectedRow(), 0)); 
				//object는 바로 int로 못 바꾸기 때문에 object->String->int 로 바꿔줘야함
				
				if(!ProductService.getInstance().removeProduct(productId)) {
					JOptionPane.showMessageDialog(contentPane, "상품 삭제 중 오류가 발생했습니다.", "삭제 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(contentPane, "선택한 상품을 삭제하였습니다.", "삭제 성공", JOptionPane.PLAIN_MESSAGE);
				setSearchProductTableModel();
			}
		});
		productRemoveBtn.setBounds(875, 10, 97, 23);
		contentPane.add(productRemoveBtn);
		
		//'검색' 레이블
		JLabel searchLabel = new JLabel("검색");
		searchLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		searchLabel.setBounds(508, 46, 43, 21);
		contentPane.add(searchLabel);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"전체", "상품명", "색상", "카테고리"}));
		comboBox.setBounds(563, 45, 97, 23);
		contentPane.add(comboBox);
		
		searchTextField = new JTextField();
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					setSearchProductTableModel();
				}
			}
		});
		searchTextField.setBounds(665, 46, 307, 21);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 77, 960, 374);
		contentPane.add(scrollPane);
		
		productTable = new JTable();
		setSearchProductTableModel();
		productTable.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseClicked(MouseEvent e) {
				productRemoveBtn.setEnabled(true);
				productModifyBtn.setEnabled(true);
			}
		});
		
		scrollPane.setViewportView(productTable);
	}
	
	public static ProductSearchFrame getInstance() {
		if(instance == null) {
			instance = new ProductSearchFrame();
		}
		return instance;
	}
	
	public void setSearchProductTableModel() {
		String searchOption = (String) comboBox.getSelectedItem(); //콤보박스에서 선택된 값을 가져옴
		String searchValue = searchTextField.getText(); //입력한 검색할 내용을 가져옴
		
		List<Product> searchProductList = ProductService.getInstance().searchProduct(searchOption, searchValue);
		String[][] searchProductModelArray = CustomSwingTableUtil.searchProductListToArray(searchProductList);
		
		//searchProductTableModel를 전역변수로 뺐기 때문에 엔터 치고 검색 안해도 기존 테이블이 뜸
		searchProductTableModel = new DefaultTableModel(
					searchProductModelArray,
					new String[] {
						"product_id", "product_name", "product_price", "product_color_id", "product_color_name", "product_category_id", "product_category_name"
					}
					
				);
		productTable.setModel(searchProductTableModel);
		productRemoveBtn.setEnabled(false);
		productModifyBtn.setEnabled(false);
	}
}
