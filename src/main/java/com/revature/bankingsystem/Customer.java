package com.revature.bankingsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Customer {
	
	public void withdraw(int id) {
		System.out.println("Type the amount you want to withdraw: ");
		Scanner s= new Scanner(System.in);
		System.out.print("Amount :");
		int amount= s.nextInt();
		s.nextLine();
		int bal=viewAccountBalance(id);
		if (amount<0) {
			System.out.println("invalid value for amount ☹");
		}
		
		else if(amount > bal) System.out.println("balance is not sufficent! ☹");
		else {
			bal-=amount;
			Statement st;
			try {
				st=DBConfigure.DBConnection().createStatement();	
				st.executeUpdate("insert into account values ("+id+" , "+bal+" , "+-amount+" )");
				System.out.println("Money withdrawn : " + -amount);
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		s.close();
	}

	public void deposite(int id) {
		System.out.println("Type the amount you want to deposite: ");
		Scanner s= new Scanner(System.in);
		System.out.print("Amount :");
		int amount= s.nextInt();
		s.nextLine();
		int bal=viewAccountBalance(id);
		if (amount<0) {
			System.out.println("invalid value for amount ☹");
		}
		
		else {
			bal+=amount;
			
			Statement st;
			try {
				st=DBConfigure.DBConnection().createStatement();	
	
				st.executeUpdate("insert into account values ("+id+" , "+bal+" ,0 , "+amount+" )");
				System.out.println("Money added : " + amount);
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		s.close();
	}

	public int viewAccountBalance(int id) {
		Statement st;
		int bal=0;
		try {
			st=DBConfigure.DBConnection().createStatement();	
			ResultSet rs= st.executeQuery(" select * from account where cust_id="+id+" ");
			while(rs.next()) {
				bal=rs.getInt("balance");
			}
	
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return bal;
		
	}

	
	public void login() {
		System.out.println("Type your generated id");
		Scanner s= new Scanner(System.in);
		System.out.print("Id :");
		int id= s.nextInt();
		s.nextLine();
		
		
		System.out.print("Password :");
		String pass= s.nextLine();
		
		Statement st;
		try {
			st=DBConfigure.DBConnection().createStatement();	
			ResultSet rs= st.executeQuery("select * from customer where cust_id="+id+" ");
			if(rs.next() && rs.getString("cust_pass").equals(pass) ) {
				System.out.println("You have successfully logged in");
				
				System.out.println("To view your balance type => 1");
				System.out.println("To view your deposite type => 2");
				System.out.println("To view your withdraw type => 3");
				int choice= s.nextInt();
				switch(choice) {
				case 1: 
					int bal=viewAccountBalance(id);
					System.out.println("Your account balance is "+bal);
					break;
				case 2: 
					deposite(id);
					break;
				case 3: 
					withdraw(id);
					break;
				default :
					System.out.println("Please choose a valid option ");
					System.out.println("Terminating session.......");
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
					System.out.println("Terminating session.......");
					break;
				}
				
			}
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		s.close();
		
	}

	public void register() {
		System.out.println("To create an account follow steps below: ");
		Scanner s= new Scanner(System.in);
		System.out.print("Name :");
		String name= s.nextLine();
	
		System.out.print("Phone number:");
		String phNo= s.nextLine();
		
		boolean isValid=Employee.validAccountDetails(name , phNo);
		while (!isValid) {
			register();
			
		}
		
		System.out.print("password:");
		String pass= s.nextLine();
		
		
		System.out.println("Your password is " +pass + " (Kindly Remember This as this will be required during login)");
		
		Statement st;
		try {
			st=DBConfigure.DBConnection().createStatement();	
			//**********to fetch previous key
			
			ResultSet rs= st.executeQuery("select * from customer");
			boolean f=rs.next();
			if (f==false) {
				st.executeUpdate("insert into customer values (1 , ' "+name+" ', ' "+phNo+" ', '"+pass+"')");
				System.out.println("And, your generated id is " +1 + " (Kindly Remember This as this will be required during login)");
			}
			
			else {
				int index=0;
				while(f) {
					index=rs.getInt("cust_id");
					f=rs.next();
				}
				index+=1;
				System.out.println("Your generated id is " +index + " (Kindly Remember This as this will be required during login)");
				st.executeUpdate("insert into customer values ( "+index+", ' "+name+" ', ' "+phNo+" ', '"+pass+"') ");
			
				st.executeUpdate("insert into account values ( "+index+") ");
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("To login type => 1");
		System.out.println("To exit type  => 2");
		int choice= s.nextInt(); 
		
		switch (choice) {
		case 1:
			login();
			break;

		case 2:
			break;
			
		default :
			System.out.println("Please choose a valid option ( Either 1 or 2)");
			System.out.println("Terminating session.......");
			break;
		}
		s.close();
	}

}
