package minjae.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import minjae.dto.SalesDTO;
import minjae.dto.ScheduleDTO;

public class SalesDAO extends DB{

	public List<SalesDTO> selectDaySales(Date date){
		
		List<SalesDTO> list = null;
		String sql = "select "
				+ "sum(customer_total.total_money)"
				+ " from "
				+ " customer_total,schedule "
				+ " where "
				+ " total_date = '"
				+ date
				+ "' and"
				+ " schedule.custid = customer_total.custid"
				+ " and"
				+ " schedule.schedate = customer_total.total_date"
				+ " and"
				+ " (schedule.payhow = 1 "
				+ " and"
				+ " schedule.payhow !=3"
				+ " or"
				+ " customer_total.total_desc = '������')"
				+ " union all"
				+ " select"
				+ " sum(customer_total.total_money) "
				+ " from "
				+ " customer_total,schedule "
				+ " where "
				+ " total_date = '"
				+ date
				+ "' and"
				+ " schedule.custid = customer_total.custid"
				+ " and"
				+ " schedule.schedate = customer_total.total_date"
				+ " and"
				+ " schedule.payhow = 2"
				+ " union all"
				+ " select"
				+ " sum(customer_total.total_money) "
				+ " from "
				+ " customer_total,schedule "
				+ " where "
				+ " total_date = '"
				+ date
				+ "' and"
				+ " schedule.custid = customer_total.custid"
				+ " and"
				+ " schedule.schedate = customer_total.total_date"
				+ " and"
				+ " ("
				+ " schedule.payhow !=3"
				+ " or"
				+ " customer_total.total_desc = '������')";
		
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<SalesDTO>();
					while(rs.next()) {
						SalesDTO b = new SalesDTO();
						b.setTotal(rs.getInt(1));
						
						list.add(b);
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close();
			}
		}else {
			System.out.println("������ ���̽� ���� ����");
			System.exit(0);
		}
		return list;
	}
	
	public List<SalesDTO> selectMonthSales(String year,String month){
		List<SalesDTO> list = null;
		String sql ="select" + 
				" DECODE(total_date, NULL, '0', total_date) total_date" + 
				" ,DECODE(payhow, NULL, 0,3,1, payhow) payhow" + 
				" ,sum(customer_total.total_money) total" + 
				" from " + 
				" customer_total,schedule" + 
				" where " + 
				" total_date like '"
				+ year +"/"+month+"/%" + 
				"' and" + 
				" schedule.custid = customer_total.custid" + 
				" and" + 
				" schedule.schedate = customer_total.total_date" + 
				" and" + 
				" (" + 
				" schedule.payhow !=3" + 
				" or" + 
				" customer_total.total_desc = '������')" + 
				" group by ROLLUP(total_date,payhow)";
		
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<SalesDTO>();
					while(rs.next()) {
						SalesDTO b = new SalesDTO();
						b.setDate(rs.getString(1));
						b.setHow(rs.getInt(2));
						b.setTotal(rs.getInt(3));
						list.add(b);
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close();
			}
		}else {
			System.out.println("������ ���̽� ���� ����");
			System.exit(0);
		}
		return list;
	}
}
