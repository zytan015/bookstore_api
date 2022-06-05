package com.bookstore.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.api.model.Book;
import com.bookstore.api.repository.BookRepository;

@Service
@Transactional
public class BookService {
	private final BookRepository bookRepo;
	
	@Autowired
	public BookService(BookRepository bookRepo) {
		this.bookRepo = bookRepo;
	}
	
	public List<Book> getBook() {
		return bookRepo.findAll();
	}
	
	public void addBook(Book book) {
		bookRepo.save(book);
	}
	
	public void updateBook(Book book) {
		bookRepo.save(book);
	}
	
	public void deleteBook(String isbn) {
		bookRepo.deleteById(isbn);
	}
}
