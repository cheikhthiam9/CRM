# Customer-Relationship-Management
Full working Spring MVC and Hibernate application that connects to a database application


 SAMPLE APP ARCHICHETURE - Big Picture 
 -> The Web Browser makes a request to the Front Controller which will delegate the request to the appropriate controller (Customer Controller).
  
 -> The Customer Controller will make use of the Service Layer that will interact with a DAO which is in charge of accessing data from database using the Hibernate API.
  
 -> Once the data has been retrieved from the database, the Controller will place the data into the Spring MVC Model and send that to the JSP pages. 
  
 -> Finally, the JSP page will render the data to the screen. 
 
 
 Key Features
  
 1. Retrieve/List Customers 
 2. Add a new Customer 
 3. Update a Customer 
 4. Delete a Customer 
