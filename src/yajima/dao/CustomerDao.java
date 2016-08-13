package yajima.dao;

import yajima.model.Customer;

public interface CustomerDao {
	void addCustomer(Customer c);
	Customer findCustomer(int id);
}
