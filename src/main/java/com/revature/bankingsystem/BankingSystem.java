package com.revature.bankingsystem;

import java.util.*;


//project
public class BankingSystem {


	public void viewLog() {
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s =new Scanner(System.in);
		System.out.println("To login as an User type => 1");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("To Register as a NEW USER type => 2");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("To login as an employee type => 3");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		int choice= s.nextInt(); 
		
		switch (choice) {
		case 1:
			Customer c=new Customer();
			c.login();
			break;

		case 2:
			Customer c1=new Customer();
			c1.register();
			break;
			
		case 3:
			Employee e= new Employee();
			e.login();
			break;
			
		default :
			System.out.println("Please choose a valid option ( Either 1 or 2)");
			break;
		}
		System.out.println("Thank You");

		
	}

}
