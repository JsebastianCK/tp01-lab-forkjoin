import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessor extends RecursiveTask<HashMap<String, Integer>> {
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
    final int anchoMaximo = 25;

    /**
     * Alto maximo representado en pixeles
     * hasta donde el procesador va a procesar
     */
    final int altoMaximo = 25;

    public ImageProcessor(BufferedImage file, int xInicial, int yInicial) {
        this.file = file;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        this.xFinal = this.file.getWidth();
        this.yFinal = this.file.getHeight();
    }

    public ImageProcessor(BufferedImage file, int xInicial, int yInicial, int xFinal, int yFinal) {
        this.file = file;
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
    }

    @Override
    protected HashMap<String, Integer> compute() {
        HashMap<String, Integer> sumColor;
        int ancho = this.xFinal - this.xInicial;
        int alto = this.yFinal - this.yInicial;

        if(ancho > this.anchoMaximo) {
            int mitad = ancho / 2;

            ImageProcessor subProcessorA = new ImageProcessor(this.file, this.xInicial, this.yInicial, this.xFinal - mitad, this.yFinal);
            ImageProcessor subProcessorB = new ImageProcessor(this.file, this.xInicial + mitad, this.yInicial, this.xFinal, this.yFinal);

            subProcessorA.fork();
            subProcessorB.fork();

            sumColor = this.mergeProcessedImagesColors(subProcessorA.join(), subProcessorA.join());
        } else if(alto > this.altoMaximo) {
            int mitad = alto / 2;

            ImageProcessor subProcessorA = new ImageProcessor(this.file, this.xInicial, this.yInicial, this.xFinal, this.yFinal - mitad);
            ImageProcessor subProcessorB = new ImageProcessor(this.file, this.xInicial, this.yInicial + mitad, this.xFinal, this.yFinal);

            subProcessorA.fork();
            subProcessorB.fork();

            
            sumColor = this.mergeProcessedImagesColors(subProcessorA.join(), subProcessorA.join());
        } else {
            sumColor = this.processImageColor();
        }

        return sumColor;
    }

    /**
     * Obtiene los colores promedios de la "subimagen"
     * a procesar por la subtarea
     * @return Colores promedios
     */
    private HashMap<String, Integer> processImageColor() {
        HashMap<String, Integer> colores = new HashMap<String, Integer>();

        int ancho = this.xFinal - this.xInicial;
        int alto = this.yFinal - this.yInicial;

        int red = 0, green = 0, blue = 0;
        for(int i = this.xInicial; i < this.xFinal; i++) {
            for(int j = this.yInicial; j < this.yFinal; j++) {
                Color color = new Color(this.file.getRGB(i, j));

                red += color.getRed();
                green += color.getGreen();
                blue += color.getBlue();
            }
        }

        int calculo = ancho * alto;

        colores.put("red", red / calculo);
        colores.put("green", green / calculo);
        colores.put("blue", blue / calculo);
        
        return colores;
    }    

    /**
     * Hace un merge de los resultados de las subtareas
     * para poder obtener el promedio de los colores
     * @param subtaskA Colores de la primer subtarea
     * @param subtaskB Colores de la segunda subtarea
     * @return Colores de los promedios de ambas subtareas
     */
    private HashMap<String, Integer> mergeProcessedImagesColors(HashMap<String, Integer> subtaskA, HashMap<String, Integer> subtaskB ) {
        HashMap<String, Integer> resultado = new HashMap<String, Integer>();

        resultado.put("red", (subtaskA.get("red") + subtaskB.get("red")) / 2);
        resultado.put("green", (subtaskA.get("green") + subtaskB.get("green")) / 2);
        resultado.put("blue", (subtaskA.get("blue") + subtaskB.get("blue")) / 2);

        return resultado;
    }
}
