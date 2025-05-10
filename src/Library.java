import java.io.*;
import java.util.*;

public class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) { books.add(book); }

    public List<Book> getBooks() { return books; }

    private List<BorrowedBook> borrowedBooks = new ArrayList<>();

    public List<BorrowedBook> getBorrowedBooks() { return borrowedBooks; }


    public void borrowBook(String title, String name, String id, String dateBorrowed, String deadline) throws BookNotAvailableException {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                b.borrow();
                Member member = new Member(name, id);
                borrowedBooks.add(new BorrowedBook(b, member, dateBorrowed, deadline));
                return;
            }
        }
        throw new BookNotAvailableException("Book not found: " + title);
    }

    public void returnBook(String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                b.returnBook();
                return;
            }
        }
    }

    public void saveBooksToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Book b : books) {
            writer.write(b.toFileString());
            writer.newLine();
        }
        writer.close();
    }

    public void loadBooksFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        books.clear();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String type = parts[0];
            String title = parts[1];
            String author = parts[2];
            int copies = Integer.parseInt(parts[3]);
            if (type.equals("Ebook")) {
                books.add(new Ebook(title, author, copies));
            } else if (type.equals("PrintedBook")) {
                books.add(new PrintedBook(title, author, copies));
            }
        }
        reader.close();
    }
}
