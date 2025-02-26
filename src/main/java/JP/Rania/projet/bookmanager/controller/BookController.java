

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





// package JP.Rania.projet.bookmanager.controller;

// import JP.Rania.projet.bookmanager.model.Book;
// import JP.Rania.projet.bookmanager.repository.BookRepository;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/books")
// public class BookController {

//     private final BookRepository bookRepository;

//     public BookController(BookRepository bookRepository) {
//         this.bookRepository = bookRepository;
//     }

//     // 📌 POST /books → Créer un livre
//     @PostMapping
//     public ResponseEntity<Book> createBook(@RequestBody Book book) {
//         return ResponseEntity.ok(bookRepository.save(book));
//     }

//     // 📌 GET /books → Lister tous les livres
//     @GetMapping
//     public ResponseEntity<List<Book>> getAllBooks() {
//         return ResponseEntity.ok(bookRepository.findAll());
//     }

//     // 📌 GET /books/{id} → Afficher un livre
//     @GetMapping("/{id}")
//     public ResponseEntity<Book> getBookById(@PathVariable Long id) {
//         Optional<Book> book = bookRepository.findById(id);
//         return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     // 📌 PUT /books/{id} → Modifier un livre
//     @PutMapping("/{id}")
//     public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
//         return bookRepository.findById(id).map(book -> {
//             book.setAuthor(bookDetails.getAuthor());
//             book.setTitle(bookDetails.getTitle());
//             book.setYear(bookDetails.getYear());
//             book.setGenre(bookDetails.getGenre());
//             book.setCountry(bookDetails.getCountry());
//             return ResponseEntity.ok(bookRepository.save(book));
//         }).orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     // 📌 DELETE /books/{id} → Supprimer un livre
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
//         Optional<Book> book = bookRepository.findById(id);
//         if (book.isPresent()) {
//             bookRepository.delete(book.get());
//             return ResponseEntity.noContent().build();
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }
