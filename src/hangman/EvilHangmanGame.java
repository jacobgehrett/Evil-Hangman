package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {

    SortedSet<Character> guessedLetters = new TreeSet<>();
    Set<String> words = new HashSet<>();

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        words.clear();
        Scanner dict = new Scanner(dictionary);
        if (!dict.hasNext()) {
            throw new EmptyDictionaryException();
        }
        while (dict.hasNext()) {
            String line = dict.next();
            if (line.length() == wordLength) {
                words.add(line);
            }
        }
        if (words.size() == 0) {
            throw new EmptyDictionaryException();
        }
        dict.close();

    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        if (guessedLetters.contains(guess)) {
            throw new GuessAlreadyMadeException();
        }
        else {
            guessedLetters.add(guess);
        }

        Map<ArrayList<Integer>, Set<String>> partition = new HashMap<>();
        for (String item: words) {
            ArrayList<Integer> ints = new ArrayList<>();
            for (int i = 0; i < item.length(); ++i) {

                if (item.charAt(i) == guess) {
                    ints.add(i);
                }
            }
            if (partition.containsKey(ints)) {
                partition.get(ints).add(item);
            }
            else {
                Set<String> puts = new HashSet<>();
                puts.add(item);
                partition.put(ints, puts);
            }
        }
        int numTimes = -1;
        ArrayList<Integer> ants = new ArrayList<>();
        ants.add(-1);
        for (Map.Entry<ArrayList<Integer>, Set<String>> entry : partition.entrySet()) {

            ArrayList<Integer> k = entry.getKey();
            k.add(-1);
            Set<String> v = entry.getValue();

            if (v.size() > numTimes) {
                words = v;
                numTimes = v.size();
                ants = k;
            }
            else if (v.size() == numTimes) {

                if (ants.size() < k.size()) {
                }
                else if (k.size() < ants.size()) {
                    words = v;
                    ants = k;
                }
                else {
                    if (ants.size() <= k.size()) {
                        for (int i = ants.size(); i > 0; --i) {
                            if (k.get(k.size() - i) > ants.get(ants.size() - i)) {
                                words = v;
                                ants = k;
                                break;
                            }
                        }
                    }
                    else {
                        for (int i = k.size(); i > 0; --i) {
                            if (k.get(k.size() - i) > ants.get(ants.size() - i)) {
                                words = v;
                                ants = k;
                                break;
                            }
                        }
                    }
                }

            }
        }
        return words;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }
}