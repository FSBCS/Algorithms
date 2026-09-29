# Methods and Scope

Methods are the main tool of procedural programming: they let us break a program into small, named, reusable pieces. Scope determines which variables each of those pieces can see.

## Methods

> [!NOTE]
> A **function** is a named, reusable block of code that takes some inputs (_parameters_), does some work, and optionally produces an output (a _return value_). In Java, every function belongs to a class, and a function that belongs to a class is called a **method**.

Since Java has no free-standing functions, Java programmers say "method" for everything. For these notes: a `static` method is what you'd think of as an ordinary, stand-alone function, and that's the kind we'll write here. Instance methods, which belong to objects, are covered in [Objects](Objects.md).

### Anatomy of a Method

Here's a complete method:

```java
/**
 * Computes the average of an array of scores.
 */
public static double average(int[] scores) {
    int total = 0;
    for (int s : scores) {
        total += s;
    }
    return (double) total / scores.length;
}
```

The first line, everything before the `{`, is the method's _header_ (or _declaration_). Every part of it means something:

```
   public   static   double   average   (int[] scores)
   └──┬──┘  └──┬──┘  └──┬──┘  └──┬───┘  └─────┬─────┘
   access   static   return    name     parameter list
   modifier modifier type
```

| Part | Example | Meaning |
| --- | --- | --- |
| **Access modifier** | `public` | Who can call this method. `public` means any class; `private` means only code in this class. (Details in [Inheritance](Inheritance.md#access-modifiers).) |
| **`static`** (optional) | `static` | The method belongs to the class itself, not to any particular object. It's called as `ClassName.method()`, or just `method()` from inside the same class. |
| **Return type** | `double` | The type of value the method gives back. Use `void` if it returns nothing. Required. |
| **Name** | `average` | What you call it. By convention, `lowerCamelCase` and usually a verb: `computeTotal`, `isValid`, `printBoard`. |
| **Parameter list** | `(int[] scores)` | The inputs, in parentheses: zero or more `Type name` pairs separated by commas. The parentheses are required even when empty: `()`. |
| **`throws` clause** (optional) | `throws IOException` | Exceptions the method might throw. We'll discuss these with exceptions. |
| **Body** | `{ ... }` | The code that runs when the method is called. |

Notice how much the header tells you. It nails down the type of every parameter and the type of the result, so the compiler can check every call to `average` anywhere in the program. If someone calls `average("hello")`, the compiler rejects it before the program ever runs.

### The Method Signature

> [!NOTE]
> A method's **signature** is its **name** together with the **number, order, and types of its parameters**. Nothing else.

This is a precise term, and it's narrower than the header. **The signature does _not_ include** the return type, the parameter _names_, the access modifier, `static`, or the `throws` clause.

| Method header | Signature |
| --- | --- |
| `public static double average(int[] scores)` | `average(int[])` |
| `private int max(int a, int b)` | `max(int, int)` |
| `public static void main(String[] args)` | `main(String[])` |
| `static String repeat(String s, int times)` | `repeat(String, int)` |
| `public void reset()` | `reset()` |

Why does the signature matter? Because **within a class, every method must have a unique signature**. The signature is how Java tells methods apart. When the compiler sees a call like `max(3, 7)`, it looks at the name and the types of the arguments (`int, int`) to figure out exactly which method you mean.

### Overloading

Since methods are identified by signature and not just by name, a class can have several methods with the _same name_ as long as their parameter lists differ. This is called **overloading**:

```java
public static int max(int a, int b) {
    return (a > b) ? a : b;
}

public static double max(double a, double b) {
    return (a > b) ? a : b;
}

public static int max(int a, int b, int c) {
    return max(max(a, b), c);
}
```

These three methods have the signatures `max(int, int)`, `max(double, double)`, and `max(int, int, int)`. They're all different, so this is legal. Java chooses among them based on the arguments you pass:

```java
max(3, 7)          // calls max(int, int)
max(2.5, 1.0)      // calls max(double, double)
max(3, 7, 5)       // calls max(int, int, int)
max(3, 2.5)        // calls max(double, double): 3 is widened to 3.0
```

You've already been using overloaded methods: `System.out.println` has separate versions for `int`, `double`, `char`, `boolean`, `String`, `Object`, and more. That's why it can print anything. `Math.max` and `Math.abs` are also overloaded for each numeric type.

Overloading is also how Java provides "optional" arguments. Java has no default parameter values, so instead you write a version of the method with fewer parameters that calls the full version with the defaults filled in.

Here's where the precise definition of a signature matters. Because the return type and parameter names aren't part of the signature, these are **not** legal overloads:

```java
public static int parse(String s) { ... }
public static double parse(String s) { ... }   // COMPILE ERROR: same signature parse(String)

public static int area(int width, int height) { ... }
public static int area(int w, int h) { ... }    // COMPILE ERROR: same signature area(int, int)
```

In the first case, a call like `parse("42")` would be ambiguous: the compiler picks a method by looking at the call, and a call doesn't say anything about which return type you want.

### Parameters and Arguments

These two words are often used interchangeably, but they mean different things:

- A **parameter** is a variable declared in the method header: `a` and `b` in `max(int a, int b)`. It's a placeholder.
- An **argument** is the actual value passed in when the method is called: `3` and `7` in `max(3, 7)`.

When a method is called, each argument is evaluated, and its value is assigned to the corresponding parameter, in order. The arguments must match the parameters in number, order, and type (allowing for widening conversions like `int` to `double`). Java has no way to pass arguments by name, so order is everything.

If you need a method that takes any number of arguments of one type, Java offers _varargs_ with `...`:

```java
public static int sum(int... nums) {   // nums is an int[] inside the method
    int total = 0;
    for (int n : nums) total += n;
    return total;
}

sum(1, 2)          // 3
sum(1, 2, 3, 4)    // 10
```

### Return Values

A method with a non-`void` return type **must** return a value of that type using a `return` statement, and the compiler checks that _every_ possible path through the method returns something:

```java
public static String sign(int n) {
    if (n > 0) {
        return "positive";
    } else if (n < 0) {
        return "negative";
    }
    // COMPILE ERROR without the next line: missing return statement (what if n == 0?)
    return "zero";
}
```

A `return` statement ends the method _immediately_, even in the middle of a loop, and hands the value back to the caller. A `void` method doesn't return a value, but it can use a bare `return;` to exit early. In `NumberGuesser.java`, a `return;` inside `main` ends the whole program as soon as the user guesses correctly.

A method can return only one value. To return several, you can return an array, or (better) an object that bundles them together. That's one more motivation for classes.

### Pass-by-Value

> [!NOTE]
> Java is always **pass-by-value**: a method receives a _copy_ of each argument's value.

For primitives, this is simple. The method gets its own copy, and changing it has no effect on the caller:

```java
public static void addOne(int n) {
    n = n + 1;          // changes the local copy only
}

int x = 5;
addOne(x);
System.out.println(x);  // still 5
```

For reference types, the _value_ being copied is the reference. The method gets its own copy of the pointer, but it points to the same object. So the method **can** change the object's contents, but it **can't** make the caller's variable point somewhere else:

```java
public static void zeroFirst(int[] arr) {
    arr[0] = 0;              // changes the shared array: caller sees this!
}

public static void replace(int[] arr) {
    arr = new int[] {7, 7};  // re-points the local copy only: caller doesn't see this
}

int[] data = {1, 2, 3};
zeroFirst(data);
System.out.println(Arrays.toString(data));  // [0, 2, 3]
replace(data);
System.out.println(Arrays.toString(data));  // [0, 2, 3]
```

```
  Caller's variable              Method's parameter (a copy)
  ┌─────────────┐                ┌─────────────┐
  │ data  ●─────┼──┐          ┌──┼─────● arr   │
  └─────────────┘  │          │  └─────────────┘
                   ▼          ▼
                 ┌───┬───┬───┐
                 │ 1 │ 2 │ 3 │    one array, two references to it
                 └───┴───┴───┘
```

The practical upshot: a method that receives an array can modify its elements, and that's often exactly what you want (`Arrays.sort` works this way). If you _don't_ want a method to modify an array you pass it, it's your job to pass a copy.

### Calling Methods

How you call a static method depends on where it lives:

```java
// From inside the same class: just the name
double avg = average(scores);

// From another class: ClassName.methodName
double root = Math.sqrt(16.0);
int n = Integer.parseInt("42");
```

A method call is an expression whose type is the method's return type, so it can go anywhere a value of that type can: `Math.sqrt(16.0) + 1`, `if (isValid(x))`, `System.out.println(max(a, b))`. Calling a `void` method is only a statement; `int x = System.out.println("hi");` doesn't make sense and doesn't compile.

#### Why `static` (for now)

If you write a helper method and try to call it from `main` without the `static` keyword, you'll get a compile error like `non-static method average(int[]) cannot be referenced from a static context`. `main` is `static`, meaning it runs without any object existing, and a non-`static` (instance) method needs an object to run on. For now, mark all of your helper methods `static`. The full story is in [Objects](Objects.md#static-methods-and-fields).

### `main`, Decoded

Now we can read the whole entry point:

```java
public static void main(String[] args)
```

- `public`: the JVM, which is outside your class, has to be able to call it.
- `static`: it runs before any objects have been created, so it must belong to the class.
- `void`: it doesn't return a value (the program just ends).
- `main`: the name the JVM looks for.
- `String[] args`: an array of the command-line arguments.

Its signature is `main(String[])`.

### The `Math` Library

`Math` is a class full of static methods (and two constants). It lives in `java.lang`, so no import is needed.

| Method | Returns |
| --- | --- |
| `Math.abs(x)` | absolute value |
| `Math.pow(base, exp)` | `base` raised to `exp` (as a `double`) |
| `Math.sqrt(x)` | square root (as a `double`) |
| `Math.max(a, b)`, `Math.min(a, b)` | the larger / smaller of two values |
| `Math.round(x)` | `x` rounded to the nearest whole number (a `long` for `double` input) |
| `Math.floor(x)`, `Math.ceil(x)` | round down / up (as a `double`) |
| `Math.random()` | a random `double` from `0.0` (inclusive) up to `1.0` (exclusive) |
| `Math.PI`, `Math.E` | the constants π and _e_ |

A common idiom for a random integer from 1 to `n`, straight from `NumberGuesser.java`:

```java
int secretNumber = (int) (Math.random() * 1000000) + 1;   // 1 to 1,000,000
```

`Math.random()` gives a `double` in `[0, 1)`; multiplying by 1,000,000 gives `[0, 1000000)`; casting to `int` truncates to a whole number from 0 to 999,999; adding 1 shifts the range to 1 through 1,000,000.

### Recursion

A method can call itself. This is called _recursion_:

```java
public static long factorial(int n) {
    if (n <= 1) {
        return 1;                     // base case
    }
    return n * factorial(n - 1);      // recursive case
}
```

Every recursive method needs a _base case_ that doesn't recurse, or it will call itself until the program runs out of memory for method calls and crashes with a `StackOverflowError`. We'll lean on recursion heavily when we get to algorithms.

### Writing Good Methods

A few guidelines, all of which come back to _encapsulation_:

- **One method, one job.** If you can't describe what a method does in a single sentence without "and," it probably should be two methods.
- **Name it for what it does.** `computeAverage(scores)` needs no comment; `doStuff(a)` needs several.
- **Prefer returning values to printing them.** A method that _returns_ an average can be used anywhere; a method that _prints_ an average can only print it.
- **Document it.** Put a Javadoc comment above each method describing what it does, its parameters (`@param`), and its return value (`@return`).

## Scope

> [!NOTE]
> The **scope** of a variable is the region of the code in which that variable can be used. In Java, a local variable's scope runs from its declaration to the end of the **block** (the closing `}`) in which it was declared.

In Java, **every pair of braces creates a new scope**:

```java
public static void demo() {
    int a = 1;                     // a's scope: the rest of demo()
    if (a > 0) {
        int b = 2;                 // b's scope: just this if-block
        System.out.println(a + b); // fine: both in scope
    }
    System.out.println(b);         // COMPILE ERROR: cannot find symbol b

    for (int i = 0; i < 3; i++) {  // i's scope: just this loop
        // ...
    }
    System.out.println(i);         // COMPILE ERROR: cannot find symbol i
}
```

If you need a value after the block ends, declare the variable _before_ the block:

```java
int found = -1;                    // declared outside the loop
for (int i = 0; i < arr.length; i++) {
    if (arr[i] == target) {
        found = i;
        break;
    }
}
System.out.println(found);         // fine
```

A few more scope rules:

- **Parameters** are local variables whose scope is the whole method body.
- **Each method has its own scope.** Local variables in one method are completely invisible to every other method. That's why information has to travel through parameters and return values. (Two methods can both have a variable called `total` without any conflict.)
- **No redeclaring in a nested block.** Java won't let you declare a local variable with the same name as another local variable that's still in scope:

  ```java
  int x = 1;
  if (true) {
      int x = 2;   // COMPILE ERROR: variable x is already defined
  }
  ```

- **Fields are different.** A local variable or parameter _can_ have the same name as a field, in which case the local variable wins inside its scope, and you reach the field with `this`. This is exactly the `this.x = x;` situation from the constructor in [Objects](Objects.md#this-keyword).

A variable's _lifetime_ follows its scope: when execution leaves a block, the local variables declared in it cease to exist. (Objects those variables pointed to may live on, as long as something else still references them.)

---

← Previous: [Arrays](Arrays.md) · [Overview](ProceduralProgramming.md) · Next: [Putting It Together](ProceduralProgramming.md) →
