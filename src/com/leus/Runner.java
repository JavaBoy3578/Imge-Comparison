package com.leus;

import com.leus.IO.IOImage;
import com.leus.business.ComparisonImage;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Runner {

    public static void main(String[] args) {
        String pathImage1;
        String pathImage2;
        String resultPath;

        System.out.println("Программа сравнит 2 изображения на соответствие");
        System.out.println("Изображения должны быть одинакового размера!");

        try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Укажите путь к первому изображению:");
            pathImage1 = in.readLine();
            BufferedImage firstImage = IOImage.loadImage(pathImage1);

            System.out.println("Укажите путь к второму изображению:");
            pathImage2 = in.readLine();
            BufferedImage secondImage = IOImage.loadImage(pathImage2);

            if (firstImage.getWidth() != secondImage.getWidth() && firstImage.getHeight() != secondImage.getHeight()) {
                System.out.println("Размеры изображений не равны!");
                System.exit(1);
            }

            System.out.println("Укажите путь для результата:");
            resultPath = in.readLine();

            System.out.println("Выполняется сравнение...");
            BufferedImage result = ComparisonImage.compareImages(firstImage, secondImage);
            IOImage.saveImage(result, resultPath);
            System.out.println("Выполнено!");
        } catch (IOException e) {
            System.out.println("Что-то пошло не так! Проверьте корректность путей!");
            System.exit(1);
        }
    }
}
