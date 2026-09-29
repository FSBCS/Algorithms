# Java Basics

This section covers the overall shape of a Java program: where code lives, how it gets compiled and run, and the basic rules of Java syntax.

## The Shape of a Java Program

Here's the smallest complete Java program:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

That's a lot of ceremony for one line of output, but every piece has a job. Let's go through it piece by piece:

- **`public class HelloWorld`**: In Java, _all_ code lives inside a class. There are no free-floating functions or statements outside of a class. The class name must match the file name exactly, so this class has to live in `HelloWorld.java`.
- **`public static void main(String[] args)`**: This is the _entry point_. When you run a Java program, the JVM (see below) looks for a method with exactly this signature and starts running there. We'll unpack every word of this line in [Methods and Scope](Methods.md#main-decoded). For now, `main` is where your program starts.
- **`System.out.println(...)`**: Prints a line of text. `System` is a class, `out` is a static field of that class (an object representing standard output), and `println` is a method of that object. That's why there are two dots.
- **`{ }`**: Curly braces mark the beginning and end of a _block_ of code. The class body is a block, the method body is a block, and so on.

> [!TIP]
> Java 25 added a shortcut for small programs: a file can contain just `void main() { IO.println("Hello"); }` with no class declaration. It's handy for quick experiments, but everything in this course uses the full form above, so get comfortable with it.

### Compiling and Running

Some languages are _interpreted_: a program called an interpreter reads your source code and runs it directly, line by line. Java works in two steps:

```
  HelloWorld.java  ──javac──►  HelloWorld.class  ──java──►  program runs
  (source code)    compile     (bytecode)         run       (on the JVM)
```

1. **Compile**: `javac HelloWorld.java` runs the Java _compiler_, which reads your whole source file, checks it for errors, and translates it into _bytecode_ stored in `HelloWorld.class`. Bytecode is a compact, low-level instruction set that isn't specific to any one kind of computer.
2. **Run**: `java HelloWorld` starts the _Java Virtual Machine_ (JVM), which loads the `.class` file and executes the bytecode. (Notice: no `.class` extension in that command.)

Because the JVM, not your physical processor, runs the bytecode, the same `.class` file will run on Windows, Mac, Linux, or a Chromebook. That was Java's original slogan: "Write once, run anywhere."

The compile step matters for more than portability. The compiler reads your _entire_ program before anything runs, which means it can catch whole categories of mistakes up front: typos in variable names, missing semicolons, calling a method that doesn't exist, and (above all) using a value of the wrong type. In a language without a compile step, you'd often find those bugs only when execution reaches the broken line, which might be deep inside a branch you rarely test.

> [!NOTE]
> A **compile-time error** is caught by the compiler before the program runs; the program won't compile until you fix it. A **runtime error** (in Java, an _exception_) happens while the program is running, like dividing by zero or accessing index 10 of a 5-element array.

In general, compile-time errors are your friends. They're annoying, but they're the cheapest bugs you'll ever fix.

## Basic Syntax

### Statements and Semicolons

A _statement_ is a single complete instruction. In Java, every simple statement ends with a semicolon:

```java
int x = 5;
x = x + 1;
System.out.println(x);
```

Java doesn't care about line breaks at all: it uses semicolons to separate statements. This is legal (but horrible) Java:

```java
int x
    = 5; x = x
+ 1; System.out.println(x);
```

Compound statements like `if`, `for`, and method declarations _don't_ end with a semicolon after their closing brace. The braces already mark where they end.

### Blocks and Indentation

In Java, **curly braces** define blocks, and indentation is purely for human readers. The compiler would be perfectly happy with an entire program on one line.

That doesn't mean indentation is optional in practice. Indent every block consistently (four spaces is standard), because code that is indented misleadingly is a great way to hide bugs from yourself.

### Expressions

> [!NOTE]
> An **expression** is any piece of code that evaluates to a value. `5`, `x`, `x + 1`, `Math.sqrt(16)`, and `name.length() > 3` are all expressions.

Statements _do_ something; expressions _are_ something (a value). Most statements are built out of expressions: in `int y = x * 2 + 1;`, the expression `x * 2 + 1` is evaluated and the result is stored in `y`. Every expression in Java has a _type_, which the compiler figures out before the program runs. That turns out to be important.

### Comments

Java has three kinds of comments:

```java
// A single-line comment runs to the end of the line.

/* A multi-line comment
   can span as many lines
   as you like. */

/**
 * A Javadoc comment. These go directly above classes and methods
 * and describe what they do. Tools can turn them into web documentation
 * (this is how the official Java API docs are generated).
 *
 * @param n  the number to square
 * @return   n times n
 */
public static int square(int n) {
    return n * n;
}
```

### Case and Naming Conventions

Java is _case-sensitive_: `count`, `Count`, and `COUNT` are three different names. By convention:

| Kind of name | Convention | Example |
| --- | --- | --- |
| Classes | `UpperCamelCase` | `NumberGuesser`, `Scanner` |
| Variables and methods | `lowerCamelCase` | `guessesRemaining`, `nextInt()` |
| Constants | `UPPER_SNAKE_CASE` | `MAX_GUESSES`, `Math.PI` |

Notice that Java names use camelCase rather than underscores: `guessesRemaining`, not `guesses_remaining`. The compiler doesn't enforce these conventions, but every Java programmer follows them, and code that ignores them is hard to read.

---

← Previous: [Procedural Programming in Java (overview)](ProceduralProgramming.md) · [Overview](ProceduralProgramming.md) · Next: [Variables and Data Types](VariablesAndTypes.md) →
