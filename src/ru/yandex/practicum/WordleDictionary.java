package ru.yandex.practicum;

import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private final List<String> words;
    private final Random random = new Random();

    public WordleDictionary(List<String> filtered) {
        this.words = filtered;
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    public List<String> getWords() {
        return words;
    }
}
