package yajima.model;

public class Order {
	private int ordernum;
	private float amout;
	//many2one
	private Customer customer;
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public float getAmout() {
		return amout;
	}
	public void setAmout(float amout) {
		this.amout = amout;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Override
	public String toString() {
		return "Order [ordernum=" + ordernum + ", amout=" + amout
				+ ", customer=" + customer + "]";
	}
	
}
