/**
 * Example of concurrent programming using Thread class
 * @author asanchez
 *
 */
public class DemoSE9_10_1_1a {
	public static void main(String[] args) throws InterruptedException {
		
		Runnable hello = () -> { 
			for(int i = 0; i < 100; i++) {
				System.out.println("Hello " + i);
			}
		};
		
		Runnable goodbye = () -> {
			for (int i = 0; i < 100; i++){
				System.out.println("Goodbye " + i);
			}
		};
		
		Thread thread_hello = new Thread(hello);
		Thread thread_goodbye = new Thread(goodbye);
		
		thread_hello.start();
		thread_goodbye.start();
		// saying the previous code should not take more then 5 seconds
		int millis = 1000 * 1 * 5; // 5 seconds
		// main thread will sleep until below 2 threads finish.
		thread_hello.join(millis);
		thread_goodbye.join(millis);
		
		System.out.println("All Done\n");
	}
}
