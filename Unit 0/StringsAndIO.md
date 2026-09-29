# Strings and Input/Output

Strings are the most common reference type you'll use, and reading and printing text is how most of our early programs will talk to the user.

## Strings

A `String` is a sequence of characters. Strings are objects (note the capital `S`), but they're so common that Java gives them special treatment: you can create one with a literal in double quotes instead of calling `new`.

```java
String greeting = "Hello";
String empty = "";
```

### Concatenation

The `+` operator joins strings. If _either_ operand is a `String`, the other is automatically converted to a string:

```java
String name = "Ada";
int age = 36;
System.out.println(name + " is " + age + " years old.");  // Ada is 36 years old.
```

Java converts `age` to a string for you. But since `+` is evaluated left to right, watch out:

```java
System.out.println(1 + 2 + "3");   // "33"  -- 1 + 2 is 3 first, then "3" + "3"
System.out.println("1" + 2 + 3);   // "123" -- "1" + 2 is "12", then "12" + 3
System.out.println("1" + (2 + 3)); // "15"
```

### Escape Sequences

Some characters can't be typed directly inside a string literal, so they're written with a backslash:

| Sequence | Meaning |
| --- | --- |
| `\n` | newline |
| `\t` | tab |
| `\"` | double quote |
| `\'` | single quote (needed inside `char` literals: `'\''`) |
| `\\` | backslash |

### Common String Methods

Strings come with a large API. Here are the methods you'll use most:

| Method | Returns | Example (with `s = "Hello"`) |
| --- | --- | --- |
| `length()` | number of characters | `s.length()` → `5` |
| `charAt(i)` | the `char` at index `i` | `s.charAt(1)` → `'e'` |
| `substring(a, b)` | characters from index `a` up to (not including) `b` | `s.substring(1, 4)` → `"ell"` |
| `substring(a)` | characters from index `a` to the end | `s.substring(2)` → `"llo"` |
| `indexOf(str)` | index of first occurrence, or `-1` | `s.indexOf("l")` → `2` |
| `contains(str)` | whether `str` appears | `s.contains("ell")` → `true` |
| `equals(other)` | whether contents are identical | `s.equals("hello")` → `false` |
| `equalsIgnoreCase(other)` | same, ignoring case | `s.equalsIgnoreCase("hello")` → `true` |
| `compareTo(other)` | negative, zero, or positive (alphabetical order) | `s.compareTo("Help")` → negative |
| `toUpperCase()`, `toLowerCase()` | a new string with changed case | `s.toUpperCase()` → `"HELLO"` |
| `trim()` | a new string without leading/trailing whitespace | `"  hi ".trim()` → `"hi"` |
| `split(regex)` | an array of pieces | `"a,b,c".split(",")` → `{"a", "b", "c"}` |

Notice that `length()` is a method with parentheses, while (as we'll see) an array's `length` is a field with no parentheses. Yes, this is annoying. You'll get used to it.

You can't index a string with square brackets: `s[0]` is a compile error. Use `s.charAt(0)` and `s.substring(1, 4)`.

### Strings Are Immutable

Once a `String` object is created, it can never be changed. Methods like `toUpperCase()` and `trim()` don't modify the original; they return a _new_ string:

```java
String s = "hello";
s.toUpperCase();              // creates "HELLO" and throws it away
System.out.println(s);        // hello
s = s.toUpperCase();          // now s refers to the new string
System.out.println(s);        // HELLO
```

We'll discuss immutability as a design idea in [Special Classes](SpecialClasses.md).

If you need to build a long string piece by piece, say inside a loop, use a `StringBuilder`, which is a mutable sequence of characters:

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 5; i++) {
    sb.append(i).append(" ");
}
String result = sb.toString();   // "0 1 2 3 4 "
```

### Comparing Strings: `==` vs `.equals()`

> [!WARNING]
> **Never compare strings with `==`.** Use `s1.equals(s2)`.

Since strings are objects, `==` checks whether two variables refer to the _same object_, not whether they contain the same characters. Sometimes `==` appears to work (Java reuses identical string literals behind the scenes), which makes this bug especially sneaky: your code works when you test it with literals and then fails when the string comes from user input.

```java
Scanner input = new Scanner(System.in);
String answer = input.next();        // user types: yes
System.out.println(answer == "yes");       // false (different objects)
System.out.println(answer.equals("yes"));  // true  (same characters)
```

## Input and Output

### Printing

| Method | Behavior |
| --- | --- |
| `System.out.println(x)` | prints `x` followed by a newline |
| `System.out.print(x)` | prints `x` with no newline |
| `System.out.printf(format, args...)` | prints formatted output |

`printf` uses _format specifiers_, placeholders that get filled in with the values that follow:

```java
String name = "Ada";
double gpa = 3.8765;
System.out.printf("%s has a GPA of %.2f%n", name, gpa);   // Ada has a GPA of 3.88
```

Common specifiers: `%d` (integer), `%f` (floating-point; `%.2f` for two decimal places), `%s` (string), `%c` (char), `%b` (boolean), and `%n` (newline). `String.format(...)` takes the same arguments but returns the formatted string instead of printing it.

### Reading Input with `Scanner`

Java has no built-in `input()` function. Instead, we create a `Scanner` object that reads from `System.in` (the keyboard, via the terminal). Here's `HelloUser.java`:

```java
import java.util.Scanner;

public class HelloUser {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Please enter your name: ");
        String userName = input.next();

        System.out.println("Hello, " + userName + "!");

        input.close();
    }
}
```

The `import` line at the top is required because `Scanner` lives in the `java.util` _package_ (a package is a named group of related classes). Classes in `java.lang`, like `String`, `Math`, and `System`, are imported automatically.

`Scanner` has separate methods for each type: `nextInt()`, `nextDouble()`, `nextBoolean()`, `next()` (the next word), and `nextLine()` (the rest of the line). Once again, types are baked in: `nextInt()` returns an `int`, and if the user types `banana`, it throws an `InputMismatchException`.

> [!WARNING]
> **The `nextLine()` trap.** `nextInt()` reads the number but _not_ the Enter key after it. If you call `nextLine()` right after `nextInt()`, it reads that leftover newline and returns an empty string. The usual fix is to call an extra `input.nextLine();` to consume the leftover newline before reading the line you actually want.

---

← Previous: [Operators](Operators.md) · [Overview](ProceduralProgramming.md) · Next: [Control Flow](ControlFlow.md) →
