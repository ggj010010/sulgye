package minjae.dto;

import java.sql.Date;

public class TotalDTO {
	int No;
	int total_class;
	int CustId;
	String Name;
	String Phone;
	Date total_Date;
	String totalDate;
	String Desc;
	String Money;
	int Change;
	
	
	public String getTotalDate() {
		return totalDate;
	}


	public void setTotalDate(String totalDate) {
		this.totalDate = totalDate;
	}


	public int getNo() {
		return No;
	}


	public void setNo(int no) {
		No = no;
	}


	public int getTotal_class() {
		return total_class;
	}


	public void setTotal_class(int total_class) {
		this.total_class = total_class;
	}


	public int getCustId() {
		return CustId;
	}


	public void setCustId(int custId) {
		CustId = custId;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getPhone() {
		return Phone;
	}


	public void setPhone(String phone) {
		Phone = phone;
	}


	public Date getTotal_Date() {
		return total_Date;
	}


	public void setTotal_Date(Date total_Date) {
		this.total_Date = total_Date;
	}


	public String getDesc() {
		return Desc;
	}


	public void setDesc(String desc) {
		Desc = desc;
	}


	public String getMoney() {
		return Money;
	}


	public void setMoney(String money) {
		Money = money;
	}


	public int getChange() {
		return Change;
	}


	public void setChange(int change) {
		Change = change;
	}


	@Override
	public String toString() {
		return CustId + "," + total_Date + "," + Desc + "," + Money + "," + Change;
	}

}
