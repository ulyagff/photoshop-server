package org.example.filters;

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

    public static void applyFilter(PNM image, int channel) {
        if (channel < 1 || channel > 3) {
            throw FilterException.invalidFilterNumber();
        }

        var newPixels = new double[image.getPixels().length];
        for (int i =0; i < image.getPixels().length; i+=3) {
            double[] pixel = Arrays.copyOfRange(image.getPixels(), i, i+3);
            var value = pixel[channel - 1];
            for (int j = 0; j < pixel.length; j++) {
                pixel[j] = value;
                newPixels[i+j] = pixel[j];
            }
        }
        image.setPixels(newPixels);
    }
}
