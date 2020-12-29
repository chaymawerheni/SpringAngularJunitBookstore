package com.vermeg.bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.repositories.BookRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class BookRepositoryTests {
	@Autowired
	private BookRepository repo;

	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateBook() {
		Book savedBook = repo.save(new Book("titre","auteur", 20 , "12/20/20" , 5));

		assertThat(savedBook.getId()).isGreaterThan(0);
	}

	
	@Test
	@Order(2)
	public void testFindBookByTitre() {
		Book book = repo.findByTitre("The Magic Book");
		assertThat(book.getTitre()).isEqualTo("The Magic Book");
	}
	

	@Test
	@Order(3)
	public void testListProducts() {
		List<Book> books = (List<Book>) repo.findAll();
		assertThat(books).size().isGreaterThan(0);
	}
	 

	@Test
	@Rollback(true)
	@Order(4)
	public void testUpdateBook() {
		Book book = repo.findByTitre("The Magic Book");
		book.setPrix(1000);

		repo.save(book);

		Book updatedBook = repo.findByTitre("The Magic Book");

		assertThat(updatedBook.getPrix()).isEqualTo(1000);
	}
	/*

	@Test
	@Rollback(false)
	@Order(5)
	public void testDeleteProduct() {
		Book book = repo.findByTitre("The Magic Book");

		repo.deleteById(book.getId());

		Book deletedBook = repo.findByTitre("The Magic Book");

		assertThat(deletedBook).isNull();

	}*/
}