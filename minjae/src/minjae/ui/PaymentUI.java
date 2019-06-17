package minjae.ui;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import minjae.dao.ScheduleDAO;
import minjae.dao.TotalDAO;
import minjae.dto.ScheduleDTO;
import minjae.dto.TotalDTO;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PaymentUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtF_Pay;
	private JTextField txtF_ChangeAdd;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PaymentUI frame = new PaymentUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public PaymentUI(ScheduleDTO sdto,int change) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lbl_1 = new JLabel("\uACB0\uC81C \uAE08\uC561 : ");
		
		txtF_Pay = new JTextField();
		txtF_Pay.setColumns(10);
		
		JLabel lbl_Customer = new JLabel("");
		lbl_Customer.setText(sdto.getName());
		
		JLabel lbl_2 = new JLabel("\uACE0\uAC1D\uB2D8");
		
		JLabel lbl_3 = new JLabel("\uC794\uC561 : ");
		
		JLabel lbl_Change = new JLabel("");
		lbl_Change.setText(Integer.toString(change));
		
		JLabel lbl_4 = new JLabel("\uACB0\uC81C \uBC29\uBC95 : ");
		
		JComboBox cmb_pay = new JComboBox();
		
		cmb_pay.setModel(new DefaultComboBoxModel(new String[] {"\uD604\uAE08", "\uCE74\uB4DC", "\uC815\uC561\uAD8C"}));
		
		JButton btn_OK = new JButton("\uD655\uC778");
		
		JButton btn_Cancle = new JButton("\uCDE8\uC18C");
		btn_Cancle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new SchaduleUI(sdto);
				dispose();
			}
		});
		
		JLabel lbl_5 = new JLabel("\uCDA9\uC804 \uAE08\uC561 : ");
		
		txtF_ChangeAdd = new JTextField();
		txtF_ChangeAdd.setColumns(10);
		
		lbl_5.setVisible(false);
		txtF_ChangeAdd.setVisible(false);
		
		cmb_pay.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cmb_pay.getSelectedItem().equals("\uC815\uC561\uAD8C")) {
					lbl_5.setVisible(true);
					txtF_ChangeAdd.setVisible(true);
				}else {
					lbl_5.setVisible(false);
					txtF_ChangeAdd.setVisible(false);
				}
			}
		});
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(comboBox.getSelectedItem().equals("네일")) {
					cmb_pay.setModel(new DefaultComboBoxModel(new String[] {"\uD604\uAE08", "\uCE74\uB4DC", "\uC815\uC561\uAD8C"}));
					lbl_3.setVisible(true);
					lbl_Change.setVisible(true);
				}else {
					cmb_pay.setModel(new DefaultComboBoxModel(new String[] {"\uD604\uAE08", "\uCE74\uB4DC"}));
					lbl_3.setVisible(false);
					lbl_Change.setVisible(false);
					lbl_5.setVisible(false);
					txtF_ChangeAdd.setVisible(false);
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\uB124\uC77C", "\uC18D\uB208\uC379"}));
		
		btn_OK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ScheduleDAO sd = new ScheduleDAO();
				TotalDAO td = new TotalDAO();
				TotalDTO tdto = new TotalDTO();
				if(comboBox.getSelectedItem().equals("\uB124\uC77C")) {
					if(cmb_pay.getSelectedItem().equals("\uC815\uC561\uAD8C")) {
						if(txtF_ChangeAdd.getText().equals("")) {
							tdto.setCustId(sdto.getCustID());
							tdto.setTotal_Date(sdto.getScheDate());
							tdto.setDesc(sdto.getScheDesc());
							tdto.setChange(Integer.parseInt(lbl_Change.getText())-Integer.parseInt(txtF_Pay.getText()));
							tdto.setMoney("-"+txtF_Pay.getText());
							tdto.setTotal_class(comboBox.getSelectedIndex()+1);
							td.insertTotal(tdto);
						}else {
							int change1 = change;
							
							tdto.setCustId(sdto.getCustID());
							tdto.setTotal_Date(sdto.getScheDate());
							tdto.setDesc("정액제");
							tdto.setChange(Integer.parseInt(lbl_Change.getText())+Integer.parseInt(txtF_ChangeAdd.getText()));
							tdto.setMoney("+"+txtF_ChangeAdd.getText());
							tdto.setTotal_class(comboBox.getSelectedIndex()+1);
							td.insertTotal(tdto);
							List<TotalDTO> td_list = td.selectTotal_change(sdto.getCustID());
							System.out.println("tdlist"+td_list.size());
							for(TotalDTO tdto1 : td_list) {
								change1 = tdto1.getChange();
								System.out.println(tdto1.toString());
							}
							tdto.setCustId(sdto.getCustID());
							tdto.setTotal_Date(sdto.getScheDate());
							tdto.setDesc(sdto.getScheDesc());
							tdto.setChange(change1-Integer.parseInt(txtF_Pay.getText()));
							tdto.setMoney("-"+txtF_Pay.getText());
							tdto.setTotal_class(comboBox.getSelectedIndex()+1);
							td.insertTotal(tdto);
						}
						
					}else {
						tdto.setCustId(sdto.getCustID());
						tdto.setTotal_Date(sdto.getScheDate());
						tdto.setDesc(sdto.getScheDesc());
						tdto.setChange(Integer.parseInt(lbl_Change.getText()));
						tdto.setMoney(txtF_Pay.getText());
						tdto.setTotal_class(comboBox.getSelectedIndex()+1);
						td.insertTotal(tdto);
					}
				}else {
					tdto.setCustId(sdto.getCustID());
					tdto.setTotal_Date(sdto.getScheDate());
					tdto.setDesc(sdto.getScheDesc());
					tdto.setChange(0);
					tdto.setMoney(txtF_Pay.getText());
					tdto.setTotal_class(comboBox.getSelectedIndex()+1);
					td.insertTotal(tdto);
				}
				
				sdto.setPayhow(cmb_pay.getSelectedIndex()+1);
				sd.updatePayhow(sdto);
				dispose();
				new SchaduleUI();
				
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btn_Cancle)
							.addGap(18)
							.addComponent(btn_OK))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_Customer)
								.addComponent(lbl_4)
								.addComponent(lbl_1)
								.addComponent(lbl_5))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(cmb_pay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_Change)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lbl_2)
									.addGap(132)
									.addComponent(comboBox, 0, 126, Short.MAX_VALUE))
								.addComponent(txtF_Pay, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
								.addComponent(txtF_ChangeAdd, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
						.addComponent(lbl_3))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Customer)
						.addComponent(lbl_2)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_3)
						.addComponent(lbl_Change))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_4)
						.addComponent(cmb_pay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_5)
						.addComponent(txtF_ChangeAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_1)
						.addComponent(txtF_Pay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_OK)
						.addComponent(btn_Cancle))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
}
