

package JP.Rania.projet.bookmanager.controller;

import JP.Rania.projet.bookmanager.model.Book;
import JP.Rania.projet.bookmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // add, delete update book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book newBook = bookService.saveBook(book);
        return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {
        boolean found = bookService.deleteBook(id);
        if (!found) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("Book " + id + " supprimé", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookService.updateBook(id, bookDetails)
            .map(updatedBook -> ResponseEntity.ok(updatedBook))  
            .orElseGet(() -> ResponseEntity.notFound().build());  
    }
    
    // get books

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        Optional<Book> result = bookService.getBookById(id);
        if (!result.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return result;
    }

}


