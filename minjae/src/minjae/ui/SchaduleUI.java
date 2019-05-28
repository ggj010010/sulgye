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
import minjae.dto.CustomerDTO;
import minjae.dto.ScheduleDTO;

public class SchaduleUI extends JFrame {

	private JPanel contentPane;
//	private JTable table;
	final MultiSpanCellTable table;
	CellSpan cellAtt;
	java.sql.Date dd;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchaduleUI frame = new SchaduleUI();
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
		dataColumnName.addElement("����");
		
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
		setBounds(100, 100, 500, 650);
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
				setVisible(false);
				
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
				sd.scheduleDelete(custid, dd);
				
				table.setValueAt("", row, column);
				cellAtt.split(row, column);
				table.clearSelection();
				table.revalidate();
				table.repaint();
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scaduleCalender, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
							.addGap(73)
							.addComponent(btn_ScaduleUpdate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_ScaduleAdd, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btn_ScaduleAdd)
							.addComponent(btn_ScaduleUpdate))
						.addComponent(scaduleCalender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
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
		dataColumnName.addElement("����");
		
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
		setBounds(100, 100, 500, 600);
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
				setVisible(false);
			}
		});
		
		JButton btn_ScaduleUpdate = new JButton("\uC218\uC815");
		btn_ScaduleUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				System.out.println(table.getValueAt(row, column).toString().split(" : "));
				
				table.setValueAt("", row, column);
				cellAtt.split(row, column);
				table.clearSelection();
				table.revalidate();
				table.repaint();
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scaduleCalender, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
							.addGap(73)
							.addComponent(btn_ScaduleUpdate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_ScaduleAdd, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btn_ScaduleAdd)
							.addComponent(btn_ScaduleUpdate))
						.addComponent(scaduleCalender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
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
		setVisible(true);
	}
}
