import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by christine on 17.01.17.
 */
public class Cover {
    private Image img;
    private String path;

    public Cover(String path) throws IOException {
        if (path != null && !path.equals("")){
            URL url = new URL(path);
            this.img = ImageIO.read(url);
        }
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
