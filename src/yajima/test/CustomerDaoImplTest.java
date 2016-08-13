package yajima.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import yajima.dao.CustomerDao;
import yajima.dao.impl.CustomerDaoImpl;
import yajima.model.Customer;
import yajima.model.Order;

public class CustomerDaoImplTest {
	private CustomerDao dao=new CustomerDaoImpl();
	@Test
	public void testAddCustomer() {
		Customer c=new Customer();
		c.setId(1);
		c.setName("yajima");
		c.setAddress("nju");
		Order o1=new Order();
		o1.setOrdernum(20160812);
		o1.setAmout(11);
		Order o2=new Order();
		o2.setOrdernum(20260822);
		o2.setAmout(22);
		c.getOs().add(o1);c.getOs().add(o2);
		dao.addCustomer(c);
	}

	@Test
	public void testFindCustomer() {
		Customer c1 = dao.findCustomer(1);
		System.out.println(c1);
		for (Order o : c1.getOs()) {
			System.out.println(o);
		}
	}

}
