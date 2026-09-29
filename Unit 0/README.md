# Java Fundamentals for ACS

Java is a big language (in fact there's an exceptional text by Horstmann simply titled _Big Java_). In this introductory "Unit 0," we'll cover the main ideas you need to write real programs in Java, so that the rest of the course can focus on data structures and algorithms rather than on syntax.

## About This Unit

You already know how to program: you've written variables, conditionals, loops, and functions, and you may have worked with objects, inheritance, and polymorphism. None of those ideas are unique to Java. What Java adds are its _rules_: it is compiled, statically typed, and strict about structure in ways many languages aren't. Most of that strictness exists so that the compiler can catch mistakes before your program ever runs. The goal of Unit 0 is to make those rules feel natural, and to understand _why_ Java works the way it does, not just what to type.

The unit has two parts.

**Part I: Procedural Programming in Java** covers the building blocks that every Java program is made of: how a program is structured and run, variables and data types, operators, strings, input and output, conditionals, loops, arrays, and methods. Together, these let us write _procedural_ programs, organized as a set of functions that pass data to each other. Much of it will be familiar from other languages, so these lessons pay close attention to the places where Java differs: what it means for Java to be strongly and statically typed, why `7 / 2` is `3`, why you can't compare strings with `==`, and exactly what goes into a method signature.

**Part II: Object-Oriented Java** begins where procedural programming runs out of room. [Objects](Objects.md) starts with an aquarium program that gets messy when it's written only with functions and arrays, and shows how bundling data and behavior into objects fixes the problem. [Inheritance and Polymorphism](Inheritance.md) introduces the other two principles of object-oriented programming, using a fantasy game with wizards, warriors, and archers. [Special Classes](SpecialClasses.md) finishes the unit with abstract classes, interfaces, and immutable classes, which are the tools Java uses to describe what a group of classes has in common.

Throughout the notes, key definitions are set off in **Note** boxes, and common traps are flagged in **Warning** boxes.

## Roadmap

| # | Lesson | What it covers |
| --- | --- | --- |
| | **Part I: Procedural Programming in Java** | |
| — | [Overview](ProceduralProgramming.md) | Introduction to Part I, an annotated example program, common error messages, and a syntax quick reference |
| 1 | [Java Basics](JavaBasics.md) | Where code lives, `main`, compiling and running, statements, blocks, comments, naming conventions |
| 2 | [Variables and Data Types](VariablesAndTypes.md) | What a data type is, static and strong typing, primitive vs. reference types, casting, wrapper classes |
| 3 | [Operators](Operators.md) | Integer division, `++`/`--`, comparison and logical operators, the ternary operator, precedence |
| 4 | [Strings and Input/Output](StringsAndIO.md) | `String` methods, immutability, `==` vs `.equals()`, printing, `printf`, `Scanner` |
| 5 | [Control Flow](ControlFlow.md) | `if`/`else`, `switch`, `while`, `do`-`while`, `for`, for-each, `break` and `continue` |
| 6 | [Arrays](Arrays.md) | Creating and indexing arrays, arrays as references, the `Arrays` library, 2D arrays |
| 7 | [Methods and Scope](Methods.md) | Method headers and signatures, overloading, return values, pass-by-value, recursion, scope |
| | **Part II: Object-Oriented Java** | |
| 8 | [Objects](Objects.md) | Encapsulation, classes as data types, fields, constructors, `this`, the dot operator, static vs. instance members |
| 9 | [Inheritance and Polymorphism](Inheritance.md) | The three principles of OOP, subclasses, `super`, overriding, dynamic method lookup, `Object`, access modifiers |
| 10 | [Special Classes](SpecialClasses.md) | Abstract classes and methods, interfaces, immutable classes, defensive copying |

## Detailed Contents

### Part I: Procedural Programming in Java

**[Overview: Procedural Programming in Java](ProceduralProgramming.md)**
- [Contents](ProceduralProgramming.md#contents)
- [Putting It Together: `NumberGuesser.java`](ProceduralProgramming.md#putting-it-together-numberguesserjava)
- [When Things Go Wrong](ProceduralProgramming.md#when-things-go-wrong)
- [Java Syntax Quick Reference](ProceduralProgramming.md#java-syntax-quick-reference)

**1. [Java Basics](JavaBasics.md)**
- [The Shape of a Java Program](JavaBasics.md#the-shape-of-a-java-program)
  - [Compiling and Running](JavaBasics.md#compiling-and-running)
- [Basic Syntax](JavaBasics.md#basic-syntax): [statements and semicolons](JavaBasics.md#statements-and-semicolons), [blocks and indentation](JavaBasics.md#blocks-and-indentation), [expressions](JavaBasics.md#expressions), [comments](JavaBasics.md#comments), [naming conventions](JavaBasics.md#case-and-naming-conventions)

**2. [Variables and Data Types](VariablesAndTypes.md)**
- [Variables](VariablesAndTypes.md#variables): [declaration, assignment, and initialization](VariablesAndTypes.md#declaration-assignment-and-initialization), [constants with `final`](VariablesAndTypes.md#constants-with-final), [type inference with `var`](VariablesAndTypes.md#local-type-inference-with-var)
- [Data Types](VariablesAndTypes.md#data-types)
  - [Strongly and Statically Typed](VariablesAndTypes.md#strongly-and-statically-typed)
  - [Primitive Types](VariablesAndTypes.md#primitive-types)
  - [Reference Types](VariablesAndTypes.md#reference-types)
  - [Default Values](VariablesAndTypes.md#default-values)
  - [Type Conversion and Casting](VariablesAndTypes.md#type-conversion-and-casting)
  - [Wrapper Classes](VariablesAndTypes.md#wrapper-classes)

**3. [Operators](Operators.md)**
- [Arithmetic](Operators.md#arithmetic), including [integer division](Operators.md#integer-division)
- [Assignment and Compound Assignment](Operators.md#assignment-and-compound-assignment)
- [Increment and Decrement](Operators.md#increment-and-decrement)
- [Comparison Operators](Operators.md#comparison-relational-operators) and [Logical Operators](Operators.md#logical-operators)
- [The Conditional (Ternary) Operator](Operators.md#the-conditional-ternary-operator)
- [Precedence](Operators.md#precedence)

**4. [Strings and Input/Output](StringsAndIO.md)**
- [Strings](StringsAndIO.md#strings): [concatenation](StringsAndIO.md#concatenation), [escape sequences](StringsAndIO.md#escape-sequences), [common methods](StringsAndIO.md#common-string-methods), [immutability](StringsAndIO.md#strings-are-immutable), [`==` vs `.equals()`](StringsAndIO.md#comparing-strings--vs-equals)
- [Input and Output](StringsAndIO.md#input-and-output): [printing](StringsAndIO.md#printing), [reading input with `Scanner`](StringsAndIO.md#reading-input-with-scanner)

**5. [Control Flow](ControlFlow.md)**
- [Conditionals](ControlFlow.md#conditionals): [`if` / `else if` / `else`](ControlFlow.md#if-else-if-else), [`switch`](ControlFlow.md#switch)
- [Loops](ControlFlow.md#loops): [`while`](ControlFlow.md#while), [`do`-`while`](ControlFlow.md#do-while), [`for`](ControlFlow.md#for), [for-each](ControlFlow.md#enhanced-for-for-each), [`break` and `continue`](ControlFlow.md#break-and-continue), [infinite loops](ControlFlow.md#infinite-loops)

**6. [Arrays](Arrays.md)**
- [Creating Arrays](Arrays.md#creating-arrays) and [Accessing Elements](Arrays.md#accessing-elements)
- [Arrays Are Reference Types](Arrays.md#arrays-are-reference-types)
- [Printing Arrays](Arrays.md#printing-arrays) and [the `Arrays` Library](Arrays.md#the-arrays-library)
- [Multidimensional Arrays](Arrays.md#multidimensional-arrays)
- [Command-Line Arguments](Arrays.md#command-line-arguments)

**7. [Methods and Scope](Methods.md)**
- [Methods](Methods.md#methods)
  - [Anatomy of a Method](Methods.md#anatomy-of-a-method)
  - [The Method Signature](Methods.md#the-method-signature)
  - [Overloading](Methods.md#overloading)
  - [Parameters and Arguments](Methods.md#parameters-and-arguments)
  - [Return Values](Methods.md#return-values)
  - [Pass-by-Value](Methods.md#pass-by-value)
  - [Calling Methods](Methods.md#calling-methods) and [`main`, Decoded](Methods.md#main-decoded)
  - [The `Math` Library](Methods.md#the-math-library)
  - [Recursion](Methods.md#recursion)
  - [Writing Good Methods](Methods.md#writing-good-methods)
- [Scope](Methods.md#scope)

### Part II: Object-Oriented Java

**8. [Objects](Objects.md)**
- [Encapsulation](Objects.md#encapsulation): [libraries](Objects.md#libraries), [their limitations](Objects.md#limitations-of-libraries), and [object-oriented programming](Objects.md#object-oriented-programming)
- [Anatomy of a Class](Objects.md#anatomy-of-a-class): [fields](Objects.md#fields), [constructors](Objects.md#constructors), [methods](Objects.md#methods)
- [Static Methods and Fields](Objects.md#static-methods-and-fields)
  - [Uses of Static Fields and Methods](Objects.md#uses-of-static-fields-and-methods), including factory methods

**9. [Inheritance and Polymorphism](Inheritance.md)**
- [Three Principles of OOP](Inheritance.md#three-principles-of-oop)
  - [Inheritance](Inheritance.md#inheritance)
- [Polymorphism](Inheritance.md#polymorphism)
  - [Constructors and Superconstructors](Inheritance.md#constructors-and-superconstructors)
  - [But, what is it good for?](Inheritance.md#but-what-is-it-good-for)
  - [Overriding Methods](Inheritance.md#overriding-methods)
  - [Dynamic Method Lookup](Inheritance.md#dynamic-method-lookup)
- [The Magic of OOP](Inheritance.md#the-magic-of-oop)
- [`Object`, the Universal Superclass](Inheritance.md#object-the-universal-superclass)
- [Access Modifiers](Inheritance.md#access-modifiers)
- [Dynamic vs. Static Binding](Inheritance.md#dynamic-vs-static-binding)

**10. [Special Classes](SpecialClasses.md)**
- [Abstract Classes](SpecialClasses.md#abstract-class) and [Abstract Methods](SpecialClasses.md#abstract-methods)
- [Interfaces](SpecialClasses.md#interfaces)
  - [Interfaces vs. Abstract Classes](SpecialClasses.md#interfaces-vs-abstract-classes)
- [Immutable Classes](SpecialClasses.md#immutable-classes)
  - [Defensive Copying](SpecialClasses.md#defensive-copying)

## Example Programs

The folder also includes a few short programs that the notes refer to. To try one, compile it and then run it from the terminal:

```
javac NumberGuesser.java
java NumberGuesser
```

| Program | What it shows |
| --- | --- |
| [`HelloWorld.java`](HelloWorld.java) | The smallest complete Java program |
| [`HelloUser.java`](HelloUser.java) | Reading input with a `Scanner` |
| [`NumberGuesser.java`](NumberGuesser.java) | Variables, `Math.random()`, casting, a `do`-`while` loop, and `if`/`else if`/`else` together in one program (see the [annotated walkthrough](ProceduralProgramming.md#putting-it-together-numberguesserjava)) |
