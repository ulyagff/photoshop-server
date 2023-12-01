package org.example.converters.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.converters.ColorSpace;
import org.example.entities.PNM;

//@Component
public class ConverterCMY {
    public static PNM fromRGB(PNM image) {
//        double[] cmyPixels = new double[image.getPixels().length];
//
//        for (int i = 0; i < image.getPixels().length / 3; i++) {
//            int startIndex = i * 3;
//            double rd = image.getPixels()[startIndex] / 255.0; // c
//            double gd = image.getPixels()[startIndex + 1] / 255.0; // m
//            double bd = image.getPixels()[startIndex + 2] / 255.0; // y
//            double k = 1 - Double.max(rd, Double.max(gd, bd));
//            cmyPixels[startIndex] = (1 - rd- k)/(1 - k);
//            cmyPixels[startIndex + 1] = (1 - gd- k)/(1 - k);
//            cmyPixels[startIndex + 2] = (1 - bd- k)/(1 - k);
//
//        }
//
//        image.setColorSpace(ColorSpace.CMY);
//        image.setPixels(cmyPixels);
//        return image;

        var rgbPixels = image.getPixels();
        double[] cmyPixels = new double[rgbPixels.length];

        for (int i = 0; i < rgbPixels.length / 3; i++) {
            int startIndex = i * 3;
            cmyPixels[startIndex] = 1 - rgbPixels[startIndex] / 255.0; // c
            cmyPixels[startIndex + 1] = 1 - rgbPixels[startIndex + 1] / 255.0; // m
            cmyPixels[startIndex + 2] = 1 - rgbPixels[startIndex + 2] / 255.0; // y
        }

        image.setColorSpace(ColorSpace.CMY);
        image.setPixels(cmyPixels);
        return image;
    }

    public static PNM toRGB(PNM image) {
        var pixels = image.getPixels();
        double[] rgbPixels = new double[pixels.length];

        for (int i = 0; i < pixels.length / 3; i++) {
            int startIndex = i * 3;
            rgbPixels[startIndex] = (1 - pixels[startIndex]) * 255.0;
            rgbPixels[startIndex + 1] = (1 - pixels[startIndex + 1]) * 255.0;
            rgbPixels[startIndex + 2] = (1 - pixels[startIndex + 2]) * 255.0;
        }

        image.setColorSpace(ColorSpace.RGB);
        image.setPixels(rgbPixels);
        return image;
    }
}
