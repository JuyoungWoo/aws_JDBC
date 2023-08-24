package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import frame.ProductCategoryRegisterFrame;
import frame.ProductColorRegisterFrame;
import frame.ProductRegisterFrame;
import frame.ProductSearchFrame;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductManagementApplication extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductManagementApplication frame = new ProductManagementApplication();
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
	public ProductManagementApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 상품 등록 버튼 이벤트
		JButton productRegisterFrameOpenBtn = new JButton("상품 등록");
		productRegisterFrameOpenBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductRegisterFrame productRegisterFrame = new ProductRegisterFrame();
				productRegisterFrame.setVisible(true);
			}
		});
		productRegisterFrameOpenBtn.setBounds(12, 77, 410, 43);
		contentPane.add(productRegisterFrameOpenBtn);
		
		JButton productSearchFrameOpenBtn = new JButton("상품 조회");
		productSearchFrameOpenBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductSearchFrame productSearchFrame = ProductSearchFrame.getInstance();
				productSearchFrame.setVisible(true);
			}
		});
		productSearchFrameOpenBtn.setBounds(12, 17, 410, 43);
		contentPane.add(productSearchFrameOpenBtn);
		
		JButton productColorRegisterFrameOpenBtn = new JButton("상품 색상 등록");
		productColorRegisterFrameOpenBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductColorRegisterFrame productColorRegisterFrame =
						new ProductColorRegisterFrame();
				productColorRegisterFrame.setVisible(true);
			}
		});
		productColorRegisterFrameOpenBtn.setBounds(12, 137, 410, 43);
		contentPane.add(productColorRegisterFrameOpenBtn);

		JButton productCategoryRegisterFrameOpenBtn = new JButton("상품 카테고리 등록");
		productCategoryRegisterFrameOpenBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductCategoryRegisterFrame productCategoryRegisterFrame =
						new ProductCategoryRegisterFrame();
				productCategoryRegisterFrame.setVisible(true);
			}
		});
		productCategoryRegisterFrameOpenBtn.setBounds(12, 197, 410, 43);
		contentPane.add(productCategoryRegisterFrameOpenBtn);
		
	}
}
