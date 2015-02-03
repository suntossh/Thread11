package Demo;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Processor {

	private Account account1 = new Account();
	private Account account2 = new Account();
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	private void getBothLocks(Lock lockOne, Lock lockTwo) throws InterruptedException{
		boolean gotLockOne = false;
		boolean gotLockTwo = false;
		while(true){
			
			try {
				gotLockOne = lockOne.tryLock();
				gotLockTwo = lockTwo.tryLock();
			} finally {
				if(gotLockOne && gotLockTwo){
					return;
				}
				if(gotLockOne ){
					lockOne.unlock();
				}
				if(gotLockTwo){
					lockTwo.unlock();
				}
				Thread.sleep(1);
			}   
		}
		
		
	}
	
	public void threadOne() throws InterruptedException {
		Random random = new Random();
		
		for (int i = 0; i < 10000; i++) {
			getBothLocks(lock1,lock2);
			try{
				account1.transfer(account1, account2, random.nextInt(100));
			}finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void threadTwo() throws InterruptedException {

		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			getBothLocks(lock2,lock1);
			try{
				account1.transfer(account2, account1, random.nextInt(100));
			}finally{
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void finished() {
		System.out.println("Account 1 Balance" + account1.getBalance());
		System.out.println("Account 2 Balance" + account2.getBalance());
		System.out.println("Total balance :"
				+ (account1.getBalance() + account2.getBalance()));
	}
}
