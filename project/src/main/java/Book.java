import java.awt.*;
import java.time.Year;

/**
 * Created by christine on 13.01.17.
 */


public class Book {
    private String bookId;
    private Author author;
    private String title;
    private String language;
    private Publisher publisher;
    private String genre;
    private Integer publicationYear;
    private Cover cover;

    public Book (String bookId, Author author, String title, String language, Publisher publisher, String genre, Integer publicationYear, Cover cover) {
    	this.bookId = bookId;
    	this.author = author;
    	this.title = title;
    	this.language = language;
    	this.publisher = publisher;
    	this.genre = genre;
    	this.publicationYear = publicationYear;
    	this.cover = cover;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

}
