package minjae.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;

import com.toedter.calendar.JDateChooser;

import minjae.dao.DB;
import minjae.dao.SalesDAO;
import minjae.dto.SalesDTO;

public class SalesDayUI extends JFrame {
	java.sql.Date dd;
	JPanel panel;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//
//					SalesDayUI frame = new SalesDayUI();
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
	public SalesDayUI() {
		DB db = DB.sharedInstance();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
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
		mn_Sales.add(mni_DaySales);
		
		JMenuItem mni_MonthSales = new JMenuItem("\uC6D4\uAC04\uB9E4\uCD9C\uD604\uD669");
		mni_MonthSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SalesMonthUI();
				dispose();
			}
		});
		mn_Sales.add(mni_MonthSales);

		JDateChooser dateChooser = new JDateChooser();

		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("일 매출").xAxisTitle("계산 방법")
				.yAxisTitle("매출 현황").build();
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setYAxisDecimalPattern("#,###");
		chart.getStyler().setHasAnnotations(true);

		dateChooser.setDate(new java.util.Date());
		dateChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					Date d = (Date) dateChooser.getDate();
					dd = new java.sql.Date(dateChooser.getDate().getTime());
					System.out.println(dd);
					SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
					String s = sdf.format(d);

					SalesDAO sd = new SalesDAO();
					Long y[] = new Long[3];
					List<SalesDTO> sd_list = sd.selectDaySales(dd);
					int n = 0;
					for (SalesDTO beans : sd_list) {
						System.out.println(beans.toString());
						y[n] = (long) beans.getTotal();
						n++;
					}

					chart.removeSeries("매출");
					chart.addSeries("매출", Arrays.asList(new String[] { "현금", "카드", "총" }),
							Arrays.asList(new Long[] { y[0], y[1], y[2] }));

					contentPane.remove(panel);
					panel =
							// new JPanel();
							new XChartPanel(chart);
					panel.revalidate();
					panel.repaint();
					GroupLayout gl_contentPane = new GroupLayout(contentPane);
					gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
											.addComponent(panel, GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE))
									.addGap(1)));
					gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
									.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addGap(18).addComponent(panel, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE).addGap(0)));
					contentPane.setLayout(gl_contentPane);

				}
			}
		});

		Date d = (Date) dateChooser.getDate();
		dd = new java.sql.Date(dateChooser.getDate().getTime());
		System.out.println(dd);
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		String s = sdf.format(d);

		SalesDAO sd = new SalesDAO();
		Long y[] = new Long[3];
		List<SalesDTO> sd_list = sd.selectDaySales(dd);
		int n = 0;
		for (SalesDTO beans : sd_list) {
			System.out.println(beans.toString());
			y[n] = (long) beans.getTotal();
			n++;
		}

		chart.addSeries("매출", Arrays.asList(new String[] { "현금", "카드", "총" }),
				Arrays.asList(new Long[] { y[0], y[1], y[2] }));
		panel =
				// new JPanel();
				new XChartPanel(chart);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE))
						.addGap(1)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(panel, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE).addGap(0)));
		contentPane.setLayout(gl_contentPane);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}
}
