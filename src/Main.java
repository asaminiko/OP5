import org.apache.lucene.morphology.uk.UkrainianMorphology;
import java.io.*;
import java.util.*;

public class Main {
    public static List<String> commonestWords(String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Файл не може бути null або порожнім");
        }

        UkrainianMorphology morphology = new UkrainianMorphology();
        Map<String, Integer> wordCount = new HashMap<>();
        int maxCount = 0;

        try (InputStream inputStream = new FileInputStream(filename);
             Reader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.replaceAll("[^a-zA-Zа-яА-ЯёЁіІїЇєЄ']", " ").split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        List<String> baseForms = morphology.getNormalForms(word);
                        String lemma = baseForms.isEmpty() ? word : baseForms.get(0);
                        wordCount.put(lemma, wordCount.getOrDefault(lemma, 0) + 1);
                        maxCount = Math.max(maxCount, wordCount.get(lemma));
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
        String filename = "C:\\Users\\User\\Desktop\\OP5_main\\OP5_main\\src\\myText.txt";

        try {
            List<String> result = commonestWords(filename);
            System.out.println("Найчастіше вживані слова: " + result);
        } catch (FileNotFoundException e) {
            System.err.println("Файл не знайдено!");
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }
}
