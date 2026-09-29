# Operators

Operators are the "set of operations" half of a data type. Most of Java's operators will look familiar, but a few have behaviors that surprise people.

## Arithmetic

| Operator | Meaning | Example | Result |
| --- | --- | --- | --- |
| `+` | addition | `7 + 2` | `9` |
| `-` | subtraction | `7 - 2` | `5` |
| `*` | multiplication | `7 * 2` | `14` |
| `/` | division | `7 / 2` | `3` (!) |
| `%` | remainder ("mod") | `7 % 2` | `1` |

Java has no exponent operator. (`2 ^ 10` compiles, but `^` means something else entirely.) Use `Math.pow(2, 10)`, which returns a `double`.

### Integer Division

This is the big one. **When both operands of `/` are integers, Java performs integer division** and throws away the remainder:

```java
System.out.println(7 / 2);        // 3
System.out.println(7 / 2.0);      // 3.5  -- one operand is a double
System.out.println((double) 7 / 2);   // 3.5  -- cast 7 to double first
System.out.println((double) (7 / 2)); // 3.0  -- too late! 7/2 is already 3
```

Java uses the same `/` operator for both kinds of division and decides which one to do based on the operand types: if both are integers, the result is an integer. That's static typing at work. The compiler knows `7` and `2` are `int`s, so the expression `7 / 2` has type `int`, and an `int` can't hold `3.5`.

A classic bug:

```java
int total = 17, count = 4;
double average = total / count;   // 4.0, not 4.25!
```

The division happens first (as integer division, giving `4`), and only _then_ is the result widened to `double`. The fix is `(double) total / count`.

With negative numbers, integer division truncates _toward zero_: `-7 / 2` is `-3`, not `-4`. Likewise, `%` takes the sign of the left operand: `-7 % 3` is `-1`, not `2`. (Not every language does it this way, so don't assume this carries over.) This matters when you use `%` to wrap around an array index, so be careful with negative numbers.

Dividing an integer by zero throws an `ArithmeticException`. Dividing a `double` by zero doesn't: you get `Infinity`, `-Infinity`, or `NaN` ("not a number").

### Mixed-Type Arithmetic

When an operator combines two different numeric types, Java widens the "smaller" one to match the "larger" before computing. So `int + double` is a `double`, `int + long` is a `long`, and so on. Also, arithmetic on `byte`, `short`, and `char` always produces at least an `int`, which is why `'a' + 'b'` printed `195` back in [Variables and Data Types](VariablesAndTypes.md#type-conversion-and-casting).

## Assignment and Compound Assignment

Besides plain `=`, Java has _compound assignment_ operators that combine an operation with assignment:

```java
x += 5;    // x = x + 5
x -= 5;    // x = x - 5
x *= 2;    // x = x * 2
x /= 2;    // x = x / 2
x %= 3;    // x = x % 3
```

One subtle difference: compound assignment includes a hidden cast back to the variable's type. So if `x` is an `int`, `x = x + 2.7;` is a compile error (lossy conversion), but `x += 2.7;` quietly compiles and truncates.

## Increment and Decrement

`x++` and `++x` both add one to `x`; `x--` and `--x` both subtract one. Used as standalone statements, the two forms are identical. The difference shows up when the increment is part of a larger expression:

- **Postfix** (`x++`): the expression's value is the _old_ value of `x`; the increment happens afterward.
- **Prefix** (`++x`): the increment happens first; the expression's value is the _new_ value.

```java
int a = 5;
int b = a++;   // b is 5, a is now 6
int c = ++a;   // a is now 7, c is 7
```

You can see the prefix form in `NumberGuesser.java`:

```java
System.out.println("Too low! " + --guessesRemaining + " guesses left.\n");
```

This decrements `guessesRemaining` and then uses the new value in the message. It's compact, but cramming side effects into larger expressions makes code harder to read. When in doubt, put `x++;` on its own line.

## Comparison (Relational) Operators

These compare two values and produce a `boolean`:

| Operator | Meaning |
| --- | --- |
| `==` | equal to |
| `!=` | not equal to |
| `<`, `>` | less than, greater than |
| `<=`, `>=` | less than or equal, greater than or equal |

You can't chain comparisons the way you would in math: `0 < x < 10` is a compile error in Java (because `0 < x` is a `boolean`, and you can't compare a `boolean` with `10`). Write `0 < x && x < 10`.

Remember: for reference types, `==` checks whether two references point to the _same object_. To compare contents (especially `String`s), use `.equals()`.

## Logical Operators

| Operator | Name | Meaning |
| --- | --- | --- |
| `&&` | and | true if both sides are true |
| `\|\|` | or | true if at least one side is true |
| `!` | not | flips `true` and `false` |

Java's `&&` and `||` are **short-circuiting**: if the left side already determines the answer, the right side is never evaluated. This is often used deliberately as a guard:

```java
if (name != null && name.length() > 0) {
    // safe: if name is null, name.length() is never called
}
```

Both operands must be `boolean`s: `count && done` is a compile error if `count` is an `int`.

## The Conditional (Ternary) Operator

`condition ? valueIfTrue : valueIfFalse` is an _expression_ that picks one of two values:

```java
String label = (score >= 60) ? "pass" : "fail";
int max = (a > b) ? a : b;
```

It's a compact alternative to an `if`/`else` when all you need is a value. Great for short, simple choices; nesting ternaries is a quick way to make your code unreadable.

## Precedence

When an expression has several operators, Java applies them in a fixed order of _precedence_, which mostly follows the order you learned in math class. From highest to lowest (simplified):

1. Parentheses `( )`, method calls, array indexing `[ ]`, the dot operator `.`
2. Unary operators: `++`, `--`, `!`, unary `-`, casts `(type)`
3. `*`, `/`, `%`
4. `+`, `-`
5. `<`, `>`, `<=`, `>=`
6. `==`, `!=`
7. `&&`
8. `||`
9. `? :`
10. Assignment: `=`, `+=`, `-=`, etc.

Operators at the same level are evaluated left to right (except assignment, which goes right to left). Notice that casts bind more tightly than arithmetic, which is why `(double) 7 / 2` casts only the `7`. If you're ever unsure, add parentheses. They cost nothing and make your intent clear.

---

← Previous: [Variables and Data Types](VariablesAndTypes.md) · [Overview](ProceduralProgramming.md) · Next: [Strings and Input/Output](StringsAndIO.md) →
