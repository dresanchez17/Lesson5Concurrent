import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lesson5Concurrent {
	public static long count = 0;
	public static AtomicLong countAtomic = new AtomicLong();

	public static void main(String[] args) throws Exception {
		Queue<String> data = new ConcurrentLinkedQueue<>();
		int num_threads = 2;
		String file = "bin" + File.separator + "Lesson5Concurrent.class";
		String fileJava = "src" + File.separator + "Lesson5Concurrent.java";
		
		
		
		//Thread to populate queue with data from bin file
		//Need to fix the type of encoding here.
		new Thread() {
			
			public void run() {
				try {
					String line;
					BufferedReader reader = new BufferedReader(new InputStreamReader( new FileInputStream( new File(file))));
					while ((line = reader.readLine()) != null) {
						data.add(line);
					}
				}
				catch(FileNotFoundException e) {
					e.printStackTrace();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		new Thread() {
			
			public void run() {
				try {
					String line;
					BufferedReader reader = new BufferedReader(new InputStreamReader( new FileInputStream( new File(fileJava))));
					while ((line = reader.readLine()) != null) {
						data.add(line);
					}
				}
				catch(FileNotFoundException e) {
					e.printStackTrace();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		//Wait for lines to enter the queue.
		while(data.isEmpty()) {
			Thread.sleep(10);
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(num_threads);
		
		for (int i = 0; i < num_threads; i++) {
			executor.execute(new CharCounterLock(data));
		}
		
		executor.shutdown();
		executor.awaitTermination(1	, TimeUnit.MINUTES);
		
		System.out.println(count);

	}




	public static class CharCounter implements Runnable {
		Queue<String> data;

		public CharCounter (Queue<String> data) {
			this.data = data;
		}

		@Override
		public void run() {
			String line;

			while(!data.isEmpty()) {
				line = data.poll();
				if (line != null) {
					count += line.length();
				}
			}
		}
	}
	
	public static class CharCounterAtomic implements Runnable {
		Queue<String> data;
		
		public CharCounterAtomic (Queue<String> data) {
			this.data = data;
		}
		
		@Override
		public void run() {
			String line;
			while(!data.isEmpty()) {
				line = data.poll();
				if (line != null) {
					count = countAtomic.addAndGet(line.length());
				}
			}	
		}
	}
	
	public static class CharCounterLock implements Runnable {
		Queue<String> data;
		Lock countLock = new ReentrantLock();
		
		public CharCounterLock (Queue<String> data) {
			this.data = data;
		}

		@Override
		public void run() {
			String line;
			while (!data.isEmpty()) {
				line = data.poll();
				if (line != null) {
					countLock.lock();
					try {
						count += line.length();
					}
					finally {
						countLock.unlock();
					}
				}
			}
			
		}
	}
}







