package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    public WordleDictionary loadWordleDictionary(String fileName) throws IOException {

        List<String> words = readWordsFromFile(fileName);
        List<String> filtered = new ArrayList<>();

        for (String word : words) {
            word = word.trim().toLowerCase().replace("ё", "е");

            if (word.isBlank()) continue;

            if (word.length() == 5) {
                filtered.add(word);
            }
        }

        return new WordleDictionary(filtered);
    }



    private List<String> readWordsFromFile(String filename) throws IOException {

        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (line != null) {
                    words.add(line);
                }
            }
        }

        return words;
    }
}
