# Inheritance and Polymorphism

In case object-oriented programming wasn't already sufficiently exciting after the last section, get ready for the second and third principles of object-oriented programming!

## Three Principles of OOP

If a programming language is "object oriented" it always provides some means of implementing three important principles:

>[!NOTE]
> There are **Three Principles of Object-Oriented Programming**:
>
> - Encapsulation
> - Inheritance
> - Polymorphism

In the previous section, we saw that _encapsulation_ meant putting data with the code that needed it. In Java, _classes_ (not functions) are the primary means of encapsulation (although, we certainly still want to write good functions). Viewed from the outside, each class represents a set of functionality that is implemented internally. This allows classes to interact with each other without needing to  understand their inner workings: the functionality is _abstracted_. The set of functions that can be invoked on the class from outside is called an _Application Programming Interface_ or _API_.

Consider the `Scanner` class. Its API (simplified here) would look something like this:

| Function | Description |
| --- | --- |
| `Scanner(InputStream source)` | Creates a scanner that reads from the specified input stream. |
| `String nextLine()` | Reads and returns the next line of input. |
| `String next()` | Reads and returns the next token. |
| `int nextInt()` | Reads and returns the next integer. |
| `boolean hasNextLine()` | Returns whether another line of input is available. |
| `boolean hasNextInt()` | Returns whether the next token is an integer. |
| `void close()` | Closes the scanner and its input source. |

From your own class, you can invoke any of the functionality from that table. Something like:

```java
public class Hello {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);  // Read from StdIn (command line)
        System.out.print("Please enter your name: ");
        String name = input.next();
        System.out.println("Hello, " + name);

        input.close();
    }
}
```

Importantly, we have no idea how an instance of the `Scanner` class does any of that. And yet, the scanner object behaves normally. The same thing was true of our `Fish` class: the `Aquarium` class had no need to know any of the implementation details--it only needed to manipulate individual fish _objects_. That abstraction (hiding the implementation details of the functionality) is what makes classes so useful.

### Inheritance

In any given program, we are often working with classes with similar APIs. Consider an example. You are coding a fantasy adventure game with different classes of characters. Let's say there's a `Wizard`, `Warrior`, and `Archer`. Here's how those classes might look:

```java
class Wizard {
    private String name;
    private int health;
    private int mana;

    public Wizard(String name, int health, int mana) {
        this.name = name;
        this.health = health;
        this.mana = mana;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        System.out.println(name + " took " + amount + " damage. Health: " + health);
    }

    public void castSpell() {
        if (mana >= 10) {
            mana -= 10;
            System.out.println(name + " cast Fireball! Mana remaining: " + mana);
        } else {
            System.out.println(name + " is out of mana!");
        }
    }

    public void displayStats() {
        System.out.println("Name: " + name + " | Health: " + health);
        System.out.println("Role: Wizard | Mana: " + mana);
    }
}
```

```java
class Warrior {
    private String name;
    private int health;
    private int armor;

    public Warrior(String name, int health, int armor) {
        this.name = name;
        this.health = health;
        this.armor = armor;
    }

    public void takeDamage(int amount) {
        int reducedAmount = Math.max(0, amount - armor);
        System.out.println(name + "'s armor blocked " + (amount - reducedAmount) + " damage!");
        this.health -= reducedAmount;
        System.out.println(name + " took " + reducedAmount + " damage. Health: " + health);
    }

    public void displayStats() {
        System.out.println("Name: " + name + " | Health: " + health);
        System.out.println("Role: Warrior | Armor: " + armor);
    }
}
```

```java
class Archer {
    private String name;
    private int health;
    private int arrows;

    public Archer(String name, int health, int arrows) {
        this.name = name;
        this.health = health;
        this.arrows = arrows;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        System.out.println(name + " took " + amount + " damage. Health: " + health);
    }

    public void shootArrow() {
        if (arrows > 0) {
            arrows--;
            System.out.println(name + " shot an arrow! Arrows remaining: " + arrows);
        } else {
            System.out.println(name + " has no arrows left!");
        }
    }

    public void displayStats() {
        System.out.println("Name: " + name + " | Health: " + health);
        System.out.println("Role: Archer | Arrows: " + arrows);
    }
}
```

