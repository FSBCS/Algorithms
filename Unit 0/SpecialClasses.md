# Special Classes

In this lesson, we will discuss and compare three special cases of classes: **Abstract Classes**, **Interfaces**, and **Immutable Classes**

## Abstract Class

In our previous lesson on inheritance and polymorphism, we created a `GameCharacter` class:

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

Note the keyword `abstract` in the class declaration. There are a couple of uses of this keyword, but in this case, it means that the `GameCharacter` class is not meant to be instantiated directly. Thus, we are prevented from trying something like `GameCharacter x = new GameCharacter("Bob", 12)`. That was a reasonable decision because, in a real game of whatever we're playing, there are no pure `GameCharacters` walking around: everyone is a `Wizard` or `Archer` or `Warrior`: always a specific _sub_-class.

However, abstract classes still _must_ have constructors. Even though they cannot be instantiated directly they do still encapsulate certain functionality and may have fields that need to be initialized to perform those functions. Besides, they _can_ be instantiated _through their subclasses_. That is, there really are instances of `GameCharacters` walking around--they're just a specific sub-type also.

The point of an abstract class is to define a data type that encompasses many subclasses, providing a common API for all of them.

### Abstract Methods

As designed, the `GameCharacter` class is kind of lame: it gives the impression the characters just take a bunch of damage and then run out of health. In reality, each class can attack (except, at the moment, for the `Warrior`), but the attack is totally different for each and there's no common functionality to inherit. Still, it would be nice to be able to have a function like:

```java
public void partyAttack(GameCharacter[] party) {
    for (GameCharacter gc : party)
        gc.attack();
}
```

Enter abstract methods! Let's update our `GameCharacter` class and see how they work:

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

    // Abstract method: Every subclass MUST implement its own version of attack()
    public abstract void attack();
}
```

The method `attack()` is called "abstract" because we're providing the concept of the method in the superclass, but not its actual implementation. Every subclass must override all abstract methods of a parent abstract class. This allows the compiler check to succeed and the porgram to run: the compiler sees that a method called `attack` exists for `GameCharacter` objects (static type checking) and the JVM selects the correct overriding method at runtime using dynamic method lookup. For this reason, abstract methods are only allowed in abstract classes (otherwise they would need an implementation).

> [!NOTE]
> An **Abstract Method** is a method declared in an abstract superclass which _must_ be overridden by every subclass

Here's how the overriding would look in the subclasses:

```java
class Wizard extends GameCharacter {
    private int mana;

    public Wizard(String name, int health, int mana) {
        super(name, health);
        this.mana = mana;
    }

    @Override
    public void attack() {
        if (mana >= 10) {
            mana -= 10;
            System.out.println(name + " casts Fireball for 25 damage! Mana remaining: " + mana);
        } else {
            System.out.println(name + " swings staff weakly for 2 damage (Out of mana!).");
        }
    }

    @Override
    public void displayStats() {
        super.displayStats();
        System.out.println("Role: Wizard | Mana: " + mana);
    }
}
```

## Interfaces

Interfaces are not _technically_ classes, but they are closely intertwined with them and _very_ similar to abstract classes. An interface is simply a list of methods that a class must implement.

It's easiest to see them in action. Consider the very simple interface `Healable`:

```java
/** Describes an entity in a fantasy adventure game that
 * can be healed.
 */
public interface Healable {
    /**
     * Increases the health of the entity by a fixed amount.
     */
    void heal(int amount);
}
```

All the interface tells us is that the classes that implement it will have a function called `heal(int)`. In fact, from the function alone, we're not really given any hints about _how_ it should work. For that reason, interfaces often come with a lot of comments about how to implement them.

Speaking of implementing them, let's updat our `GameCharacter` class to make instances "healable."

```java
abstract class GameCharacter implements Healable {
    protected String name;
    protected int health;
    protected int maxHealth;

    public GameCharacter(String name, int health) {
        this.name = name;
        this.health = health;
        this.maxHealth = health; // Keeps track of maximum health
    }

    // Fulfilling the Healable interface contract
    @Override
    public void heal(int amount) {
        this.health = Math.min(this.health + amount, maxHealth);
        System.out.println(name + " was healed for " + amount + " HP! Current Health: " + health + "/" + maxHealth);
    }

