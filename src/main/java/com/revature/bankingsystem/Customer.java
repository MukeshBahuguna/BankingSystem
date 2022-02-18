package com.revature.bankingsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer {
	Logger logger = LogManager.getLogger(Customer.class);
	
	public void withdraw(int id) {
		System.out.println("Type the amount you want to withdraw: ");
		Scanner s= new Scanner(System.in);
		System.out.print("Amount :");
		int amount= s.nextInt();
		s.nextLine();
		int bal=viewAccountBalance(id);
		if (!validCustAmount(amount)) {
			System.out.println("invalid value for amount ☹");
			logger.error("Negetive value entered! by user with id=" +id); 
		}
		
		else if(!validCustBalanceForWithdrawal( amount, bal) ) {
			System.out.println("balance is not sufficent! ☹");
			logger.error("Balance not sufficient as value entered exceeds the balance! for user with id=" +id); 
		}
		
		else {
			bal-=amount;
			Statement st;
			try {
				st=DBConfigure.DBConnection().createStatement();	
				st.executeUpdate("update customer set balance="+bal+" where cust_id= "+id+"");
				st.executeUpdate("insert into account values ("+id+" , "+bal+" , "+-amount+" )");
				System.out.println("Money withdrawn : " + -amount);
				logger.info("Sucessful Money withdrawal for user with id=" +id);
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean validCustAmount(int amount) {
		if(amount<0) {
			return false;
		}
		return true;
	}
	public boolean validCustBalanceForWithdrawal(int amount, int bal) {
		if(amount>bal) {
			return false;
		}
		return true;
	}
	

	public void deposite(int id) {
		System.out.println("Type the amount you want to deposite: ");
		Scanner s= new Scanner(System.in);
		System.out.print("Amount :");
		int amount= s.nextInt();
		s.nextLine();
		int bal=viewAccountBalance(id);
		if (!validCustAmount(amount)) {
			System.out.println("invalid value for amount ☹");
			logger.error("Negetive value entered! by user with id=" +id); 
		}
		
		else {
			bal+=amount;
			
			Statement st;
			try {
				st=DBConfigure.DBConnection().createStatement();	
				st.executeUpdate("update customer set balance="+bal+" where cust_id= "+id+"");
				st.executeUpdate("insert into account values ("+id+" , "+bal+" ,0 , "+amount+" )");
				System.out.println("Money added : " + amount);
				logger.info("Money deposit successful for id= "+id);
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

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
	public boolean validUserForTransfer(int id,String pass) {
		Statement st ;
		try {
			st=DBConfigure.DBConnection().createStatement();	
			ResultSet rs=st.executeQuery("select * from customer where cust_id="+id+"");
			if (rs.next()) {
				if (rs.getString("cust_pass").trim().equals(pass))
					return true;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void transferMoney() {
		System.out.println("Type customer's id for transfering the money!...");
		Scanner s= new Scanner(System.in);
		System.out.print("Your Id :");
		int sid= s.nextInt();
		
		System.out.print("Your pass:");
		String pass= s.next().trim();
		
		if (validUserForTransfer(sid, pass)) {
		
			System.out.print("Reciever's Id :");
			int rid= s.nextInt();
	
			System.out.print("Enter the amount: ");
			int amount= s.nextInt();
			if (!validCustAmount(amount)) {
				System.out.println("invalid value for amount ☹");
				logger.error("Negetive value entered! by user with id=" +sid); 
			}
			
			else {
				int bal_reciever=viewAccountBalance(rid);
				bal_reciever +=amount;
				
				int bal_cust= viewAccountBalance(rid);
				if (bal_cust<amount) {
					System.out.println("Balance not sufficient!!");
					logger.error("Balance not sufficient for user with id=" + sid); 
				}
				else {
					bal_cust -=amount; 
					Statement st ;
					try {
						st=DBConfigure.DBConnection().createStatement();
						st.executeUpdate("update customer set balance="+bal_cust+" where cust_id= "+sid+"");
						st.executeUpdate("update customer set balance="+bal_reciever+" where cust_id= "+rid+"");
						st.executeUpdate("insert into account values ("+sid+" , "+bal_cust+" ,0 , 0 ,"+-amount+")");
						st.executeUpdate("insert into account values ("+rid+" , "+bal_reciever+" ,0 , 0 ,"+amount+")");
						System.out.println("Money transfered : " + amount);
						logger.info("Transfer from sender with "+sid+" to reciver with "+rid +"and Money transfered : " + amount);
						
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else {
			System.out.println("Hmmmm... The Credentials do not match the ones in the database. This is SUS!\n");
			System.out.println("Choose again");
		}
	}
	
	public void updateDetails(int id) {
	// TODO Auto-generated method stub
		Scanner s= new Scanner(System.in);
	
		System.out.print("Enter the new name: ");
		String nname= s.next().trim();
	
		System.out.print("New Phone : ");
		String nphone =s.next().trim();
		
		System.out.print("New Password :");
		String npass= s.next().trim();
		if(Employee.validAccountDetails(nname,nphone)){
			
			Statement st;
			try {
				st=DBConfigure.DBConnection().createStatement();	
				st.executeUpdate(" update customer set cust_name='"+nname+"' ,cust_phone='"+nphone+" ', "
						+ "cust_pass='"+npass+"'  where cust_id="+id+" ");
				System.out.println("Sucessfully updated details for customer with id = "+id+" ");
				logger.info("Sucessfully updated details for customer with id = "+id+" ");
		
			}catch (SQLException e) {
				e.printStackTrace();
			}
			}
		else {
			System.out.println("Type in valid Details");
			}
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
				System.out.println("You have successfully logged in!!\n");
				logger.info("User with id "+ id+" logged in");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				
				int choice;
				do {
					System.out.println("To view your balance      type => 1");
					System.out.println("To deposite money         type => 2");
					System.out.println("To withdraw money         type => 3");
					System.out.println("To transfer money         type => 4");
					System.out.println("To update account details type => 5");
					System.out.println("To Exit                   type => 6");
					choice= s.nextInt();
					switch(choice) {
					case 1: 
						int bal=viewAccountBalance(id);
						System.out.println("Your account balance is "+bal+"\n");
						break;
					case 2: 
						deposite(id);
						break;
					case 3: 
						withdraw(id);
						break;
					case 4:
						transferMoney();
						break;		
					case 5:
						updateDetails(id);
						break;
					default :
						System.out.println("Terminating session......."+"/n");
						break;
					}
				}while (choice!=6);
			}
			else {
				logger.error("Error during Customer log in ->Either Id does not exist 😒 or Password does not match");
				System.out.println("Either Id does not exist 😒 or Password does not match ☹ Try again\n");
				System.out.println("Press y to enter details again");
				System.out.println("Press n to exit");
				String choice= s.nextLine();
				switch(choice) {
				case "y": 
					login();
					break;
				default :
					System.out.println("Terminating session......."+"\n");
					break;
				}
				
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		
		System.out.print("Initital amount : ");
		int amount =s.nextInt();
		if (!validCustAmount(amount)) {
			System.out.println("Invalid value for amount ☹ Try again!!");
		}
		
		else {
		
			System.out.println("Your password is " +pass + " (Kindly Remember This as this will be required during login)\n");
			
			Statement st;
			try {
				st=DBConfigure.DBConnection().createStatement();	
				//**********to fetch previous key
				
				ResultSet rs= st.executeQuery("select * from customer");
				boolean f=rs.next();
				if (f==false) {
					st.executeUpdate("insert into customer values (1 , ' "+name+" ', ' "+phNo+" ', '"+pass+"',"+amount+")");
					st.executeUpdate("insert into account values ( 1 , "+amount+") ");
					System.out.println("And, your generated id is " +1 + " (Kindly Remember This as this will be required during login)");
					logger.info(name+" registered sucessfully");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				}
				
				else {
					int index=0;
					while(f) {
						index=rs.getInt("cust_id");
						f=rs.next();
					}
					index+=1;
					System.out.println("Your generated id is " +index + " (Kindly Remember This as this will be required during login)");
					st.executeUpdate("insert into customer values ( "+index+", ' "+name+" ', ' "+phNo+" ', '"+pass+"',"+amount+") ");
				
					st.executeUpdate("insert into account values ( "+index+" , "+amount+") ");
					logger.info(name+"registered sucessfully");
				}
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int choice;
			do {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|\n");
				System.out.println("-> To login type => 1\n");
				System.out.println("-> To exit type  => 2\n");
				choice= s.nextInt(); 
				
				switch (choice) {
				case 1:
					login();
					break;
			
				default :
					System.out.println("Terminating session......."+"\n");
					break;
				}
			} while(choice==1);
		}
		
	}

}
