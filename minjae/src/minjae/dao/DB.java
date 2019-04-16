package minjae.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.PresentationDirection;

import minjae.dto.Customer_Beans;
import minjae.dto.Total_Beans;

public class DB {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e) {
			System.out.println("클래스 로드 실패 : " + e.getMessage());
		}
	}

	private DB() {}

	private static DB obj;

	public static DB sharedInstance() {
		if(obj == null) {
			obj = new DB();
		}
		return obj;
	}

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	private boolean connect() {
		boolean result = false;
		try {
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe","test","test");
			result = true;
		}catch(Exception e) {
			System.out.println("연결 실패 : " +e.getMessage());
		}
		return result;
	}

	private void close() {
		try {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		}catch(Exception e) {
			System.out.println("해제 실패" + e.getMessage());
		}
	}

	public List<Customer_Beans> searchList_P(String search){
		List<Customer_Beans> list = null;
		String sql = "select * from Customer where Phone like '010-'|| ?";
		if(connect()) {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				if(pstmt !=null) {
					pstmt.setString(1, '%'+search+'%');

					ResultSet rs_sL =pstmt.executeQuery(); 
					list = new ArrayList<Customer_Beans>();
					while(rs_sL.next()) {
						Customer_Beans b = new Customer_Beans();

						b.setNo(rs_sL.getInt(1));
						b.setName(rs_sL.getString(2));
						b.setPhone(rs_sL.getString(3));
						list.add(b);
					}
					pstmt.close();
					this.close();
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

	public List<Customer_Beans> searchList_N(String search){
		List<Customer_Beans> list = null;
		String sql = "select * from Customer where name_div like '%'|| FN_GET_DIV_KO_CHAR(?) ||'%'";
		if(connect()) {
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				if(pstmt !=null) {
					pstmt.setString(1, '%'+search+'%');

					ResultSet rs_sL =pstmt.executeQuery();
					System.out.println(rs_sL.toString());
					list = new ArrayList<Customer_Beans>();
					while(rs_sL.next()) {
						Customer_Beans b = new Customer_Beans();

						b.setNo(rs_sL.getInt(1));
						b.setName(rs_sL.getString(2));
						b.setPhone(rs_sL.getString(3));
						list.add(b);
					}
					pstmt.close();
					this.close();
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
	
	public List<Customer_Beans> searchList(Object search){
		List<Customer_Beans> list = null;
		String sql = "select * from Customer where Phone like '" + search + "'";
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs =stmt.executeQuery(sql); 
					list = new ArrayList<Customer_Beans>();
					while(rs.next()) {
						Customer_Beans b = new Customer_Beans();

						b.setNo(rs.getInt(1));
						b.setName(rs.getString(2));
						b.setPhone(rs.getString(3));
						list.add(b);
					}
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

	public List<Customer_Beans> getList(){
		List<Customer_Beans> list = null;
		String sql = "select * from Customer";
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<Customer_Beans>();

					while(rs.next()) {
						Customer_Beans b = new Customer_Beans();

						b.setNo(rs.getInt(1));
						b.setName(rs.getString(2));
						b.setPhone(rs.getString(3));
						list.add(b);
					}
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

	public List<Total_Beans> getNailList(Object obj,int a){
		List<Total_Beans> list = null;
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
					list = new ArrayList<Total_Beans>();
					
					while(rs.next()) {
						Total_Beans b = new Total_Beans();

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
			}
		}else {
			System.out.println("데이터 베이스 연결 실패");
			System.exit(0);
		}
		return list;
	}
}
