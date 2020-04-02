import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.util.Scanner;


public class Main {
    private static BufferedImage image, output;
    private static int imageResX, imageResY;
    private static String path, name, extension;

    private static Color color;
    private static int r, g, b, newColor = 0xff;
    private static double v;
    private static int threshold;


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        if (args.length < 1) {
            System.out.println("画像のパスが必要です");
            return;
        }
        path = new File(args[0]).getParent();
        name = new File(args[0]).getName();
        extension = name.substring(name.lastIndexOf(".") + 1);
        loadImage(args[0]);
        System.out.print("閾値 : ");
        threshold = scan.nextInt();
        gray();
        binarization();
        saveImage();
    }

    private static void gray() {
        for (int x = 0; x < imageResX; x++) {
            for (int y = 0; y < imageResY; y++) {
                color = new Color(image.getRGB(x, y));
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                v = 0.299 * r + 0.587 * g + 0.114 * b;
                newColor = ((int) v << 16) + ((int) v << 8) + (int) v;
                output.setRGB(x, y, newColor);
            }
        }
    }

    private static void binarization() {
        for (int x = 0; x < imageResX; x++) {
            for (int y = 0; y < imageResY; y++) {
                color = new Color(image.getRGB(x, y));
                r = color.getRed();
                if (r < threshold) {
                    v = 0;
                } else {
                    v = 255;
                }
                newColor = ((int) v << 16) + ((int) v << 8) + (int) v;
                output.setRGB(x, y, newColor);
            }
        }
    }

    private static void loadImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageResX = image.getWidth();
        imageResY = image.getHeight();
        output = image;
    }

    private static void saveImage() {
        try {
            ImageIO.write(output, extension, new File(path + "\\edited_" + name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
