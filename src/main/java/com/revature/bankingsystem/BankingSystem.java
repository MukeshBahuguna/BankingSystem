package com.revature.bankingsystem;

import java.util.*;


//project
public class BankingSystem {


	public void viewLog() {
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s =new Scanner(System.in);
		System.out.println("|~~~~~~~~~~~~~~~~~~~~WELCOME TO JAVA BANKING~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
		int choice;
		do {
			System.out.println("To login as an Existing User type => 1");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
			System.out.println("To Register as a NEW USER    type => 2");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
			System.out.println("To login as an Employee      type => 3");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
			
			choice= s.nextInt(); 
		
			switch (choice) {
			case 1:
				Customer c=new Customer();
				System.out.println("|~~~~~~~~~~~~~~~~~~~~WELCOME TO CUSTOMER LOGIN PAGE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
				c.login();
				break;
	
			case 2:
				Customer c1=new Customer();
				System.out.println("|~~~~~~~~~~~~~~~~~~~~WELCOME TO CUSTOMER REGISTER PAGE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
				c1.register();
				break;
				
			case 3:
				Employee e= new Employee();
				System.out.println("|~~~~~~~~~~~~~~~~~~~~WELCOME TO EMPLOYEE LOGIN PAGE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
				e.login();
				break;
				
			case 4:
				break;
				
			default :
				System.out.println("Please choose a valid option");
				break;
			}
		}while (choice!=4);
		System.out.println("Thank You For using Java Banking");

		
	}

}
