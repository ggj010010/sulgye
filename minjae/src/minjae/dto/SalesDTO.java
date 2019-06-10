package minjae.dto;

import java.sql.Date;

public class SalesDTO {
	String date;
	int how;
	int total;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getHow() {
		return how;
	}
	public void setHow(int how) {
		this.how = how;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		return "date : " + date + " how : " + how + " total : " + total;
	}

}
