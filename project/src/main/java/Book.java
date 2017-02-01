/**
 * Created by christine on 13.01.17.
 */

/**
 * Class to represent a book object.
 */
public class Book {
    private String isbn;
    private String authorId;
    private String title;
    private String publisherId;
    private String genre;
    private Integer publicationYear;

    /**
     * Constructor of a book object
     * @param isbn
     * @param authorId
     * @param title
     * @param publisherId
     * @param genre
     * @param publicationYear
     */
    public Book(String isbn, String authorId, String title, String publisherId, String genre, Integer publicationYear) {
    	this.isbn = isbn;
    	this.authorId = authorId;
    	this.title = title;
    	this.publisherId = publisherId;
    	this.genre = genre;
    	this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

}
