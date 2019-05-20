package minjae.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import minjae.dao.DB;
import minjae.dao.ManagerDAO;
import minjae.dto.CustomerDTO;
import minjae.dto.scheduleDTO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class selectCustomerUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtF_Search;
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
//					selectCustomerUI frame = new selectCustomerUI();
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
//	public selectCustomerUI() {
//		
//	}
	public selectCustomerUI(scheduleDTO sdto) {
		
		DB db = DB.sharedInstance();
		ManagerDAO md = new ManagerDAO();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JComboBox cmb_Search = new JComboBox();
		cmb_Search.setModel(new DefaultComboBoxModel(new String[] {"\uBC88\uD638", "\uC774\uB984"}));
		
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
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(cmb_Search, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtF_Search, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmb_Search, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtF_Search, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbl_Customer = new JTable();
		tbl_Customer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				row = tbl_Customer.getSelectedRow();
				column = tbl_Customer.getSelectedColumn();
				sdto.setPhone(tbl_Customer.getValueAt(row, 1).toString());
				sdto.setName(tbl_Customer.getValueAt(row, 0).toString());
				new SchaduleInsertUI(sdto);
				setVisible(false);
			}
		});
		tbl_Customer.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uACE0    \uAC1D", "\uBC88    \uD638"
			}
		));
		scrollPane.setViewportView(tbl_Customer);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
}
