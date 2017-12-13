package com.leus.util;

import java.awt.image.BufferedImage;

public class Converter {

    public static BufferedImage convertPixelArrToBufferedImage(int[][] pixelArr) {
        BufferedImage result = new BufferedImage(pixelArr[0].length, pixelArr.length, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < pixelArr.length; i++) {
            for (int j = 0; j < pixelArr[i].length; j++) {
                result.setRGB(j, i, pixelArr[i][j]);
            }
        }

        return result;
    }

    public static int[][] convertBufferedImageToPixelArray(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();
        int[][] result = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = src.getRGB(j, i);
            }
        }

        return result;
    }
}
