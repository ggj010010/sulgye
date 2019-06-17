package minjae.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import minjae.dao.DB;
import minjae.dao.SalesDAO;
import minjae.dto.SalesDTO;
import javax.swing.JLabel;

public class SalesMonthUI extends JFrame {
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
	//					SalesMonthUI frame = new SalesMonthUI();
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
	public SalesMonthUI() {
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
		mni_DaySales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SalesDayUI();
				dispose();
			}
		});
		mn_Sales.add(mni_DaySales);

		JMenuItem mni_MonthSales = new JMenuItem("\uC6D4\uAC04\uB9E4\uCD9C\uD604\uD669");
		mn_Sales.add(mni_MonthSales);

		JMonthChooser monthChooser = new JMonthChooser();

		JYearChooser yearChooser = new JYearChooser();


		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("월 매출").xAxisTitle("날짜")
				.yAxisTitle("매출 현황").build();
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setYAxisDecimalPattern("#,###");
		chart.getStyler().setHasAnnotations(true);
		chart.getStyler().setOverlapped(true);

		SalesDAO sd = new SalesDAO();
		int year = yearChooser.getValue();
		int month = monthChooser.getMonth()+1;

		List<SalesDTO> sd_list = sd.selectMonthSales(String.valueOf(year).substring(2, 4), "0"+String.valueOf(month));

		List<Long> ta = new ArrayList<>();
		List<String> da = new ArrayList<>();
		List<Long> ma = new ArrayList<>();
		List<Long> ca = new ArrayList<>();
		for (SalesDTO beans : sd_list) {
			System.out.println(beans.toString());
			if(beans.getDate().equals("0")) {
				//ta.add((long) beans.getTotal());
			}else {
				if(da.size() != 0) {
					if(beans.getDate().equals(da.get(da.size()-1))) {
						if(beans.getHow() == 1) {
							//ta.add((long) 0);
							if(ma.size() != 0) {
								System.out.println(ma.size()-1);
								long save = ma.get(ma.size()-1);
								ma.remove(ma.size()-1);
								ma.add(ma.size(),save + (long) beans.getTotal());
								//ca.add((long) 0);
								continue;
							}
						}
					}
				}

				if(beans.getHow() == 0) {
					if(da.size() != 0) {
						if(!da.get(da.size()-1).equals(beans.getDate())) {
							da.add(beans.getDate());
							ta.add((long) beans.getTotal());
						}else {
							ta.add((long) beans.getTotal());
						}
					}else {
						da.add(beans.getDate());
						ta.add((long) beans.getTotal());
					}
				}

				if(beans.getHow() == 1) {
					//ta.add((long) 0);
					if(da.size() != 0) {
						if(!da.get(da.size()-1).equals(beans.getDate())) {
							da.add(beans.getDate());
							ma.add((long) beans.getTotal());
						}else {
							ma.add((long) beans.getTotal());
						}
					}else {
						da.add(beans.getDate());
						ma.add((long) beans.getTotal());
					}
					//ca.add((long) 0);
				}else if(beans.getHow() == 2) {
					//ta.add((long) 0);
					//ma.add((long) 0);
					if(da.size() !=0) {
						if(!da.get(da.size()-1).equals(beans.getDate())) {
							da.add(beans.getDate());
							ca.add((long) beans.getTotal());
						}else {
							ca.add((long) beans.getTotal());
						}
					}else {
						da.add(beans.getDate());
						ca.add((long) beans.getTotal());
					}
				}
				if(da.size() > 0) {
					if(beans.getDate() != da.get(da.size()-1)) {
						if(da.size() > ca.size()) {
							ca.add((long)0); 
						}
						if(da.size() > ma.size()) {
							ma.add((long)0);
						}
					}
				}

			}

		}
		System.out.println("da size : " + da.size()+" ta size : " + ta.size() + " ma size : " + ma.size() +" ca size : "+ ca.size());

		if(da.size() != 0) {
			if(ta.size() != 0)
				chart.addSeries("총",da,ta);
			if(ma.size() != 0)
				chart.addSeries("현금",da,ma);
			if(ca.size() != 0)
				chart.addSeries("카드",da,ca);
		}

		panel =
				//new JPanel();
				new XChartPanel(chart);

		JButton btnNewButton = new JButton("\uAC80\uC0C9");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SalesDAO sd = new SalesDAO();
				int year = yearChooser.getValue();
				int month = monthChooser.getMonth()+1;

				List<SalesDTO> sd_list = sd.selectMonthSales(String.valueOf(year).substring(2, 4), "0"+String.valueOf(month));

				List<Long> ta = new ArrayList<>();
				List<String> da = new ArrayList<>();
				List<Long> ma = new ArrayList<>();
				List<Long> ca = new ArrayList<>();
				for (SalesDTO beans : sd_list) {
					System.out.println(beans.toString());
					if(beans.getDate().equals("0")) {
						//ta.add((long) beans.getTotal());
					}else {
						if(da.size() != 0) {
							if(da.size() != 0) {
								if(beans.getDate().equals(da.get(da.size()-1))) {
									if(beans.getHow() == 1) {
										//ta.add((long) 0);
										if(ma.size() != 0) {
											System.out.println(ma.size());
											long save = ma.get(ma.size()-1);
											ma.remove(ma.size()-1);
											ma.add(ma.size()-1,save + (long) beans.getTotal());
											//ca.add((long) 0);
											continue;
										}
									}
								}
							}
						}

						if(beans.getHow() == 0) {
							if(da.size() != 0) {
								if(!da.get(da.size()-1).equals(beans.getDate())) {
									da.add(beans.getDate());
									ta.add((long) beans.getTotal());
								}else {
									ta.add((long) beans.getTotal());
								}
							}else {
								da.add(beans.getDate());
								ta.add((long) beans.getTotal());
							}
						}

						if(beans.getHow() == 1) {
							//ta.add((long) 0);
							if(da.size() != 0) {
								if(!da.get(da.size()-1).equals(beans.getDate())) {
									da.add(beans.getDate());
									ma.add((long) beans.getTotal());
								}else {
									ma.add((long) beans.getTotal());
								}
							}else {
								da.add(beans.getDate());
								ma.add((long) beans.getTotal());
							}
							//ca.add((long) 0);
						}else if(beans.getHow() == 2) {
							//ta.add((long) 0);
							//ma.add((long) 0);
							if(da.size() !=0) {
								if(!da.get(da.size()-1).equals(beans.getDate())) {
									da.add(beans.getDate());
									ca.add((long) beans.getTotal());
								}else {
									ca.add((long) beans.getTotal());
								}
							}else {
								da.add(beans.getDate());
								ca.add((long) beans.getTotal());
							}
						}/*else if(beans.getHow() == 3) {
							ta.add((long) beans.getTotal());
							ma.add((long) 0);
							ca.add((long) 0);
						}*/
					}

				}
				System.out.println("da size : " + da.size()+" ta size : " + ta.size() + " ma size : " + ma.size() +" ca size : "+ ca.size());
				chart.removeSeries("총");
				chart.removeSeries("현금");
				chart.removeSeries("카드");

				if(da.size() != 0) {
					if(ta.size() != 0)
						chart.addSeries("총",da,ta);
					if(ma.size() != 0)
						chart.addSeries("현금",da,ma);
					if(ca.size() != 0)
						chart.addSeries("카드",da,ca);
				}
				contentPane.remove(panel);
				panel =
						// new JPanel();
						new XChartPanel(chart);
				panel.revalidate();
				panel.repaint();
				GroupLayout gl_contentPane = new GroupLayout(contentPane);
				gl_contentPane.setHorizontalGroup(
						gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addContainerGap()
												.addComponent(yearChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(monthChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnNewButton))
										.addComponent(panel, GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE))
								.addGap(1))
						);
				gl_contentPane.setVerticalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btnNewButton)
										.addComponent(monthChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(yearChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(15)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
								.addGap(0))
						);
				contentPane.setLayout(gl_contentPane);
			}
		});
		
		JLabel lblNewLabel = new JLabel("\uC774 \uB2EC\uC758 \uC778\uC13C\uD2F0\uBE0C : ");
		
		JLabel lbl_insentive = new JLabel(sd.selectMonthInsentive(String.valueOf(year).substring(2, 4), "0"+String.valueOf(month)));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lbl_insentive)
							.addPreferredGap(ComponentPlacement.RELATED, 635, Short.MAX_VALUE)
							.addComponent(yearChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(monthChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE))
					.addGap(1))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton)
						.addComponent(monthChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(yearChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel)
							.addComponent(lbl_insentive)))
					.addGap(15)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
					.addGap(0))
		);
		contentPane.setLayout(gl_contentPane);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	}
}
