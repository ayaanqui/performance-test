import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {

    private static final long V = 1_000_000_000 * 40;

    private static void threadPoolEx(final int N, ExecutorService exec) {
        Future<Boolean>[] f = new Future[N];

        // Run N tasks
        for (int i = 0; i < N; i++) {
            System.out.print('.');
            f[i] = exec.submit(() -> {
                for (long x = 0; x < V; x++)
                    ;
                return true;
            });
        }

        for (int i = 0; i < N; i++) {
            try {
                f[i].get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private static void threadEx(final int N) {
        Thread[] threads = new Thread[N];

        for (int i = 0; i < N; i++) {
            System.out.print('.');
            threads[i] = new Thread(() -> {
                for (long x = 0; x < V; x++)
                    ;
            });
            threads[i].start();
        }

        for (int i = 0; i < N; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sequentialEx(final int N) {
        // Run N tasks
        for (int i = 0; i < N; i++) {
            System.out.print('.');
            // takes 1h of computation
            for (long x = 0; x < V; x++)
                ;
        }
    }

    private static void printTime(final long timeElapsed) {
        System.out.println(String.format("\n%dms\n", timeElapsed));
    }

    public static void main(String[] args) {
        final int N = 40;
        // ExecutorService exec = Executors.newFixedThreadPool(N);
        ExecutorService exec = Executors.newCachedThreadPool();
        long startTime, time;

        
        startTime = System.currentTimeMillis();
        threadPoolEx(N, exec);
        time = System.currentTimeMillis() - startTime;
        printTime(time);
        System.out.println("Done.\n\n");

        exec.shutdownNow();

        startTime = System.currentTimeMillis();
        threadEx(N);
        time = System.currentTimeMillis() - startTime;
        printTime(time);
        System.out.println("Done.\n\n");


        startTime = System.currentTimeMillis();
        sequentialEx(N);
        time = System.currentTimeMillis() - startTime;
        printTime(time);
        System.out.println("Done.");
    }
}
