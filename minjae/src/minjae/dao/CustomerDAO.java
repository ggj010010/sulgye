package minjae.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import minjae.dto.CustomerDTO;

public class CustomerDAO extends DB{
	
	public List<CustomerDTO> customerSelect(int custid){
		List<CustomerDTO> list = null;
		String sql = "select * from Customer where custid = " + custid ;
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<CustomerDTO>();

					while(rs.next()) {
						CustomerDTO b = new CustomerDTO();

						b.setNo(rs.getInt(1));
						b.setName(rs.getString(2));
						b.setPhone(phone(rs.getString(3)));
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
	
	public void customerInsert(CustomerDTO cd){
		String sql = "insert into Customer values((select nvl(max(custid)+1,0) from Customer),?,?,FN_GET_DIV_KO_CHAR(?))";
		System.out.println(sql);
		if(connect()) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, cd.getName());
				pstmt.setString(2, cd.getPhone());
				pstmt.setString(3, cd.getName());
				pstmt.executeUpdate();

			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close();
			}
		}else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
	}
	
	public void customerUpdate(CustomerDTO cd,Object search){
		String sql = "update Customer set phone = '" + cd.getPhone() + "' where phone like '" + search + "'";
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);

			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close();
			}
		}else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
	}
	
	public void customerDelete(Object search) {
		String sql = "delete from Customer where Phone like '" + search + "'";
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close();
			}
		}else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
	}
	public static String phone(String src) {
	    if (src == null) {
	      return "";
	    }
	    if (src.length() == 8) {
	      return src.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
	    } else if (src.length() == 12) {
	      return src.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
	    }
	    return src.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
	  }

}
