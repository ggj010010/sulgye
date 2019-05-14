package minjae.dto;

import java.sql.Date;

public class scheduleDTO {
	int scheID;
	int custID;
	Date scheDate;
	String scheDesc;
	int startIndex;
	int endIndex;
	
	public scheduleDTO() {
		
	}
	
	public scheduleDTO(int scheID, int custID, Date scheDate, String scheDesc, int startIndex, int endIndex) {
		this.scheID = scheID;
		this.custID = custID;
		this.scheDate = scheDate;
		this.scheDesc = scheDesc;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public int getScheID() {
		return scheID;
	}



	public void setScheID(int scheID) {
		this.scheID = scheID;
	}



	public int getCustID() {
		return custID;
	}



	public void setCustID(int custID) {
		this.custID = custID;
	}



	public Date getScheDate() {
		return scheDate;
	}



	public void setScheDate(Date scheDate) {
		this.scheDate = scheDate;
	}



	public String getScheDesc() {
		return scheDesc;
	}



	public void setScheDesc(String scheDesc) {
		this.scheDesc = scheDesc;
	}



	public int getStartIndex() {
		return startIndex;
	}



	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}



	public int getEndIndex() {
		return endIndex;
	}



	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}



	@Override
	public String toString() {
		return scheID + "," + custID + "," + scheDate + "," + scheDesc + "," + startIndex + "," + endIndex;
	}
}
