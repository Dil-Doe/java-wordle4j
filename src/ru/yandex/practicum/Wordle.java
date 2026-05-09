package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        WordleDictionaryLoader loader = new WordleDictionaryLoader();

        try (FileWriter log = new FileWriter("log.txt");
             Scanner scanner = new Scanner(System.in)) {

            WordleDictionary dictionary = loader.loadWordleDictionary("words_ru.txt");

            String answer = dictionary.getRandomWord()
                    .trim()
                    .toLowerCase()
                    .replace("ё", "е");

            WordleGame game = new WordleGame(
                    answer,
                    new java.util.ArrayList<>(),
                    dictionary,
                    6
            );

            log.write("Игра началась\n");

            while (game.getSteps() > 0) {

                System.out.println("Введите слово (Enter = подсказка):");
                String input = scanner.nextLine();

                if (input.isEmpty()) {
                    String hint = game.getHint();
                    System.out.println("Подсказка: " + hint);
                    log.write("ПОДСКАЗКА: " + hint + "\n");
                    continue;
                }

                try {
                    WordleGame.Move move = game.makeTurn(input);

                    System.out.println(move.getResult());
                    log.write(move.getWord() + " -> " + move.getResult() + "\n");

                    if (game.isWin(input)) {
                        System.out.println("Вы победили!");
                        log.write("ПОБЕДА\n");
                        return;
                    }

                } catch (WordNotFoundInDictionaryException e) {
                    System.out.println("Слово отсутствует в словаре!");
                    log.write("ОШИБКА: слово не найдено\n");
                } catch (IllegalArgumentException e) {
                    System.out.println("Слово должно содержать 5 букв!");
                    log.write("ОШИБКА: неверная длина слова\n");
                }
            }

            System.out.println("Вы проиграли! Слово: " + answer);
            log.write("ПОРАЖЕНИЕ: " + answer + "\n");

        } catch (IOException e) {
            System.out.println("Ошибка работы с логом или словарём");
        }
    }
}