Wait a minute! There's _a lot_ of overlap between these classes! In fact, there's some code (like the `takeDamage(int amount)`) that is nearly identical in all three classes. That seems to break our rule about functions: "Don't Repeat Yourself."

> [!NOTE]
> As often as possible our code should be **DRY** (i.e. it follows "Dont' Repeat Yourself") and not **WET** ("Write Everything Twice"). If we find ourselves typing out identical code or (owrse) copying and pasting, there is probably a better way!

Good news for us, there is a better way. We can move the overlapping functionality from each of the classes into a shared API. Consider this class:

```java
abstract class GameCharacter {
    protected String name;
    protected int health;

    public GameCharacter(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        System.out.println(name + " took " + amount + " damage. Health: " + health);
    }

    public void displayStats() {
        System.out.println("Name: " + name + " | Health: " + health);
    }
}
```

The `GameCharacter` class now houses all of our shared functionality! The trick is now to get it _into_ the three classes from before:

```java
class Wizard extends GameCharacter {
    private int mana;

    public Wizard(String name, int health, int mana) {
        super(name, health); // Pass common attributes to parent constructor
        this.mana = mana;
    }

    public void castSpell() {
        if (mana >= 10) {
            mana -= 10;
            System.out.println(name + " cast Fireball! Mana remaining: " + mana);
        } else {
            System.out.println(name + " is out of mana!");
        }
    }
}

class Archer extends GameCharacter {
    private int arrows;

    public Archer(String name, int health, int arrows) {
        super(name, health);
        this.arrows = arrows;
    }

    public void shootArrow() {
        if (arrows > 0) {
            arrows--;
            System.out.println(name + " shot an arrow! Arrows remaining: " + arrows);
        } else {
            System.out.println(name + " has no arrows left!");
        }
    }
}

class Warrior extends GameCharacter {
    private int armor;

    public Warrior(String name, int health, int armor) {
        super(name, health);
        this.armor = armor;
    }
}
```

Notice the extra bit in the class declaration--there's a new keyword: `extends`. This means that any (public or protected--more on that later) function inside the `GameCharacter` class will be "copied" into the `Wizard` class. So, we can do things like:

```java
Wizard w = new Wizard("Steve", 100, 100);
w.takeDamage();
```

...eventhough the `Wizard` class has no such function: it was _inherited_ from the `GameCharacter` class. When one class _extends_ another, we call the class extended from the _superclass_ and the extending class the _subclass_.

#### Inheritance and Fields

Fields, also, are inherited by subclasses. In memory they are treated as though they were declared in the subclass itself, so these literally are "copied" into the subclass. Inside the class, fields can be accessed like normal using the `this` pointer; outside, they can be accessed using a pointer to a specific instance. So, with our `Wizard` object above, we could reference `w.health`.

#### Inheritance and Constructors

Unlike functions and fields, constructors are _not_ inherited, but they are related to each other. In order to understand how, we need to look at the "is-a" relationship between sub- and superclass.

## Polymorphism

Instances of a subclass are _also_ instances of the corresponding superclass. In fact, if you use the Java keyword `instanceof` you can test this:

```java
Wizard w = new Wizard("Jeff" 10, 10);

if (w instanceof GameCharacter) {
    System.out.println("It's a GameCharacter");
}
```

This _will_ in fact print "It's a GameCharacter", because `w` is a `Wizard` and all `Wizard`s are `GameCharacters`. But, superclasses are _themselves_ classes: they encapsulate their own data and functionality. So, when we setup a new `Wizard` object, we _first_ need to set it up as a new `GameCharacter` object.

### Constructors and SuperConstructors

Let's start off with a general rule about constructors that has (almost) no exceptions:

> [!NOTE]
> Every constructor must call a constructor of its superclass.

So, constructors aren't really inherited from superclass by subclass; rather they are "chained," with the superconstructor happening first (generally) and the sub-constructor happening after. To invoke the superconstructor, we use the keyword `super`. For example, in the `Wizard` class above:

```java
    public Wizard(String name, int health, int mana) {
        super(name, health);
        this.mana = mana;
    }
```

Since the name of the character and initial health are encapsulated in the `GameCharacter` class, we pass these two parameters up the chain using `super(name, health)`, invoking the `GameCharacter` constructor:

