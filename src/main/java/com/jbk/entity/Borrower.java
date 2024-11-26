package com.jbk.entity;

import java.util.Set;
import javax.persistence.*;

@Entity
public class Borrower {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Book> books;
	
	public Borrower() {
	}

	public Borrower(String name, String email, Set<Book> books) {
		super();
		this.name = name;
		this.email = email;
		this.books = books;
	}
	
	public Borrower(Integer id, String name, String email, Set<Book> books) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.books = books;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Borrower [id=" + id + ", name=" + name + ", email=" + email + ", booksCount=" + (books != null ? books.size() : 0) + "]";
	}
}
