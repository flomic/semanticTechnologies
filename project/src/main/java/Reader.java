import java.util.Date;
import java.util.LinkedList;

/**
 * Created by christine on 17.01.17.
 */

/**
 * Class to represent a reader. A reader extends Person and has in addition a library.
 * The library id is constructed out of "Lib_" + the id of the reader.
 */
public class Reader extends Person {
    private Library library = null;

    /**
     * Constructor for a reader. Creates also the library for the reader.
     * @param id
     * @param gender
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     */
    //TODO Should the id really be handed over in the constructor??
    public Reader(String id, String gender, String firstName, String lastName, String dateOfBirth) {
    	super(id, gender, firstName, lastName, dateOfBirth);
        library = new Library("Lib_"+this.getId());
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
