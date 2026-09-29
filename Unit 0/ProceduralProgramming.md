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

Here's `NumberGuesser.java` again, annotated with the concepts from these notes:

```java
import java.util.Scanner;                          // Scanner lives in java.util

public class NumberGuesser {                       // file must be NumberGuesser.java
    public static void main(String[] args) {       // entry point: signature main(String[])

        // Random int from 1 to 1,000,000: double → cast (truncate) → shift by 1
        int secretNumber = (int) (Math.random() * 1000000) + 1;
        int guessesRemaining = 20;                 // declared and initialized
        Scanner input = new Scanner(System.in);    // a reference type: an object

        System.out.println("\nI'm thinking of a number between 1 and 1 Million.");
        System.out.println("You have 20 guesses to find my number \n");

        do {                                       // body runs at least once
            System.out.print("Please enter a guess: ");
            int guess = input.nextInt();           // scope: only inside this loop body

            if (guess == secretNumber) {           // == is fine: comparing primitives
                System.out.println("Correct! You win!!");
                return;                            // exits main, ending the program
            } else if (guess < secretNumber) {
                // prefix decrement: subtract 1, then use the new value
                System.out.println("Too low! " + --guessesRemaining + " guesses left.\n");
            } else {
                System.out.println("Too high! " + --guessesRemaining + " guesses left.\n");
            }

        } while (guessesRemaining > 0);            // condition checked after each guess

        System.out.println("Out of guesses :-(");
        System.out.println("Correct answer: " + secretNumber);  // int concatenated into String
    }
}
```

A few things worth noticing:

- `guess` is declared _inside_ the `do` block, so it doesn't exist in the `while (...)` condition or after the loop. That's fine here because the condition only needs `guessesRemaining`, which was declared outside the loop.
- The `20` appears twice (in the initialization and in the instructions). If you changed one, you'd have to remember to change the other. A `final int MAX_GUESSES = 20;` constant used in both places would be better.
- The program never calls `input.close()`, and a `return` in the middle of `main` would skip it anyway. It's harmless in a program this small, but it's a good habit to close a `Scanner` when you're done with it.

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
