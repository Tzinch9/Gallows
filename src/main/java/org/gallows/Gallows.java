package org.gallows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Gallows {
    private List<String> words;
    private String randomWord;
    private String maskWord;
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private final List<String> listOfErrors = new ArrayList<>();

    private void game(){

        while (true){
            try {
                System.out.println();
                System.out.println("""
                        Введите число:
                        1 - Начать игру.
                        2 - Выход из игры.
                        """);
                int choice = scanner.nextInt();
                if (choice == 1){
                    System.out.println("Отлично, давай начнём!");
                    scanner.nextLine();
                    startGame();
                } else if (choice == 2){
                    System.out.println();
                    System.out.println("До скорой встречи!");
                    break;
                } else {
                    System.out.println("Выберите один из двух вариантов.");
                    scanner.nextLine();
                }
            } catch (Exception e){
                System.out.println("Вы вводите что-то другое. Необходимо вводить именно числа.");
                scanner.nextLine();
            }
        }
    }

    public void readingFromFile(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Path filePath = Paths.get(Objects.requireNonNull(classLoader.getResource("words.txt")).toURI());
            words = new ArrayList<>(Files.readAllLines(filePath));
        } catch (IOException | java.net.URISyntaxException e) {
            System.out.println("Error reading file " + e.getMessage());
            words = new ArrayList<>();
        }
    }

    public void initRandomWord(){
        randomWord =  words.get(random.nextInt(words.size() - 1));
    }

    private void maskOn(){
        maskWord = randomWord.replaceAll("[а-яА-Я]", "*");
    }

    private void startGame(){

        readingFromFile();
        initRandomWord();
        maskOn();
        int attempts = 6;

        while (true){
            System.out.println();
            System.out.println("Введите букву:");
            String letter = scanner.nextLine().toLowerCase();

            System.out.println();
            System.out.println("Количество оставшихся попыток: " + attempts);
            showGallows(attempts);

            if (letter.length() != 1){
                System.out.println();
                System.out.println("Необходимо вводить одну букву.");
                continue;
            }

            if (!isRussianLetter(letter.charAt(0))) {
                System.out.println();
                System.out.println("Вводите буквы на русском языке.");
                continue;
            }

            System.out.print("Загаданное слово: ");
            getTheWordAfterGuessingLetter(letter);


            if (listOfErrors.contains(letter)){
                System.out.println();
                System.out.println("Вы уже вводили эту букву.Введите другую");
                continue;
            }

            if (randomWord.contains(letter)){
                listOfErrors.add(letter);
                //"Успех! Буква \"" + letter + "\" присутствует в загаданном слове!"
                System.out.println();
                System.out.println("Успех! Буква \"" + letter + "\" присутствует в загаданном слове!");

            } else {
                listOfErrors.add(letter);
                attempts--;
                System.out.println();
                System.out.println("К сожалению буква \"" + letter + "\" в загаданном слове отсутствует.");
            }

            if (attempts == 0){
                System.out.println();
                System.out.println("Вас повесили, загаданным словом было: \"" + randomWord + "\"");
                System.out.println("Начнём новую игру?");
                break;

            }
            if (isWin()){
                System.out.println();
                System.out.println("Поздравляю! Вы угадали слово!");
                System.out.println("Начнём новую игру?");
                break;
            }
        }
    }

    private boolean isWin(){
        return maskWord.equals(randomWord);
    }

    private boolean isRussianLetter(char ch) {
        return String.valueOf(ch).matches("[А-Яа-яЁё]");
    }

    private void getTheWordAfterGuessingLetter(String letter){

        char[] wordArray = randomWord.toCharArray();
        char[] maskArray = maskWord.toCharArray();

        char charLetter = letter.charAt(0);

        for (int i = 0; i < wordArray.length; i++){
            if (wordArray[i] == charLetter){
                maskArray[i] = charLetter;
            }
        }
        maskWord = new String(maskArray);
        System.out.println(maskWord);
    }


    private void showGallows(int attempts){
        switch (attempts){
            case 1: System.out.println("""
                          ████████████████
                          ████████████████
                          ██          ████
                          ███         ████
                        ██   █        ████
                        ██   █        ████
                          ███         ████
                    ███    █   ███    ████
                         ████         ████
                           █          ████
                           █          ████
                           █          ████
                           █          ████
                          ███         ████
                        █    ██       ████
                      █        ██     ████
                    ██           █    ████
                                      ████
                                      ████
                       █████████████████████████
                       █████████████████████████
                    """);
                break;
            case 2: System.out.println("""
                         ████████████████
                         ████████████████
                         ██          ████
                         ███         ████
                       ██   █        ████
                       ██   █        ████
                         ███         ████
                   ███    █   ███    ████
                        ████         ████
                          █          ████
                          █          ████
                          █          ████
                          █          ████
                         █           ████
                       █             ████
                     █               ████
                   ██                ████
                                     ████
                                     ████
                      █████████████████████████
                      █████████████████████████
                   """);
                break;
            case 3: System.out.println("""
                          ████████████████
                          ████████████████
                          ██          ████
                          ███         ████
                        ██   █        ████
                        ██   █        ████
                          ███         ████
                    ███    █   ███    ████
                         ████         ████
                           █          ████
                           █          ████
                           █          ████
                           █          ████
                                      ████
                                      ████
                                      ████
                                      ████
                                      ████
                                      ████
                       █████████████████████████
                       █████████████████████████
               
                    """);
                break;
            case 4: System.out.println("""
                         ████████████████
                         ████████████████
                         ██          ████
                         ███         ████
                       ██   █        ████
                       ██   █        ████
                         ███         ████
                   ███    █          ████
                        ███          ████
                          █          ████
                          █          ████
                          █          ████
                          █          ████
                                     ████
                                     ████
                                     ████
                                     ████
                                     ████
                                     ████
                      █████████████████████████
                      █████████████████████████
                   """);
                break;
            case 5: System.out.println("""
                       ████████████████
                       ████████████████
                       ██          ████
                       ███         ████
                     ██   █        ████
                     ██   █        ████
                       ███         ████
                        █          ████
                        █          ████
                        █          ████
                        █          ████
                        █          ████
                        █          ████
                                   ████
                                   ████
                                   ████
                                   ████
                                   ████
                                   ████
                    █████████████████████████
                    █████████████████████████
                    """);
                break;
            case 6: System.out.println("""
                      ████████████████
                      ████████████████
                      ██          ████
                      ███         ████
                    ██   █        ████
                    ██   █        ████
                      ███         ████
                                  ████
                                  ████
                                  ████
                                  ████
                                  ████
                                  ████
                                  ████
                                  ████
                                  ████
                                  ████
                                  ████
                                  ████
                   █████████████████████████
                   █████████████████████████
                   """);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        Gallows gallows = new Gallows();
        gallows.game();
    }
}