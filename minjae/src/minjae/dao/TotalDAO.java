package minjae.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import minjae.dto.TotalDTO;

public class TotalDAO extends DB{

	public List<TotalDTO> getNailList(Object obj,int a){
		List<TotalDTO> list = null;
		String sql = "select totalid,class,customer_total.custid,customer.name,customer.phone,to_char(total_date,'yy/mm/dd'),total_desc,total_money,nail_change "
				+ "from Customer_total,customer where class = "
				+a
				+" and (customer.phone = '"
				+ obj
				+"' and customer.custid = customer_total.custid)";
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<TotalDTO>();
					
					while(rs.next()) {
						TotalDTO b = new TotalDTO();

						b.setNo(rs.getInt(1));
						b.setTotal_class(rs.getInt(2));
						b.setCustId(rs.getInt(3));
						b.setName(rs.getString(4));
						b.setPhone(rs.getString(5));
						b.setDate(rs.getString(6));
						b.setDesc(rs.getString(7));
						b.setMoney(rs.getString(8));
						b.setChange(rs.getInt(9));
						list.add(b);
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close();
			}
		}else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
		return list;
	}
}
