package org.example.converters.impl;

import org.example.converters.ColorSpace;
import org.example.entities.PNM;

public class ConverterYCbCr709 {
    public static PNM fromRGB(PNM image) {
        var rgbPixels = image.getPixels();
        double[] pixels = new double[rgbPixels.length];

        for (int i = 0; i < rgbPixels.length / 3; i++) {
            int startIndex = i * 3;
            double rChannel = rgbPixels[startIndex];
            double gChannel = rgbPixels[startIndex + 1];
            double bChannel = rgbPixels[startIndex + 2];

            pixels[startIndex] =  0.2126 * rChannel + 0.7152*gChannel + 0.0722*bChannel;
            pixels[startIndex + 1] = -0.1146 * rChannel - 0.3854*gChannel + 0.5*bChannel + 128;
            pixels[startIndex + 2] = 0.5 * rChannel - 0.4542*gChannel - 0.0458*bChannel + 128;

        }

        image.setColorSpace(ColorSpace.YCbCr709);
        image.setPixels(pixels);
        return image;
    }

    public static PNM toRGB(PNM image) {
        var pixels = image.getPixels();
        double[] rgbPixels = new double[pixels.length];

        for (int i = 0; i < pixels.length / 3; i++) {
            int startIndex = i * 3;
            double yChannel = pixels[startIndex];
            double cbChannel = pixels[startIndex + 1];
            double crChannel = pixels[startIndex + 2];

            rgbPixels[startIndex] = yChannel + 1.5748 * (crChannel - 128);
            rgbPixels[startIndex + 1] = yChannel - 0.1873 * (cbChannel - 128) - 0.4681 * (crChannel - 128);
            rgbPixels[startIndex + 2] = yChannel + 1.8556 * (cbChannel - 128);
//            rgbPixels[startIndex] = yChannel + 1.5748*crChannel;
//            rgbPixels[startIndex + 1] = yChannel - 0.1873*cbChannel - 0.4681*crChannel;
//            rgbPixels[startIndex + 2] = yChannel + 1.8556*cbChannel;

        }

        image.setColorSpace(ColorSpace.RGB);
        image.setPixels(rgbPixels);
        return image;
    }
}
