# Виселица (Gallows)

![Логотип игры](src/main/java/org/gallows/logo/gallows.jpeg)

## Описание
Реализация консольной игры на Java, согласно ТЗ: https://zhukovsd.github.io/java-backend-learning-course/projects/hangman/

"Виселица" — это консольная игра, написанная на Java. 
Игроку нужно угадать слово, вводя по одной букве. На это даётся 6 попыток. За каждую ошибку рисуется часть тела.
Цель — отгадать слово до того, как игрок будет "повешен".

Слова для игры берутся из файла `words.txt`, который содержит список русских слов.

## Геймплей

![Геймплей игры Виселица](src/main/java/org/gallows/logo/gameplay.gif)


### Краткая инструкция
1. Проверьте наличие Java:
   Для работы игры требуется Java версии 21 или выше. Проверьте версию Java командой:
```bash
java -version
```
Если Java не установлена или версия ниже 21, скачайте и установите её [отсюда](https://www.oracle.com/java/technologies/downloads/#java21) 
(выберите Java 21 для вашей операционной системы) или используйте OpenJDK.

2. Скачать Виселицу можно [тут](https://github.com/DMgen10/Gallows/releases/tag/v1.0.1).
3. Для запуска выполните:
```bash
java -jar Gallows-1.0.jar
