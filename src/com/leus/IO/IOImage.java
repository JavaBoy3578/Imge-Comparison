package com.leus.IO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IOImage {

    public static BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    public static boolean saveImage(BufferedImage scr, String pathToWrite) throws IOException {
        return ImageIO.write(scr, "PNG", new File(pathToWrite));
    }

}
