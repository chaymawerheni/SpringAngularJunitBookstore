package com.vermeg.bookstore.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.entities.Provider;
import com.vermeg.bookstore.repositories.BookRepository;
import com.vermeg.bookstore.repositories.ProviderRepository;
import com.vermeg.bookstore.exception.ResourceNotFoundException;


@RestController
@RequestMapping({"/books"})
@CrossOrigin(origins = "*")
public class BookRestController {
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping("/list")
    public List<Book> getAllProvider() {
        return (List<Book>) bookRepository.findAll();
    }
	
	@PostMapping("/add")
    public Book createBook(@Valid @RequestBody Book book) {
        return bookRepository.save(book);
    }
	
	@PutMapping("/{bookId}")
    public Book updateBook(@PathVariable Long bookId, @Valid @RequestBody Book bookRequest) {
        return bookRepository.findById(bookId).map(book -> {
        	book.setTitre(bookRequest.getTitre());
        	book.setAuthor(bookRequest.getAuthor());
        	book.setPrix(bookRequest.getPrix());
        	book.setDatecreation(bookRequest.getDatecreation());
        	book.setNbrstock(bookRequest.getNbrstock());
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("BookId " + bookId + " not found"));
    }


    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        return bookRepository.findById(bookId).map(book -> {
        	bookRepository.delete(book);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("BookId " + bookId + " not found"));
    }
    @GetMapping("/{bookId}")
    public Optional<Book> getBook(@PathVariable Long bookId) {
        return  bookRepository.findById(bookId);
    }

}