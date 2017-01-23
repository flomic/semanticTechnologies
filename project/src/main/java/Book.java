import java.awt.*;
import java.time.Year;

/**
 * Created by christine on 13.01.17.
 */

/**
 * Class to represent a book object.
 */
public class Book {
    private String bookId;
    private String authorId;
    private String title;
    private String language;
    private String publisherId;
    private String genre;
    private Integer publicationYear;
    private Cover cover;

    /**
     * Constructor of a book object
     * @param bookId
     * @param authorId
     * @param title
     * @param language
     * @param publisherId
     * @param genre
     * @param publicationYear
     * @param cover
     */
    public Book (String bookId, String authorId, String title, String language, String publisherId, String genre, Integer publicationYear, Cover cover) {
    	this.bookId = bookId;
    	this.authorId = authorId;
    	this.title = title;
    	this.language = language;
    	this.publisherId = publisherId;
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

    public String getAuthor() {
        return authorId;
    }

    public void setAuthor(Author author) {
        this.authorId = authorId;
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

    public String getPublisher() {
        return publisherId;
    }

    public void setPublisher(Publisher publisher) {
        this.publisherId = publisherId;
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
