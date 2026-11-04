# Procedural Programming in Java

Before we get to objects, inheritance, and all the machinery that makes Java "object-oriented," we need the basic building blocks: variables, data types, operators, conditionals, loops, arrays, and functions. Taken together, these let us write programs as a sequence of steps organized into functions that pass data to each other. This style is called _procedural programming_. It's the style you've been using all along, and it's the style we'll contrast with objects in the aquarium example in [Objects](Objects.md).

If you've programmed in another language before, much of what follows will feel familiar. The ideas are largely the same; what changes are the _rules_. Java is stricter than many languages, and most of that strictness is there so the compiler can catch mistakes before your program ever runs.

## Contents

Read these in order; each builds on the ones before it.

| # | Topic | What's in it |
| --- | --- | --- |
| 1 | [Java Basics](JavaBasics.md) | Program structure, `main`, compiling and running, statements, blocks, comments, naming conventions |
| 2 | [Variables and Data Types](VariablesAndTypes.md) | Declaring variables, `final`, `var`, what a data type is, static and strong typing, primitive and reference types, casting, wrapper classes |
| 3 | [Operators](Operators.md) | Arithmetic and integer division, compound assignment, `++`/`--`, comparison and logical operators, the ternary operator, precedence |
| 4 | [Strings and Input/Output](StringsAndIO.md) | Concatenation, escape sequences, `String` methods, immutability, `==` vs `.equals()`, printing, `printf`, `Scanner` |
| 5 | [Control Flow](ControlFlow.md) | `if`/`else`, `switch`, `while`, `do`-`while`, `for`, for-each, `break` and `continue` |
| 6 | [Arrays](Arrays.md) | Creating and indexing arrays, arrays as references, the `Arrays` library, 2D arrays, command-line arguments |
| 7 | [Methods and Scope](Methods.md) | Anatomy of a method, method signatures, overloading, parameters vs. arguments, return values, pass-by-value, `main`, the `Math` library, recursion, scope |

The rest of this page pulls everything together: an annotated example program, a guide to common error messages, and a syntax quick reference.

## Putting It Together: `NumberGuesser.java`

Here's a complete program that uses nearly everything from these notes. The computer picks a secret number, and the player has 20 guesses to find it, with a "too high" or "too low" hint after each one. Read `main` first: it should read almost like an outline of the game. The details are handed off to three small helper methods below it.

```java
import java.util.Scanner;                              // Scanner lives in java.util

/**
 * A guessing game. The computer picks a secret number, and the player
 * has a limited number of guesses to find it, with a hint after each one.
 */
public class NumberGuesser {                           // file must be NumberGuesser.java
    public static void main(String[] args) {           // entry point: signature main(String[])
        final int MAX_NUMBER = 1_000_000;              // constants: each value written once
        final int MAX_GUESSES = 20;

        Scanner input = new Scanner(System.in);        // a reference type: an object
        int secretNumber = randomInt(1, MAX_NUMBER);   // a method call is an expression
        int guessesRemaining = MAX_GUESSES;
        boolean won = false;                           // tracks whether the game is over

        printInstructions(MAX_NUMBER, MAX_GUESSES);    // arguments matched to parameters in order

        // Take guesses until the number is found or the guesses run out.
        do {                                           // body runs at least once
            int guess = readGuess(input);              // scope: only inside this loop body
            guessesRemaining--;                        // side effect on its own line

            if (guess == secretNumber) {               // == is fine: comparing primitives
                won = true;
            } else if (guess < secretNumber) {
                System.out.println("Too low! " + guessesRemaining + " guesses left.\n");
            } else {
                System.out.println("Too high! " + guessesRemaining + " guesses left.\n");
            }
        } while (!won && guessesRemaining > 0);        // checked after each guess

        if (won) {
            System.out.println("Correct! You win!!");
        } else {
            System.out.println("Out of guesses :-(");
            System.out.println("Correct answer: " + secretNumber);  // int concatenated into a String
        }

        input.close();                                 // always reached: no early return
    }

    /**
     * Returns a random integer from low to high, inclusive.
     */
    public static int randomInt(int low, int high) {   // signature: randomInt(int, int)
        // double in [0, 1) → scale to the size of the range → truncate → shift up by low
        return (int) (Math.random() * (high - low + 1)) + low;
    }

    /**
     * Prints the rules of the game.
     */
    public static void printInstructions(int maxNumber, int maxGuesses) {  // void: no return value
        System.out.printf("%nI'm thinking of a number between 1 and %,d.%n", maxNumber);
        System.out.printf("You have %d guesses to find my number.%n%n", maxGuesses);
    }

    /**
     * Asks for a guess, re-prompting until the player types a whole number.
     */
    public static int readGuess(Scanner input) {       // an object passed as an argument
        System.out.print("Please enter a guess: ");
        while (!input.hasNextInt()) {                  // while: may run zero times
            input.next();                              // throw away the input that wasn't a number
            System.out.print("That's not a whole number. Try again: ");
        }
        return input.nextInt();
    }
}
```

