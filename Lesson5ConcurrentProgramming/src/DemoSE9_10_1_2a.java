import java.util.concurrent.*;

class hello implements Callable {
	public String call() {
		for(int i = 1; i < 100; i++) {
			System.out.println("Hello" + i);
		}
		
		return "Hello Done!";
	}
}

class goodbye implements Callable {
	public String call() {
		for (int i = 1; i < 100; i++)  {
			System.out.println("Goodbye" + i);
	}
	
	return "Goodbye Done!";
	}
}

public class DemoSE9_10_1_2a {
	public static void main (String[] args) throws InterruptedException, 
	ExecutionException {
		int num_processors = Runtime.getRuntime().availableProcessors();
		System.out.println("num_processors: " + num_processors);
		
		ExecutorService executor = Executors.newFixedThreadPool(num_processors);
		// Future represents a value we want in the future
		Future<String> result1 = executor.submit(new hello());
		Future<String> result2 = executor.submit(new goodbye());
		
		// get() gets whatever is being returned.
		// blocks right here until we get result1   
		System.out.println("result1: " + result1.get());
		System.out.println("result2: " + result2.get());
		
		System.out.println("All Done\n");
	}
}
