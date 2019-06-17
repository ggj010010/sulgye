package minjae.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import minjae.dao.ManagerDAO;
import minjae.dao.ScheduleDAO;
import minjae.dto.CustomerDTO;
import minjae.dto.ScheduleDTO;

public class SchaduleInsertUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtF_Search;
	private JTextField txtF_Desc;
	private JButton btn_Cancle;
	private JTable tbl_Customer;
	int row;
	int column;

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
	
	public SchaduleInsertUI(ScheduleDTO sdto) {
		ManagerDAO md = new ManagerDAO();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JComboBox cmb_Search = new JComboBox();
		cmb_Search.setModel(new DefaultComboBoxModel(new String[] {"\uBC88\uD638", "\uC774\uB984"}));
		
		JLabel lbl_Desc = new JLabel("\uB0B4    \uC6A9");
		
		txtF_Search = new JTextField();
		txtF_Search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (cmb_Search.getSelectedItem().toString() == "\uBC88\uD638") {

					List<CustomerDTO> b = md.searchList_P(txtF_Search.getText());

					int r = b.size();
					int i = 0;
					Object[][] obj = new Object[r][2];
					for (CustomerDTO beans : b) {
						obj[i][0] = beans.getName();
						obj[i][1] = beans.getPhone();
						i++;
					}
					tbl_Customer.setModel(new DefaultTableModel(obj, new String[] { "\uACE0\uAC1D", "\uBC88\uD638" }) {
						public boolean isCellEditable(int row, int column) {
							return false;
						};
					});

				} else if (cmb_Search.getSelectedItem().toString() == "\uC774\uB984") {

					List<CustomerDTO> b = md.searchList_N(txtF_Search.getText());
					int r = b.size();
					int i = 0;
					Object[][] obj = new Object[r][2];
					for (CustomerDTO beans : b) {
						obj[i][0] = beans.getName();
						obj[i][1] = beans.getPhone();
						i++;
					}
					tbl_Customer.setModel(new DefaultTableModel(obj, new String[] { "\uACE0\uAC1D", "\uBC88\uD638" }) {
						public boolean isCellEditable(int row, int column) {
							return false;
						};
					});

				}
			}
		});
		txtF_Search.setColumns(10);
		
		txtF_Desc = new JTextField();
		txtF_Desc.setColumns(10);
		
		System.out.println("!!!!!"+sdto.toString());
		
		JButton btn_Save = new JButton("\uC800    \uC7A5");
		btn_Save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ScheduleDAO sd = new ScheduleDAO();
				sdto.setScheDesc(txtF_Desc.getText());
				sdto.setPayhow(0);
				System.out.println(sdto.toString());
				sd.insertSchedule(sdto);
				new SchaduleUI(sdto);
				dispose();
			}
		});
		
		btn_Cancle = new JButton("\uCDE8    \uC18C");
		btn_Cancle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SchaduleUI(sdto);
				dispose();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		
		
		JLabel lblName = new JLabel("");
		
		JLabel lblPhone = new JLabel("");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(248, Short.MAX_VALUE)
							.addComponent(btn_Cancle)
							.addGap(18)
							.addComponent(btn_Save))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lbl_Desc)
								.addComponent(cmb_Search, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtF_Desc, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
								.addComponent(txtF_Search, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblName)
									.addPreferredGap(ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
									.addComponent(lblPhone)
									.addGap(101)))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtF_Search, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmb_Search, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(lblPhone))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbl_Desc, Alignment.LEADING)
						.addComponent(txtF_Desc, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_Save)
						.addComponent(btn_Cancle))
					.addContainerGap())
		);
		
		tbl_Customer = new JTable();
		tbl_Customer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				row = tbl_Customer.getSelectedRow();
				column = tbl_Customer.getSelectedColumn();
				lblName.setText(tbl_Customer.getValueAt(row, 0).toString());
				lblPhone.setText(tbl_Customer.getValueAt(row, 1).toString());
				List<CustomerDTO> b = md.searchList(tbl_Customer.getValueAt(row, 1).toString());
				for(CustomerDTO beans : b) {
					sdto.setCustID(beans.getNo());
					System.out.println(sdto.getCustID());
				}
			}
		});
		tbl_Customer.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uACE0\uAC1D", "\uBC88\uD638"
			}
		));
		scrollPane.setViewportView(tbl_Customer);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
}
