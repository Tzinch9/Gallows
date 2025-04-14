package org.gallows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Gallows {

    private static final int START = 1;
    private static final int QUIT = 2;
    private static final int MAX_ATTEMPTS = 6;
    private static final int MIN_ATTEMPTS = 0;
    private static String MASK_SYMBOL = "*";
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private final List<String> errors = new ArrayList<>();
    private final List<String> duplicates = new ArrayList<>();
    private List<String> words;
    private String randomWord;
    private String maskWord;
    private int attempts;


    public void start() {

        while (true) {

            System.out.println();
            System.out.println("""
                    Введите число:
                    1 - Начать игру
                    2 - Выход из игры
                    """);
            String input = scanner.nextLine().trim();

            if(isNumber(input)) {
                int choice = Integer.parseInt(input);
                if (choice == START) {
                    errors.clear();
                    duplicates.clear();
                    System.out.println("Отлично, давай начнём!");
                    startGame();
                }
                if (choice == QUIT) {
                    System.out.println();
                    System.out.println("До скорой встречи!");
                    break;
                }
            } else {
                System.out.println("Вы вводите что-то другое. Необходимо вводить именно числа.");
            }
        }
    }

    private boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void readFromFile() {
        words = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("words.txt")) {
            if (inputStream == null) {
                System.out.println("Файл не найден.");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    words.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private void initRandomWord() {
        randomWord = words.get(random.nextInt(words.size()));
    }

    private void createMaskWord() {
        if (randomWord == null) {
            throw new IllegalStateException("The word is not initialized");
        }
        maskWord = MASK_SYMBOL.repeat(randomWord.length());
    }

    private void beforeTheStart(){
        reloadATTEMPTS();
        readFromFile();
        initRandomWord();
        createMaskWord();
        errors.clear();
        duplicates.clear();
    }

    private void startGame() {

            beforeTheStart();
            while (!isGameOver()) {
                processGuess();
                if (isLose()) {
                    printMessageLose();
                    break;
                }
                if (isWin()) {
                    printMessageWin();
                    break;
                }
            }
    }

    private void processGuess() {
        System.out.printf("Введите букву: %n");
        String letter = scanner.nextLine().toLowerCase();
        if (letter.length() != 1) {
            System.out.println("Необходимо вводить одну букву.");
            return;
        }
        if (!isRussianLetter(letter.charAt(0))) {
            System.out.println("Вводите буквы на русском языке.");
            return;
        }
        if (errors.contains(letter) || duplicates.contains(letter)) {
            System.out.println("Вы уже вводили эту букву. Введите другую.");
            return;
        }
        if (isSuccessLetterInput(letter)) {
            handlingSuccessfulLetterInput(letter);
        } else {
            handlingUnsuccessfulLetterInput(letter);
        }
        System.out.printf("Загаданное слово: \"%s\" %n", maskWord);
        System.out.printf("Количество оставшихся попыток: %s %n", attempts);
        showErrors();
    }

    private void showErrors() {
        System.out.println();
        if (errors.isEmpty()) {
            System.out.println("Список неправильно введённых букв пуст.");
        } else {
            System.out.printf("Список неправильно введённых букв: %s%n", String.join(", ", errors));
        }
    }

    private boolean isSuccessLetterInput(String letter){
        return randomWord.contains(letter);
    }

    private void handlingSuccessfulLetterInput(String letter){
        duplicates.add(letter.toLowerCase());
        revealLetterInWord(letter);
        System.out.println();
        System.out.printf("Успех! Буква '%s' присутствует в загаданном слове. %n", letter);
    }
    private void handlingUnsuccessfulLetterInput(String letter){
        errors.add(letter.toLowerCase());
        attempts--;
        revealLetterInWord(letter);
        System.out.println();
        System.out.printf("К сожалению буква '%s' в загаданном слове отсутствует. %n", letter);
        Pictures.printPicture(attempts);
    }

    private void printMessageWin(){
        System.out.println();
        System.out.printf("Поздравляю! Вы угадали слово: \"%s\"! %n",randomWord);
        System.out.println("Начнём новую игру?");
    }

    private void printMessageLose(){
        System.out.println();
        System.out.printf("Вас повесили, загаданным словом было: \"%s\" %n", randomWord);
        System.out.println("Начнём новую игру?");
    }

    private boolean isGameOver(){
        return isWin() || isLose();
    }

    private boolean isWin() {
        return maskWord.equals(randomWord);
    }

    private boolean isLose() {
        return attempts == MIN_ATTEMPTS;
    }

    private void reloadATTEMPTS() {
        attempts = MAX_ATTEMPTS;

    }

    private boolean isRussianLetter(char ch) {
        return String.valueOf(ch).matches("[А-Яа-яЁё]");
    }

    private void revealLetterInWord(String letter) {

        char[] wordArray = randomWord.toCharArray();
        char[] maskArray = maskWord.toCharArray();

        char charLetter = letter.charAt(0);

        for (int i = 0; i < wordArray.length; i++) {
            if (wordArray[i] == charLetter) {
                maskArray[i] = charLetter;
            }
        }
        maskWord = new String(maskArray);
    }

    static class Pictures {
        private static final String[][] PICTURES = {
                {
"    █████████████     ",
"    █         ███     ",
"   ████       ███     ",
"  ██  █       ███     ",
"   ████       ███     ",
"███████████   ███     ",
"    ██        ███     ",
"    ██        ███     ",
"    ██        ███     ",
"    ██        ███     ",
"   ███        ███     ",
"  █    █      ███     ",
"█        █    ███     ",
"              ███     ",
"              ███     ",
"  ████████████████████"
},
                {
"    █████████████     ",
"    █         ███     ",
"   ████       ███     ",
"  ██  █       ███     ",
"   ████       ███     ",
"███████████   ███     ",
"    ██        ███     ",
"    ██        ███     ",
"    ██        ███     ",
"    ██        ███     ",
"   ██         ███     ",
"  █           ███     ",
"█             ███     ",
"              ███     ",
"              ███     ",
"  ████████████████████"
},
                {
"    █████████████     ",
"    █         ███     ",
"   ████       ███     ",
"  ██  █       ███     ",
"   ████       ███     ",
"███████████   ███     ",
"    ██        ███     ",
"    ██        ███     ",
"    ██        ███     ",
"    ██        ███     ",
"              ███     ",
"              ███     ",
"              ███     ",
"              ███     ",
"              ███     ",
"  ████████████████████"
},
                {
"    █████████████     ",
"    █         ███     ",
"   ████       ███     ",
"  ██  █       ███     ",
"   ████       ███     ",
"█████         ███     ",
"    ██        ███     ",
"    ██        ███     ",
"    ██        ███     ",
"    ██        ███     ",
"              ███     ",
"              ███     ",
"              ███     ",
"              ███     ",
"              ███     ",
"  ████████████████████"
},
                {
"  █████████████     ",
"  █         ███     ",
" ████       ███     ",
"██  █       ███     ",
" ████       ███     ",
"  ██        ███     ",
"  ██        ███     ",
"  ██        ███     ",
"  ██        ███     ",
"  ██        ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"████████████████████"
},
                {
"  █████████████     ",
"  █         ███     ",
" ████       ███     ",
"██  █       ███     ",
" ████       ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"            ███     ",
"████████████████████"
}, {""}};

        private static void printPicture(int numPicture) {
            String[] picture = PICTURES[numPicture];

            for (String line : picture) {
                System.out.println(line);
            }
        }
    }
}