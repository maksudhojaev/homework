package Homewrok3;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class RandomWords {

    private static final String[] words = {"book", "pen", "ruler", "note", "laptop", "window"};

    public static void writeRandomWordsToFile(String fileName, int count) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                String word = words[random.nextInt(words.length)];
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removeWord(String fileName, String wordToRemove) {
        try {
            File file = new File(fileName);
            List<String> lines = Files.readAllLines(file.toPath());

            lines = lines.stream().filter(line -> !line.equals(wordToRemove)).collect(Collectors.toList());

            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void checkAndWriteToFile(String fileName) {
        File file = new File(fileName);
        if (file.length() == 0) {
            writeRandomWordsToFile(fileName, 1000);
            System.out.println("Fayl bo'sh edi, yangi 1000 ta so'z yozildi.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String fileName = "D:\\pdp\\homework\\src\\main\\java\\Homewrok3\\a.txt";


        checkAndWriteToFile(fileName);


        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Qaysi so'zni o'chirishni xohlaysiz?");
            System.out.println("Iltimos, quyidagi so'zlardan birini tanlang: book, pen, ruler, note, laptop, window");
            String wordToRemove = scanner.nextLine().trim().toLowerCase();


            if (Arrays.asList(words).contains(wordToRemove)) {
                removeWord(fileName, wordToRemove);
                System.out.println(wordToRemove + " so'zi fayldan o'chirildi.");
            } else {
                System.out.println("Noto'g'ri so'z tanlandi.");
            }

            checkAndWriteToFile(fileName);
        }
    }
}
