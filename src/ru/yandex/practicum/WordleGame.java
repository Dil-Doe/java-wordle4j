package ru.yandex.practicum;

import java.util.List;

public class WordleGame {

    private final String answer;
    private final WordleDictionary dictionary;
    private final List<Move> history;
    private int steps;

    public WordleGame(String answer, List<Move> history, WordleDictionary dictionary, int steps) {
        this.answer = answer;
        this.history = history;
        this.dictionary = dictionary;
        this.steps = steps;
    }

    static class Move {
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

    private String normalize(String word) {
        return word.trim().toLowerCase().replace("ё", "е");
    }

    private String makeMove(String word) {

        char[] result = {'-', '-', '-', '-', '-'};
        boolean[] used = new boolean[5];

        for (int i = 0; i < word.length(); i++) {

            if (answer.charAt(i) == word.charAt(i)) {
                result[i] = '+';
                used[i] = true;
            }
        }

        for (int i = 0; i < word.length(); i++) {

            if (result[i] == '+') {
                continue;
            }

            char current = word.charAt(i);

            for (int j = 0; j < answer.length(); j++) {

                if (!used[j] && answer.charAt(j) == current) {
                    result[i] = '^';
                    used[j] = true;
                    break;
                }
            }
        }

        return new String(result);
    }

    public Move makeTurn(String word) {
        word = normalize(word);

        if (!dictionary.contains(word)) {
            throw new WordNotFoundInDictionaryException();
        }

        String result = makeMove(word);
        Move move = new Move(word, result);
        history.add(move);

        steps--;

        return move;
    }

    public boolean isWin(String word) {
        return answer.equals(normalize(word));
    }

    public int getSteps() {
        return steps;
    }

    public String getHint() {
        for (String hint : dictionary.getWords()) {

            if (isValidHint(hint)) {
                return hint;
            }
        }

        return "нет подсказки";
    }

    private boolean isValidHint(String hint) {

        for (Move move : history) {

            String word = move.getWord();
            String result = move.getResult();

            for (int i = 0; i < word.length(); i++) {
                char w = word.charAt(i);
                char r = result.charAt(i);
                boolean letterExists = false;

                for (int j = 0; j < result.length(); j++) {
                    char check = result.charAt(j);

                    if (check == '+' || check == '^') {
                        if (word.charAt(j) == w) {
                            letterExists = true;
                            break;
                        }
                    }
                }

                if (r == '+') {
                    if (hint.charAt(i) != w) {
                        return false;
                    }
                }

                if (r == '-' && !letterExists) {
                    if (hint.contains(String.valueOf(w))) {
                        return false;
                    }
                }

                if (r == '^') {
                    if (hint.charAt(i) == w ||
                            !hint.contains(String.valueOf(w))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