```java
    public GameCharacter(String name, int health) {
        this.name = name;
        this.health = health;
    }
```

The client, however, sees none of this when constructing a new `Wizard`. They simply invoke the `Wizard` constructor `new Wizard("Johan", 5, 5);` with all the parameters and the `Wizard` constructor decides which fields to initialize itself and which to pass up to the superconstructor behind the scenes.

#### Sneaky Constructors

Despite the note above, not every class makes an explicit call to its super-constructor. Is that allowed? At least sometimes, yes. If a constructor does not include a call to any super-constructor, java will add the call automatically to the beginning of the constructor by invoking `super();` with no arguments (this is often called the _no-args_ or _default_ constructor).

This is an especially helpful fact to know when you're trying to debug a compiler error about a constructor that, seemingly, doesn't exist. This code, for example, does not compile:

```java
class A {
    public A(int x) {
    }
}

class B extends A {
    public B() {
    }
}
```

Instead, you get a weird error message:

```bash
A.java:11: error: constructor A in class A cannot be applied to given types;
    public B() {
               ^
  required: int
  found:    no arguments
  reason: actual and formal argument lists differ in length
1 error
```

In other words, the constructor it "found" was the no-args constructor, but the `A` class requires an `int` argument. We didn't type `super()` into the code; nevertheless, it was added to the `B()` constructor for us.

There's another kind of sneaky constructor, though, known as the _default constructor_. Consider the opening lines of the `Math` class in the Java source code:

```java
public final class Math {

    /**
     * Don't let anyone instantiate this class.
     */
    private Math() {}
```

What's going on with that constructor? Why does _adding_ it to the class prevent anyone from instantiating it? In Java, _every_ class has a constructor. Even your original Hello world program. Weirdly, this will compile:

```java
public class Hello {
    public static void main(String[] args) {
        Hello x = new Hello();

        System.out.println("Hello, World");
    }
}
```

You can even invoke the constructor `new Hello()` from _another_ class altogether. Try it! This works because, when no other constructor is provided, a no args constructor is automatically generated. So, after compiling, our original `Hello` class becomes:

```java
public class Hello {
    public Hello() {}

    public static void main(String[] args) {
        System.out.println("Hello, World");
    }
}
```

The compiler-generated constructor is always no-args and has no body (except for a call to the super-constructor).

### But, what is it good for?

There's been a lot of setup for the real power of this Polymorphism thing. Here's the big key in a nice note box:

> [!NOTE]
> Objects of a sub-type are instances of the super-type and can be stored in super-typed variables.

Superficially, it means that `GameCharacter x = new Wizard("Marty", 25, 30);` is a perfectly legal expression since `Wizard` is a subclass of `GameCharacter`. In practice, it's a super useful tool. Principally, it allows us to treat objects of a variety of specific types as the same type when the specifics don't matter. Consider a function that inflicts damage upon all of the characters in a party of `GameCharacters`.

```java
public static void damageParty(GameCharacter[] party, int damage) {
    for (GameCharacter gc : party)
        gc.takeDamage(damage);
}
```

Remember that the `takeDamage(damage)` function was defined in the `GameCharacter` class, so, even if the elements of that array `party` are instances of a subclass, we know they will inherit the `takeDamage()` functionality. Even if our party looked like this:

```java
GameCharacter[] party = new GameCharacter[3];
party[0] = new Wizard("Biff", 71, 12);
party[1] = new Archer("Archie", 9, 2);
party[2] = new Warrior("Lisa", 150, 2000);
```

We can still call:

```java
damageParty(party, 10);
```

While this is very bad new for Archie, everything will work correctly.

### Overriding Methods

Next, let's think about some nuances between the `GameCharacter` objects in the way they handle their inherited behavior. Suppose the warrior's special ability, unlike shooting arrows and casting spells, is actually taking less damage than the others. But, the `takeDamage()` function belongs to `Warrior`'s super class, so we can't change that without changing the method for all the other subclasses.

The solution is _overriding_ the `takeDamage()` function. Here's how that might look:

