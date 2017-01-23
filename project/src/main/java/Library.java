import java.util.LinkedList;

/**
 * Created by christine on 17.01.17.
 */

/**
 * Class to represent the collection of books of a reader
 */
public class Library {
    private String id;
    private LinkedList<Book> books;

    public Library(String id){
    	this.id = id;
    }

    public LinkedList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public String getId(){
        return id;
    }
}
