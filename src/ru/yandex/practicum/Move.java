package ru.yandex.practicum;

public class Move {
    private final String word;
    private final String result;

    public Move(String word, String result) {
        this.word = word;
        this.result = result;
    }

    public String getWord() {
        return word;
    }

    public String getResult() {
        return result;
    }
}
