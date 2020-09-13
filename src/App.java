import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.awt.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class App {
    public static void main(String[] args) throws Exception {
        File file = new File("images/red.png");

        BufferedImage image = ImageIO.read(file);

        ImageProcessor processor = new ImageProcessor(image, 0, 0);

        ForkJoinPool fjp = new ForkJoinPool(); 

        HashMap<String, Integer> color = fjp.invoke(processor);

        System.out.println(String.format("RGB: (%s, %s, %s)", color.get("red"), color.get("green"), color.get("blue")));
    }
}
