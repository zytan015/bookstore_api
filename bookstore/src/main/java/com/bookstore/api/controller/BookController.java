package com.bookstore.api.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.api.model.Authors;
import com.bookstore.api.model.Book;
import com.bookstore.api.repository.AuthorsRepository;
import com.bookstore.api.repository.BookRepository;
import com.bookstore.api.services.BookService;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.istack.logging.Logger;

@RestController
@RequestMapping("/bookstore")
public class BookController {
	private static Logger logger = Logger.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private AuthorsRepository authorRepo;
	
	private ObjectMapper getObjectMapper() {
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;
	} // end getObjectMapper
	
	@GetMapping("/books") 
	public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String isbn){  
		List<Book> books = new ArrayList<Book>();
		 if (isbn == null)
		      bookRepo.findAll().forEach(books::add);
		 else
			  bookRepo.findByIsbn(isbn);
		 if (books.isEmpty()) {
		     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		    return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@GetMapping("/books/{isbn}") 
	public ResponseEntity<Book> getBookById(@PathVariable("isbn") String isbn){  
		Optional<Book> book = Optional.ofNullable(bookRepo.findByIsbn(isbn));
		if (book != null) {
			return new ResponseEntity<>(book.get(), HttpStatus.OK);
	    } else {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}


	@DeleteMapping("/delete/{isbn}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable("isbn") String isbn){
		try {
			bookRepo.deleteByIsbn(isbn);
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	@PostMapping("/addbook")
	@ResponseBody
    public void register (@RequestBody JsonNode objMap)throws Exception {  
		ObjectMapper om = getObjectMapper();
		Map<String, Object> data = om.readValue(objMap.toString(), new TypeReference<HashMap<String, Object>>(){});
		for (String key : data.keySet()) {
			logger.info("key: " + key + " = " + data.get(key));
		}
		Book Add = new Book();
		
		Add.setIsbn(data.get("isbn").toString());
		Add.setTitle(data.get("title").toString());
		Add.setGenre(data.get("genre").toString());
		Add.setPrice((Double) data.get("price"));
		Add.setYear((Integer) data.get("year"));
		Set<Authors> author = new HashSet<Authors>();
		author.addAll((Collection<? extends Authors>) authorRepo.findByNameIn((List<String>) data.get("name")));
        bookService.addBook(Add);
		/*try {
			if (bookRepo.findByIsbn(isbn) != null) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			else {
				Book Add = new Book();
				
				Add.setIsbn(data.get("isbn").toString());
				Add.setTitle(data.get("title").toString());
				Add.setGenre(data.get("genre").toString());
				Add.setPrice((Double) data.get("price"));
				Add.setYear((Integer) data.get("year"));
				Set<Authors> author = new HashSet<Authors>();
				author.addAll((Collection<? extends Authors>) authorRepo.findByNameIn((List<String>) data.get("name")));
		        bookService.addBook(Add);
		        return new ResponseEntity<>(HttpStatus.CREATED);
			}
		}
		catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}	*/
    } 
	
	
	@PutMapping("/edit/{isbn}")
	public ResponseEntity<HttpStatus> updateBook(@PathVariable("isbn") String isbn, @RequestBody JsonNode objMap) throws Exception {
		ObjectMapper om = getObjectMapper();
		HashMap<String, Object> data = om.readValue(objMap.toString(), new TypeReference<HashMap<String, Object>>(){});
		for (String key : data.keySet()) {
		logger.info("key: " + key + " = " + data.get(key));
		}
		try {
			if (bookRepo.findByIsbn(isbn) == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else {
				Book existingBook = bookRepo.findById(isbn).orElse(null);

				existingBook.setIsbn(data.get("isbn").toString());
				existingBook.setTitle(data.get("title").toString());
				existingBook.setGenre(data.get("genre").toString());
				existingBook.setPrice((Double)data.get("price"));
				existingBook.setYear ((Integer)data.get("year"));
				Set<Authors> author = new HashSet<Authors>();
				author.addAll((Collection<? extends Authors>) authorRepo.findByNameIn((List<String>) data.get("name")));
				existingBook.setAuthors(author);
				logger.info("result" + existingBook.toString());
				bookService.updateBook(existingBook);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

}
