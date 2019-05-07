package minjae.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;

public class SchaduleUI extends JFrame {

	private JPanel contentPane;
//	private JTable table;
	final MultiSpanCellTable table;
	final CellSpan cellAtt;
	
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
				int[] columns = table.getSelectedColumns();
				int[] rows = table.getSelectedRows();
				cellAtt.combine(rows, columns);
				table.clearSelection();
				table.revalidate();
				table.repaint();
			}
		});
		
		JDateChooser scaduleCalender = new JDateChooser();
		scaduleCalender.setDate(new Date());
		scaduleCalender.getDateEditor().addPropertyChangeListener(
			    new PropertyChangeListener() {
			        @Override
			        public void propertyChange(PropertyChangeEvent e) {
			            if ("date".equals(e.getPropertyName())) {
			                System.out.println(e.getNewValue());
			                System.out.println(scaduleCalender.getDate().toString());
			            }
			        }
			    });
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scaduleCalender, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
					.addGap(164)
					.addComponent(btn_ScaduleAdd, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btn_ScaduleAdd, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
						.addComponent(scaduleCalender, GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 508, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
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
		dataColumnName.addElement("\uC6D0\uC7A5");
		dataColumnName.addElement("\uC870\uC324");
//		System.out.println(dataColumnName);
		
		Vector rowData2 = new Vector();
		for(int i =0;i<c.length;i++) {
			Vector rowData = new Vector();
			rowData.addElement(c[i]);
			rowData.addElement(null);
			rowData.addElement(null);
			rowData2.addElement(rowData);
//			System.out.println(rowData);
			}
//		System.out.println(rowData2);
		
		
		AttributiveCellTableModel ml = new AttributiveCellTableModel(rowData2,dataColumnName);
		cellAtt = (CellSpan) ml.getCellAttribute();
		table = new MultiSpanCellTable(ml);
		
//		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(25);
		table.setFillsViewportHeight(true);
//		table.setModel(new DefaultTableModel(
//			new Object[][] {
//				{"10:30~11:00", null, null},
//				{"11:00~11:30", null, null},
//				{"11:30~12:00", null, null},
//				{"12:00~12:30", null, null},
//				{"12:30~13:00", null, null},
//				{"13:00~13:30", null, null},
//				{"13:30~14:00", null, null},
//				{"14:00~14:30", null, null},
//				{"14:30~15:00", null, null},
//				{"15:00~15:30", null, null},
//				{"15:30~16:00", null, null},
//				{"16:00~16:30", null, null},
//				{"16:30~17:00", null, null},
//				{"17:00~17:30", null, null},
//				{"17:30~18:00", null, null},
//				{"18:00~18:30", null, null},
//				{"18:30~19:00", null, null},
//				{"19:00~19:30", null, null},
//				{"19:30~", null, null},
//			},
//			new String[] {
//				"\uC2DC\uAC04", "\uC6D0\uC7A5", "\uC870\uC324"
//			}
//		));
		table.getColumnModel().getColumn(0).setPreferredWidth(85);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		
//		table.getColumn("\uC2DC\uAC04").setCellRenderer(new SelectCellRenderer());
//		table.getColumn("\uC6D0\uC7A5").setCellRenderer(new SelectCellRenderer());
		
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	
//	class SelectCellRenderer extends DefaultTableCellRenderer {
//
//		public SelectCellRenderer() {
//
//			setOpaque(true);
//			setHorizontalAlignment(0);
//
//		}
//
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//				int row, int column) {
//
//			Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//			if (isSelected && hasFocus)
//				System.out.println("I am cell in row " + row + " and column " + column);
//
//			return cell;
//
//		}
//	}
}
