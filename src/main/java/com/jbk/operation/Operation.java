package com.jbk.operation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.jbk.config.HibernateConfig;
import com.jbk.entity.Book;
import com.jbk.entity.Author;
import com.jbk.entity.Borrower;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Operation {

	SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

	public String addBook(Book book) {
		try (Session session = sessionFactory.openSession()) {
	        session.beginTransaction();

	        Author author = book.getAuthor();
	        if (author != null) {
	            Author existingAuthor = session.createQuery("from Author where name = :name", Author.class)
	                                           .setParameter("name", author.getName())
	                                           .uniqueResult();
	            if (existingAuthor != null) {
	                book.setAuthor(existingAuthor);
	            } else {
	                session.save(author);
	            }
	        }

	        Set<Borrower> borrowers = book.getBorrowers();
	        if (borrowers != null) {
	            Set<Borrower> savedBorrowers = new HashSet<>();
	            for (Borrower borrower : borrowers) {
	                Borrower existingBorrower = session.createQuery("from Borrower where name = :name", Borrower.class)
	                                                   .setParameter("name", borrower.getName())
	                                                   .uniqueResult();
	                if (existingBorrower != null) {
	                    savedBorrowers.add(existingBorrower);
	                } else {
	                    session.save(borrower);
	                    savedBorrowers.add(borrower);
	                }
	            }
	            book.setBorrowers(savedBorrowers);

	            for (Borrower borrower : savedBorrowers) {
	                borrower.getBooks().add(book); 
	                session.saveOrUpdate(borrower); 
	            }
	        }

	        session.save(book);
	        session.getTransaction().commit();

	        return "Book Added Successfully!";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Something Went Wrong!";
	    }
	}

	// Update Book
	public String updateBook(Book book) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Book dbBook = session.get(Book.class, book.getId());

			if (dbBook != null) {
				dbBook.setTitle(book.getTitle());
				dbBook.setIsbn(book.getIsbn());
				dbBook.setPrice(book.getPrice());
				dbBook.setAuthor(book.getAuthor());
				session.update(dbBook);
				transaction.commit();
				return "Book Updated Successfully!";
			} else {
				return "Book Not Found!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Something Went Wrong!";
		}
	}

	// Delete Book
	public String deleteBook(int bookId) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Book book = session.get(Book.class, bookId);

			if (book != null) {
				session.delete(book);
				transaction.commit();
				return "Book Deleted Successfully!";
			} else {
				return "Book Not Found!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Something Went Wrong!";
		}
	}

	// Fetch Book by ID
	public Book getBookById(int bookId) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Book.class, bookId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Fetch All Books
	@SuppressWarnings("unchecked")
	public List<Book> getAllBooks() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from Book").list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Add Author
	@SuppressWarnings("rawtypes")
	public String addAuthor(Author author) {
		try (Session session = sessionFactory.openSession()) {
			// Check if author already exists based on the name (or other unique field)
			Query query = session.createQuery("from Author a where a.name = :authorName");
			query.setParameter("authorName", author.getName());
			Author existingAuthor = (Author) query.uniqueResult();

			if (existingAuthor == null) {
				Transaction transaction = session.beginTransaction();
				session.save(author); // Save new author
				transaction.commit();

				return "Author Added Successfully!";
			} else {
				return "Author Already Exists!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Something Went Wrong!";
		}
	}

	// Fetch All Authors
	@SuppressWarnings("unchecked")
	public List<Author> getAllAuthors() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from Author").list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Fetch Books by Author
	public Object getBooksByAuthor(int authorId) {
		try (Session session = sessionFactory.openSession()) {
			// Check if the author exists
			Author author = session.get(Author.class, authorId);
			if (author == null) {
				return "Author does not exist"; // Return a message if the author doesn't exist
			}

			// Fetch the books if the author exists
			Query<Book> query = session.createQuery("from Book b where b.author.id = :authorId", Book.class);
			query.setParameter("authorId", authorId);
			List<Book> books = query.list();

			if (books.isEmpty()) {
				return "No books found for the given author"; // Return a message if no books are found
			}

			return books; // Return the list of books if available
		} catch (Exception e) {
			e.printStackTrace();
			return "An error occurred while fetching books.";
		}
	}

	// Add Borrower
	@SuppressWarnings("rawtypes")
	public String addBorrower(Borrower borrower) {
		try (Session session = sessionFactory.openSession()) {
			// Check if borrower already exists based on name (assuming names are unique in
			// your case)
			Query query = session.createQuery("from Borrower b where b.name = :borrowerName");
			query.setParameter("borrowerName", borrower.getName());
			Borrower existingBorrower = (Borrower) query.uniqueResult();

			if (existingBorrower == null) {
				Transaction transaction = session.beginTransaction();
				session.save(borrower); // Save new borrower
				transaction.commit();

				return "Borrower Added Successfully!";
			} else {
				return "Borrower Already Exists!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Something Went Wrong!";
		}
	}

	// Fetch All Borrowers
	@SuppressWarnings("unchecked")
	public List<Borrower> getAllBorrowers() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from Borrower").list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Fetch Borrowers by Book
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getBorrowersByBook(int bookId) {
		try (Session session = sessionFactory.openSession()) {
			// HQL query to get borrowers associated with the book
			Query query = session.createQuery("select b from Borrower b join b.books book where book.id = :bookId");
			query.setParameter("bookId", bookId);
			List<Borrower> borrowers = query.list();

			// Check if the list is empty, indicating no borrowers were found for the book
			if (borrowers.isEmpty()) {
				return "Book does not exist"; // Return message if no borrowers found
			} else {
				return borrowers; // Return the list of borrowers
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong!";
		}
	}

	// Fetch the Most Expensive Book
	@SuppressWarnings("rawtypes")
	public Book getMostExpensiveBook() {
		try (Session session = sessionFactory.openSession()) {
			Query query = session.createQuery("from Book order by price desc");
			query.setMaxResults(1);
			return (Book) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Fetch Books with Title Like Keyword
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Book> getBooksByTitleKeyword(String keyword) {
		try (Session session = sessionFactory.openSession()) {
			Query query = session.createQuery("from Book where title like :keyword");
			query.setParameter("keyword", "%" + keyword + "%");
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
