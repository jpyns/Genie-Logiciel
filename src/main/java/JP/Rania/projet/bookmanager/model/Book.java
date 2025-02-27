package JP.Rania.projet.bookmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotEmpty(message = "nom auteur manquant")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "titre manquant")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "date manquante")
    @Positive(message = "la date doit être positive")
    @Column(name = "year")
    private int year;

    @NotEmpty(message = "genre manquant")
    @Column(name = "genre")
    private String genre;

    @NotEmpty(message = "pays manquant")
    @Column(name = "country")
    private String country;

    public Book() {
    }

    public Book(Long id, String author, String title, int year, String genre, String country) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.country = country;
    }

    public Book(String author, String title, int year, String genre, String country) {
        this(null, author, title, year, genre, country);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
