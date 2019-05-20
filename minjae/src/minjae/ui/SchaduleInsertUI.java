package minjae.ui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import minjae.dao.ManagerDAO;
import minjae.dao.scheduleDAO;
import minjae.dto.CustomerDTO;
import minjae.dto.scheduleDTO;

public class SchaduleInsertUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtF_Customer;
	private JTextField txtF_Desc;
	private JButton btn_Cancle;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SchaduleInsertUI frame = new SchaduleInsertUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public SchaduleInsertUI() {
//		
//	}
	
	public SchaduleInsertUI(scheduleDTO sdto) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lbl_Customer = new JLabel("\uACE0    \uAC1D");
		
		JLabel lbl_Desc = new JLabel("\uB0B4    \uC6A9");
		
		txtF_Customer = new JTextField();
		if(sdto.getPhone() != null) {
			ManagerDAO md = new ManagerDAO();
			List<CustomerDTO> b = md.searchList_P(sdto.getPhone());
			for(CustomerDTO beans : b) {
				sdto.setCustID(beans.getNo());
			}
			txtF_Customer.setText(sdto.getName() + " : " + sdto.getPhone());
		}
		txtF_Customer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new selectCustomerUI(sdto);
				setVisible(false);
			}
		});
		txtF_Customer.setColumns(10);
		
		txtF_Desc = new JTextField();
		txtF_Desc.setColumns(10);
		
		System.out.println("!!!!!"+sdto.toString());
		
		JButton btn_Save = new JButton("\uC800    \uC7A5");
		btn_Save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scheduleDAO sd = new scheduleDAO();
				sdto.setScheDesc(txtF_Desc.getText());
				System.out.println(sdto.toString());
				sd.insertSchedule(sdto);
				setVisible(false);
			}
		});
		
		btn_Cancle = new JButton("\uCDE8    \uC18C");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_Customer)
							.addGap(18)
							.addComponent(txtF_Customer, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_Desc)
							.addGap(18)
							.addComponent(txtF_Desc, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(200, Short.MAX_VALUE)
					.addComponent(btn_Cancle)
					.addGap(18)
					.addComponent(btn_Save)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Customer)
						.addComponent(txtF_Customer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_Desc)
						.addComponent(txtF_Desc, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_Save)
						.addComponent(btn_Cancle))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
}
