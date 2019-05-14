package minjae.dto;

public class CustomerDTO {
	int No;
	String Name;
	String Phone;
	
	public CustomerDTO() {
		super();
	}
	
	public CustomerDTO(int No,String Name,String Phone) {
		super();
		this.No = No;
		this.Name = Name;
		this.Phone = Phone;
	}
	
	public int getNo() {
		return No;
	}
	public void setNo(int no) {
		No = no;
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
	
	@Override
	public String toString() {
		return Name + "," + Phone;
	}
	
}
