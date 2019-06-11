package minjae.ui;

import java.awt.Component;
import java.awt.Font;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import minjae.dao.DB;
import minjae.dao.ManagerDAO;
import minjae.dao.TotalDAO;
import minjae.dto.CustomerDTO;
import minjae.dto.TotalDTO;

public class SubUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SubUI frame = new SubUI();
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

	public SubUI(Object value,int a) {
		System.out.println(value.toString() +":::" +a);
		DB db = DB.sharedInstance();
		TotalDAO td = new TotalDAO();
		ManagerDAO md = new ManagerDAO();
		String name = null;
		String phone = null;
		List<TotalDTO> nb = td.getNailList(value,a);
		int r = nb.size();
		int i = 0;
		Object[][] obj = null;
		String[] str = null;
		
		if(nb.size() == 0) {
			List<CustomerDTO> cb = md.searchList(value);
			for (CustomerDTO beans : cb) {
				name = beans.getName();
				phone = beans.getPhone();
			}
		}
		if(a == 1) {
			str = new String[] { "\uB0A0\uC9DC", "\uB0B4\uC6A9", "\uAE08\uC561", "\uC794\uC561" };
			obj = new Object[r][4];
			System.out.println("size"+nb.size());
			for (TotalDTO beans : nb) {
				name = beans.getName();
				phone = beans.getPhone();
				obj[i][0] = beans.getTotal_Date();
				obj[i][1] = beans.getDesc();
				obj[i][2] = beans.getMoney();
				obj[i][2] = String.valueOf(Integer.parseInt(obj[i][2].toString())).format("#,###");
			    
				obj[i][3] = beans.getChange();
				i++;
				System.out.println("asd"+beans.toString());
			}
		}else if(a == 2) {
			str = new String[] { "\uB0A0\uC9DC", "\uB0B4\uC6A9", "\uAE08\uC561" };
			obj = new Object[r][3];
			for (TotalDTO beans : nb) {
				name = beans.getName();
				phone = beans.getPhone();
				obj[i][0] = beans.getTotal_Date();
				obj[i][1] = beans.getDesc();
				obj[i][2] = beans.getMoney();
				i++;
			}
		}
		
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JLabel lbl_Name = new JLabel(name + "\uACE0\uAC1D\uB2D8");
		lbl_Name.setFont(new Font("±¼¸²", Font.PLAIN, 30));
		lbl_Name.setVerticalAlignment(0);
		lbl_Name.setHorizontalAlignment(0);

		JLabel lbl_Phone = new JLabel(phone);
		lbl_Phone.setFont(new Font("±¼¸²", Font.PLAIN, 30));
		lbl_Phone.setVerticalAlignment(0);
		lbl_Phone.setHorizontalAlignment(0);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lbl_Name, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(lbl_Phone, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lbl_Phone, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(lbl_Name, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
				.addGap(18).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
				.addContainerGap()));

		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(obj, str) {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		});
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
	

	class SelectCellRenderer extends DefaultTableCellRenderer {

		public SelectCellRenderer() {

			setOpaque(true);
			setHorizontalAlignment(SwingConstants.CENTER);

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
