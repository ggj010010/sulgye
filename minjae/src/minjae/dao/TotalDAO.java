package minjae.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import minjae.dto.TotalDTO;

public class TotalDAO extends DB {

	public List<TotalDTO> getNailList(Object obj, int a) {
		String[] aa = obj.toString().split("-");
		obj = aa[0] + aa[1] + aa[2];
		List<TotalDTO> list = null;
		String sql = "select totalid,total_class,customer_total.custid,name,phone,total_date,total_desc,total_money,nail_change "
				+ "from customer_total,customer where total_class = " 
				+ a 
				+ " and (phone = '" 
				+ obj.toString()
				+ "' and customer.custid = customer_total.custid)"
				+" order by totalid desc";
		System.out.println(sql);
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<TotalDTO>();
					while (rs.next()) {
						TotalDTO b = new TotalDTO();

						b.setNo(rs.getInt(1));
						b.setTotal_class(rs.getInt(2));
						b.setCustId(rs.getInt(3));
						b.setName(rs.getString(4));
						b.setPhone(rs.getString(5));
						b.setTotal_Date(rs.getDate(6));
						b.setDesc(rs.getString(7));
						b.setMoney(rs.getString(8));
						b.setChange(rs.getInt(9));
						list.add(b);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		} else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
		return list;
	}

	public List<TotalDTO> selectTotal_change(int custid) {
		List<TotalDTO> list = null;
		String sql = "select NAIL_CHANGE from (select * from customer_total order by TOTALID desc) where custid = "
				+ custid + " and rownum = 1 and total_class = 1";
		System.out.println(sql);
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<TotalDTO>();

					while (rs.next()) {
						TotalDTO b = new TotalDTO();
						b.setChange(rs.getInt(1));
						list.add(b);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		} else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
		return list;
	}

	public void insertTotal(TotalDTO td) {

		String sql = "insert into customer_total values((select nvl(max(totalid)+1,0) from customer_total),?,?,?,?,?,?)";
		System.out.println(sql);
		if (connect()) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, td.getTotal_class());
				pstmt.setInt(2, td.getCustId());
				pstmt.setDate(3, td.getTotal_Date());
				pstmt.setString(4, td.getDesc());
				pstmt.setString(5, td.getMoney());
				pstmt.setInt(6, td.getChange());
				pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		} else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
	}
}