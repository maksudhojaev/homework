package homewrok1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SumCalculator {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final int START  = 1;
        final int END  = 1_000_000;

        long startTimeSingle = System.currentTimeMillis();
        long singleThreadSum = calculateSumSingleThread(START, END);
        long endTimeSingle = System.currentTimeMillis();

        long startTimeMulti = System.currentTimeMillis();
        long multiThreadSum = calculateSumWithThreads(START, END, 5);
        long endTimeMulti = System.currentTimeMillis();

        System.out.println("1ta thread bilan hisoblangan yig'indi: " + singleThreadSum);
        System.out.println("1ta thread bilan ishlash vaqti: " + (endTimeSingle - startTimeSingle) + " ms");

        System.out.println("Ko'p thread bilan hisoblangan yig'indi: " + multiThreadSum);
        System.out.println("Ko'p thread bilan ishlash vaqti: " + (endTimeMulti - startTimeMulti) + " ms");


        System.out.println("Ko'p thread ishlatish usuli tezroq bo'ldi: " + ((endTimeSingle - startTimeSingle) - (endTimeMulti - startTimeMulti)) + " ms");
    }

    private static long calculateSumSingleThread(int start, int end) {
        long sum = 0;
        for (int i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }

    private static long calculateSumWithThreads(int start, int end, int threadCount) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Long>> futures = new ArrayList<>();

        int range = (end - start + 1) / threadCount;
        for (int i = 0; i < threadCount; i++) {
            int rangeStart = start + i * range;
            int rangeEnd = (i == threadCount - 1) ? end : (rangeStart + range - 1);

            futures.add(executor.submit(() -> {
                long partialSum = 0;
                for (int j = rangeStart; j <= rangeEnd; j++) {
                    partialSum += j;
                }
                return partialSum;
            }));
        }

        long totalSum = 0;
        for (Future<Long> future : futures) {
            totalSum += future.get();
        }

        executor.shutdown();
        return totalSum;
    }
}

