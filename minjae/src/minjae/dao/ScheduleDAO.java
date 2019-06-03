package minjae.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import minjae.dto.CustomerDTO;
import minjae.dto.ScheduleDTO;

public class ScheduleDAO extends DB{
	public List<ScheduleDTO> getSchedule(Date date){
		List<ScheduleDTO> list = null;
		String sql = "select * from SCHEDULE where schedate = '" + date + "'";
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<ScheduleDTO>();
					while(rs.next()) {
						ScheduleDTO b = new ScheduleDTO();
						b.setCustID(rs.getInt(2));
						b.setScheDesc(rs.getString(4));
						b.setStartIndex(rs.getInt(5));
						b.setEndIndex(rs.getInt(6));
						b.setPayhow(rs.getInt(7));
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
	
	public List<ScheduleDTO> selectSchedule(Object search,Date date){
		List<ScheduleDTO> list = null;
		String sql = "select name,phone,schedate,schedesc,startindex,endindex,scheID,schedule.custid"
				+ " from SCHEDULE,customer where schedate = '" + date
				+ "' and customer.custid = " + search
				+ " and schedule.custid = " + search;
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<ScheduleDTO>();
					while(rs.next()) {
						ScheduleDTO b = new ScheduleDTO();
						b.setName(rs.getString(1));
						b.setPhone(rs.getString(2));
						b.setScheDate(rs.getDate(3));
						b.setScheDesc(rs.getString(4));
						b.setStartIndex(rs.getInt(5));
						b.setEndIndex(rs.getInt(6));
						b.setScheID(rs.getInt(7));
						b.setCustID(rs.getInt(8));
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

	public void insertSchedule(ScheduleDTO sd){

		String sql = "insert into schedule values((select nvl(max(scheid)+1,0) from schedule),?,?,?,?,?,?)";
		System.out.println(sql);
		if(connect()) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, sd.getCustID());
				pstmt.setDate(2, sd.getScheDate());
				pstmt.setString(3, sd.getScheDesc());
				pstmt.setInt(4, sd.getStartIndex());
				pstmt.setInt(5, sd.getEndIndex());
				pstmt.setInt(6, sd.getPayhow());
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
	
	public void updateSchedule(ScheduleDTO sd){
		String sql = "update schedule "
				+ "set schedate = ?, schedesc = ?,startindex = ?,endindex = ? "
				+ "where scheid = ?";
		System.out.println(sql);
		if(connect()) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setDate(1, sd.getScheDate());
				pstmt.setString(2, sd.getScheDesc());
				pstmt.setInt(3, sd.getStartIndex());
				pstmt.setInt(4, sd.getEndIndex());
				pstmt.setInt(5, sd.getScheID());
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
	public void updatePayhow(ScheduleDTO sd){
		String sql = "update schedule "
				+ "set payhow = ? "
				+ "where scheid = ?";
		System.out.println(sql);
		if(connect()) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, sd.getPayhow());
				pstmt.setInt(2, sd.getScheID());
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
	
	public void scheduleDelete(Object search,Date date) {
		String sql = "delete from SCHEDULE where schedate = '" + date + "' and custid = " + search;
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