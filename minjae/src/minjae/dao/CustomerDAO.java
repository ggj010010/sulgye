package minjae.dao;

import java.sql.SQLException;

import minjae.dto.CustomerDTO;

public class CustomerDAO extends DB{
	
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

}
