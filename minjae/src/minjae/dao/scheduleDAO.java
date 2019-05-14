package minjae.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import minjae.dto.scheduleDTO;

public class scheduleDAO extends DB{
	public List<scheduleDTO> getSchedule(String date){
		List<scheduleDTO> list = null;
		String sql = "select * from SCHEDULE";
		System.out.println(sql);
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt !=null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<scheduleDTO>();
					while(rs.next()) {
						scheduleDTO b = new scheduleDTO();
						
						b.setScheDesc(rs.getString(4));
						b.setStartIndex(rs.getInt(5));
						b.setEndIndex(rs.getInt(6));
						System.out.println(b.toString());
						list.add(b);
						System.out.println(list);
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