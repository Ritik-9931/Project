package Library_Management;

import java.util.ArrayList;
import java.util.Scanner;

class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String msg){
        super(msg);
    }
}

class MaxBookLimitException extends Exception {
    public MaxBookLimitException(String msg){
        super(msg);
    }
}

class Book{
    private int id;
    private String name;
    private String authorname;
    private String genre;
    private boolean isBorrow;

    public Book(int id, String name, String authorname, String genre){
        this.id = id;
        this.name = name;
        this.authorname = authorname;
        this.genre = genre;
        this.isBorrow = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isBorrow() {
        return isBorrow;
    }

    public void setBorrow(boolean borrow) {
        isBorrow = borrow;
    }

    @Override
    public String toString() {
        return  "Id='" + id + '\''+
                ", name='" + name + '\'' +
                ", Author Name='" + authorname + '\'' +
                ", Genre='" + genre + '\'' +
                ", Borrow=" + isBorrow;
    }
}

class Member{
    private String name;
    private int id;
    private ArrayList<Book> borrow;

    public Member(int id, String name){
        this.name = name;
        this.id = id;
        this.borrow = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Book> getBorrow() {
        return borrow;
    }

    public void setBorrow(ArrayList<Book> borrow) {
        this.borrow = borrow;
    }

    public void borrowBook(Book book) throws MaxBookLimitException {
        if(borrow.size() >= 3) {
            throw new MaxBookLimitException("A member can borrow max 3 books.");
        }
        borrow.add(book);
    }

    public void returnBook(Book book) {
        borrow.remove(book);
    }

    @Override
    public String toString() {
        return id + " | " + name + " | Borrowed: " + borrow.size();
    }
}

class Library{
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<Member> members = new ArrayList<>();
    private String[] genres = {"Fiction", "Science", "History", "Biography", "Mystery"};

    public String getGenres() {
        return genres[0]+" | "+genres[1]+" | " +genres[2]+" | "+genres[3]+" | "+genres[4];
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void addBooks(Book book){
        books.add(book);
        System.out.println("Add Book Successfully...");
    }

    public void registerMember(Member member){
        members.add(member);
        System.out.println("Member Register Successfully...");
    }

    public Book searchBook(String booktile){
        for(Book book : books){
            if (book.getName().contains(booktile)){
                return book;
            }
        }
        System.out.println("Book Not found...!");
        return null;
    }

    public Member findMember(int id){
        for(Member member : members){
            if(member.getId() == id){
                return member;
            }
        }
        System.out.println("Not Found...!");
        return null;
    }

    public void borrowBook(int memberId, String bookName) throws BookNotAvailableException, MaxBookLimitException {
        Member member = findMember(memberId);
        Book book = searchBook(bookName);

        if(book == null || book.isBorrow()){
            throw new BookNotAvailableException("The Book is not available");
        }

        member.borrowBook(book);
        book.setBorrow(true);

        System.out.println("Book Borrowed Successfully...");
    }

    public void returnBook(int memberId, String bookName){
        Member member = findMember(memberId);
        Book book = searchBook(bookName);

        if(member != null || book != null) {
            member.returnBook(book);
            book.setBorrow(false);
            System.out.println("Book Return Successfully...");
        }
    }

    public void viewMemberAndBorrowBooks(){
        for(Member m : members){
            System.out.println(m);
            if(m.getBorrow().isEmpty()){
                System.out.println(" No Borrowed Books");
            }else {
                for(Book b : m.getBorrow()){
                    System.out.println(" -> " + b);
                }
            }
        }
    }

}

public class Main {

    public static void main() {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--------- Choose one -------");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. View All Members & Borrowed Books");
            System.out.println("7. View Book List");
            System.out.println("8. Exit");
            System.out.print("Enter your choice : ");
            int ch = sc.nextInt();
            switch (ch){
                case 1:
                    System.out.print("Enter Book Id: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Book Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();

                    System.out.println(library.getGenres());
                    System.out.print("Enter Book Genra: ");
                    String genra = sc.nextLine();

                    Book book = new Book(id, name, author, genra);

                    library.addBooks(book);

                    break;

                case 2:
                    System.out.print("Enter member id: ");
                    int memberId = sc.nextInt();

                    sc.nextLine();
                    System.out.print("Enter member Name: ");
                    String memberName = sc.nextLine();

                    Member member = new Member(memberId, memberName);

                    library.registerMember(member);
                    break;

                case 3:
                    System.out.print("Enter you id : ");
                    int memID = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter book title : ");
                    String title = sc.nextLine();
                    try {
                        library.borrowBook(memID, title);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter you id : ");
                    int memberID = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter book title : ");
                    String booktitle = sc.nextLine();

                    library.returnBook(memberID, booktitle);
                    break;

                case 5:
                    System.out.println("Enter you book title : ");
                    sc.nextLine();
                    String subtitle = sc.nextLine();

                    System.out.println(library.searchBook(subtitle));
                    break;

                case 6:
                    library.viewMemberAndBorrowBooks();
                    break;

                case 7:
                    for(Book bk : library.books){
                        System.out.println(bk);
                    }
                    break;
                case 8:
                    System.out.println("Exit the program.");
                    return;

                default:
                    System.out.println("--------- Invalid choice -----------");
            }
        }
    }
}