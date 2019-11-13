import java.io.*;
import java.util.*;
import java.nio.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lesson5Concurrent2 {
	public static void main (String[] args) {
		String myFile = "bin/Lesson5Concurrent2";
		int numThreads = 4;

		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0 ; i < numThreads; i++) {
			CharacterCountAtomic task = new CharacterCountAtomic(myFile);
			executor.execute(task);
		}
	}
}

class CharacterCounter implements Runnable  {
	public static boolean done = false;
	public static long count = 0;
	String inputFile;

	public CharacterCounter(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * This is being implemented without any locks. Default
	 * When multiple threads execute, count gets incremented by each thread.
	 */
	@Override
	public void run() {
		String data;

		try {
			File file = new File(inputFile);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader input = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(input);

			while ((data = reader.readLine()) != null) {

				count += data.length();

			}
			System.out.println(count);

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long getCount() {
		return count;
	}
}

class CharacterCountLock implements Runnable {
	public static long count = 0;
	public static volatile boolean done = false;
	String inputFile;

	public CharacterCountLock(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * I think this is currently locking correctly. But each thread continues to count the whole file.
	 * Will need to implement the boolean (?) 
	 */

	@Override
	public void run() {
		String data;

		try {
			File file = new File(inputFile);
			Scanner scan = new Scanner(file);


			//FileInputStream fis = new FileInputStream(file);
			//InputStreamReader input = new InputStreamReader(fis);
			//BufferedReader reader = new BufferedReader(input);
			Lock countLock = new ReentrantLock();


			while ((data = scan.nextLine()) != null && !done) {

				countLock.lock();
				try {

					count += data.length();

				}
				finally {
					countLock.unlock();
					if (scan.hasNext() == false) {
						done = true;
					}
				}
			}
			System.out.println(count);

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long getCount() {
		return count;
	}
}

class CharacterCountAtomic implements Runnable {
	public static AtomicLong count = new AtomicLong();
	String inputFile;

	public CharacterCountAtomic(String inputFile){
		this.inputFile = inputFile;
	}

	@Override
	public void run() {
		String data;

		try {
			File file = new File(inputFile);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader input = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(input);

			while ((data = reader.readLine()) != null) {

				count.addAndGet(data.length());

			}
			System.out.println(count);

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AtomicLong getCount() {
		return count;
	}
}





















