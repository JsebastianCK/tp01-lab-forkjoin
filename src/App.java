import java.io.File;
import java.util.concurrent.ForkJoinPool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class App {
    public static void main(String[] args) throws Exception {
        File file = new File("images/red.png");

        BufferedImage image = ImageIO.read(file);

        ImageProcessor processor = new ImageProcessor(image, 0, 0);

        ForkJoinPool fjp = new ForkJoinPool(); 

        int numero = fjp.invoke(processor);

        System.out.println(String.format("Numero: %s", numero));
    }
}
