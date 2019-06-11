package minjae.ui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import minjae.dao.CustomerDAO;
import minjae.dao.DB;
import minjae.dao.ManagerDAO;
import minjae.dao.ScheduleDAO;
import minjae.dao.TotalDAO;
import minjae.dto.CustomerDTO;
import minjae.dto.ScheduleDTO;
import minjae.dto.TotalDTO;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SchaduleUI extends JFrame {

	private JPanel contentPane;
	//	private JTable table;
	final MultiSpanCellTable table;
	CellSpan cellAtt;
	java.sql.Date dd;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SchaduleUI frame = new SchaduleUI();
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
	public SchaduleUI() {

		DB db = DB.sharedInstance();
		ScheduleDAO sd = new ScheduleDAO();

		Object[] c = new Object[] {
				"10:30~11:00",
				"11:00~11:30",
				"11:30~12:00",
				"12:00~12:30",
				"12:30~13:00",
				"13:00~13:30",
				"13:30~14:00",
				"14:00~14:30",
				"14:30~15:00",
				"15:00~15:30",
				"15:30~16:00",
				"16:00~16:30",
				"16:30~17:00",
				"17:00~17:30",
				"17:30~18:00",
				"18:00~18:30",
				"18:30~19:00",
				"19:00~19:30",
				"19:30~"
		};

		Vector dataColumnName = new Vector();
		dataColumnName.addElement("\uC2DC\uAC04");
		dataColumnName.addElement("예약");

		Vector rowData2 = new Vector();
		for(int i =0;i<c.length;i++) {
			Vector rowData = new Vector();
			rowData.addElement(c[i]);
			rowData.addElement(null);
			rowData2.addElement(rowData);
		}

		JDateChooser scaduleCalender = new JDateChooser();

		scaduleCalender.setDate(new Date());
		scaduleCalender.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						if ("date".equals(e.getPropertyName())) {
							Date d =(Date) scaduleCalender.getDate();
							dd = new java.sql.Date(scaduleCalender.getDate().getTime());
							System.out.println(dd);
							SimpleDateFormat sdf= new SimpleDateFormat("yy/MM/dd");
							String s = sdf.format(d);

							Vector rowData2 = new Vector();
							for(int i =0;i<c.length;i++) {
								Vector rowData = new Vector();
								rowData.addElement(c[i]);
								rowData.addElement(null);
								rowData2.addElement(rowData);
							}

							AttributiveCellTableModel ml = new AttributiveCellTableModel(rowData2,dataColumnName){
								public boolean isCellEditable(int row,int column) {return false;}
								};
							cellAtt = (CellSpan) ml.getCellAttribute();
							table.setModel(ml);

							List<ScheduleDTO> sd_list = sd.getSchedule(dd);
							CustomerDAO cd = new CustomerDAO();
							List<CustomerDTO> cd_list;
							for (ScheduleDTO beans : sd_list) {
								int si = beans.getStartIndex();
								int ei = beans.getEndIndex();
								int[] rows = new int[ei-si+1];
								cd_list = cd.customerSelect(beans.getCustID());
								for (CustomerDTO cdto : cd_list) {
									ml.setValueAt(cdto.getName()+" : "+cdto.getPhone()+" :::: "+beans.getScheDesc(), si, 1);
									
								}
								for(int i = 0;si<ei;i++,si++) {
									rows[i] = si;
								}
								int[] columns = {1};
								cellAtt.combine(rows, columns);
								
								table.clearSelection();
								table.revalidate();
								table.repaint();
							}
							
							;
							table.setColumnSelectionAllowed(true);
							table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
							table.setRowHeight(25);
							table.setFillsViewportHeight(true);
							table.getColumnModel().getColumn(0).setPreferredWidth(85);
							table.getColumnModel().getColumn(0).setMaxWidth(100);
						}
					}
				});

		Date d =(Date) scaduleCalender.getDate();
		dd = new java.sql.Date(scaduleCalender.getDate().getTime());
		System.out.println(dd);
		SimpleDateFormat sdf= new SimpleDateFormat("yy/MM/dd");
		String s = sdf.format(d);

		AttributiveCellTableModel ml = new AttributiveCellTableModel(rowData2,dataColumnName);
		cellAtt = (CellSpan) ml.getCellAttribute();
		table = new MultiSpanCellTable(ml);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 650);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mn_Manager = new JMenu("\uACE0\uAC1D\uAD00\uB9AC");
		mn_Manager.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new ManagerUI();
				dispose();
			}
		});
		menuBar.add(mn_Manager);
		
		JMenu mn_schedule = new JMenu("\uC2DC\uAC04\uD45C");
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

		JButton btn_ScaduleAdd = new JButton("\uCD94\uAC00");
		btn_ScaduleAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ScheduleDTO sdto = new ScheduleDTO();
				int[] rows = table.getSelectedRows();
				sdto.setStartIndex(rows[0]);
				sdto.setEndIndex(rows[rows.length-1]);
				sdto.setScheDate(dd);
				new SchaduleInsertUI(sdto);
				System.out.println(sdto.toString());
				//				
				//				int[] columns = {1};
				//				cellAtt.combine(rows, columns);
				//				table.clearSelection();
				//				table.revalidate();
				//				table.repaint();
				dispose();

			}
		});

		JButton btn_ScaduleUpdate = new JButton("\uC218\uC815");
		btn_ScaduleUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ManagerDAO md = new ManagerDAO();
				int custid = 0;
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[0]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[2]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[4]);
				List<CustomerDTO> cdto = md.searchList(table.getValueAt(row, column).toString().split(" ",5)[2]);
				for(CustomerDTO beans : cdto) {
					custid = beans.getNo();
				}
				List<ScheduleDTO> sd_list = sd.selectSchedule(custid, dd);
				for(ScheduleDTO sdto : sd_list) {
					new ScaduleUpdateUI(sdto);
				}
			}
		});

		JButton btn_ScaduleDelete = new JButton("\uC0AD\uC81C");
		btn_ScaduleDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ManagerDAO md = new ManagerDAO();
				int custid = 0;
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[0]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[2]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[4]);
				List<CustomerDTO> cdto = md.searchList(table.getValueAt(row, column).toString().split(" ",5)[2]);
				for(CustomerDTO beans : cdto) {
					custid = beans.getNo();
				}
				sd.scheduleDelete(custid, dd);

				table.setValueAt("", row, column);
				cellAtt.split(row, column);
				table.clearSelection();
				table.revalidate();
				table.repaint();

			}
		});

		JButton btn_ScadulePay = new JButton("\uACB0\uC81C");
		btn_ScadulePay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ManagerDAO md = new ManagerDAO();
				TotalDAO td = new TotalDAO();
				int custid = 0;
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				int change = 0;
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[0]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[2]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[4]);
				List<CustomerDTO> cdto = md.searchList(table.getValueAt(row, column).toString().split(" ",5)[2]);
				for(CustomerDTO beans : cdto) {
					custid = beans.getNo();
					
				}
				List<TotalDTO> td_list = td.selectTotal_change(custid);
				System.out.println("tdlist"+td_list.size());
				for(TotalDTO tdto : td_list) {
					change = tdto.getChange();
				}
				List<ScheduleDTO> sd_list = sd.selectSchedule(custid, dd);
				for(ScheduleDTO sdto : sd_list) {;
					new PaymentUI(sdto,change);
					dispose();
				}
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
									.addComponent(btn_ScaduleAdd, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
									.addComponent(btn_ScaduleUpdate, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btn_ScadulePay, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_ScaduleDelete, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addComponent(scaduleCalender, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
							.addGap(130)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addComponent(scaduleCalender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_ScaduleAdd)
							.addGap(33)
							.addComponent(btn_ScaduleUpdate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(32)
							.addComponent(btn_ScaduleDelete)
							.addGap(41)
							.addComponent(btn_ScadulePay, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);



		List<ScheduleDTO> sd_list = sd.getSchedule(dd);
		CustomerDAO cd = new CustomerDAO();
		List<CustomerDTO> cd_list;
		for (ScheduleDTO beans : sd_list) {
			int si = beans.getStartIndex();
			int ei = beans.getEndIndex();
			int[] rows = new int[ei-si+1];
			cd_list = cd.customerSelect(beans.getCustID());
			for (CustomerDTO cdto : cd_list) {
				ml.setValueAt(cdto.getName()+" : "+cdto.getPhone()+" :::: "+beans.getScheDesc(), si, 1);
			}
			for(int i = 0;si<ei;i++,si++) {
				rows[i] = si;
			}
			int[] columns = {1};
			cellAtt.combine(rows, columns);
			table.clearSelection();
			table.revalidate();
			table.repaint();
		}

		table.setColumnSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(25);
		table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(85);
		table.getColumnModel().getColumn(0).setMaxWidth(100);

		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}

	public SchaduleUI(ScheduleDTO sdto) {

		DB db = DB.sharedInstance();
		ScheduleDAO sd = new ScheduleDAO();

		Object[] c = new Object[] {
				"10:30~11:00",
				"11:00~11:30",
				"11:30~12:00",
				"12:00~12:30",
				"12:30~13:00",
				"13:00~13:30",
				"13:30~14:00",
				"14:00~14:30",
				"14:30~15:00",
				"15:00~15:30",
				"15:30~16:00",
				"16:00~16:30",
				"16:30~17:00",
				"17:00~17:30",
				"17:30~18:00",
				"18:00~18:30",
				"18:30~19:00",
				"19:00~19:30",
				"19:30~"
		};

		Vector dataColumnName = new Vector();
		dataColumnName.addElement("\uC2DC\uAC04");
		dataColumnName.addElement("예약");

		Vector rowData2 = new Vector();
		for(int i =0;i<c.length;i++) {
			Vector rowData = new Vector();
			rowData.addElement(c[i]);
			rowData.addElement(null);
			rowData2.addElement(rowData);
		}

		JDateChooser scaduleCalender = new JDateChooser();

		scaduleCalender.setDate(sdto.getScheDate());
		scaduleCalender.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						if ("date".equals(e.getPropertyName())) {
							Date d =(Date) scaduleCalender.getDate();
							dd = new java.sql.Date(scaduleCalender.getDate().getTime());
							System.out.println(dd);
							SimpleDateFormat sdf= new SimpleDateFormat("yy/MM/dd");
							String s = sdf.format(d);

							Vector rowData2 = new Vector();
							for(int i =0;i<c.length;i++) {
								Vector rowData = new Vector();
								rowData.addElement(c[i]);
								rowData.addElement(null);
								rowData2.addElement(rowData);
							}

							AttributiveCellTableModel ml = new AttributiveCellTableModel(rowData2,dataColumnName);
							cellAtt = (CellSpan) ml.getCellAttribute();
							table.setModel(ml);

							List<ScheduleDTO> sd_list = sd.getSchedule(dd);
							CustomerDAO cd = new CustomerDAO();
							List<CustomerDTO> cd_list;
							for (ScheduleDTO beans : sd_list) {
								int si = beans.getStartIndex();
								int ei = beans.getEndIndex();
								int[] rows = new int[ei-si+1];
								cd_list = cd.customerSelect(beans.getCustID());
								for (CustomerDTO cdto : cd_list) {
									ml.setValueAt(cdto.getName()+" : "+cdto.getPhone()+" :::: "+beans.getScheDesc(), si, 1);
								}
								for(int i = 0;si<ei;i++,si++) {
									rows[i] = si;
								}
								int[] columns = {1};
								cellAtt.combine(rows, columns);
								table.clearSelection();
								table.revalidate();
								table.repaint();
							}

							table.setColumnSelectionAllowed(true);
							table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
							table.setRowHeight(25);
							table.setFillsViewportHeight(true);
							table.getColumnModel().getColumn(0).setPreferredWidth(85);
							table.getColumnModel().getColumn(0).setMaxWidth(100);
						}
					}
				});

		Date d =(Date) scaduleCalender.getDate();
		dd = new java.sql.Date(scaduleCalender.getDate().getTime());
		System.out.println(dd);
		SimpleDateFormat sdf= new SimpleDateFormat("yy/MM/dd");
		String s = sdf.format(d);

		AttributiveCellTableModel ml = new AttributiveCellTableModel(rowData2,dataColumnName);
		cellAtt = (CellSpan) ml.getCellAttribute();
		table = new MultiSpanCellTable(ml);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		JButton btn_ScaduleAdd = new JButton("\uCD94\uAC00");
		btn_ScaduleAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ScheduleDTO sdto = new ScheduleDTO();
				int[] rows = table.getSelectedRows();
				sdto.setStartIndex(rows[0]);
				sdto.setEndIndex(rows[rows.length-1]);
				sdto.setScheDate(dd);
				new SchaduleInsertUI(sdto);
				System.out.println(sdto.toString());
				//				
				//				int[] columns = {1};
				//				cellAtt.combine(rows, columns);
				//				table.clearSelection();
				//				table.revalidate();
				//				table.repaint();
				dispose();
			}
		});

		JButton btn_ScaduleUpdate = new JButton("\uC218\uC815");
		btn_ScaduleUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ManagerDAO md = new ManagerDAO();
				int custid = 0;
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[0]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[2]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[4]);
				List<CustomerDTO> cdto = md.searchList(table.getValueAt(row, column).toString().split(" ",5)[2]);
				for(CustomerDTO beans : cdto) {
					custid = beans.getNo();
				}
				List<ScheduleDTO> sd_list = sd.selectSchedule(custid, dd);
				for(ScheduleDTO sdto : sd_list) {
					new ScaduleUpdateUI(sdto);
				}
				dispose();	
			}
		});

		JButton btn_ScaduleDelete = new JButton("\uC0AD\uC81C");
		btn_ScaduleDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ManagerDAO md = new ManagerDAO();
				int custid = 0;
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[0]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[2]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[4]);
				List<CustomerDTO> cdto = md.searchList(table.getValueAt(row, column).toString().split(" ",5)[2]);
				for(CustomerDTO beans : cdto) {
					custid = beans.getNo();
				}
				sd.scheduleDelete(custid, dd);

				table.setValueAt("", row, column);
				cellAtt.split(row, column);
				table.clearSelection();
				table.revalidate();
				table.repaint();

			}
		});

		JButton btn_ScadulePay = new JButton("\uACB0\uC81C");
		btn_ScadulePay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ManagerDAO md = new ManagerDAO();
				TotalDAO td = new TotalDAO();
				int custid = 0;
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				int change = 0;
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[0]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[2]);
				System.out.println(table.getValueAt(row, column).toString().split(" ",5)[4]);
				List<CustomerDTO> cdto = md.searchList(table.getValueAt(row, column).toString().split(" ",5)[2]);
				for(CustomerDTO beans : cdto) {
					custid = beans.getNo();
					
				}
				List<TotalDTO> td_list = td.selectTotal_change(custid);
				System.out.println("tdlist"+td_list.size());
				for(TotalDTO tdto : td_list) {
					change = tdto.getChange();
				}
				List<ScheduleDTO> sd_list = sd.selectSchedule(custid, dd);
				for(ScheduleDTO sdto : sd_list) {;
					new PaymentUI(sdto,change);
					dispose();
				}
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
									.addComponent(btn_ScaduleAdd, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
									.addComponent(btn_ScaduleUpdate, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btn_ScadulePay, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_ScaduleDelete, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addComponent(scaduleCalender, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
							.addGap(130)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addComponent(scaduleCalender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_ScaduleAdd)
							.addGap(33)
							.addComponent(btn_ScaduleUpdate, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(32)
							.addComponent(btn_ScaduleDelete)
							.addGap(41)
							.addComponent(btn_ScadulePay, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);

		List<ScheduleDTO> sd_list = sd.getSchedule(dd);
		CustomerDAO cd = new CustomerDAO();
		List<CustomerDTO> cd_list;
		for (ScheduleDTO beans : sd_list) {
			int si = beans.getStartIndex();
			int ei = beans.getEndIndex();
			int[] rows = new int[ei-si+1];
			cd_list = cd.customerSelect(beans.getCustID());
			for (CustomerDTO cdto : cd_list) {
				ml.setValueAt(cdto.getName()+" : "+cdto.getPhone()+" :::: "+beans.getScheDesc(), si, 1);
			}
			for(int i = 0;si<ei;i++,si++) {
				rows[i] = si;
			}
			int[] columns = {1};
			cellAtt.combine(rows, columns);
			table.clearSelection();
			table.revalidate();
			table.repaint();
		}

		table.setColumnSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(25);
		table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(85);
		table.getColumnModel().getColumn(0).setMaxWidth(100);

		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}
}
