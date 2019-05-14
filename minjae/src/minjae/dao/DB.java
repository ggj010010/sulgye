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

import minjae.dto.CustomerDTO;
import minjae.dto.TotalDTO;

public class DB {
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException e) {
			System.out.println("클래스 로드 실패 : " + e.getMessage());
		}
	}

	protected DB() {}

	private static DB obj;

	public static DB sharedInstance() {
		if(obj == null) {
			obj = new DB();
		}
		return obj;
	}

	protected Connection conn;
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet rs;

	protected boolean connect() {
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

	protected void close() {
		try {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(pstmt != null)
				pstmt.close();
			if(conn != null)
				conn.close();
		}catch(Exception e) {
			System.out.println("해제 실패" + e.getMessage());
		}
	}
}
