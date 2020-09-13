import java.util.concurrent.RecursiveTask;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessor extends RecursiveTask<Integer> {
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
    
    /**
     * Ancho maximo representado en pixeles
     * hasta donde el procesador va a procesar
     */
    final int anchoMaximo = 100;

    /**
     * Alto maximo representado en pixeles
     * hasta donde el procesador va a procesar
     */
    final int altoMaximo = 100;

    ImageProcessor(BufferedImage file, int xInicial, int yInicial) {
        this.file = file;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        this.xFinal = this.file.getWidth();
        this.yFinal = this.file.getHeight();
    }

    ImageProcessor(BufferedImage file, int xInicial, int yInicial, int xFinal, int yFinal) {
        this.file = file;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        int ancho = this.xFinal - this.xInicial;
        int alto = this.yFinal - this.yInicial;

        if(ancho < this.anchoMaximo) {
            sum += 1;
        } else {
            int mitad = ancho / 2;

            System.out.println(String.format("ImageProcessor subProcessorA = new ImageProcessor(this.file, %s, %s, %s, %s)", this.xInicial, this.yInicial, mitad, this.yFinal));
            System.out.println(String.format("ImageProcessor subProcessorB = new ImageProcessor(this.file, %s, %s, %s, %s)", mitad, this.yInicial, this.xFinal, this.yFinal));
            ImageProcessor subProcessorA = new ImageProcessor(this.file, this.xInicial, this.yInicial, mitad, this.yFinal);
            ImageProcessor subProcessorB = new ImageProcessor(this.file, mitad, this.yInicial, this.xFinal, this.yFinal);

            // subProcessorA.fork();
            subProcessorB.fork();

            sum += subProcessorA.compute() + subProcessorB.join();
        }
        
        return sum;
    }









    // else if(alto > this.altoMaximo) {
        //     int mitad = alto / 2;

        //     ImageProcessor subProcessorA = new ImageProcessor(this.file, this.xInicial, this.yInicial, this.xFinal, mitad);
        //     ImageProcessor subProcessorB = new ImageProcessor(this.file, this.xInicial, mitad, this.xFinal, this.yFinal);

        //     subProcessorA.fork();
        //     subProcessorB.fork();

        //     sum += subProcessorA.join() + subProcessorB.join();
        // }

        // long red = 0, green = 0 ,blue = 0;

        // for(int i = this.xInicial; i < this.xFinal; i++) {
        //     for(int j = this.yInicial; j < this.yFinal; j++) {
        //         Color color = new Color(this.file.getRGB(i, j));

        //         red += color.getRed();
        //         green += color.getGreen();
        //         blue += color.getBlue();
        //     }
        // }

        // int calculo = ancho * alto;

        // red /= calculo;
        // green /= calculo;
        // blue /= calculo;
    
}
