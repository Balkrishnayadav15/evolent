# Welcome to Evolent Health Content Management application

This application includes the following functionality with given validation.

```bash
1. Add user - User can add "First name, last name, email id, mobile number and for new user status will be active."
  Validations:
      a. First name should have only alphabets and less than 50 characters.
      b. Last name should have only alphabets and less than 50 characters.
      c. Email id should contain a '.' 
      d. Mobile number should be only numeric and must be greater than 10 digits.
      e. By default, new user status will be Active.

2. Update user - User can update "First name, last name, email id, mobile number, and status to Active/Inactive."
  Validations:
      a. First name should have only alphabets and less than 50 characters.
      b. Last name should have only alphabets and less than 50 characters.
      c. Email id should contain a '.' 
      d. Mobile number should be only numeric and must be greater than 10 digits.
      e. Status can be Active/Inactive.

3. Delete user - User can be deleted by clicking on the Delete button.
  
4. User Details- User can be view user details by clicking on the Details button.

5. All user List- We can see all user list.
   
```

## Pre-requisite

```bash
1. Node.js
    Node.js version 10.9.0 or later.
2. npm package manager
    Install angular-cli if not installed - "npm install -g @angular/cli"
3. Java-8
4. Apache Tomcat 8.5 - Download and unzip "https://tomcat.apache.org/download-80.cgi"
5. Git 
6. Eclipse and any tool support Angular development. I prefer "Visual studio code"
```

## 1. Steps to run the application using on a separate tomcat

```bash
1. Import the codebase in your eclipse from repository "https://github.com/Balkrishnayadav15/evolent.git"
2. It contains two modules:
   a. evolent-user-client - This is Angular 8 application.
   b. evolent-user-rest-service - This is the spring-boot project which consists of Rest API for managing user information.
3. Now after successful import of "evolent" project. You can import "evolent-user-rest-service" as a General Java project. 
4. Right click on the pom.xml of "evolent-user-rest-service", click on "Run As" > "maven build". Give command "clean install" , click on Run button. 
5. If build is successfull then you will see a "evolent.war" file in target folder.
6. Copy the evolent.war file.
7. Goto "apache-tomcat-8.5.49\webapps" paste evolent.war file here. 
9. To start the server go ot "apache-tomcat-8.5.49\bin" and run "startup.sh" file. After successfull server startup you will a message the server is started.
10. Hit the "http://localhost:8080/evolent/" to start the application.
```

## 2. Steps to run spring-boot application separately and angular application separately.  
```bash
1. Goto "evolent-user-rest-service", open "EvolentUserRestServiceApplication.java" class. Run it as a main class. It will start the tomcat server and deploy the applcation into it.
2. Open "evolent-user-client" in Visual studio and open Terminal - Follow below steps
    a. Install all package - "npm install"
	b. Build the application using "ng build"
	c. Start the server "ng server -o"
3. Hit the url in browser "http://localhost:4200". It will open the application.
```
