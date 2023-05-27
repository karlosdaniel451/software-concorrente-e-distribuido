import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;

public class App {

    public static void main(String[] args) {
        Runnable task1 = () -> {
            System.out.println("Simulating the execution of a concurrent task...");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e);
            }

            System.out.println("Concurrent task finnished...");
        };

        Runnable task2 = () -> {
            System.out.println("Simulating the execution of a concurrent task...");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println(e);
            }

            System.out.println("Concurrent task finnished...");
        };

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(task1);
        singleThreadExecutor.execute(task2);
        singleThreadExecutor.shutdown();
        try {
            singleThreadExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        System.out.println("Shutdown single thread executor (Previous task was executed).");


        ExecutorService cachedExecutor = Executors.newCachedThreadPool();
        cachedExecutor.execute(task1);
        cachedExecutor.execute(task2);
        cachedExecutor.shutdown();
        try {
            cachedExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        System.out.println("Shutdown cached multithread executor (Previous task was executed).");

        System.out.println("\n\n");

        Callable<Integer> callableTask = () -> {
            System.out.println("Starting CPU bound task...");
            int sum = 0;
            for (int i = 0; i < 100000; i++) {
                sum += i;
            }
            System.out.println("Finished CPU bound task.");
            return sum;
        };

        ExecutorService newCachedExecutor = Executors.newCachedThreadPool();
        Future<Integer> result1 = newCachedExecutor.submit(callableTask);
        Future<Integer> result2 = newCachedExecutor.submit(callableTask);
        Future<Integer> result3 = newCachedExecutor.submit(callableTask);
        Future<Integer> result4 = newCachedExecutor.submit(callableTask);

        try {
            System.out.println(result1.get());
            System.out.println(result2.get());
            System.out.println(result3.get());
            System.out.println(result4.get());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println(e);
        }

        newCachedExecutor.shutdown();
    }
}
