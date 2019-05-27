package minjae.ui;

import java.awt.Component;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import minjae.dao.CustomerDAO;
import minjae.dao.DB;
import minjae.dao.ManagerDAO;
import minjae.dto.CustomerDTO;

public class CustomerUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtF_Name;
	private JTextField txtF_Phone1;
	private JTextField txtF_Phone2;
	private JTextField txtF_Phone3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerUI frame = new CustomerUI();
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
	public CustomerUI() {
		
		DB db = DB.sharedInstance();
		CustomerDAO cd = new CustomerDAO();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lbl_Name = new JLabel("\uC774    \uB984");
		
		JLabel lbl_Phone = new JLabel("\uBC88    \uD638");
		
		txtF_Name = new JTextField();
		txtF_Name.setColumns(10);
		
		txtF_Phone1 = new JTextField();
		txtF_Phone1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("-");
		
		txtF_Phone2 = new JTextField();
		txtF_Phone2.setColumns(10);
		
		JLabel label = new JLabel("-");
		
		txtF_Phone3 = new JTextField();
		txtF_Phone3.setColumns(10);
		
		JButton btn_Save = new JButton("\uD655\uC778");
		btn_Save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CustomerDTO cdto = new CustomerDTO();
				cdto.setName(txtF_Name.getText());
				cdto.setPhone(txtF_Phone1.getText()+txtF_Phone2.getText()+txtF_Phone3.getText());
				cd.customerInsert(cdto);
				dispose();
				
			}
		});
		
		JButton btn_Cancle = new JButton("\uCDE8\uC18C");
		btn_Cancle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_Phone)
							.addGap(18)
							.addComponent(txtF_Phone1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtF_Phone2, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtF_Phone3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_Name)
							.addGap(18)
							.addComponent(txtF_Name)))
					.addContainerGap(15, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(82, Short.MAX_VALUE)
					.addComponent(btn_Cancle)
					.addGap(18)
					.addComponent(btn_Save))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Name)
						.addComponent(txtF_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Phone)
						.addComponent(txtF_Phone1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(txtF_Phone2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(txtF_Phone3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_Save)
						.addComponent(btn_Cancle))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}

	public CustomerUI(JTable tbl_Customer) {
		// TODO Auto-generated constructor stub
		DB db = DB.sharedInstance();
		CustomerDAO cd = new CustomerDAO();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lbl_Name = new JLabel("\uC774    \uB984");
		
		JLabel lbl_Phone = new JLabel("\uBC88    \uD638");
		
		txtF_Name = new JTextField();
		txtF_Name.setColumns(10);
		
		txtF_Phone1 = new JTextField();
		txtF_Phone1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("-");
		
		txtF_Phone2 = new JTextField();
		txtF_Phone2.setColumns(10);
		
		JLabel label = new JLabel("-");
		
		txtF_Phone3 = new JTextField();
		txtF_Phone3.setColumns(10);
		
		JButton btn_Save = new JButton("\uD655\uC778");
		btn_Save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CustomerDTO cdto = new CustomerDTO();
				cdto.setName(txtF_Name.getText());
				cdto.setPhone(txtF_Phone1.getText()+txtF_Phone2.getText()+txtF_Phone3.getText());
				cd.customerInsert(cdto);
				
				ManagerDAO md = new ManagerDAO();
				List<CustomerDTO> b = md.getList();
				int r = b.size();
				int i = 0;
				Object[][] obj = new Object[r][2];
				for (CustomerDTO beans : b) {
					obj[i][0] = beans.getName();
					obj[i][1] = beans.getPhone();
					i++;
				}
				
				tbl_Customer.setCellSelectionEnabled(true);
				tbl_Customer.setModel(new DefaultTableModel(obj, new String[] { "\uACE0\uAC1D", "\uBC88\uD638" }) {
					public boolean isCellEditable(int row, int column) {
						return false;
					};
				});
				tbl_Customer.getTableHeader().setReorderingAllowed(false);
				tbl_Customer.getColumn("\uACE0\uAC1D").setCellRenderer(new SelectCellRenderer());
				tbl_Customer.getColumn("\uBC88\uD638").setCellRenderer(new SelectCellRenderer());

				dispose();
				
			}
		});
		
		JButton btn_Cancle = new JButton("\uCDE8\uC18C");
		btn_Cancle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_Phone)
							.addGap(18)
							.addComponent(txtF_Phone1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtF_Phone2, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtF_Phone3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_Name)
							.addGap(18)
							.addComponent(txtF_Name)))
					.addContainerGap(15, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(82, Short.MAX_VALUE)
					.addComponent(btn_Cancle)
					.addGap(18)
					.addComponent(btn_Save))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Name)
						.addComponent(txtF_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Phone)
						.addComponent(txtF_Phone1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(txtF_Phone2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(txtF_Phone3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_Save)
						.addComponent(btn_Cancle))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
	
	public CustomerUI(JTable tbl_Customer, Object value) {
		DB db = DB.sharedInstance();
		ManagerDAO md = new ManagerDAO();
		CustomerDAO cd = new CustomerDAO();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lbl_Name = new JLabel("\uC774    \uB984");
		
		JLabel lbl_Phone = new JLabel("\uBC88    \uD638");
		
		txtF_Name = new JTextField();
		txtF_Name.setColumns(10);
		
		txtF_Phone1 = new JTextField();
		txtF_Phone1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("-");
		
		txtF_Phone2 = new JTextField();
		txtF_Phone2.setColumns(10);
		
		JLabel label = new JLabel("-");
		
		txtF_Phone3 = new JTextField();
		txtF_Phone3.setColumns(10);
		
		List<CustomerDTO> cb = md.searchList(value);
		for (CustomerDTO beans : cb) {
			txtF_Name.setText(beans.getName());
			txtF_Phone1.setText(beans.getPhone().substring(0, 3));
			txtF_Phone2.setText(beans.getPhone().substring(3, 7));
			txtF_Phone3.setText(beans.getPhone().substring(7, 11));
			
		}
		
		JButton btn_Save = new JButton("\uD655\uC778");
		btn_Save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CustomerDTO cdto = new CustomerDTO();
				cdto.setName(txtF_Name.getText());
				cdto.setPhone(txtF_Phone1.getText()+txtF_Phone2.getText()+txtF_Phone3.getText());
				cd.customerUpdate(cdto, value);
				
				ManagerDAO md = new ManagerDAO();
				List<CustomerDTO> b = md.getList();
				int r = b.size();
				int i = 0;
				Object[][] obj = new Object[r][2];
				for (CustomerDTO beans : b) {
					obj[i][0] = beans.getName();
					obj[i][1] = beans.getPhone();
					i++;
				}
				
				tbl_Customer.setCellSelectionEnabled(true);
				tbl_Customer.setModel(new DefaultTableModel(obj, new String[] { "\uACE0\uAC1D", "\uBC88\uD638" }) {
					public boolean isCellEditable(int row, int column) {
						return false;
					};
				});
				tbl_Customer.getTableHeader().setReorderingAllowed(false);
				tbl_Customer.getColumn("\uACE0\uAC1D").setCellRenderer(new SelectCellRenderer());
				tbl_Customer.getColumn("\uBC88\uD638").setCellRenderer(new SelectCellRenderer());

				dispose();
				
			}
		});
		
		JButton btn_Cancle = new JButton("\uCDE8\uC18C");
		btn_Cancle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_Phone)
							.addGap(18)
							.addComponent(txtF_Phone1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtF_Phone2, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtF_Phone3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_Name)
							.addGap(18)
							.addComponent(txtF_Name)))
					.addContainerGap(15, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(82, Short.MAX_VALUE)
					.addComponent(btn_Cancle)
					.addGap(18)
					.addComponent(btn_Save))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Name)
						.addComponent(txtF_Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Phone)
						.addComponent(txtF_Phone1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(txtF_Phone2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(txtF_Phone3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_Save)
						.addComponent(btn_Cancle))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}

	class SelectCellRenderer extends DefaultTableCellRenderer {

		public SelectCellRenderer() {

			setOpaque(true);
			setHorizontalAlignment(0);

		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if (isSelected && hasFocus)
				System.out.println("I am cell in row " + row + " and column " + column);

			return cell;

		}
	}
}


