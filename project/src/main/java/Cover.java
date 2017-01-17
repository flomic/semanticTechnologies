import java.awt.*;

/**
 * Created by christine on 17.01.17.
 */
public class Cover {
    private Image img;
    private String path;

    public Cover(Image img, String path) {
        this.img = img;
        this.path = path;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
