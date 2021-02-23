import java.util.Scanner;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        Scanner file1 = new Scanner(new File("words.txt"));
        Scanner file2 = new Scanner(new File("words_english.txt"));
        Scanner file3 = new Scanner(new File("words_alpha.txt"));

        Map<String, String> words = new HashMap<>();

        while (file1.hasNext()) {
            String word = file1.next();
            words.putIfAbsent(word, word);
        }

        while (file2.hasNext()) {
            String word = file2.next();
            words.putIfAbsent(word, word);
        }

        while (file3.hasNext()) {
            String word = file3.next();
            words.putIfAbsent(word, word);
        }

        // Threads
        final int numThreads = 50000;
        final int numThreadLoops = 100000;
        List<Thread> threads = new ArrayList<>();

        Runnable prog = () -> {
            List<Integer> l = new ArrayList<>();
            for (int i = 0; i < numThreadLoops; i++)
                l.add(i);
        };

        for (int i = 0; i < numThreads; i++) {
            threads.add(new Thread(prog));
        }

        for (Thread t : threads)
            t.start();

        for (Thread t : threads) {
            try {
                t.join();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        System.out.println(words.size());

        long end = System.currentTimeMillis();

        System.out.println("Time elapsed: " + (end - start) + "ms");
    }
}