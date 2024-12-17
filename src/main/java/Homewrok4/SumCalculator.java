package Homewrok4;

import java.io.*;
import java.util.concurrent.*;

public class SumCalculator {


    private static final int[] ranges = {0, 1000000, 2000000, 3000000};


    public static void writeSumToFile(String fileName, int start, int end) {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += i;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Sum from " + start + " to " + (end - 1) + ": " + sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(3);


        executor.submit(() -> writeSumToFile("D:\\pdp\\homework\\src\\main\\java\\Homewrok4\\a1.txt", ranges[0], ranges[1]));
        executor.submit(() -> writeSumToFile("D:\\pdp\\homework\\src\\main\\java\\Homewrok4\\a2.txt", ranges[1], ranges[2]));
        executor.submit(() -> writeSumToFile("D:\\pdp\\homework\\src\\main\\java\\Homewrok4\\a3.txt", ranges[2], ranges[3]));


        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);


        long totalSum = 0;
        for (int i = 1; i <= 3; i++) {
            String fileName = "D:\\pdp\\homework\\src\\main\\java\\Homewrok4\\a" + i + ".txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line = reader.readLine();

                String[] parts = line.split(": ");
                totalSum += Long.parseLong(parts[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Yig'indini ekranga chiqarish
        System.out.println("Total sum of all ranges: " + totalSum);
    }
}
