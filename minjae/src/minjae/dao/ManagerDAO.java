package minjae.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import minjae.dto.CustomerDTO;

public class ManagerDAO extends DB{
	
	public List<CustomerDTO> getList(){
		List<CustomerDTO> list = null;
		String sql = "select * from Customer";
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
						b.setPhone(rs.getString(3));
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
	
	public List<CustomerDTO> searchList(Object search){
		List<CustomerDTO> list = null;
		String sql = "select * from Customer where Phone like '" + search + "'";
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs =stmt.executeQuery(sql); 
					list = new ArrayList<CustomerDTO>();
					while(rs.next()) {
						CustomerDTO b = new CustomerDTO();

						b.setNo(rs.getInt(1));
						b.setName(rs.getString(2));
						b.setPhone(rs.getString(3));
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

	public List<CustomerDTO> searchList_P(String search){
		List<CustomerDTO> list = null;
		String sql = "select * from Customer where Phone like '010-'|| ?";
		if(connect()) {
			try {
				pstmt = conn.prepareStatement(sql);
				if(pstmt !=null) {
					pstmt.setString(1, '%'+search+'%');

					ResultSet rs_sL =pstmt.executeQuery(); 
					list = new ArrayList<CustomerDTO>();
					while(rs_sL.next()) {
						CustomerDTO b = new CustomerDTO();

						b.setNo(rs_sL.getInt(1));
						b.setName(rs_sL.getString(2));
						b.setPhone(rs_sL.getString(3));
						list.add(b);
					}
					close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
		return list;
	}

	public List<CustomerDTO> searchList_N(String search){
		List<CustomerDTO> list = null;
		String sql = "select * from Customer where name_div like '%'|| FN_GET_DIV_KO_CHAR(?) ||'%'";
		if(connect()) {
			try {
				 pstmt = conn.prepareStatement(sql);
				if(pstmt !=null) {
					pstmt.setString(1, '%'+search+'%');

					ResultSet rs_sL =pstmt.executeQuery();
					System.out.println(rs_sL.toString());
					list = new ArrayList<CustomerDTO>();
					while(rs_sL.next()) {
						CustomerDTO b = new CustomerDTO();

						b.setNo(rs_sL.getInt(1));
						b.setName(rs_sL.getString(2));
						b.setPhone(rs_sL.getString(3));
						list.add(b);
					}
					close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
		return list;
	}
}
