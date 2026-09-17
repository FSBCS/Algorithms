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

Do you see the problem? The aquarium class contains all sorts of data about its fish in a bunch of different arrays. That's pretty cumbersome. Then, anytime we want to draw a fish, we have to look up the relevant data from each array and pass it off to the `Fish` library. In fact, apart from the initial setup of each fish, the `Aquarium` class does nothing with this data except repeatedly pass it off to the `Fish` class. Perhaps more annoying, the `Fish` class cannot update the data on its own: we have to calculate the fish's next location and then the aquarium class has to make those changes itself. Imagine if we wanted to add crabs or eels or some other type of creatuer! This would get messy fast.

### Object-Oriented Programming

Thes solution to this program is to broaden our understanding of _classes_ from simple libraries into templates for structures called _objects_. An object is a data structure defined by a class that bundles functionality with the relevant data to achieve that functionality. We'll explain all the details in a minute, but let's look at what happens when we use `Fish.java` to define a `Fish` object:

```java
public class Fish {
    int x, y;
    int bodyColor, tailColor;
    int numberOfSpots, int size;
    double weight;

    public Fish(int x, int y, int bc, int tc, int numberOfSpots, int sz, double w) {
        this.x = x;
        this.y = y;
        this.bodyColor = bc;
        this.tailColor = tc;
        this.size = sz;
        this.weight = w;
    }

    public void draw() {
        // Implementation hidden
    }

    public void swim() {
        this.x = /* some formula */
        this.y = /* some formula */
    }
}
```

```java
public class Aquarium {
    public static void main(String[] args) {
        Fish[] fish = new Fish[10];

        fish[0] = new Fish(23, 0, 0xfc0303, 0x0, 3, 1, 3.14159265);
        fish[1] = new Fish(-5, 91, 0xa1ffa1, 0xbadbed, 0, 2, 67.0);
        fish[2] = new Fish(16, 100, 0x361d57, 0xbeef42, 123456, 5, DOUBLE.POSITIVE_INFINITY);

        // ... More fish created, but not shown

        while (true) {
            for (int i = 0; i < fish.length; i++) {
                fish[i].draw();
                fish[i].swim();
            }
        }
    }
}
```

Notice how much simpler the `Aquarium` class is! We still need to tell the fish class all the specific data (that's okay--our Aquarium _should_ be the place that data originates because every Aquarium has unique fish), but once we do that, it lives permanently inside the `Fish` class.

Actually, what's happening is that, instead of maintaining lots of arrays for each property of a fish, we are now creating many `Fish` objects: each fish "knows" all of its own property values. What's more, since it knows its values, each `Fish` object can perform its own `swim()` and `draw()` actions _without_ needing to have all the data sent back in from the `Aquarium` class!

## Anatomy of a Class
