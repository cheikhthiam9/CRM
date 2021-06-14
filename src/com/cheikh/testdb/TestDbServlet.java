package com.cheikh.testdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class TestDbServlet
 */
@WebServlet("/TestDbServlet")
public class TestDbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//setup connection variables
		
		String user = "springstudent";
		String password = "springstudent";
		String jdbcUrl = "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC";
		String driver = "com.mysql.cj.jdbc.Driver";
		
		//get connection to database 
		
		
		
		// get connection to database
		try {
			PrintWriter out = response.getWriter();
			
			out.println("Connecting to database: " + jdbcUrl);
			
			Class.forName(driver);
			
			Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);
			
			out.println("SUCCESS!!!");
			
			myConn.close();
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
			throw new ServletException(exc);
		}
		
	}

}




/*

 
 CREATING a database application 
 Project:  Full working Spring MVC and Hibernate application that connects to a database application
  
 ----------------------
 ----------------------
 CONCEPT 
 ----------------------
 ---------------------- 
 
 Dvlpt Process -  Customer Relationship Management  
 
 1. Set Up Db Dev Envrnt 
 2. Retrieve/List Customers 
 3. Add a new Customer 
 4. Update a Customer 
 5. Delete a Customer 
 
 SAMPLE APP ARCHICHETURE - Big Picture 
 -> The Web Browser will make a request to our Front Controller  
 	which will delegate the request to the appropriate controller (Customer Controller)
 -> The Customer Controller will make use of the Service Layer that will interact with a DAO (Customer Data Access Object).	
 	The DAO be in charge of accessing data from database using the Hibernate API.
 -> Once the data will be retrieved from the db, the Customer Controller will place the data into the Spring MVC Model 
 	and send that to the JSP pages 
 -> Finally, the JSP page will render the data to the screen 	 
 
 ---------
 
 Data Access Object 
 This is an object responsible for interacting with the database (= an helper/utility class)
 -> This is a common design pattern in the industry
 -> example of methods in a DAO : saveCustomer(), getCustomer()... all the CRUD Operation 
 
 
 For Hibernate, our DAO needs a Hibernate SessionFactory since it will be in charge of communicating with DB 
 -> The Session Factory needs to be connected to a Data Source which defines the database connection information (inside of  spring-mvc-crud-demo-servlet.xml)
 (As these are all dependencies, we'll wire the DAO <-> SessionFactory <->Data Source together with DI )
 
 Specialized Annotation for DAO
 
 @Repository annotation: An annotation that will place on top of our DAO implementation 
 ->  This annotation will specifically be used when your class will interact with a Data Source such as a Database 
 -> It inherits from @Component annotation so Spring will automatically register the bean as a DAO implementation 
 -> Spring will also provides translation of any JDBC related exception  

 --------------
Spring @Transactional annotation

This annotation will automagically begin and end a transaction for our Hibernate code. 
-> That way we don't need to explicitly do that in our code. 
-> Spring will take care of that behind the scene
--------
@GetMapping  & @PostMapping
When the web browser sends a request to a Spring MVC controller, it usually does so with the help of a HTML form. 

Most Commonly Used HTTPS Methods 

Method / Description
GET method / Request Data from a given  resource 
-> To send over data with your GET request we have to specify that in the parameters. 
-> When the request is processed, the data from the form will be added at the of URL request as a mane/value pairs format: 

	ex: theUrlRequest?field1=value1&field2=value2...... 
	
To make a Get Request using Spring we can make use of the @GetMapping annotation 
which will only map GET REQUEST. Any other method will be reject.

 	Key features of GET method
 	1. Good for debbuging 
 	2. Bookmark or email URL
 	3. Limitations on data length
	
	
POST method: Submits data to given resource 

To make a POST Request using Spring we can make use of the @PostMapping annotation 
which will only map Post REQUEST. Any other method will be reject. 

Key features of Post method
 	1. Can also send binary data 
 	2. Can't Bookmark or email URL
 	3. No limitations on data length	
.... 

-------
Service Layer 

We want to add a Service Layer that will sit in between the Controller and the DAO. 

Purpose of Service Layer 
-> It is an implementation of the Service Facade design pattern 
-> It is in intermediate layer for custom business logic 
-> It will integrate data from multiple sources (DAO/repositories)
	For example: the service layer will collect data via multiple DAOs interacting with different database. 
	Once the data have been collected and gathered into the service layer, 
	the Controller will have a single view of all the data via the service layer


Spring provides the @Service annotation to detect which bean is used as a service layer 
It is applied to the @Service implementations so Spring will automatically register the Service implementation thanks to component-scanning 


----------------------
----------------------
IMPLEMENTATION 
----------------------
---------------------- 

Dvlpt Process - Retrieve/List Customers

1. Create Customer.java (= Entity class - Java class that is mapped to a db table)

	Do the following inside the class: 
	- match class with  appropriate table name in db 
	- match each field with  appropriate column name in db 
	- setup construtors 
	- setup toString method 
	- setup getters and setteres

	Entity Scanning - Hibernate needs to know which classes will be used as en entity. 
	For this to happen, we need to configure the Spring MVC config file 
	(see spring-mvc-crud-demo-servlet.xml)
	
	
2.Create CusstomerDAO.java & CustomerDAOImpl.java
	2.a. Define DAO interface with different methods that will interact with db 
	2.b. Define DAO Implementation 
		-> Add @Repository annotation 
		-> Inject Session Factory
		-> Implement methods that will interact with DB (Ex : getCustomers())
		
3. Create CustomerController.java 

	Do the following inside the class: 
	- Add @Controller annotation  to class
	- Inject DAO or Service Layer depending on design pattern implementation we have chosen 
		- If Controller interacts first with service layer then we inject Service
		- If Controller interacts first with DAO then we inject DAO 
	- Add appropriate Mapping request annotation to class/method with web request path as a parameters 
 
	

4. Create JSP page: list-customers.jsp 

----------

Dvlpt Process - Add a new Customer 

1. Update list-customer.jsd 
	-> add a new button: "Add Customer"
2. Create HTML form for new customer 
3. Process Form Data 
	-> Controller -> Service -> DAO 
	
--------	

Dvlpt Process - Update Customer 

1. Update list-customers.jsp 
	-> Add link "Update"
2. Create customer -form.jsp
	-> Prepopulate the form 
3. Process Form Data 
	-> Controller -> Service -> DAO	

-----------
Dlvpt Process - Making JSP page pretty using CSS 

1. Place CSS directory with css files in a 'ressources' directory underneath WebContent directory
2. configur Spring to sevu 'ressources' directory
3. Reference CSS in our JSP
------
Dvlpt Process - Customer Service Implementation 
1. Define Service  Interface 
2. Define Service Implementation 
	-> Inject CustomerDAO 
-----------	

  
  
**/

