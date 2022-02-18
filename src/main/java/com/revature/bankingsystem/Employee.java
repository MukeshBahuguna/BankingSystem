package com.revature.bankingsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Employee {
	Logger logger = LogManager.getLogger(Employee.class);
	
	public void login() {
		System.out.println("Type your id to login as an employee");
		Scanner s= new Scanner(System.in);
		System.out.print("Id :");
		int id= s.nextInt();
		s.nextLine();
		
		
		System.out.print("Password :");
		String pass= s.nextLine();
		
		Statement st;
		try {
			st=DBConfigure.DBConnection().createStatement();	
			ResultSet rs= st.executeQuery("select * from employee where emp_id="+id+" ");
			if(rs.next() && rs.getString("emp_pass").equals(pass) ) {
				System.out.println("You have successfully logged in ");
				System.out.println("**********Welcome "+rs.getString("emp_name")+"*********\n");
				int choice;
				do {
					System.out.println("To view customer's details     type => 1");
					System.out.println("To delete customer's account   type => 2");
					System.out.println("To Exit                        type => 3");
	
					choice= s.nextInt();
					switch(choice) {
					case 1: 
						viewCustomerAccount();
						break;	
					case 2:
						deleteCustomerAccount();
						break;
					default :
						break;
					}
				}while (choice!=3);
				
			}
			else {
				logger.error("Error during Employee log in ->Either Id does not exist 😒 or Password does not match");
				System.out.println("Either Id does not exist 😒 or Password does not match ☹ Try again");
				System.out.println("Press y to enter details again");
				System.out.println("Press n to exit");
				String choice= s.nextLine();
				switch(choice) {
				case "y": 
					login();
					break;
				default :
					break;
				}
				
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	public void deleteCustomerAccount() {
		// TODO Auto-generated method stub
		System.out.println("Type customer's id which you want to delete...");
		Scanner s= new Scanner(System.in);
		System.out.print("Id :");
		int id= s.nextInt();
		s.nextLine();
		
		Statement st;
		try {
			st=DBConfigure.DBConnection().createStatement();	
			int n=st.executeUpdate(" delete from customer where cust_id="+id+" ");
			if (n!=0){
				System.out.println("Sucessfully deleted customer with id = "+id);
				logger.info("Sucessfully deleted customer with id = "+id+"");
			}
			else {
				System.out.println("Table empty or id does not Exist!\n");
			}
			
	
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public void viewCustomerAccount() { //here
		Statement st;
		try {
			st=DBConfigure.DBConnection().createStatement();	
			ResultSet rs= st.executeQuery(" select * from customer limit 5 ");
			while(rs.next()) {
				System.out.println("Id: " + rs.getInt("cust_id") + ",  Name: " + rs.getString("cust_name") + 
						",  Phone: " + rs.getString("cust_phone") + ", balance: " +rs.getInt("balance") );
			}
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
	
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean validAccountDetails(String customerName , String phNo) {
		return validPhoneNo(phNo) && validNameC(customerName);
	}

	public static boolean validPhoneNo(String phNo) {
		if (phNo.length() != 10) {
			System.out.println("Phone number should contain 10 numbers");
			return false;
		}
		for (int i=0 ; i <phNo.length() ; i++) {
			if (!Character.isDigit(phNo.charAt(i))) {
				System.out.println("Phone number should only contain numbers");
				return false;
			}
		}
		return true;
	}
	
	public static boolean validNameC(String customerName ) {
		for (int i=0 ; i <customerName.length() ; i++) {
			char chr=customerName.charAt(i);
			if (!Character.isLetter(chr)) {
				System.out.println("Name should only contain alphabets");
				return false;
			}
		}
		return true;
	}
	
	
}
