package minjae.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

		String sql = "insert into schedule values((select nvl(max(scheid)+1,0) from schedule),?,?,?,?,?)";
		System.out.println(sql);
		if(connect()) {
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, sd.getCustID());
				pstmt.setDate(2, sd.getScheDate());
				pstmt.setString(3, sd.getScheDesc());
				pstmt.setInt(4, sd.getStartIndex());
				pstmt.setInt(5, sd.getEndIndex());
				pstmt.executeUpdate();

			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close();
			}
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