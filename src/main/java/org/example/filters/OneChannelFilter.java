package org.example.filters;

import org.example.entities.ImageTypes;
import org.example.entities.PNM;
import org.example.exceptions.FilterException;

import java.util.Arrays;

public class OneChannelFilter {
    public static void applyFilterZero(PNM image, int channel) {
        if (channel < 1 || channel > 3) {
            throw FilterException.invalidFilterNumber();
        }

        var newPixels = new double[image.getPixels().length];
        for (int i =0; i < image.getPixels().length; i+=3) {
            double[] pixel = Arrays.copyOfRange(image.getPixels(), i, i+3);
            for (int j = 0; j < pixel.length; j++) {
                if (j != (channel - 1)) {
                    pixel[j] = 0;
                }
                newPixels[i+j] = pixel[j];
            }
        }
        image.setPixels(newPixels);
    }

    //создание p5 изображения при одном фильтре
    public static void applyFilter(PNM image, int channel) {
        if (channel < 1 || channel > 3) {
            throw FilterException.invalidFilterNumber();
        }

        var newPixels = new double[image.getPixels().length / 3];
        int pointer = 0;
        for (int i =0; i < image.getPixels().length; i+=3) {
            double[] pixel = Arrays.copyOfRange(image.getPixels(), i, i+3);
            for (int j = 0; j < pixel.length; j++) {
                if (j == (channel - 1)) {
                    newPixels[pointer++] = pixel[j];
                }
            }
        }
        image.setPixels(newPixels);
        image.setType(ImageTypes.P5);
    }

    public static void applyFilterYCbCr(PNM image, int channel) {
        if (channel < 1 || channel > 3) {
            throw FilterException.invalidFilterNumber();
        }

        var newPixels = new double[image.getPixels().length];
        for (int i =0; i < image.getPixels().length; i+=3) {
            double[] pixel = Arrays.copyOfRange(image.getPixels(), i, i+3);
            for (int j = 0; j < pixel.length; j++) {
                if (j != (channel - 1)) {
                    pixel[j] = 128;
                }
                newPixels[i+j] = pixel[j];
            }
        }
        image.setPixels(newPixels);
    }

    public static void applyFilterHSV(PNM image, int channel) {
        if (channel < 1 || channel > 3) {
            throw FilterException.invalidFilterNumber();
        }

        var newPixels = new double[image.getPixels().length];
        for (int i =0; i < image.getPixels().length; i+=3) {
            double[] pixel = Arrays.copyOfRange(image.getPixels(), i, i+3);
            for (int j = 0; j < pixel.length; j++) {
                if (j != (channel - 1)) {
                    pixel[j] = 255;
                }
                newPixels[i+j] = pixel[j];
            }
        }
        image.setPixels(newPixels);
    }

    public static void applyFilterHSL(PNM image, int channel) {
        if (channel < 1 || channel > 3) {
            throw FilterException.invalidFilterNumber();
        }

        var pixels = image.getPixels();

        for (int i = 0; i < pixels.length / 3; i++) {
            int startIndex = i * 3;
            double hChannel = pixels[startIndex];
            double sChannel = pixels[startIndex + 1];
            double lChannel = pixels[startIndex + 2];

            if (channel == 1) {
                pixels[startIndex] = hChannel;
                pixels[startIndex + 1] = 255;
                pixels[startIndex + 2] = 127.5;
            } else if (channel == 2) {
                pixels[startIndex] = 255;
                pixels[startIndex + 1] = sChannel;
                pixels[startIndex + 2] = 127.5;
            } else if (channel == 3) {
                pixels[startIndex] = 255;
                pixels[startIndex + 1] = 255;
                pixels[startIndex + 2] = lChannel;
            }

        }
    }
}
