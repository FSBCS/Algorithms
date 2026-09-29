# Control Flow

_Control flow_ is the order in which statements run. By default, Java runs statements from top to bottom, one after another. Conditionals and loops let us change that: skipping code, choosing between alternatives, or repeating code.

## Conditionals

### `if`, `else if`, `else`

```java
if (score >= 90) {
    grade = 'A';
} else if (score >= 80) {
    grade = 'B';
} else if (score >= 70) {
    grade = 'C';
} else {
    grade = 'F';
}
```

A few rules to notice:

- The condition goes in **parentheses**, which are required.
- The body goes in **curly braces**.
- `else if` is two separate words.
- The condition must be a `boolean`. If `count` is an `int`, then `if (count)` is a compile error. Write `if (count != 0)`.

Java checks the conditions in order and runs the body of the _first_ one that's true, skipping the rest. The `else` branch runs only if none of the conditions were true.

#### Always Use Braces

If the body is a single statement, Java lets you leave out the braces:

```java
if (x > 0)
    System.out.println("positive");
```

This is legal, but dangerous, because only the _first_ statement belongs to the `if`, no matter how things are indented:

```java
if (x > 0)
    System.out.println("positive");
    System.out.println("definitely positive");   // ALWAYS runs! Indentation lies.
```

The indentation makes it look like both lines belong to the `if`, but the compiler ignores indentation entirely. Always use braces, even for one-line bodies.

### `switch`

When you're comparing a single value against many specific possibilities, a `switch` can be cleaner than a long `if`/`else if` chain. You can switch on integers, `char`s, `String`s, and enums.

The modern form (Java 14+) uses arrows:

```java
switch (day) {
    case "Saturday", "Sunday" -> System.out.println("Weekend!");
    case "Friday"             -> System.out.println("Almost there.");
    default                   -> System.out.println("Weekday.");
}
```

A `switch` can also be an _expression_ that produces a value:

```java
int daysInMonth = switch (month) {
    case 2 -> 28;
    case 4, 6, 9, 11 -> 30;
    default -> 31;
};
```

You'll also see the older, C-style form with colons and `break`, especially in older code and textbooks:

```java
switch (month) {
    case 2:
        days = 28;
        break;
    case 4:
    case 6:
    case 9:
    case 11:
        days = 30;
        break;
    default:
        days = 31;
}
```

In the old form, once a `case` matches, execution _falls through_ into every case below it until it hits a `break`. Forgetting a `break` is a classic bug. The arrow form never falls through, which is one reason it's preferred.

## Loops

Loops repeat a block of code. Java has four kinds.

### `while`

A `while` loop checks its condition _before_ each repetition and keeps going as long as the condition is `true`:

```java
int n = 1;
while (n < 1000) {
    n *= 2;
}
System.out.println(n);   // 1024
```

If the condition is false from the start, the body never runs at all. As with `if`, the condition needs parentheses and must be a `boolean`.

### `do`-`while`

A `do`-`while` loop checks its condition _after_ each repetition, so the body always runs at least once. It's a natural fit when you have to do something before you can decide whether to repeat it, like asking for a guess. From `NumberGuesser.java`:

```java
do {
    System.out.print("Please enter a guess: ");
    int guess = input.nextInt();
    // ... check the guess ...
} while (guessesRemaining > 0);
```

Note the semicolon after the `while (...)` at the end. It's easy to forget.

### `for`

Java's `for` loop has three parts in its header, separated by semicolons:

```java
for (int i = 0; i < 10; i++) {
    System.out.println(i);
}
```

```
for (  initialization  ;  condition  ;  update  ) {
        runs once          checked        runs after
        at the start       before each    each iteration
                           iteration
    body
}
```

1. **Initialization** (`int i = 0`) runs once, before the loop starts. Usually it declares and initializes a loop counter.
2. **Condition** (`i < 10`) is checked before every iteration, exactly like a `while` loop. When it's `false`, the loop ends.
3. **Update** (`i++`) runs at the end of every iteration.

This `for` loop is exactly equivalent to:

```java
int i = 0;
while (i < 10) {
    System.out.println(i);
    i++;
}
```

(with one difference: in the `for` version, `i` exists only inside the loop.) Because you control all three parts, `for` loops are very flexible:

```java
for (int i = 10; i > 0; i--) { ... }        // count down: 10, 9, ..., 1
for (int i = 0; i < 100; i += 5) { ... }    // count by 5s: 0, 5, ..., 95
for (int i = 1; i <= 1024; i *= 2) { ... }  // powers of two: 1, 2, 4, ..., 1024
```

The standard idiom for visiting every index of an array is:

```java
for (int i = 0; i < arr.length; i++) { ... }
```

Note the `<`, not `<=`. Since indices run from `0` to `arr.length - 1`, using `<=` goes one step too far. This "off-by-one" error is one of the most common bugs in all of programming.

### Enhanced `for` ("for-each")

When you want every element of an array (or collection) and don't need the index, the _enhanced for loop_ is cleaner:

```java
int[] scores = {88, 92, 75, 100};
int total = 0;
for (int score : scores) {
    total += score;
}
```

Read the colon as "in": "for each `int score` in `scores`." You must declare the loop variable's type. The limitation: `score` is a _copy_ of each element, so assigning to it (`score = 0;`) doesn't change the array. If you need the index or need to modify elements, use a regular `for` loop.

### `break` and `continue`

Two keywords let you change a loop's flow from inside its body:

- `break` exits the innermost loop immediately.
- `continue` skips the rest of the current iteration and moves to the next one (in a `for` loop, the update step still runs).

```java
for (int i = 0; i < 100; i++) {
    if (i % 2 == 0) {
        continue;      // skip even numbers
    }
    if (i > 15) {
        break;         // stop entirely
    }
    System.out.print(i + " ");   // 1 3 5 7 9 11 13 15
}
```

To break out of an _outer_ loop from inside a nested loop, you can put a _label_ on the outer loop:

```java
outer:
for (int r = 0; r < grid.length; r++) {
    for (int c = 0; c < grid[r].length; c++) {
        if (grid[r][c] == target) {
            System.out.println("Found at " + r + ", " + c);
            break outer;
        }
    }
}
```

Labels are rare. If you find yourself needing one, it's often a sign that the nested loops should be moved into their own method, where a `return` does the same job.

### Infinite Loops

A loop whose condition never becomes false runs forever. Sometimes that's on purpose (the aquarium's `while (true)` loop from [Objects](Objects.md)), but usually it's a bug: forgetting the `i++` in a `while` loop, say, or updating the wrong variable. If your program hangs, press `Ctrl+C` in the terminal to stop it.

---

← Previous: [Strings and Input/Output](StringsAndIO.md) · [Overview](ProceduralProgramming.md) · Next: [Arrays](Arrays.md) →
