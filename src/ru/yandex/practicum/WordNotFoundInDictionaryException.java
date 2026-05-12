package ru.yandex.practicum;

public class WordNotFoundInDictionaryException extends RuntimeException {
    public WordNotFoundInDictionaryException() {
        super("Слово отсутствует в словаре.");
    }
}
