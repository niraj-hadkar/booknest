package com.jbk;

import java.util.List;
import java.util.Scanner;
import com.jbk.entity.Author;
import com.jbk.entity.Book;
import com.jbk.entity.Borrower;
import com.jbk.operation.Operation;
import com.jbk.utility.InputData;

public class BookNestTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		Operation operation = new Operation();
		int choice;
		boolean exit = false;

		do {
			System.out.println("\n=== BookNest ===");
			System.out.println("1) Add Book");
			System.out.println("2) Add Author");
			System.out.println("3) Add Borrower");
			System.out.println("4) Update Book");
			System.out.println("5) Delete Book");
			System.out.println("6) Fetch Book By ID");
			System.out.println("7) Fetch All Books");
			System.out.println("8) Fetch All Author");
			System.out.println("9) Fetch All Borrower");
			System.out.println("10) Fetch All Books of a specific Author");
			System.out.println("11) List All Borrowers of a specific Book");
			System.out.println("12) Fetch the most expensive book");
			System.out.println("13) Fetch All Books with the specific title");
			System.out.println("0) Exit");
			System.out.print("Enter your choice : ");


			while (!scanner.hasNextInt()) {
				System.out.println("Invalid input! Please enter a number.");
				scanner.next();
			}

			choice = scanner.nextInt();

			switch (choice) {
			case 1: {
				Book book = InputData.getBookInfoFromUser();
				String msg = operation.addBook(book);
				System.out.println(msg);
				break;
			}

			case 2: {
				Author author = InputData.getAuthorInfoFromUser();
				String msg = operation.addAuthor(author);
				System.out.println(msg);
				break;
			}

			case 3: {
				Borrower borrower = InputData.getBorrowerInfoFromUser();
				String msg = operation.addBorrower(borrower);
				System.out.println(msg);
				break;
			}
			
			case 4: {
				System.out.print("Enter Book ID to Update: ");
                int bookId = scanner.nextInt();
                scanner.nextLine();
                Book updatedBook = InputData.getBookInfoFromUser();
                updatedBook.setId(bookId);
                String updateBookMessage = operation.updateBook(updatedBook);
                System.out.println(updateBookMessage);
                break;
			}
			
			case 5: {
				System.out.print("Enter Book ID to Delete: ");
                int deleteBookId = scanner.nextInt();
                String deleteBookMessage = operation.deleteBook(deleteBookId);
                System.out.println(deleteBookMessage);
                break;
			}
			
			case 6: {
				System.out.print("Enter Book ID to Fetch: ");
                int fetchBookId = scanner.nextInt();
                Book fetchedBook = operation.getBookById(fetchBookId);
                System.out.println(fetchedBook != null ? fetchedBook : "Book Not Found!");
                break;
			}
			
			case 7: {
				List<Book> allBooks = operation.getAllBooks();
                allBooks.forEach(System.out::println);
                break;
			}
			
			case 8: {
				List<Author> allAuthors = operation.getAllAuthors();
                allAuthors.forEach(System.out::println);
                break;
			}
			
			case 9: {
				List<Borrower> allBorrowers = operation.getAllBorrowers();
                allBorrowers.forEach(System.out::println);
                break;
			}
			
			case 10: {
				System.out.print("Enter Author ID: ");
			    int authorId = scanner.nextInt();
			    
			    // Call the method and store the result in an Object
			    Object booksByAuthor = operation.getBooksByAuthor(authorId);
			    
			    // Check if the result is a String (message) or a List<Book>
			    if (booksByAuthor instanceof String) {
			        // If it's a String (message), print the message
			        System.out.println(booksByAuthor);
			    } else if (booksByAuthor instanceof List<?>) {
			        // If it's a List<Book>, cast and print each book
			        List<Book> books = (List<Book>) booksByAuthor;
			        if (books.isEmpty()) {
			            System.out.println("No books found for the given author.");
			        } else {
			            books.forEach(System.out::println); // Print each book
			        }
			    }
			    break;
			}
			
			case 11: {
				System.out.print("Enter Book ID: ");
			    int bookBorrowersId = scanner.nextInt();
			    
			    // Call the method and store the result in an Object
			    Object borrowersByBook = operation.getBorrowersByBook(bookBorrowersId);
			    
			    // Check if the result is a String (message) or a List<Borrower>
			    if (borrowersByBook instanceof String) {
			        // If it's a String (message), print the message (e.g., Book does not exist)
			        System.out.println(borrowersByBook);
			    } else if (borrowersByBook instanceof List<?>) {
			        // If it's a List<Borrower>, cast and print each borrower
			        List<Borrower> borrowers = (List<Borrower>) borrowersByBook;
			        if (borrowers.isEmpty()) {
			            System.out.println("No borrowers found for this book.");
			        } else {
			            borrowers.forEach(System.out::println); // Print each borrower
			        }
			    }
			    break;
			}
			
			case 12: {
				Book mostExpensiveBook = operation.getMostExpensiveBook();
                System.out.println(mostExpensiveBook != null ? mostExpensiveBook : "No Books Found!");
                break;
			}
			
			case 13: {
				System.out.print("Enter Title Keyword: ");
                scanner.nextLine(); // Consume the newline
                String keyword = scanner.nextLine();
                List<Book> booksByKeyword = operation.getBooksByTitleKeyword(keyword);
                booksByKeyword.forEach(System.out::println);
                break;
			}

			case 0: {
				exit = true;
				System.out.println("Exiting application...");
				break;
			}

			default: {
				System.out.println("Invalid choice! Please try again.");
				break;
			}
			}
		} while (!exit);

		scanner.close();
		System.out.println("App Terminated!");
	}

}