A few design choices worth noticing:

- **`main` tells the story; helpers do the work.** Each helper method has one job, a name that says what that job is, and a Javadoc comment. Because `randomInt` takes its range as parameters instead of hard-coding 1,000,000, it would work unchanged in any program that needs a random number.
- **Every number appears exactly once.** `MAX_NUMBER` and `MAX_GUESSES` are declared `final` at the top of `main` and passed to `printInstructions` as arguments. To make the game easier, you change one line, and the instructions stay correct automatically.
- **One way out of the loop.** Instead of calling `return` from the middle of the loop when the player wins, the program records the result in the `boolean` variable `won`. The loop condition checks it, and the code after the loop decides which message to print. Because nothing exits early, the program always reaches `input.close()`.
- **Two kinds of loops, each for a reason.** The game loop is a `do`-`while` because the player always gets at least one guess. The loop in `readGuess` is a plain `while` because it should run zero times when the input is already a valid number. `hasNextInt()` checks the input _before_ `nextInt()` reads it, so typing `banana` gets a polite retry instead of an `InputMismatchException`.
- **Scope keeps things tidy.** `guess` is declared inside the loop body because it's needed only there. `guessesRemaining` and `won` are declared before the loop because the loop condition and the code after the loop both need them.
- **The decrement gets its own line.** Writing `guessesRemaining--;` by itself, rather than inside the `println` call, makes it obvious that it happens exactly once per guess, whichever branch of the `if` runs.

## When Things Go Wrong

Now that you've seen most of the rules, here are the error messages you'll run into most often, and what they usually mean.

**Compile-time errors** (from `javac`):

| Message | Usual cause |
| --- | --- |
| `';' expected` | a missing semicolon, often on the line _before_ the one reported |
| `cannot find symbol` | a misspelled name, a variable used outside its scope, or a missing `import` |
| `incompatible types: X cannot be converted to Y` | assigning or passing a value of the wrong type |
| `possible lossy conversion from double to int` | a narrowing conversion without a cast |
| `variable x might not have been initialized` | reading a local variable before assigning it |
| `missing return statement` | some path through a non-`void` method doesn't `return` |
| `non-static method ... cannot be referenced from a static context` | calling an instance method from `main` (add `static`) |
| `class X is public, should be declared in a file named X.java` | the class name and file name don't match |

**Runtime exceptions** (while the program runs):

| Exception | Usual cause |
| --- | --- |
| `ArrayIndexOutOfBoundsException` | an index less than 0 or at least `length` (often an off-by-one `<=`) |
| `NullPointerException` | calling a method or accessing a field on a `null` reference |
| `ArithmeticException: / by zero` | integer division by zero |
| `InputMismatchException` | `Scanner.nextInt()` read something that wasn't an integer |
| `NumberFormatException` | `Integer.parseInt()` on a string that isn't a number |
| `StackOverflowError` | recursion with no (reachable) base case |

When an exception occurs, Java prints a _stack trace_: the exception type, a message, and the chain of method calls that led there, with line numbers. Read the top few lines first. They tell you what went wrong and exactly where.

## Java Syntax Quick Reference

| Concept | Java |
| --- | --- |
| Print | `System.out.println(x);` |
| Print, no newline | `System.out.print(x);` |
| Formatted output | `String.format("%s: %.2f", name, gpa)` |
| Read input | `Scanner sc = new Scanner(System.in);` then `sc.nextLine()` |
| Comment | `// ...` or `/* ... */` |
| Variable | `int x = 5;` |
| Constant | `final int MAX = 10;` |
| Boolean values | `true`, `false` |
| No object | `null` |
| Logical operators | `&&`, `\|\|`, `!` |
| Integer division | `7 / 2` (when both are integers) |
| Exponent | `Math.pow(2, 10)` |
| Increment | `x++;` or `x += 1;` |
| `if` / `else if` / `else` | `if (x > 0) { } else if (...) { } else { }` |
| Conditional expression | `cond ? a : b` |
| Counting loop | `for (int i = 0; i < 10; i++) { }` |
| For-each loop | `for (int x : items) { }` |
| While loop | `while (cond) { }` |
| `do`-`while` loop | `do { } while (cond);` |
| Array literal | `int[] a = {1, 2, 3};` |
| Length | `a.length`, `s.length()` |
| Last element | `a[a.length - 1]` |
| Substring | `s.substring(1, 4)` |
| String equality | `s1.equals(s2)` |
| Convert to int | `Integer.parseInt("42")` |
| Convert to string | `String.valueOf(42)` or `"" + 42` |
| Define a function | `public static int f(int x) { }` |
| Entry point | `public static void main(String[] args)` |
