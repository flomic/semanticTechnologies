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
    private String publisherId;
    private String genre;
    private Integer publicationYear;

    /**
     * Constructor of a book object
     * @param bookId
     * @param authorId
     * @param title
     * @param publisherId
     * @param genre
     * @param publicationYear
     */
    public Book(String bookId, String authorId, String title, String publisherId, String genre, Integer publicationYear) {
    	this.bookId = bookId;
    	this.authorId = authorId;
    	this.title = title;
    	this.publisherId = publisherId;
    	this.genre = genre;
    	this.publicationYear = publicationYear;
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
