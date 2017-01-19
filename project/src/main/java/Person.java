import java.util.Date;

/**
 * Created by christine on 17.01.17.
 */
public class Person {
    private String id;
    private String gender;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public Person(String id, String gender, String firstName, String lastName, String dateOfBirthString) {
        this.id = id;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = new Date(Integer.parseInt(dateOfBirthString.substring(0,4))-1900, Integer.parseInt(dateOfBirthString.substring(5,7))-1, Integer.parseInt(dateOfBirthString.substring(8,10)));
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirthString) {
        this.dateOfBirth = new Date(Integer.parseInt(dateOfBirthString.substring(0,4))-1900, Integer.parseInt(dateOfBirthString.substring(5,7))-1, Integer.parseInt(dateOfBirthString.substring(9,11)));
    }
}