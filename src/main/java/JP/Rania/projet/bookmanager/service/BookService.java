package JP.Rania.projet.bookmanager.service;

import JP.Rania.projet.bookmanager.model.Book;
import JP.Rania.projet.bookmanager.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id).map(book -> {
            book.setAuthor(bookDetails.getAuthor());
            book.setTitle(bookDetails.getTitle());
            book.setYear(bookDetails.getYear());
            book.setGenre(bookDetails.getGenre());
            book.setCountry(bookDetails.getCountry());
            return bookRepository.save(book);
        });
    }

    public boolean deleteBook(Long id) {
        Optional<Book> book = getBookById(id);
        bookRepository.deleteById(id);
        return book.isPresent();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // même chose pour ube liste de livres 

    public List<Book> getBooksByIds(List<Long> bookIds) {
        return bookIds.stream()
                .map(this::getBookById) 
                .filter(Optional::isPresent) 
                .map(Optional::get) 
                .collect(Collectors.toList()); 
    }


}
   