```java
class Warrior extends GameCharacter {
    private int armor;

    public Warrior(String name, int health, int armor) {
        super(name, health);
        this.armor = armor;
    }

    @Override
    public void takeDamage(int amount) {
        int reducedAmount = Math.max(0, amount - armor);
        System.out.println(name + "'s armor blocked " + (amount - reducedAmount) + " damage!");
        super.takeDamage(reducedAmount);
    }
}
```

When we include a function in a subclass with the same signature in the subclass (in this case `takeDamage(int)`), the subclass function _overrides_ the superclass function. So if we have something like this:

```java
Warrior x = new Warrior("Gladys", 10, 5);
x.takeDamage(7);
```

The Warrior's health will only be depleted by 2 (leaving her with 8), because the `Warrior` class's `takeDamage()` function runs instead.

You will notice a familiar keyword, though: `super`. Though, this time it's used in a new pattern: `super.takeDamage()`. This invokes the overridden method inside the superclass `GameCharacter`. Its often useful to do this because the superclass usually _encapsulates_ both the data and base functionality, so we often don't need to reinvent the wheel. Here, we adjust the parameter a bit and invoke the parent method via `super`. But, unlike with constructors, we do not **have** to do this; it's just helpful here. Note, also, that invoking the overridden method is really only possible from inside the overriding method--that's on purpose as the next section will make clear.

> [!TIP]
> When overriding a method, its useful to include the `@Override` tag directly above it. This informs the compiler that you are intending to override something. If you make a mistake and it _doesn't_ override (wrong spelling or parameters, for example) the code will not compile. This can catch frustrating bugs before you have to chase down which method is actually running.

### Dynamic Method Lookup

Here's a little puzzle. Take our `damageParty()` function from earlier:

```java
public static void damageParty(GameCharacter[] party, int damage) {
    for (GameCharacter gc : party)
        gc.takeDamage(damage);
}
```

Let's suppose we pass it an array with two `GameCharacter` objects in it.

```java
GameCharacter[] party = new GameCharacter[1];
party[0] = new Wizard("Henri", 100, 100);
party[1] = new Warrior("Karen", 100, 10);

damageParty(party, 10);
```

How much damage will each of them take--that is, which version of the `takeDamage()` function will run? Both objects are in an array of type `GameCharacter`, so the `damageParty()` method only knows that each of them belongs to that superclass, so perhaps it will have no choice to default to that method for both the `Wizard` and the `Warrior`.

Actually, Java is a little more powerful than that. At _runtime_, whenever an overridden function is called it is the type of the _actual object_ that determines the function that is run, not the type of the variable or parameter. So, in the `damageParty()` function, Java will notice that first item in the array is a `Wizard` (which does not override `takeDamage()`) and call the `takeDamage()` function inherited from `GameCharacter`. But, it will notice that the _second_ object is actually a `Warrior` (which has does override the method) and will run _its_ `takeDamage()` method instead. This process is known as _Dynamic Method Lookup_.

> [!NOTE]
> Java uses _Dynamic Method Lookup_ to select at runtime the correct overriding method to run based on the actual type of objects--not the type of the variable that contains/points to them.

## The Magic of OOP

We've created a really powerful system here: encapsulation, inheritance, and polymorphism all work together to make functionality seamless without the need for clients to introspect classes. The `damageParty()` method gives a simple but clear demonstration of this. By encapsulating universal functionality in the superclass, we guarantee a consistent API while hiding the potentially messy implementation details. Inheritance ensures classes like `Wizard` that extend the `GameCharacter` class get to keep that functionality while adding some of their own. Finally, polymorphism, through dynamic method lookup, ensures that whenever a part of the API of `GameCharacter` is envoked, the correct version of that functionality is performed based on the actual object.

The end result is that the `takeDamage(GameCharacter[] party, int damage)` funtion only needs to know that the items in the array are `GameCharacters` and a single line of code, `gc.takeDamage(damage)` just works seemlessly while Java does all the dirty work behind the scenes. _That_ is the whole point of OOP: when classes are structured well, the code we write using them just works without having to look inside them. It's almost like magic! Almost.

## `Object` the Universal Superclass

Earlier, we noted that _every_ class must call its super-class with one exception. That might seem weird: what is the superclass of `GameCharacter`? It doesn't appear to `extend` any class? Well, here's the answer to that and the one exception: the `Object` class.

> [!NOTE]
> In Java, every class which does not extend another class extends the `Object` class.

