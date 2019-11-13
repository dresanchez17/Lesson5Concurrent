import java.util.concurrent.*;
/**
 * Example of concurrent programming using ExecutorService
 * @author asanchez
 *
 */

public class DemoSE9_10_1_1b {
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
		
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(hello);
		executor.execute(goodbye);
		
		// All Done will print out before the above code b\e we didnt sync up
		// with main thread.
		System.out.println("All Done\n");
	}
}
