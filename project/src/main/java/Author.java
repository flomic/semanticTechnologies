/**
 * Created by christine on 17.01.17.
 */

/**
 * Class to represent an author. It simply extends Person.
 */
public class Author extends Person {

    /**
     * Constructor of an author
     *
     * @param id
     * @param name
     * @param gender
     * @param dateOfBirthString
     */
    public Author(String id, String name, String gender, String dateOfBirthString){
        super(id, name, gender, dateOfBirthString);
    }
}
