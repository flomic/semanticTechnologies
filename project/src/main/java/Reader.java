import java.util.Date;
import java.util.LinkedList;

/**
 * Created by christine on 17.01.17.
 */
public class Reader extends Person {
    private Library library = new Library("Lib_"+this.getId());

    public Reader(String id, String gender, String firstName, String lastName, String dateOfBirth) {
    	super(id, gender, firstName, lastName, dateOfBirth);
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
