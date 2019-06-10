package minjae.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManagerUI extends JFrame {
	private JPanel contentPane;
	private JTable tbl_Customer;
	private final JComboBox cmb_Search = new JComboBox();
	private JTextField txtF_Search;
	Object value;
	int row;
	int column;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					ManagerUI frame = new ManagerUI();
					frame.setExtendedState(MAXIMIZED_BOTH);
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
	public ManagerUI() {
		// DB 연결
		DB db = DB.sharedInstance();
		ManagerDAO md = new ManagerDAO();

		cmb_Search.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				System.out.println(cmb_Search.getSelectedItem().toString());

			}
		});

		cmb_Search.setModel(new DefaultComboBoxModel(new String[] { "\uBC88\uD638", "\uC774\uB984" }));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mn_Customer = new JMenu("\uACE0\uAC1D\uAD00\uB9AC");
		menuBar.add(mn_Customer);
		
		JMenu mn_schedule = new JMenu("\uC2DC\uAC04\uD45C");
		mn_schedule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SchaduleUI();
				dispose();
			}
		});
		menuBar.add(mn_schedule);
		
		JMenu mn_Sales = new JMenu("\uB9E4\uCD9C\uD604\uD669");
		menuBar.add(mn_Sales);
		
		JMenuItem mni_DaySales = new JMenuItem("\uC77C\uAC04\uB9E4\uCD9C\uD604\uD669");
		mni_DaySales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SalesDayUI();
				dispose();
			}
		});
		mn_Sales.add(mni_DaySales);
		
		JMenuItem mni_MonthSales = new JMenuItem("\uC6D4\uAC04\uB9E4\uCD9C\uD604\uD669");
		mni_MonthSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SalesMonthUI();
				dispose();
			}
		});
		mn_Sales.add(mni_MonthSales);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		// DB db = new DB();
		List<CustomerDTO> b = md.getList();
		int r = b.size();
		int i = 0;
		Object[][] obj = new Object[r][2];
		for (CustomerDTO beans : b) {
			obj[i][0] = beans.getName();
			obj[i][1] = beans.getPhone();
			i++;
		}

		tbl_Customer = new JTable();
		tbl_Customer.setCellSelectionEnabled(true);
		tbl_Customer.setModel(new DefaultTableModel(obj, new String[] { "\uACE0\uAC1D", "\uBC88\uD638" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		});
		tbl_Customer.getTableHeader().setReorderingAllowed(false);
		tbl_Customer.getColumn("\uACE0\uAC1D").setCellRenderer(new SelectCellRenderer());
		tbl_Customer.getColumn("\uBC88\uD638").setCellRenderer(new SelectCellRenderer());

		// 검색창
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
					tbl_Customer.getColumn("\uACE0\uAC1D").setCellRenderer(new SelectCellRenderer());
					tbl_Customer.getColumn("\uBC88\uD638").setCellRenderer(new SelectCellRenderer());

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
					tbl_Customer.getColumn("\uACE0\uAC1D").setCellRenderer(new SelectCellRenderer());
					tbl_Customer.getColumn("\uBC88\uD638").setCellRenderer(new SelectCellRenderer());

				}
			}
		});

		txtF_Search.setColumns(10);

		scrollPane.setViewportView(tbl_Customer);

		JButton btn_Insert = new JButton("\uCD94\uAC00");
		btn_Insert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new CustomerUI(tbl_Customer);

			}
		});

		JButton btn_Neil = new JButton("\uB124\uC77C");
		btn_Neil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				row = tbl_Customer.getSelectedRow();
				column = tbl_Customer.getSelectedColumn();
				value = tbl_Customer.getValueAt(row, 1);
				System.out.println(value);
				new SubUI(value,1);
			}
		});

		JButton btn_Eye = new JButton("\uC18D\uB208\uC379");
		btn_Eye.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				row = tbl_Customer.getSelectedRow();
				column = tbl_Customer.getSelectedColumn();
				value = tbl_Customer.getValueAt(row, 1);
				System.out.println(value);
				new SubUI(value,2);
			}
		});
		
		JButton btn_Update = new JButton("\uC218\uC815");
		btn_Update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				row = tbl_Customer.getSelectedRow();
				column = tbl_Customer.getSelectedColumn();
				value = tbl_Customer.getValueAt(row, 1);
				new CustomerUI(tbl_Customer,value);
			}
		});
		
		JButton btn_Delete = new JButton("\uC0AD\uC81C");
		btn_Delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CustomerDAO cd = new CustomerDAO();
				row = tbl_Customer.getSelectedRow();
				column = tbl_Customer.getSelectedColumn();
				value = tbl_Customer.getValueAt(row, 1);
				cd.customerDelete(value);
				
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

			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(72)
							.addComponent(cmb_Search, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtF_Search, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
							.addGap(14)
							.addComponent(btn_Neil, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btn_Eye, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btn_Insert, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btn_Update, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btn_Delete, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(txtF_Search, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmb_Search, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_Eye)
						.addComponent(btn_Neil))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addComponent(btn_Insert)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btn_Update)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btn_Delete)
							.addGap(12))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(19)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
							.addContainerGap())))
		);
		contentPane.setLayout(gl_contentPane);
		setExtendedState(MAXIMIZED_BOTH);
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
