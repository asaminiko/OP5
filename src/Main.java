import java.io.*;
import java.util.*;

public class Main {
    public static List<String> commonestWords(String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Файл не може бути null або порожнім");
        }

        Map<String, Integer> wordCount = new HashMap<>();
        int maxCount = 0;

        try (InputStream inputStream = new FileInputStream(filename);
             Reader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.replaceAll("[^a-zA-Zа-яА-ЯіІїЇєЄґҐ']", " ").split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                        maxCount = Math.max(maxCount, wordCount.get(word));
                    }
                }
            }
        }

        if (wordCount.isEmpty()) {
            throw new IOException("Файл порожній або не містить слів.");
        }

        List<String> commonWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() == maxCount) {
                commonWords.add(entry.getKey());
            }
        }
        return commonWords;
    }

    public static void main(String[] args) {
        if (args.length == 0 || args[0].isEmpty()) {
            throw new IllegalArgumentException("Помилка. Треба передати шлях до файлу через аргумент");
        }

        try {
            String filename = args[0];
            List<String> result = commonestWords(filename);
            System.out.println("Найчастіше вживані слова: " + result);
        } catch (FileNotFoundException e) {
            System.err.println("Файл не знайдено!");
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }
}
