package hangman;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String @NotNull [] args) {
        int guesses = Integer.parseInt(args[2]);
        int wordLength = Integer.parseInt(args[1]);
        File file = new File(args[0]);
        IEvilHangmanGame game = new EvilHangmanGame();
        try {
            game.startGame(file, wordLength);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyDictionaryException e) {
            System.out.println("Dictionary empty/unusable!");
        }
        StringBuilder word = new StringBuilder();
        word.append("-".repeat(Math.max(0, wordLength)));
        String winWord = "";
        while ((word.indexOf("-") != -1) && (guesses > 0)) {

            System.out.println("You have " + guesses + " guesses left");
            System.out.println("Used letters: " + game.getGuessedLetters().toString());
            System.out.println("Word: " + word.toString());

            Scanner in = new Scanner(System.in);
            char c = 0;
            System.out.print("Enter Guess: ");
            while (c == 0) {

                if (in.hasNext()) {
                    c = in.next().charAt(0);
                    if (((c < 'a') || (c > 'z')) && ((c < 'A') || (c > 'Z'))) {
                        System.out.print("Invalid input! Enter Guess: ");
                        c = 0;
                    }
                    else {
                        try {
                            Set<String> wordSet = game.makeGuess(c);

                            for (String item: wordSet) {
                                int charNum = 0;
                                for (int i = 0; i < wordLength; ++i) {
                                    if (item.charAt(i) == c) {
                                        word.setCharAt(i, c);
                                        ++charNum;
                                    }
                                }
                                if (charNum > 0) {
                                    System.out.println("Yes, there is " + charNum + " " + c + "\n");
                                    if (word.indexOf("-") == -1) {
                                        winWord = item;
                                        break;
                                    }
                                }
                                else {
                                    System.out.println("Sorry, there are no " + c + "’s\n");
                                    --guesses;
                                    if (guesses == 0) {
                                        winWord = item;
                                        break;
                                    }
                                }
                                break;
                            }

                        } catch (GuessAlreadyMadeException e) {
                            System.out.print("Guess already made! Enter Guess: ");
                            c = 0;
                        }
                    }
                }

            }

        }
        if (guesses == 0) {
            System.out.println("You lose!");
            System.out.println("The word was: " + winWord);
        }
        else {
            System.out.println("You win! You guessed the word: " + winWord);
        }
    }
}
