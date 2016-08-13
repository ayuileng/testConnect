package yajima.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import yajima.dao.CustomerDao;
import yajima.model.Customer;
import yajima.model.Order;
import yajima.utils.DBCPUtil;

public class CustomerDaoImpl implements CustomerDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

	@Override
	public void addCustomer(Customer c) {
		// 保存客户的基本信息
		try {
			qr.update("insert into customers (id,name,address) values (?,?,?)",
					c.getId(), c.getName(), c.getAddress());
			// 查看是否存在订单，若有则保存
			List<Order> os = c.getOs();
			if (os.size() > 0) {
				Object[][] params = new Object[os.size()][];
				for (int i = 0; i < os.size(); i++) {

					Order o = os.get(i);
					params[i] = new Object[] { o.getOrdernum(), o.getAmout(),
							c.getId() };

				}
				qr.batch(
						"insert into orders (ordernum,amount,customerId) values (?,?,?)",
						params);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//客户对应的订单要不要查询？
	//查询客户时，吧订单查询出来，但是订单中只有主键，这就是延迟加载机制
	@Override
	public Customer findCustomer(int id) {
		try{
			Customer c = qr.query("select * from customers where id=?", new BeanHandler<Customer>(Customer.class),id);
			if(c!=null){
				List<Order> os = qr.query("select * from orders where customerId=?", new BeanListHandler<Order>(Order.class),id);
				c.setOs(os);
			}
			return c;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
