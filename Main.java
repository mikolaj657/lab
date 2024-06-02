import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final BookManager bookManager = new BookManager(new ArrayList<>());

    public static void main(String[] args) {
        initializeBooks();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    removeBook(scanner);
                    break;
                case 3:
                    updateBook(scanner);
                    break;
                case 4:
                    listBooks();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void initializeBooks() {
        bookManager.addBook(new Book("Title1", "Author1", "ISBN1", 2001));
        bookManager.addBook(new Book("Title2", "Author2", "ISBN2", 2002));
        bookManager.addBook(new Book("Title3", "Author3", "ISBN3", 2003));
        bookManager.addBook(new Book("Title4", "Author4", "ISBN4", 2004));
        bookManager.addBook(new Book("Title5", "Author5", "ISBN5", 2005));
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("[1] Add book");
        System.out.println("[2] Remove book");
        System.out.println("[3] Update book");
        System.out.println("[4] List books");
        System.out.println("[5] Exit");
        System.out.print("Choose an option: ");
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Book book = new Book(title, author, isbn, year);
        bookManager.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void removeBook(Scanner scanner) {
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();
        bookManager.removeBook(isbn);
        System.out.println("Book removed successfully.");
    }

    private static void updateBook(Scanner scanner) {
        System.out.print("Enter ISBN of the book to update: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Book updatedBook = new Book(title, author, isbn, year);
        bookManager.updateBook(isbn, updatedBook);
        System.out.println("Book updated successfully.");
    }

    private static void listBooks() {
        List<Book> books = bookManager.getBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
}