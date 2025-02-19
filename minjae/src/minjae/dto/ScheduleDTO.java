package minjae.dto;

import java.sql.Date;

public class ScheduleDTO {
	int scheID;
	int custID;
	Date scheDate;
	String scheDesc;
	int startIndex;
	int endIndex;
	int payhow;
	
	String name;
	String phone;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public ScheduleDTO() {
		
	}
	
	public ScheduleDTO(int scheID, int custID, Date scheDate, String scheDesc, int startIndex, int endIndex) {
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

	public int getPayhow() {
		return payhow;
	}

	public void setPayhow(int payhow) {
		this.payhow = payhow;
	}

	@Override
	public String toString() {
		return scheID + "," + custID + "," + scheDate + "," + scheDesc + "," + startIndex + "," + endIndex;
	}
}
