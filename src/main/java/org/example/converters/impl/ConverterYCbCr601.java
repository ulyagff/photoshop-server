package org.example.converters.impl;

import org.example.converters.ColorSpace;
import org.example.entities.PNM;

public class ConverterYCbCr601 {
    public static PNM fromRGB(PNM image) {
        var rgbPixels = image.getPixels();
        double[] pixels = new double[rgbPixels.length];

        for (int i = 0; i < rgbPixels.length / 3; i++) {
            int startIndex = i * 3;
            double rChannel = rgbPixels[startIndex];
            double gChannel = rgbPixels[startIndex + 1];
            double bChannel = rgbPixels[startIndex + 2];

            pixels[startIndex] = (0.299 * rChannel) + (0.587*gChannel) + (0.114*bChannel);
            pixels[startIndex + 1] = -0.168736 * rChannel - 0.331264 * gChannel + 0.5 * bChannel + 128;
            pixels[startIndex + 2] = (0.5 * rChannel) - (0.418688*gChannel) - (0.081312*bChannel) + 128;

//            pixels[startIndex] = 16 + (65.738 * rChannel)/256 + (129.057*gChannel)/256 + (25.064*bChannel)/256;
//            pixels[startIndex + 1] = 128 + (-37.945 * rChannel)/256 + (74.494*gChannel)/256 + (112.439*bChannel)/256;
//            pixels[startIndex + 2] = 128 + (112.439 * rChannel)/256 + (94.154*gChannel)/256 + (18.285*bChannel)/256;


//            pixels[startIndex] = 16 + (65.481 * rChannel)/255 + (128.553*gChannel)/255 + (24.966*bChannel)/255;
//            pixels[startIndex + 1] = 128 - (37.797 * rChannel)/255 - (74.203*gChannel)/255 + (112.0*bChannel)/255;
//            pixels[startIndex + 2] = 128 + (112.0 * rChannel)/255 - (93.786*gChannel)/255 - (18.214*bChannel)/255;

        }

        image.setColorSpace(ColorSpace.YCbCr601);
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

            rgbPixels[startIndex] = yChannel + 1.402 * (crChannel - 128);
            rgbPixels[startIndex + 1] = yChannel - 0.344136 * (cbChannel - 128) - 0.714136 * (crChannel - 128);
            rgbPixels[startIndex + 2] = yChannel + 1.772 * (cbChannel - 128);

//            rgbPixels[startIndex] = (298.082 * yChannel)/256 + (408.583*crChannel)/256 - 222.921;
//            rgbPixels[startIndex + 1] = (298.082 * yChannel)/256 - (100.291*cbChannel)/256 - (208.120*crChannel)/256 + 135.576;
//            rgbPixels[startIndex + 2] = (298.082 * yChannel)/256 + (516.412*cbChannel)/256 - 276.836;

        }

        image.setColorSpace(ColorSpace.RGB);
        image.setPixels(rgbPixels);
        return image;
    }
}
