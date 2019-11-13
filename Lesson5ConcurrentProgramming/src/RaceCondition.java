import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RaceCondition {
	public static void main(String[] args) {

		Executor executor = Executors.newCachedThreadPool();
		for (int i = 1; i <= 100; i++) {

			int taskId = i;

			Runnable task = () -> {
				private static volatile int count = 0;
				for (int k = 1; k <= 1000; k++)

					count++;

				System.out.println(taskId + ": " + count);

			};

			executor.execute(task);

		}
	}
}
