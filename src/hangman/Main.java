package hangman;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String @NotNull [] args) {
        int guesses = Integer.parseInt(args[2]);
        List<Character> usedLetters = new ArrayList();
        StringBuilder word = new StringBuilder();
        word.append("-".repeat(Math.max(0, Integer.parseInt(args[1]))));
        System.out.println("You have " + guesses + " guesses left");
        System.out.println("Used letters: " + usedLetters.toString());
        System.out.println("Word: " + word.toString());
    }
}
