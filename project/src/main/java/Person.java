import java.util.Date;

/**
 * Created by christine on 17.01.17.
 */

/**
 * Class to represent a person
 */
public class Person {
    private String id;
    private String gender;
    private String name;
    private Date dateOfBirth;

    /**
     * Constructor
      * @param id
     * @param name
     * @param gender
     * @param dateOfBirthString
     */
    public Person(String id, String name, String gender,  String dateOfBirthString) {
        this.id = id; //TODO Generate id automatically??? LName + dateOfBirth
        this.gender = gender;
        this.name = name;
        if(dateOfBirthString != null && !dateOfBirthString.equals("")){ //TODO check if date format is correct
            this.dateOfBirth = new Date(Integer.parseInt(dateOfBirthString.substring(0,4))-1900, Integer.parseInt(dateOfBirthString.substring(5,7))-1, Integer.parseInt(dateOfBirthString.substring(8,10)));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }


    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirthString) {
        //TODO Check for correctness of the format of the string
        this.dateOfBirth = new Date(Integer.parseInt(dateOfBirthString.substring(0,4))-1900, Integer.parseInt(dateOfBirthString.substring(5,7))-1, Integer.parseInt(dateOfBirthString.substring(9,11)));
    }
}