    // Other functions hidden
}
```

We indicate that a class implements an interface using the keyword `implements`. This is a "promise" to the compiler that we will fully _implement_ every function specified by the interface. If we fail to do so, the compiler will throw an error.

### Interfaces vs. Abstract Classes

Interfaces are quite similar to abstract classes but much narrower in scope. They are _only_ a list of functions and _not_ a superclass. This means they don't tie a class into an inheritance structure. So a class can extend one class _and_ implement an interface--these accomplish two different, but overlapping goals. Also unlike abstract classes, a class can implement multiple interfaces. Take a new `Horse` class in our game:

```java
interface Healable {
    void heal(int amount);
}

interface Sellable {
    int getPrice();
}

class Horse implements Healable, Sellable {
    private String name;
    private int health;
    private int maxHealth;
    private int energy;
    private int price;

    public Horse(String name, int health, int energy, int price) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.energy = energy;
        this.price = price;
    }

    // Horse-specific behavior: consuming energy
    public void gallop() {
        if (energy >= 10) {
            energy -= 10;
            System.out.println(name + " gallops swiftly! Energy remaining: " + energy);
        } else {
            System.out.println(name + " is too exhausted to gallop!");
        }
    }

    // From Healable interface
    @Override
    public void heal(int amount) {
        this.health = Math.min(this.health + amount, maxHealth);
        System.out.println(name + " (Horse) was healed for " + amount + " HP! Health: " + health + "/" + maxHealth);
    }

    // From Sellable interface
    @Override
    public int getPrice() {
        return price;
    }
}
```

Notice the use of the `@Override` tag: even though we don't specify a method body in the interface, we do supply an abstract function that is implemented in the `Horse` class. That still counts as "overriding."

When a class implements an interface, we can use that interface as a data type to store many different objects that implement a sellable interface:

```java
public void HealParty(Healable[] party, int amount) {
    for (Healable h : party)
        h.heal(amount);
}
```

As with abstract classes, the data type is serving the purpose of a "contract" with the compiler that ensures type safety. There are no pure `Healable` objects out there, but the `Healable` data type indicates that any object of that type (i.e. that implements the `Healable` interface) will have all the functionality of that interface (in this case just `void heal(int)`). Of course, the actual _version_ of that function is unknown to the compiler: dynamic method lookup handles that at runtime.

## Immutable Classes

Our final "special" class is not another construct in the Java language but rather a coding "practice."

> [!NOTE] Definition
> An **Immutable Class** is a class whose instances cannot change their internal state after construction.

Every object has an "internal state," which is a fancy way of saying "the current set of values of all the parameters." An immutable class is not permitted to change _any_ of these values once the constructor initializes them. To achieve immutability we have to introduce one `final` keyword...

> [!NOTE] Definition
> The modifier `final` indicates that the variable, method, or class modified cannot be updated (once initialized), overridden, or extended.

We can make a class _immutable_ by:

1. Declaring the class as `final`
2. Mark all fields as both `private` _and_ `final`
3. Do not provide "setter" methods
4. Initialize all fields in the constructor
5. Defensivelly copy field values when necessary

### Defensive Copying

Good news! Pointers are back! Fortunately, you already understand how they work :-)

Consider the abbreviated class below:

```java
public final class ReportCard {
    private final String studentName;
    private final int studentId;
    private final int[] examScores; 


    public ReportCard(String studentName, int studentId, int[] scores) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.examScores = scores;
    }

    public int[] getExamScores() {
        return this.examScores;
    }
}
```

This class is _almost_ immutable. Remember, though, that array-type variables likee `int[] examScores` store _pointers_ and not the value themselves. So, the class that constructs the `GradeBook` will still be able to modify the array because it maintains a _pointer_ to that array.

But wait, isn't the field `examScores` final? Shouldn't that stop it from being modified? This is a classic "gotcha:" once again, it is the _pointer_ which matters. The _pointer_ itself cannot change, but the data pointed to is absolutely able to change. So, if we want the class to be truly immutable (i.e. no changing the array values), we need to make a copy of the values both when we construct the class _and_ when we return the scores, since both will result in an external class receiving a pointer to some mutable data. This way, the client can change whatever it wants without affecting the internal state of the `ReportCard` class:

```java
public final class ReportCard {
    private final String studentName;
    private final int studentId;
    private final int[] examScores; 


    public ReportCard(String studentName, int studentId, int[] scores) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.examScores = this.examScores = (scores == null) ? new int[0] : scores.clone();
    }

    public int[] getExamScores() {
        return this.examScores.clone();
    }
}
```
