package com.bookstore.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.api.model.Authors;
import com.bookstore.api.repository.AuthorsRepository;

@Service
public class AuthorService {
private final AuthorsRepository authorRepo;
	
	@Autowired
	public AuthorService(AuthorsRepository authorRepo) {
		this.authorRepo = authorRepo;
	}
	
	public List<Authors> getAuthor() {
		return authorRepo.findAll();
	}
	
	public void addAuthor(Authors author) {
		authorRepo.save(author);
	}
	
	public void updateAuthor(Authors author) {
		authorRepo.save(author);
	}
	
	

}
