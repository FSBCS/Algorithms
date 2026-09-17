# Objects

Java is called an "Object-oriented" language. This indicates that a data structure called an _object_ is the fundamental unit used for structuring _both_ the data and code of a program.

## Encapsulation

You may have heard the word _encapsulation_ used in reference to functions. We often think of functions as the building blocks of a program since they enclose or _encapsulate_ some functionality that can then be called repeatedly. Functions make our code more stable, intelligible, and maintainable, especially compared to typing out the same code repeatedly. At this point in your coding career, you've probably written several functions and should be comfortable working with them.

### Libraries

But, you don't need to write _every_ function you use. Java itself comes with a number of _libraries_, which bundle a bunch of functions in their own file. A great example is `Math.java`, which contains a number of super useful functions. Importantly, you don't need to know how they work or ever see the code (the function `Math.pow()` alone is about 250 lines). Instead, you call the functions and just trust they give the output they promise to give.

Bundling functions into libraries is an incredibly powerful tool that has been around since the beginning of procedural programming. Computational thinking is almost synonymous with the expression "divide and conque:" if we do our job right, we nearly always end up with short, targeted functions that perform one task clearly and reliably. If we're working broadly enough, those functions become tools that can be used in an numbe of programs. Hence, nearly every modern programming language comes with some standard library for abstracting the most basic functionality (printing, math, etc.).

### Limitations of Libraries

Even with the usefulness of libraries like the `Math` class in Java, purely functional programming often runs into some significant limitations. Consider a program that simulates an aquarium that contains a bunch of fish. Suppose that someone has also created a Fish library for drawing individual Fish so you don't have to invent that code on your own. _But_, which fish you draw in your aquarium is up to you--this library is quite general and doesn't just lock every aquarium into having the same fish.

That means that our aquarium needs to keep track of all the information about _its_ fish, so that it can request drawings from the fish class. Here's what that would look like:

```java
/**
 *  Library for drawing and moving fish.
 */
public class Fish {
    public static void drawFish(int x, int y, int bodyColor, int tailColor, int number of spots, int size) {
        // Implementation not shown...
    }

    // If the fish is swimming, calculate its next (x, y) coordinate
    // based on its weight.
    public static int getNextXCoordinate(int x, double fishWeight) {}

    public static int getNextYCoordinate(int y, double fishWeight) {}
}
```
```java
public class Aquarium {
    public static void main(String[] args) {
        int[] fishXCoordinates = {23, -5, 16, ...};
        int[] fishYCoordinates = {0, 91, 100, ...};

        // Fish colors using hex codes
        int[] fishBodyColors = {0xfc0303, 0xa1ffa1, 0x361d57, ...};
        int[] fishTailColors = {0x0, 0xbadbed, 0xbeef42, ...};

        int[] fishSpotCount = {3, 0, 123456, ...}
        int[] fishSizes = {1, 2, 5, ...};

        double[] fishWeights = {3.14159265, 67.0, Double,POSITIVE_INFINITY};

        // Repeatedly draw and update all fish forever.
        while (true) {
            for (int i = 0; i < fishXCoordinates.length; i++) {
                drawFish(i);
                swimFish(i);
            }
        }

    }

    /* Draw the i-th fish */
    public static void drawFish(int i) {
        Fish.drawFish(fishXCoordinates[i], fishYCoordinates[i], fishBodyColors[i],fishTailColors[i], fishSpotcount[i], fishSizes[i]);
    }

    /* Update the (x, y) coordinates of the fish */
    public static void swimFish(int i) {
        fishXCoordinates[i] = Fish.getNextXCoordinate(fishXCoordinates[i], fishWeights[i]);
        fishYCoordinates[i] = Fish.getNextXCoordinate(fishYCoordinates[i], fishWeights[i]);
    }
}
```
