import java.util.concurrent.RecursiveTask;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessor extends RecursiveTask<Color> {
    /**
     * Archivo a procesar
     */
    private BufferedImage file;

    /**
     * Posicion dentro del eje horizontal
     * a partir de la cual se va a procesar
     */
    private int xInicial;

    /**
     * Posicion dentro del eje vertical
     * a partir de la cual se va a procesar
     */
    private int yInicial;

    /**
     * Posicion dentro del eje horizontal
     * hasta la cual se va a procesar
     */
    private int xFinal;

    /**
     * Posicion dentro del eje vertical
     * hasta la cual se va a procesar
     */
    private int yFinal;

    ImageProcessor(BufferedImage file, int xInicial, int yInicial) {
        this.file = file;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
    }

    ImageProcessor(BufferedImage file, int xInicial, int yInicial, int xFinal, int yFinal) {
        this.file = file;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
    }

    @Override
    protected Color compute() {
        int ancho = this.file.getWidth();
        int alto = this.file.getHeight();

        long red = 0, green = 0 ,blue = 0;

        for(int i = 0; i < ancho; i++) {
            for(int j = 0; j < alto; j++) {
                Color color = new Color(this.file.getRGB(i, j));

                red += color.getRed();
                green += color.getGreen();
                blue += color.getBlue();
            }
        }

        red /= ancho*alto;
        green /= ancho*alto;
        blue /= ancho*alto;

        return null;
    }

    
}
