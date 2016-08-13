package yajima.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int id;
	private String name;
	private String address;
	//one2many
	private List<Order> os=new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Order> getOs() {
		return os;
	}
	public void setOs(List<Order> os) {
		this.os = os;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address
				+ ", os=" + os + "]";
	}
	
	
}
