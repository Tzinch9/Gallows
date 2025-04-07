package org.gallows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Gallows {

private String randomWord;
private Scanner scanner = new Scanner(System.in);
private boolean isRun = true;
private Random random = new Random();
private List<String> words = new ArrayList<>();

    // инструкция перед игрой - gameInfo

    // Хотите начать новую игру? - startGame
    // да -> старт
    // нет -> выход

    private void readingFromFile(){

        try {
            words = new ArrayList<>(Files.readAllLines(Paths.get("/home/dmitry/IdeaProjects/Gallows/src/main/resources/words.txt")));
        } catch (IOException e){
            System.out.println("Error reading file" + e.getMessage());
            words = new ArrayList<>();
        }
    }

    private String generatedRandomWord(){
        readingFromFile();
        randomWord = words.get(random.nextInt(words.size() - 1));
        return randomWord;
    }

    public void startGame(){

        while (isRun){
            try {
                System.out.println("Хотите начать новую игру?");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1){
                    System.out.println("START");
                    isRun = false;
                } else if (option == 2) {
                    System.out.println("EXIT");
                    isRun = false;
                } else {
                    System.out.println("Неверный ввод");
                    startGame();
                }
            }catch (Exception e){
                System.out.println("""
                        0========================0
                        |НЕОБХОДИМО ВВОДИТЬ ЦИФРЫ|
                        0========================0
                        """);
                scanner.nextLine();
            }
        }
    }


    private void gameInfo(){
        System.out.println("""
                Тут будет инструкция перед игрой.
                """);
    }

    private String getRandomWord() {
        return randomWord;
    }

    public static void main(String[] args) {
        Gallows g = new Gallows();
        System.out.println(g.generatedRandomWord());

        System.out.println(g.getRandomWord());
    }
}
