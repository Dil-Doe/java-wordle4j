package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    private WordleDictionary createDictionary() {
        return new WordleDictionary(List.of(
                "лампа",
                "лодка",
                "масса",
                "лапка",
                "котик"
        ));
    }

    @Test
    void contains_shouldFindWord() {
        WordleDictionary dictionary = createDictionary();

        assertTrue(dictionary.contains("лампа"));
        assertFalse(dictionary.contains("арбуз"));
    }

    @Test
    void getRandomWord_shouldReturnWordFromDictionary() {
        WordleDictionary dictionary = createDictionary();

        String word = dictionary.getRandomWord();

        assertNotNull(word);
        assertTrue(dictionary.getWords().contains(word));
    }

    @Test
    void loadWordleDictionary_shouldLoadAndFilterWords() throws Exception {

        String fileName = "test_words.txt";

        try (java.io.FileWriter writer = new java.io.FileWriter(fileName)) {
            writer.write("лампа\n");
            writer.write("кот\n");
            writer.write("лодка\n");
        }

        WordleDictionaryLoader loader = new WordleDictionaryLoader();

        WordleDictionary dictionary = loader.loadWordleDictionary(fileName);

        List<String> words = dictionary.getWords();

        assertTrue(words.contains("лампа"));
        assertTrue(words.contains("лодка"));
        assertFalse(words.contains("кот"));
    }

    @Test
    void makeTurn_shouldReturnCorrectResult() {
        WordleDictionary dictionary = createDictionary();

        WordleGame game = new WordleGame(
                "лампа",
                new ArrayList<>(),
                dictionary,
                6
        );

        Move move = game.makeTurn("лапка");

        assertNotNull(move);
        assertEquals("лапка", move.getWord());
        assertEquals("++^-+", move.getResult());
    }

    @Test
    void isWin_shouldReturnTrueOnCorrectWord() {
        WordleDictionary dictionary = createDictionary();

        WordleGame game = new WordleGame(
                "лампа",
                new ArrayList<>(),
                dictionary,
                6
        );

        assertTrue(game.isWin("лампа"));
        assertFalse(game.isWin("лодка"));
    }

    @Test
    void steps_shouldDecreaseAfterMove() {
        WordleDictionary dictionary = createDictionary();

        WordleGame game = new WordleGame(
                "лампа",
                new ArrayList<>(),
                dictionary,
                6
        );

        game.makeTurn("котик");

        assertEquals(5, game.getSteps());
    }

    @Test
    void makeTurn_shouldThrowException_ifWordNotInDictionary() {
        WordleDictionary dictionary = createDictionary();

        WordleGame game = new WordleGame(
                "лампа",
                new ArrayList<>(),
                dictionary,
                6
        );

        assertThrows(
                WordNotFoundInDictionaryException.class,
                () -> game.makeTurn("арбуз")
        );
    }
}