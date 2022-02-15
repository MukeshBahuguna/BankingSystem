package com.revature.bankingsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Employee {
	
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
				System.out.println("Welcome "+rs.getString("emp_name"));
				
				System.out.println("Type a number");

				int choice= s.nextInt();
				switch(choice) {
				case 1: 
					viewCustomerAccount();
					break;	
				case 2:
					deleteCustomerAccount();
					break;
				case 3:
					updateCustomerDetails();
					break;
				default :
					break;
				}
				
			}
			else {
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
		s.close();
	}
	
	public void updateCustomerDetails() {
		// TODO Auto-generated method stub
		
		System.out.println("Type customer's id for which you want to update the details...");
		Scanner s= new Scanner(System.in);
		System.out.print("Id :");
		int id= s.nextInt();
		s.nextLine();
		
		System.out.println("Enter the new name: ");
		String nname= s.nextLine();

		System.out.println("Phone");
		String nphone =s.nextLine();
		
		System.out.print("Password :");
		String npass= s.nextLine();
		
		
		Statement st;
		try {
			st=DBConfigure.DBConnection().createStatement();	
			st.executeQuery(" update from customer set cust_name="+nname+" ,cust_phone="+nphone+" , cust_pass="+npass+"  where cust_id="+id+" ");
			System.out.println("Sucessfully deleted customer with id = "+id);
	
		}catch (SQLException e) {
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
			st.executeQuery(" delete from customer where cust_id="+id+" ");
			System.out.println("Sucessfully deleted customer with id = "+id);
	
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public void viewCustomerAccount() {
		Statement st;
		try {
			st=DBConfigure.DBConnection().createStatement();	
			ResultSet rs= st.executeQuery(" select * from customer limit 5 ");
			while(rs.next()) {
				System.out.println("Id: "+ rs.getInt("cust_id") +",  Name: " +rs.getString("cust_name") + ",  Phone: "+ rs.getString("cust_phone"));
			}
	
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean validAccountDetails(String customerName , String phNo) {
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
