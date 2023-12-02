package org.example.converters.impl;

import org.example.converters.ColorSpace;
import org.example.entities.PNM;

public class ConverterYCoCg {
    public static PNM fromRGB(PNM image) {
        var rgbPixels = image.getPixels();
        double[] pixels = new double[rgbPixels.length];

        for (int i = 0; i < rgbPixels.length / 3; i++) {
            int startIndex = i * 3;
            double rChannel = rgbPixels[startIndex] / 255;
            double gChannel = rgbPixels[startIndex + 1] / 255;
            double bChannel = rgbPixels[startIndex + 2] / 255;

            pixels[startIndex] =  0.25 * rChannel + 0.5*gChannel + 0.25*bChannel;
            pixels[startIndex + 1] = 0.5 * rChannel - 0.5*bChannel;
            pixels[startIndex + 2] = -0.25 * rChannel + 0.5*gChannel - 0.25*bChannel;

            pixels[startIndex] *= 255;
            pixels[startIndex + 1] *= 255;
            pixels[startIndex + 2] *= 255;


        }

        image.setColorSpace(ColorSpace.YCoCg);
        image.setPixels(pixels);
        return image;
    }

    public static PNM toRGB(PNM image) {
        var pixels = image.getPixels();
        double[] rgbPixels = new double[pixels.length];

        for (int i = 0; i < pixels.length / 3; i++) {
            int startIndex = i * 3;
            double yChannel = pixels[startIndex] / 255;
            double coChannel = pixels[startIndex + 1] / 255;
            double cgChannel = pixels[startIndex + 2] / 255;

            rgbPixels[startIndex] = (yChannel + coChannel - cgChannel) * 255;
            rgbPixels[startIndex + 1] = (yChannel + cgChannel) * 255;
            rgbPixels[startIndex + 2] = (yChannel - coChannel - cgChannel) * 255;

        }

        image.setColorSpace(ColorSpace.RGB);
        image.setPixels(rgbPixels);
        return image;
    }
}
