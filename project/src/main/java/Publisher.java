/**
 * Created by christine on 17.01.17.
 */

/**
 * Class to represent a publisher
 */
public class Publisher{
    private String id;

    public Publisher(String publisherId) {
    	this.id = publisherId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
