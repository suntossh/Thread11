package Demo;

public class Account {

	private int balance = 10000;
	
	public void withdraw(int withdrawAmt){
		balance = balance - withdrawAmt;
	}
	
	public void deposit(int depositAmt){
		balance = balance + depositAmt;
	}
	
	
	public void transfer (Account acct1, Account acct2, int Amt){
		acct1.withdraw(Amt);
		acct2.deposit(Amt);
	}

	public int getBalance(){
		return balance;
	}
	
}
