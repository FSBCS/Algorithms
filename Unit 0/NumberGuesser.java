import java.util.Scanner;

/**
 * A guessing game. The computer picks a secret number, and the player
 * has a limited number of guesses to find it, with a hint after each one.
 */
public class NumberGuesser {
    public static void main(String[] args) {
        final int MAX_NUMBER = 1_000_000;
        final int MAX_GUESSES = 20;

        Scanner input = new Scanner(System.in);
        int secretNumber = randomInt(1, MAX_NUMBER);
        int guessesRemaining = MAX_GUESSES;
        boolean won = false;

        printInstructions(MAX_NUMBER, MAX_GUESSES);

        // Take guesses until the number is found or the guesses run out.
        do {
            int guess = readGuess(input);
            guessesRemaining--;

            if (guess == secretNumber) {
                won = true;
            } else if (guess < secretNumber) {
                System.out.println("Too low! " + guessesRemaining + " guesses left.\n");
            } else {
                System.out.println("Too high! " + guessesRemaining + " guesses left.\n");
            }
        } while (!won && guessesRemaining > 0);

        if (won) {
            System.out.println("Correct! You win!!");
        } else {
            System.out.println("Out of guesses :-(");
            System.out.println("Correct answer: " + secretNumber);
        }

        input.close();
    }

    /**
     * Returns a random integer from low to high, inclusive.
     */
    public static int randomInt(int low, int high) {
        return (int) (Math.random() * (high - low + 1)) + low;
    }

    /**
     * Prints the rules of the game.
     */
    public static void printInstructions(int maxNumber, int maxGuesses) {
        System.out.printf("%nI'm thinking of a number between 1 and %,d.%n", maxNumber);
        System.out.printf("You have %d guesses to find my number.%n%n", maxGuesses);
    }

    /**
     * Asks for a guess, re-prompting until the player types a whole number.
     */
    public static int readGuess(Scanner input) {
        System.out.print("Please enter a guess: ");
        while (!input.hasNextInt()) {
            input.next();   // throw away the input that wasn't a number
            System.out.print("That's not a whole number. Try again: ");
        }
        return input.nextInt();
    }
}
