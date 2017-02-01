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
     * @param name
     * @param gender
     * @param dateOfBirth
     */
    //TODO Should the id really be handed over in the constructor??
    public Reader(String id, String name, String gender, String dateOfBirth) {
    	super(id, name, gender, dateOfBirth);
        library = new Library("Lib_"+this.getId());
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
