package minjae.ui;

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

import com.toedter.calendar.JDateChooser;

import minjae.dao.DB;
import minjae.dao.ScheduleDAO;
import minjae.dto.ScheduleDTO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScaduleUpdateUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	java.sql.Date dd;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ScaduleUpdateUI frame = new ScaduleUpdateUI();
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
	public ScaduleUpdateUI(ScheduleDTO sdto) {
		
		DB db = DB.sharedInstance();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lbl_1 = new JLabel("\uC774\uB984 : ");
		
		JLabel lbl_Name = new JLabel(sdto.getName());
		
		JLabel lbl_2 = new JLabel("\uBC88\uD638 : ");
		
		JLabel lbl_Phone = new JLabel(sdto.getPhone());
		
		JLabel lbl_3 = new JLabel("\uB0B4\uC6A9");
		
		textField = new JTextField();
		textField.setText(sdto.getScheDesc());
		textField.setColumns(10);
		
		
		
		JButton btn_Cancle = new JButton("\uCDE8\uC18C");
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDate(sdto.getScheDate());
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"}));
		comboBox.setSelectedIndex(sdto.getStartIndex());
		
		JLabel lbl_4 = new JLabel("~");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "\uB9C8\uAC10"}));
		comboBox_1.setSelectedIndex(sdto.getEndIndex());
		
		Date d =(Date) dateChooser.getDate();
		dd = new java.sql.Date(dateChooser.getDate().getTime());
		System.out.println(dd);
		SimpleDateFormat sdf= new SimpleDateFormat("yy/MM/dd");
		String s = sdf.format(d);
		
		JButton btn_Update = new JButton("\uD655\uC778");
		btn_Update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ScheduleDAO sd = new ScheduleDAO();
				sdto.setScheDate(dd);
				sdto.setScheDesc(textField.getText());
				sdto.setStartIndex(comboBox.getSelectedIndex());
				sdto.setEndIndex(comboBox_1.getSelectedIndex());
				System.out.println(sdto.toString());
				sd.updateSchedule(sdto);
				new SchaduleUI(sdto);
				setVisible(false);
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lbl_1)
									.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
									.addComponent(lbl_Name, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lbl_2)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(65)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lbl_4)
									.addGap(5)
									.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lbl_Phone, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(268)
							.addComponent(btn_Cancle)
							.addGap(18)
							.addComponent(btn_Update))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_3)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lbl_4)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_1)
								.addComponent(lbl_Name))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lbl_3))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_2)
								.addComponent(lbl_Phone, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_Update)
						.addComponent(btn_Cancle))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
}
