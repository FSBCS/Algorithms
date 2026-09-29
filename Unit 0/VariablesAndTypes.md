# Variables and Data Types

Variables and types are at the heart of how Java works. In Java, every variable and every expression has a type that the compiler knows and checks before your program ever runs.

## Variables

> [!NOTE]
> A **variable** is a named location in memory that stores a value. In Java, every variable has a **name**, a **type**, and (once assigned) a **value**.

### Declaration, Assignment, and Initialization

In Java, you must _declare_ a variable before you use it, which means stating its type and name:

```java
int count;           // declaration: count is a variable that holds an int
count = 0;           // assignment: store the value 0 in count
int total = 100;     // declaration and initialization in one statement
int a = 1, b = 2;    // several variables of the same type at once (use sparingly)
```

- **Declaration** creates the variable and fixes its type _forever_. You declare a variable exactly once (within a given scope).
- **Assignment** (`=`) stores a value in an existing variable. You can assign as many times as you like, but the value must always match the declared type.
- **Initialization** is just the first assignment. It's good practice to initialize a variable at the moment you declare it.

The `=` sign in Java is not a statement of mathematical equality. `x = x + 1` makes perfect sense: "compute `x + 1`, then store the result in `x`." Read `=` as "gets" ("x gets x plus one"). Testing for equality uses `==`.

Java will not let you read a local variable before it's been given a value:

```java
int score;
System.out.println(score);  // COMPILE ERROR: variable score might not have been initialized
```

Java catches this mistake at compile time, before the program ever runs.

### Identifiers

A variable's name (its _identifier_) can contain letters, digits, `_`, and `$`, but it can't start with a digit and can't be a _reserved word_ (`int`, `class`, `if`, `for`, `public`, `static`, `new`, `return`, and about fifty others). Choose names that say what the variable means: `guessesRemaining` beats `g` every time. (Loop counters like `i` and `j` are a traditional exception.)

### Constants with `final`

Adding the keyword `final` to a declaration means the variable can be assigned only once:

```java
final int MAX_GUESSES = 20;
MAX_GUESSES = 25;   // COMPILE ERROR: cannot assign a value to final variable
```

Use `final` for values that shouldn't change: it documents your intent and lets the compiler enforce it. You'll see `final` again in [Special Classes](SpecialClasses.md), where it's one of the tools for building immutable classes.

### Local Type Inference with `var`

Since Java 10, you can write `var` instead of a type when declaring a local variable, as long as you initialize it on the same line:

```java
var count = 0;           // compiler infers int
var name = "Ada";        // compiler infers String
var input = new Scanner(System.in);  // compiler infers Scanner
```

This does **not** mean the variable can hold values of any type (that would be _dynamic typing_, described below). The variable still has a fixed type; the compiler just works it out from the right-hand side. `count = "hello";` on the next line would still be a compile error. In this course, write the types out explicitly for now: part of learning Java is getting used to thinking about what type every value has.

## Data Types

We've been saying "type" a lot. Here's what we mean:

>[!NOTE]
> A **Data Type** is a set of values and a set of operations on those values.

For example, the type `int` is the set of whole numbers from −2,147,483,648 to 2,147,483,647, together with operations like `+`, `-`, `*`, `/`, `%`, and comparisons. The type `boolean` is the set `{true, false}` together with operations like `&&` (and), `||` (or), and `!` (not). The type `String` is the set of all sequences of characters, together with operations like `length()`, `charAt()`, `substring()`, and concatenation with `+`.

Both halves of the definition matter. The type tells you what values are _possible_ and also what you're _allowed to do_ with them. You can multiply two `int`s, but multiplying two `boolean`s is meaningless, so Java doesn't allow it. And the operations can depend on the type: `+` means addition for `int`s but concatenation for `String`s.

### Strongly and Statically Typed

You'll often hear Java described as a **strongly typed** language. People use that phrase loosely to cover two related ideas, and it's worth separating them:

> [!NOTE]
> A language is **statically typed** if every variable and expression has a type that is known and checked at _compile time_, before the program runs. Once a variable is declared with a type, it can only ever hold values of that type.
>
> A language is **strongly typed** if it refuses to silently treat a value of one type as a value of an unrelated type. Operations on the wrong types are errors, not quiet guesses.

Java is both. Every variable is declared with a type, the compiler checks every assignment, every operator, and every method call against those types, and it rejects the program if anything doesn't line up:

