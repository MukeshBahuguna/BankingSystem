package testBankingSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.revature.bankingsystem.Customer;
import com.revature.bankingsystem.Employee;

public class BankingSystemTest {

	@Test
	public static void validPhone() {
		//use database
		boolean x=Employee.validPhoneNo("3242342342");
		assertEquals(true, x);	
	}
	
	@Test
	public void validName() {
		boolean x=Employee.validNameC("ram");
		assertEquals(true, x);	
	}
	
	@Test
	public void validWithdrawal() {
		Customer c=new Customer();
		boolean x=c.validCustBalanceForWithdrawal(121, 100);
		assertEquals(true, x);	
	}
	
	@Test
	public void validAmount() {
		Customer c=new Customer();
		boolean x=c.validCustAmount(-23424);
		assertEquals(true, x);	
	}


}
