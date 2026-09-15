import java.util.Scanner;

public class NumberGuesser {
    public static void main(String[] args) {
        int secretNumber = (int) (Math.random() * 1000000) + 1;
        int guessesRemaining = 20;
        Scanner input = new Scanner(System.in);

        // Print instructions.
        System.out.println("\nI'm thinking of a number between 1 and 1 Million.");
        System.out.println("You have 20 guesses to find my number \n");

        // Take guesses until number is guessed or we run out of guesses.
        do {

            System.out.print("Please enter a guess: ");
            int guess = input.nextInt();
            
            // Inform the user if their guess is high, low, or correct.
            if (guess == secretNumber) {
                System.out.println("Correct! You win!!");
                return;
            } else if (guess < secretNumber) {
                System.out.println("Too low! " + --guessesRemaining + " guesses left.\n");
            } else {
                System.out.println("Too high! " + --guessesRemaining + " guesses left.\n");
            }

        } while (guessesRemaining > 0);

        // Display lose message and the secret number.
        System.out.println("Out of guesses :-(");
        System.out.println("Correct answer: " + secretNumber);
    }
}
