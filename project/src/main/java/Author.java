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
    //TODO Should the id really be handed over in the constructor??
    public Author(String id, String name, String gender, String dateOfBirthString){
        super(id, name, gender, dateOfBirthString);
    }
}
