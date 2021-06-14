package com.cheikh.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cheikh.springdemo.entity.Customer;


//We use the @Repository annotation so that srping can register this class as a DAO
// That way it will also take care of  providing translation of any JDBC related exception 
@Repository
public class CustomerDAOImpl implements CustomerDAO{

	//we use the @Autowireda annotation to inject the session factory 
	// which is connected to data source 
	//remember that the 'sessionFactory' field needs to match the id 
	// in the spring config file where we setup  Hibernate session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<Customer> getCustomers() {
		
		//get the current  hibernate session 
		Session currentSession =  sessionFactory.getCurrentSession();
		
		//create a query... sort by last
		Query<Customer> theQuery = 
				currentSession.createQuery("from Customer order by lastName", Customer.class);
		
		// execute query and get result list 
		List<Customer> customers = theQuery.getResultList();
		
		/// return the results
		return customers;
	}


	@Override
	public void saveCustomer(Customer theCustomer) {
		
		//get current the hibernate session 
		Session currentSession =  sessionFactory.getCurrentSession();
		
		
		//save/update the customer 
		currentSession.saveOrUpdate(theCustomer);
		
	}


	@Override
	public Customer getCustomers(int theId) {
		
		//get current the hibernate session 
				Session currentSession =  sessionFactory.getCurrentSession();
				
				
				//get the customer using id past in
				Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}


	@Override
	public void deleteCustomer(int theId) {
		//get current the hibernate session 
		Session currentSession =  sessionFactory.getCurrentSession();
		
		
		//get the customer using id past in
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();


	}

}
