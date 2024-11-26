package com.jbk.utility;

import com.jbk.entity.Author;
import com.jbk.entity.Book;
import com.jbk.entity.Borrower;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class InputData {

     @SuppressWarnings("resource")
    public static Book getBookInfoFromUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Book Title: ");
        String title = scanner.nextLine();

        System.out.println("Enter ISBN: ");
        String isbn = scanner.next();

        System.out.println("Enter Price: ");
        double price = scanner.nextDouble();

        scanner.nextLine(); 

        System.out.println("Enter Author Name: ");
        String authorName = scanner.nextLine();

        System.out.println("Enter Author Email: ");
        String authorEmail = scanner.nextLine();

        Author author = new Author(authorName, authorEmail, new HashSet<>()); 

        Book book = new Book(title, isbn, price, author);

        System.out.println("Would you like to assign a Borrower to this book? (yes/no)");
        String assignBorrower = scanner.nextLine();

        if (assignBorrower.equalsIgnoreCase("yes")) {
            Borrower borrower = getBorrowerInfoFromUser();
            Set<Borrower> borrowers = new HashSet<>();  
            borrowers.add(borrower);
            book.setBorrowers(borrowers);  

            Set<Book> books = borrower.getBooks();
            if (books == null) {
                books = new HashSet<>();
            }
            books.add(book);  
            borrower.setBooks(books);
        }

        return book;
    }

    @SuppressWarnings("resource")
    public static Author getAuthorInfoFromUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Author Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Author Email: ");
        String email = scanner.nextLine();

        Author author = new Author(name, email, new HashSet<>());  

        return author;
    }

    @SuppressWarnings("resource")
    public static Borrower getBorrowerInfoFromUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Borrower Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Borrower Email: ");
        String email = scanner.nextLine();

        Borrower borrower = new Borrower(name, email, new HashSet<>());
        return borrower;
    }
}
