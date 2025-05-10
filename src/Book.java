public abstract class Book {
    private String title;
    private String author;
    private int copies;

    public Book(String title, String author, int copies) {
        this.title = title;
        this.author = author;
        this.copies = copies;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getCopies() { return copies; }

    public void borrow() throws BookNotAvailableException {
        if (copies <= 0) throw new BookNotAvailableException("No copies left for: " + title);
        copies--;
    }

    public void returnBook() {
        copies++;
    }

    public String toFileString() {
        return getClass().getSimpleName() + "," + title + "," + author + "," + copies;
    }
}

