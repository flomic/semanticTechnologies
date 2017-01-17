import java.net.URL;

/**
 * Created by christine on 17.01.17.
 */
public class Publisher{
    private String publisherId;

    public Publisher(String publisherId) {
    	this.publisherId = publisherId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
}