```java
int count = 10;
count = "ten";               // COMPILE ERROR: String cannot be converted to int
boolean done = count;        // COMPILE ERROR: int cannot be converted to boolean
String s = count * "hello";  // COMPILE ERROR: bad operand types for binary operator '*'
```

Not every language works this way. Many popular languages are **dynamically typed**: _values_ have types, but _variables_ don't, so a variable can hold `5` one moment and `"five"` the next, and type errors show up only when the offending line actually runs. Static vs. dynamic and strong vs. weak are separate questions, as this comparison with two well-known dynamically typed languages shows:

| | Types checked... | Variable can change type? | `"5" + 3` |
| --- | --- | --- | --- |
| **Java** | at compile time (static) | No | `"53"` (string concatenation is a defined operation on `String`) |
| **Python** | at run time (dynamic) | Yes | `TypeError` |
| **JavaScript** | at run time (dynamic) | Yes | `"53"`, and `"5" * 3` is `15` |

(Java's `"5" + 3` isn't weak typing: the `String` type explicitly defines `+` with any other value as concatenation. What Java won't do is `"5" * 3`.)

Why put up with all this strictness? Static typing buys you:

1. **Early error detection.** An entire class of bugs is caught before the program runs.
2. **Documentation.** A method declared `double average(int[] scores)` tells you exactly what goes in and what comes out.
3. **Tooling.** Because the editor knows every variable's type, it can autocomplete method names and flag mistakes as you type.
4. **Speed.** The compiler knows exactly how much memory each value needs and which operation to perform, so it doesn't have to check types over and over while the program runs.

The cost is verbosity and less flexibility. For large programs written by teams, most programmers find the trade well worth it.

### Primitive Types

Java has exactly eight _primitive_ types. These are the basic building blocks, built into the language itself. A primitive variable stores its value directly.

| Type | Size | Range / Values | Example literals |
| --- | --- | --- | --- |
| `byte` | 8 bits | −128 to 127 | `byte b = 42;` |
| `short` | 16 bits | −32,768 to 32,767 | `short s = 1000;` |
| `int` | 32 bits | about ±2.1 billion | `42`, `-7`, `1_000_000`, `0xFF`, `0b1010` |
| `long` | 64 bits | about ±9.2 × 10¹⁸ | `42L`, `9_000_000_000L` |
| `float` | 32 bits | about ±3.4 × 10³⁸, ~7 significant digits | `3.14f` |
| `double` | 64 bits | about ±1.8 × 10³⁰⁸, ~15–16 significant digits | `3.14`, `2.0`, `6.02e23` |
| `char` | 16 bits | a single character (a Unicode code unit, 0 to 65,535) | `'a'`, `'Z'`, `'7'`, `'\n'` |
| `boolean` | — | `true` or `false` | `true`, `false` |

In practice you'll use four of these almost all the time: **`int`** for whole numbers, **`double`** for decimals, **`boolean`** for true/false, and **`char`** for single characters. Reach for `long` when numbers might exceed about 2 billion.

A _literal_ is a value written directly into the code. A few things to notice about literals:

- A whole-number literal like `42` is an `int`; add `L` to make it a `long`. A decimal literal like `3.14` is a `double`; add `f` to make it a `float`.
- Underscores in numeric literals are ignored, so `1_000_000` is just easier-to-read `1000000`.
- `char` literals use **single quotes** (`'a'`), `String` literals use **double quotes** (`"a"`). These are different types! Mixing them up is a common mistake.

#### Primitives Have Limits

Java's integer types have a fixed size. Go past the end of the range and the value _overflows_, wrapping around to the other end with no warning:

```java
int big = Integer.MAX_VALUE;   // 2147483647
big = big + 1;
System.out.println(big);       // -2147483648 (!)
```

Floating-point types (`float` and `double`) store numbers in binary, so most decimal fractions can only be approximated:

```java
System.out.println(0.1 + 0.2);         // 0.30000000000000004
System.out.println(0.1 + 0.2 == 0.3);  // false
```

(This isn't a Java quirk: nearly every programming language stores decimals this way.) The lesson: never compare `double`s with `==`. Check whether they're close instead: `Math.abs(a - b) < 1e-9`.

### Reference Types

Everything that isn't one of the eight primitives is a **reference type**: `String`, arrays, `Scanner`, and every class you or anyone else writes. A reference variable doesn't hold the object itself; it holds a _reference_ (a pointer) to where the object lives in memory. You saw this in the memory diagram in [Objects](Objects.md).

```
    int n = 42;                        String s = "hi";

    ┌──────────┐                       ┌──────────┐         ┌─────────────┐
    │ n │  42  │                       │ s │  ●───┼───────► │ String      │
    └──────────┘                       └──────────┘         │ "hi"        │
    the value itself                   a reference          └─────────────┘
                                                            the object (on the heap)
```

This distinction matters in a few places:

- **Assignment copies the reference, not the object.** After `int[] b = a;`, the variables `a` and `b` point to the _same_ array. Change `b[0]` and you've changed `a[0]` too. (With primitives, `int y = x;` copies the value, and the two variables are independent from then on.)
- **`==` compares references, not contents.** For primitives, `==` asks "same value?" For reference types, `==` asks "same object in memory?" To ask whether two objects have the same _contents_, use `.equals()` (more on this in [Strings and Input/Output](StringsAndIO.md)).
- **References can be `null`.** A reference variable can hold the special value `null`, meaning "points to nothing." Trying to use a `null` reference (e.g. `s.length()` when `s` is `null`) throws a `NullPointerException`, which is probably the single most common runtime error in Java. Primitives can never be `null`.

By convention, primitive type names are all lowercase (`int`, `double`), while reference types are classes and are capitalized (`String`, `Scanner`). That capital `S` in `String` is a clue that strings are objects.

### Default Values

Local variables (those declared inside a method) have no default value, which is why the compiler insists you initialize them. But _fields_ and _array elements_ are automatically set to a default when they're created:

| Type | Default |
| --- | --- |
| `byte`, `short`, `int`, `long` | `0` |
| `float`, `double` | `0.0` |
| `char` | `'\u0000'` (the "null character," not a space) |
| `boolean` | `false` |
| any reference type | `null` |

### Type Conversion and Casting

Sometimes you need to use a value of one type where another is expected. Java will do some conversions automatically, but only the safe ones.

A **widening conversion** goes from a "smaller" type to a "larger" one that can hold every value of the original. These happen automatically:

```
byte → short → int → long → float → double
               ↑
             char
```

```java
int count = 7;
double d = count;         // fine: 7 becomes 7.0
long big = count;         // fine
```

A **narrowing conversion** goes the other way and might lose information. Java won't do these automatically; you must request them explicitly with a **cast**, written as the target type in parentheses:

```java
double price = 9.99;
int dollars = price;          // COMPILE ERROR: possible lossy conversion from double to int
```

```java
int dollars = (int) price;    // OK: dollars is 9
```

Casting a `double` to an `int` _truncates_: it chops off the decimal part rather than rounding. `(int) 9.99` is `9`, and `(int) -3.7` is `-3`. If you want rounding, use `Math.round()`.

The cast is you telling the compiler, "I know this might lose information; do it anyway." That's the strongly typed philosophy in a nutshell: conversions that can lose information are allowed, but they're never silent.

Notice that `boolean` doesn't appear in the conversion chart. You can't convert between `boolean` and any number, in either direction, even with a cast. Java has no notion that `0` means `false` or that `1` means `true`.

`char` is secretly a number (the character's Unicode value), which leads to some fun:

```java
char c = 'a';
int code = c;                 // 97
char next = (char) (c + 1);   // 'b'
System.out.println('a' + 'b');  // 195 (!) -- two chars added as numbers
```

### Wrapper Classes

Each primitive type has a corresponding _wrapper class_ that represents the same value as an object: `Integer` for `int`, `Double` for `double`, `Boolean` for `boolean`, `Character` for `char`, and so on. You need these when a context requires objects rather than primitives (most notably `ArrayList` and the rest of the Collections framework, which we'll cover later). Java converts between the two automatically, which is called _autoboxing_ and _unboxing_:

```java
Integer boxed = 5;        // autoboxing: int → Integer
int plain = boxed;        // unboxing: Integer → int
```

The wrapper classes are also home to some useful static methods and constants:

```java
int n = Integer.parseInt("42");          // String → int
double x = Double.parseDouble("3.14");   // String → double
int biggest = Integer.MAX_VALUE;         // 2147483647
boolean isDigit = Character.isDigit('7');  // true
```

---

← Previous: [Java Basics](JavaBasics.md) · [Overview](ProceduralProgramming.md) · Next: [Operators](Operators.md) →
