package thread.performance.latency;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Multithreaded Solution - Partition the image
 * Break up the image into as many parts as we have threads
 * Allocate each thread to process only it's portion
 * Then we save the image buffer once the threads are all done
 */

public class Main {
    public static final String DESTINATION_FILE = "src/main/java/thread/performance/latency/out/many-flowers-purple.jpg";

    public static void main(String[] args) throws IOException {
        String sourceFile = "src/main/java/thread/performance/latency/many-flowers.jpg";

        BufferedImage originalImage = ImageIO.read(new File(sourceFile));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long startTimeMulti = System.currentTimeMillis();
        recolorMultiThreaded(originalImage, resultImage, 3);
        long endTimeMulti = System.currentTimeMillis();
        System.out.println("Time for multithreaded: " + (endTimeMulti - startTimeMulti));

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);
    }

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage){
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads){
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        for (int i = 0; i < numberOfThreads; i++){
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> { // Each thread gets its own image partition
                int leftCorner = 0;
                int topCorner = height * threadMultiplier;

                recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
            });

            threads.add(thread);
        }

        for (Thread thread : threads){
            thread.start();
        }

        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e){
            }
        }
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner,
                                    int width, int height){
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++){
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++){
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y){
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        // The shade of purple we want is closer to red - so we remove effect of green heavily, improve red, slightly lower blue
        if (isShadeOfGray(red, green, blue)){
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb){
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    // Check if all components are similar in value (using empirical value of 30 for proximity)
    public static boolean isShadeOfGray(int red, int green, int blue){
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue){
        int rgb = 0;

        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

    public static int getRed(int rgb){
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb){
        return (rgb & 0x0000FF00) >> 8; // Green is second byte from the right
    }

    public static int getBlue(int rgb){
        return rgb & 0x000000FF; // Apply a bit mask to get the blue value
    }
}
