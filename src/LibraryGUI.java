import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LibraryGUI extends JFrame {

    private JButton addBtn;
    private JButton showBtn;
    private JButton borrowBtn;
    private JButton saveBtn;
    private JButton loadBtn;
    private JPanel pnlMain;
    private JButton showBorrowedBtn;

    private final Library library = new Library();

    public static void main(String[] args) {
        LibraryGUI app = new LibraryGUI();
        app.setContentPane(app.pnlMain);
        app.setSize(450, 300);
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    public LibraryGUI() {
        File file = new File("books.txt");
        if (file.exists()) {
            try {
                library.loadBooksFromFile("books.txt");
                System.out.println("Books loaded from file.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage());
            }
        }


        addBtn.addActionListener(e -> addBook());
        showBtn.addActionListener(e -> showBooks());
        borrowBtn.addActionListener(e -> borrowBook());
        showBorrowedBtn.addActionListener(e -> showBorrowedBooks());
        saveBtn.addActionListener(e -> saveBooks());
        loadBtn.addActionListener(e -> loadBooks());



        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    library.saveBooksToFile("books.txt");
                    System.out.println("Books saved on exit.");
                } catch (IOException ex) {
                    System.out.println("Error saving books on exit: " + ex.getMessage());
                }
            }
        });
    }

    private void addBook() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] options = {"Ebook", "PrintedBook"};
        JComboBox<String> typeCombo = new JComboBox<>(options);
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField copiesField = new JTextField();

        panel.add(new JLabel("Type:"));
        panel.add(typeCombo);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("Copies:"));
        panel.add(copiesField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String type = (String) typeCombo.getSelectedItem();
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String copiesText = copiesField.getText().trim();

                if (title.isEmpty()) throw new IllegalArgumentException("Title is required.");
                if (author.isEmpty()) throw new IllegalArgumentException("Author is required.");
                if (copiesText.isEmpty()) throw new IllegalArgumentException("Copies are required.");

                int copies = Integer.parseInt(copiesText);
                if (copies <= 0) throw new IllegalArgumentException("Copies must be positive.");

                assert type != null;
                if (type.equals("Ebook")) {
                    library.addBook(new Ebook(title, author, copies));
                } else {
                    library.addBook(new PrintedBook(title, author, copies));
                }

                JOptionPane.showMessageDialog(this, "Book added!");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format for copies.");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Input Error: " + e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unexpected Error: " + e.getMessage());
            }
        }
    }


    private void showBooks() {
        StringBuilder sb = new StringBuilder();
        for (Book b : library.getBooks()) {
            sb.append(b.getTitle()).append(" by ").append(b.getAuthor())
                    .append(" (").append(b.getCopies()).append(" copies)\n");
        }
        if (sb.isEmpty()) sb.append("No books in the library.");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void saveBooks() {
        try {
            library.saveBooksToFile("books.txt");
            JOptionPane.showMessageDialog(this, "Books saved to file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
        }
    }

    private void loadBooks() {
        try {
            library.loadBooksFromFile("books.txt");
            JOptionPane.showMessageDialog(this, "Books loaded from file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage());
        }
    }

    private void borrowBook() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField titleField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField dateBorrowedField = new JTextField();
        JTextField deadlineField = new JTextField();

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Borrower Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Borrower ID:"));
        panel.add(idField);
        panel.add(new JLabel("Date Borrowed (YYYY-MM-DD):"));
        panel.add(dateBorrowedField);
        panel.add(new JLabel("Deadline (YYYY-MM-DD):"));
        panel.add(deadlineField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Borrow Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText().trim();
                String name = nameField.getText().trim();
                String id = idField.getText().trim();
                String dateBorrowed = dateBorrowedField.getText().trim();
                String deadline = deadlineField.getText().trim();

                if (title.isEmpty()) throw new IllegalArgumentException("Title is required.");
                if (name.isEmpty()) throw new IllegalArgumentException("Borrower name is required.");
                if (id.isEmpty()) throw new IllegalArgumentException("Borrower ID is required.");
                if (dateBorrowed.isEmpty()) throw new IllegalArgumentException("Date borrowed is required.");
                if (deadline.isEmpty()) throw new IllegalArgumentException("Deadline is required.");

                // Validate date format
                LocalDate.parse(dateBorrowed);
                LocalDate.parse(deadline);

                // Process borrowing in the library system
                library.borrowBook(title, name, id, dateBorrowed, deadline);

                // Write borrowed record to file
                writeBorrowedToFile(title, name, id, dateBorrowed, deadline);

                JOptionPane.showMessageDialog(this, "Borrowed successfully!");

            } catch (BookNotAvailableException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format! Please use YYYY-MM-DD.");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Input Error: " + e.getMessage());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "File Error: " + e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unexpected Error: " + e.getMessage());
            }
        }
    }

    private void writeBorrowedToFile(String title, String name, String id, String dateBorrowed, String deadline) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("borrowed_books.txt", true)); // append mode
        writer.write(title + "," + name + "," + id + "," + dateBorrowed + "," + deadline);
        writer.newLine();
        writer.close();
    }

    private void showBorrowedBooks() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowed_books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String title = parts[0];
                    String borrowerName = parts[1];
                    String borrowerId = parts[2];
                    String borrowedDate = parts[3];
                    String deadlineDate = parts[4];

                    // Append the formatted information to StringBuilder
                    sb.append("Title: ").append(title)
                            .append(" | Borrower: ").append(borrowerName)
                            .append(" (ID: ").append(borrowerId)
                            .append("), Borrowed: ").append(borrowedDate)
                            .append(", Deadline: ").append(deadlineDate)
                            .append("\n");
                }
            }
        } catch (IOException e) {
            sb.append("Error reading borrowed books file: ").append(e.getMessage());
        }

        // Display the formatted list in a dialog box
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    }
