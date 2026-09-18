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

Remember our definition of a data type? 

>[!NOTE]
> A **Data Type** is a set of values and a set of operations on those values. 

In Java, a class always defines a new concrete data type. In the example above, the `Fish` class defines a new data type that represents a fish. Since each class is a new data type, that class must have both _values_ and _operations_.

### Fields

_Fields_ are the variables that contain information or properties about the object or class. They are declared at the top of the class (just below the class declaration) and are (usually) not initialized. In our example above, fields include `x`, `y`, `bodyColor`, and so on. These are all properties _each_ fish, which distinguishes objects from our previous array data structures which grouped the properties of _all_ fish (e.g. `fishXCoordinates[]`).


### Constructors

In Java a constructor looks fairly similar to a function, except that instead of a name and a return type, the Constructor simply uses the _class name_ followed by arguments:

```java
public Fish(int x, int y, int bc, int tc, int numberOfSpots, int sz, double w) {

}
```

The role of the constructor is to set up a new _instance_ of the class and initialize its fields. The constructor is invoked using the `new` keyword:

```java
Fish x = new Fish(23, 0, 0xfc0303, 0x0, 3, 1, 3.14159265);
```

<br/>

#### `this` Keyword

In the fully implemented constructor above, you may have noticed the use of the keyword `this` in lines like `this.x = x;`. In Java (and many other languages) `this` is a _pointer_ to the object we are operating on. In constructors, it's the objec that we have just created using the `new` keyword and are currently initializing.

Often, `this` is redundant. Fields have a scope corresponding to the entire class, so they can be accessed freely from (almost) anywhere. But, in our constructor above, you may notice there are naming conflicts: some fields have the same name as constructor arguments. That's not uncommon: we often want to pass field values into the constructor and there's only so many ways to name the same value. Java's scoping rules give priority to the variable with _narrower_ scope. So, in the constructor above, `x` refers to the _parameter_ `x` and not the field. We can still access the field though by using the keyword `this`, referencing `this.x`, which is the `x` field of `this` instance of `Fish`.

#### Dot ("`.`") Operator

You've now seen this mysterious `.` ("dot") floating around all over the place. Most recently in `this.x`, but also in places like `arr.length` or twice in `System.out.println()`. The dot operator is used to reference a field or method (see below) of a particular object. So `this.x` means the `x` field of `this` (`Fish` or whatever class it is).

It's actually helpful to understand exactly what an object looks like in memory. More or less, the thing is just a header describing the object plus an array-like sequence of all the fields...and that's it.

```markdown
POINTER VARIABLE                MEMORY (HEAP)
┌─────────────┐
│ spookyDog   │  ──────────►   ┌───────────────────────────┐  addr: 0x7F3A10
│ (reference) │                │  Object Header            │
└─────────────┘                │  ┌──────────────────────┐ │
                               │  │ type: Dog            │ │
                               │  │ (points to Dog class │ │
                               │  │  metadata / methods) │ │
                               │  └──────────────────────┘ │
                               │                           │
                               │  Fields (instance data)   │
                               │  ┌──────────────────────┐ │
                               │  │ name  : "Zero"       │ │
                               │  │ age   : 3            │ │
                               │  │ owner : 0x7F3B88  ──┐│ │
                               │  └─────────────────────│  │
                               └────────────────────────┼──┘
                                                        │
                                                        ▼
                               ┌───────────────────────────┐  addr: 0x7F3B88
                               │  Object Header            │
                               │  type: Person             │
                               │  ┌──────────────────────┐ │
                               │  │ name : "Skellington" │ │
                               │  └──────────────────────┘ │
                               └───────────────────────────┘
```

Hopefully its easy to see why the dot operator is always preceded by a _pointer_. The pointer, uh, points to the start of an object and then every field is some known distance below it in memory. In the diagram above of an imaginary `Dog` class, _one_ of the fields is actually a pointer to another object (of the `Person` class)! That's totally fine: it just means that one of the fields (`owner`) is itself an object with its own fields. If we wanted to find out the name of the owner, we would use two dot operators: `spookyDog.owner.name` to reference the name of the owner of the dog object in the variable `spookyDog`. Something similar is going on with `System.out.println()`.

### Methods

A method is a function inside of a class. We already know about functions. The power of methods comes from the object data structure. Conceptually, you can think of objects as containing a "copy" of each method in the class. So, when we call `fishA.swim()` this causes whatever `Fish` object `fishA` points to to swim. `fishB.swim()` perhaps causes a totally different fish to swim: each `Fish` has a "copy" of the `swim()` function.

Buuuuuut, that's not exactly true. The actual function isn't copied--you can actually see from the memory diagram above that there aren't any functions in there. Actually it's all pointer magic!

When you have a function like:

```java
public void swim() {
    this.x = // something;
    this.y = // something;
}
```

It is secretly translated to another function when you compile it:

```java
public void swim(this) {
    this.x = // something;
    this.y = // something;  
}
```

And when you call `fishA.swim` it gets translated into `swim(fishA)`, passing in the correct pointer so that when the function references `this.x` it's accessing the fields of the right object. Neat!

So, the dot operator works a little differently for fields and mehtods under the hood, but conceptually they are the same. The thing _after_ the operator is something that belongs to that specific instance.

## Static methods and fields

You have also probably seen a number of methods marked as `static`. Most commonly in `public static void main(String args[])`. It turns out, methods come in two flavors: instance and static. Instance methods are the kind we just described. They always require a specific _instance_ of a class to run and can access the fields of that instance. Conversely, _static_ methods belong only to the class itself and do not require an instance to run.

Fields, too, can be static. Unlike instance fields, they are _not_ copied into individual instances of an object in memory and represent the state of the entire class.

Static fields and methods are both accessed using the dot operator on the _class name_: `Math.pow()`, `Fish.getNextXCoordinat()`, etc.

### Uses of Static Fields and Methods

You are already familiar with _one_ use of static methods: libraries. You can see these in action in the original library version of the `Fish` class:

```java
public class Fish {
    public static void drawFish(int x, int y, int bodyColor, int tailColor, int number of spots, int size) {
        // Implementation not shown...
    }

    public static int getNextXCoordinate(int x, double fishWeight) {}

    public static int getNextYCoordinate(int y, double fishWeight) {}
}
```

The disadvantage of this particular class is obvious: a `this` pointer could have passd in all of the parameters rather than this mess, but plenty of classes in Java provide useful library functions: `Math`, `System`, and `Arrays` all provide essential functions that you are already using.

#### Factory Methods

Sometimes constructing an instance requires lots of instances of 