For this reason, we call `Object` the "universal super class." In other words, if you trace the inheritance hierarchy back far enough, eventually you will find that some class extends `Object`. In other-er words, every object is an `Object`: instances of `Warrior` are instances of `Object`, so are instances of `Wizard` and `String`...and every other class. Thus, it is _always_ acceptable for any class to use `Object` as the variable data type:

```java
Object o = new Warrior("Shirley", 38, 2);
```

The object class also comes with some functionality that is about as esoteric as expected. These exist to ensure that every object has certain methods, though they're really only useful when overridden.

For example, you will recall from the business of pointers that this:

```java
String a = "Hello";
String b = "Hello";
System.out.println(a == b);
```

...prints `false` because, though the strings are the same characters, the variables `a` and `b` are pointers to two totally separate--but identical--strings stored in two totally separate memory addresses. When we compare pointers, we are comparing the literal pointers. So, the `Object` class gives us the function `equals(Object o)`.

The implementation in the class is comical:

```java
public boolean equals(Object obj) {
    return (this == obj);
}
```

By default, it just compares pointers because there's not really much else it can do: instances of `Object` alone have no fields to base any comparison on. Fortuately the `String` class overrides the `equals(Object o)`, comparing two separate strings character by character.

The `toString()` function you may also recognize:

```java
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```

You've seen this jumbled mess of ClassName@pile-of-numbers if you've ever tried to put an object (other than a string) or an array (which is sort of a "special" quasi-object) directly into `System.out.println()`, a function which calls `toString()` on any parameter you put into it, hence the weird output (the `hashCode()` is just the object's memory address by default).

## Dynamic vs Static Binding

In closing, we should note a small warning about function binding. _Binding_ means moving from a method _name_ to the actual code run. With overriding methods, we saw an example of _dynamic_ or _late_ binding. Overriding methods share a _name_ but have different _code_. In that case, Java waits until runtime (the "latests" possible moment) to resolve the name and "bind" it to the actual code that gets run.

That only applies to _overriding_ methods, and there is another way methods can have the same name: _overloading_ methods. Consider an example from some class outside of the `Warrior` and `GameCharacter` classes:

```java
void whatIsIt(GameCharacter gc) {
    System.out.println("I am a GameCharacter!");
}

void whatIsIt(Warrior w) {
    System.out.println("I am a Warrior!");
}

GameCharacter x = new Warrior("Linda", 25, 6);
whatIsIt(x);
```

Here dynamic method look up does _not_ kick in: This code prints `I am a GameCharacter`. There are a number of good reasons for this. For one, this isn't really object-oriented programming anymore. We're dealing with functions _outside_ the `GameCharacter` inheritance structure, so we've lost all the nice encapsulation and inheritance benefits.

The other reason is something you're more likely to encounter in the wild: type checking. When the compiler examines whether or not a function is allowed to run, it looks at the types of variables and parameters; it does _not_ try to figure out what specific type a variable or parameter might contain at any given moment. That why this:

```java
public void wizardSwarmAttack(GameCharacter[] wizards) {
    for (GameCharacter gc : wizards) {
        gc.castSpell();
    }
}
```

...doesn't compile. You will get a "Cannot find symbol: `castSpell`" error because the compiler is checking the parameter type (`GameCharacter`) against the function invoked on it (`castSpell()`). `GameCharacter` has no such function, so compilation falis--even if you only ever pass in `Wizard` objects to the function. The compiler cares only about variable and parameter types when deciding if a function is valid or not.

This and the overloading methods is known as _static_ or _early_ binding because the method is resolved to actual code earlier at _compile time_ when only the types of variables are known.

> [!WARNING]
> Java uses _dynamic_ (late) binding to select between overriding methods at runtime and _static_ (early) binding to verify methods and to decide between _overloading_ methods at compile time.

This might seem kind of annoying, but it is actually a feature of Java. Java is called "type safe" because the compiler enforces typing at compile time. It requires a function to be _certain_ that it can perform all of its operations before it will compile. If we put in a type that doesn't match the compiler will catch it and we can fix the problem before it blows something up at runtime. That makes the code a little more verbose and rigid, but it also makes it more stable and forces the programmer into disciplined design patterns.
