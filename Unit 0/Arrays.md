# Arrays

> [!NOTE]
> An **array** is a fixed-size, ordered sequence of values that all have the same type. Each value (an _element_) is accessed by its _index_, starting at 0.

Arrays are Java's most basic data structure. They come with two important restrictions:

1. **Every element has the same type**, declared up front. An `int[]` holds only `int`s.
2. **The size is fixed** when the array is created. There's no `append`. If you need a bigger array, you create a new one and copy the elements over.

(For a resizable, list-like structure, Java provides `ArrayList`, which we'll meet with the Collections framework.)

## Creating Arrays

The type of an array is written as the element type followed by square brackets: `int[]` is "array of `int`," `String[]` is "array of `String`." There are two ways to create one:

```java
int[] scores = new int[5];              // 5 elements, all set to the default value 0
String[] names = {"Ada", "Alan", "Grace"};   // array literal: size and contents given directly
```

With `new int[5]`, every element starts at its type's default value (`0` for numbers, `false` for `boolean`, `null` for reference types; see [Default Values](VariablesAndTypes.md#default-values)). The array literal form `{...}` can only be used in a declaration. Anywhere else, you need to write `new int[] {1, 2, 3}`.

(You may also see `int scores[]` with the brackets after the name. It's legal, left over from C, but `int[] scores` is preferred because it keeps the whole type together.)

## Accessing Elements

```java
names[0]                 // "Ada"
names[2] = "Katherine";  // replace the element at index 2
names.length             // 3  -- a field, not a method: no parentheses!
names[names.length - 1]  // the last element
```

There's no negative indexing: `names[-1]` is an error, not the last element. Accessing any index outside `0` to `length - 1` throws an `ArrayIndexOutOfBoundsException` at runtime.

## Arrays Are Reference Types

An array variable holds a _reference_ to the array, not the array itself. This has the consequences we discussed under [Reference Types](VariablesAndTypes.md#reference-types):

```java
int[] a = {1, 2, 3};
int[] b = a;          // b points to the SAME array
b[0] = 99;
System.out.println(a[0]);   // 99

int[] c = {1, 2, 3};
System.out.println(a == c);                  // false: different arrays
System.out.println(Arrays.equals(a, new int[] {99, 2, 3}));  // true: same contents
```

To make an independent copy, use `Arrays.copyOf(a, a.length)` or `a.clone()`.

## Printing Arrays

Printing an array directly gives you something unhelpful:

```java
int[] a = {1, 2, 3};
System.out.println(a);                   // [I@1b6d3586  (type code + memory hash)
System.out.println(Arrays.toString(a));  // [1, 2, 3]
```

## The `Arrays` Library

The `java.util.Arrays` class (you'll need `import java.util.Arrays;`) is a library of static methods for working with arrays:

| Method | Purpose |
| --- | --- |
| `Arrays.toString(arr)` | a readable string like `"[1, 2, 3]"` |
| `Arrays.sort(arr)` | sorts the array in place |
| `Arrays.fill(arr, value)` | sets every element to `value` |
| `Arrays.copyOf(arr, newLength)` | a new array with the elements copied (padded with defaults or truncated) |
| `Arrays.equals(a, b)` | whether two arrays have the same contents |

## Multidimensional Arrays

A two-dimensional array is really an array of arrays:

```java
int[][] grid = new int[3][4];     // 3 rows, 4 columns, all zeros
grid[1][2] = 7;                   // row 1, column 2

int[][] table = {
    {1, 2, 3},
    {4, 5, 6}
};
table.length       // 2  (number of rows)
table[0].length    // 3  (number of columns in row 0)
```

To visit every element, use nested loops:

```java
for (int r = 0; r < table.length; r++) {
    for (int c = 0; c < table[r].length; c++) {
        System.out.print(table[r][c] + " ");
    }
    System.out.println();
}
```

Since each row is its own array, rows don't all have to be the same length (a "jagged" array). Using `table[r].length` rather than a fixed number in the inner loop handles that case correctly. Use `Arrays.deepToString(table)` to print a 2D array.

## Command-Line Arguments

Now you can see what `String[] args` in `main` is: an array of `String`s. When you run `java MyProgram apple banana`, `args` is `{"apple", "banana"}`. If you run the program with no extra words, `args` is an empty array (length 0).

---

← Previous: [Control Flow](ControlFlow.md) · [Overview](ProceduralProgramming.md) · Next: [Methods and Scope](Methods.md) →
