package com.leus.business;

import com.leus.util.Converter;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparisonImage {

    private static int widthImage;
    private static int heightImage;
    private static final List<Coordinate> AREA = new ArrayList<>();
    private static final List<Area> AREAS = new ArrayList<>();

    public static BufferedImage compareImages(BufferedImage first, BufferedImage second) {
        widthImage = first.getWidth();
        heightImage = first.getHeight();

        int[][] firstImagePixelArr = Converter.convertBufferedImageToPixelArray(first);
        int[][] secondImagePixelArr = Converter.convertBufferedImageToPixelArray(second);

        int[][] comparedImagePixelArr = comparePixels(firstImagePixelArr, secondImagePixelArr);

        makeAreas(comparedImagePixelArr);

        secondImagePixelArr = drawRectangle(secondImagePixelArr);

        second = Converter.convertPixelArrToBufferedImage(secondImagePixelArr);

        AREA.clear();
        AREAS.clear();

        return second;
    }

    private static int[][] drawRectangle(int[][] pixelArr) {
        for (Area elem : AREAS) {
            for (int k = elem.getMostLeftPixel().getX(); k < elem.getMostRightPixel().getX(); k++) {
                pixelArr[elem.getUpperPixel().getY()][k] = 0xFFFF0000;
                pixelArr[elem.getLowerPixel().getY()][k] = 0xFFFF0000;
            }

            for (int k = elem.getUpperPixel().getY(); k < elem.getLowerPixel().getY(); k++) {
                pixelArr[k][elem.getMostLeftPixel().getX()] = 0xFFFF0000;
                pixelArr[k][elem.getMostRightPixel().getX()] = 0xFFFF0000;
            }
        }

        return pixelArr;
    }

    private static int[][] comparePixels(int[][] firstImagePixelArr, int[][] secondImagePixelArr) {
        int[][] comparedImagePixelArr = new int[firstImagePixelArr.length][firstImagePixelArr[0].length];

        for (int i = 0; i < heightImage; i++) {
            for (int j = 0; j < widthImage; j++) {

                if (firstImagePixelArr[i][j] == secondImagePixelArr[i][j]) {
                    comparedImagePixelArr[i][j] = 0;
                } else if (((secondImagePixelArr[i][j] - firstImagePixelArr[i][j]) / firstImagePixelArr[i][j]) * 100 <= 10) {
                    comparedImagePixelArr[i][j] = 0;
                } else {
                    comparedImagePixelArr[i][j] = 1;
                }
            }
        }

        if (isVeryDifferentImages(comparedImagePixelArr)) {
            JOptionPane.showMessageDialog(null, "Images very different!");
            System.exit(0);
        }

        return comparedImagePixelArr;
    }

    private static boolean isVeryDifferentImages(int[][] comparedPixelArr) {
        int count = 0;
        for (int i = 0; i < heightImage; i++) {
            for (int j = 0; j < widthImage; j++) {
                if (comparedPixelArr[i][j] == 1) {
                    count++;
                }
            }
        }

        if (count >= widthImage * heightImage / 100 * 60) {
            return true;
        } else {
            return false;
        }
    }

    private static void makeAreas(int[][] comparedPixelArr) {
        for (int i = 0; i < heightImage; i++) {
            for (int j = 0; j < widthImage; j++) {
                if (comparedPixelArr[i][j] == 1) {
                    comparedPixelArr = findAreas(comparedPixelArr, i, j);
                }

                if (!AREA.isEmpty()) {
                    AREAS.add(new Area(findUpperPixelInArea(), findMostLeftPixelInArea(), findMostRightPixelInArea(), findLowerPixelInArea()));
                    AREA.clear();
                }
            }
        }
    }

    private static int[][] findAreas(int[][] comparedPixelArr, int column, int row) {
        if (column < 0 || column >= heightImage || row < 0 || row >= widthImage) {
            return comparedPixelArr;
        }

        if ((column == 0 || column == 1 && comparedPixelArr[column][row] == 0) || (row == 0 || row == 1 && comparedPixelArr[column][row] == 0) ||
                (column == heightImage - 1 || column == heightImage - 2 && comparedPixelArr[column][row] == 0) ||
                (row == widthImage - 1 || row == widthImage - 2 && comparedPixelArr[column][row] == 0)) {
            return comparedPixelArr;
        }

        boolean topPixel = false;
        boolean botPixel = false;
        boolean leftPixel = false;
        boolean rightPixel = false;
        boolean leftTopPixel = false;
        boolean rightTopPixel = false;
        boolean rightBotPixel = false;
        boolean leftBotPixel = false;

        if (column - 2 >= 0) {
            topPixel = comparedPixelArr[column - 2][row] == 0;
        }

        if (column + 2 < heightImage) {
            botPixel = comparedPixelArr[column + 2][row] == 0;
        }

        if (row - 2 >= 0) {
            leftPixel = comparedPixelArr[column][row - 2] == 0;
        }

        if (row + 2 < widthImage) {
            rightPixel = comparedPixelArr[column][row + 2] == 0;
        }

        if (column - 2 >= 0 && row - 2 >= 0) {
            leftTopPixel = comparedPixelArr[column - 2][row - 2] == 0;
        }

        if (column - 2 >= 0 && row + 2 < widthImage) {
            rightTopPixel = comparedPixelArr[column - 2][row + 2] == 0;
        }

        if (column + 2 < heightImage && row + 2 < widthImage) {
            rightBotPixel = comparedPixelArr[column + 2][row + 2] == 0;
        }

        if (column + 2 < heightImage && row - 2 >= 0) {
            leftBotPixel = comparedPixelArr[column + 2][row - 2] == 0;
        }

        if (comparedPixelArr[column][row] == 0 && topPixel && botPixel && leftPixel && rightPixel && leftTopPixel && rightTopPixel && rightBotPixel && leftBotPixel) {
            return comparedPixelArr;
        }

        AREA.add(new Coordinate(column, row));
        comparedPixelArr[column][row] = 0;

        if (!AREA.contains(new Coordinate(column - 1, row))) {
            comparedPixelArr = findAreas(comparedPixelArr, column - 1, row);
        }

        if (!AREA.contains(new Coordinate(column + 1, row))) {
            comparedPixelArr = findAreas(comparedPixelArr, column + 1, row);
        }

        if (!AREA.contains(new Coordinate(column, row - 1))) {
            comparedPixelArr = findAreas(comparedPixelArr, column, row - 1);
        }

        if (!AREA.contains(new Coordinate(column, row + 1))) {
            comparedPixelArr = findAreas(comparedPixelArr, column, row + 1);
        }

        if (!AREA.contains(new Coordinate(column + 1, row + 1))) {
            comparedPixelArr = findAreas(comparedPixelArr, column + 1, row + 1);
        }

        if (!AREA.contains(new Coordinate(column - 1, row - 1))) {
            comparedPixelArr = findAreas(comparedPixelArr, column - 1, row - 1);
        }

        if (!AREA.contains(new Coordinate(column - 1, row + 1))) {
            comparedPixelArr = findAreas(comparedPixelArr, column - 1, row + 1);
        }

        if (!AREA.contains(new Coordinate(column + 1, row - 1))) {
            comparedPixelArr = findAreas(comparedPixelArr, column + 1, row - 1);
        }

        return comparedPixelArr;
    }

    private static Coordinate findUpperPixelInArea() {
        AREA.sort(Comparator.comparingInt(Coordinate::getY));

        return AREA.get(0);
    }

    private static Coordinate findLowerPixelInArea() {
        AREA.sort((o1, o2) -> o2.getY() - o1.getY());

        return AREA.get(0);
    }

    private static Coordinate findMostLeftPixelInArea() {
        AREA.sort(Comparator.comparingInt(Coordinate::getX));

        return AREA.get(0);
    }

    private static Coordinate findMostRightPixelInArea() {
        AREA.sort((o1, o2) -> o2.getX() - o1.getX());

        return AREA.get(0);
    }
}
