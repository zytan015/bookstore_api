package com.bookstore.api.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.api.model.Book;
import com.fasterxml.jackson.databind.JsonNode;

@Repository
public interface BookRepository extends JpaRepository<Book, String>{

	List<Book> findAll();
	public Book findByIsbn(String isbn);
	public Book deleteByIsbn(String isbn);


}
