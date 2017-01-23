import java.util.Date;

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
     * @param gender
     * @param firstName
     * @param lastName
     * @param dateOfBirthString
     */
    //TODO Should the id really be handed over in the constructor??
    public Author(String id, String gender, String firstName, String lastName, String dateOfBirthString){
        super(id, gender, firstName, lastName, dateOfBirthString);
    }
}
