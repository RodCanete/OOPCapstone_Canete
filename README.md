# OOPCapstone_Canete

# Library Management System

This is a Java-based Library Management System capstone project.  
It allows users to manage books (Ebooks and PrintedBooks), track borrowed books, and handle members using Java Swing for the GUI.

The system highlights key Object-Oriented Programming (OOP) principles:

---

## 💡 Abstraction

Abstraction is used to represent general concepts like **Book**, **Member**, and **Library** without worrying about internal details.

- The `Book` class defines the common attributes (`title`, `author`, `copies`) and behaviors (`borrow()`, `returnBook()`, `displayInfo()`).
- The `Library` class abstracts the management of books and members, providing methods like `addBook()`, `borrowBook()`, `returnBook()` without exposing how the data is stored.

---

## 🔒 Encapsulation

Encapsulation is achieved by:

- Declaring fields as `private` and providing `public` getter and setter methods when necessary.
  
Examples:
- In the `Book` class, fields like `title`, `author`, `copies` are private and accessed through methods like `getTitle()`.
- In the `Member` class, fields like `name` and `id` are private, with public `getName()` and `getId()` methods.

This keeps the internal data safe from direct modification and enforces controlled access.

---

## 🏛️ Inheritance

Inheritance is implemented through:

- `Ebook` and `PrintedBook` classes, which **extend** the `Book` class.
  
These subclasses inherit common attributes and methods from `Book` and override the `displayInfo()` method to show specific details.

---

## 🔄 Polymorphism

Polymorphism is demonstrated by:

- Overriding the `displayInfo()` method in both `Ebook` and `PrintedBook` classes, allowing them to behave differently while sharing the same method name.
- The `Library` class can handle both `Ebook` and `PrintedBook` objects through their shared `Book` reference.

This allows flexible and dynamic behavior when working with different book types.

---

## 📦 Features

- Add new books (Ebook, PrintedBook)
- Borrow books
- Return books
- Save and load books from file (`books.txt`)
- Save and load borrowed books from file (`borrowed_books.txt`)
- Display borrowed books in GUI

---

## 🛠️ Requirements

- Java 8 or higher
- Java Swing (built-in)

---

## 📄 
