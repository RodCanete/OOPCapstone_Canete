public class BorrowedBook {
    private Book book;
    private Member member;
    private String dateBorrowed;
    private String deadline;

    public BorrowedBook(Book book, Member member, String dateBorrowed, String deadline) {
        this.book = book;
        this.member = member;
        this.dateBorrowed = dateBorrowed;
        this.deadline = deadline;
    }

    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public String getDateBorrowed() { return dateBorrowed; }
    public String getDeadline() { return deadline; }
}
