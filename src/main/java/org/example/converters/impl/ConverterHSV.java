package org.example.converters.impl;

import org.example.converters.ColorSpace;
import org.example.entities.PNM;

//@Component
public class ConverterHSV {
    public static PNM fromRGB(PNM image) {
        var rgbPixels = image.getPixels();
        double[] hsvPixels = new double[rgbPixels.length]; // 3 компонента: H, S и V

        for (int i = 0; i < rgbPixels.length / 3; i++) {
            int startIndex = i * 3;
            double rd = rgbPixels[startIndex] / 255.0;
            double gd = rgbPixels[startIndex + 1] / 255.0;
            double bd = rgbPixels[startIndex + 2] / 255.0;

            double cmax = Math.max(rd, Math.max(gd, bd));
            double cmin = Math.min(rd, Math.min(gd, bd));
            double delta = cmax - cmin;

            double h, s, v;

            if (delta == 0) {
                h = 0; // оттенок не определен
            } else if (cmax == rd) {
                h = 60 * (((gd - bd) / delta) % 6);
            } else if (cmax == gd) {
                h = 60 * (((bd - rd) / delta) + 2);
            } else {
                h = 60 * (((rd - gd) / delta) + 4);
            }

            if (cmax == 0) {
                s = 0;
            } else {
                s = delta / cmax;
            }

            v = cmax;
            hsvPixels[startIndex] = h * 255;
            hsvPixels[startIndex + 1] = s * 255;
            hsvPixels[startIndex + 2] = v * 255;
        }

        image.setColorSpace(ColorSpace.HSV);
        image.setPixels(hsvPixels);
        return image;
    }

    public static PNM toRGB(PNM image) {
        var pixels = image.getPixels();
        int pixelCount = pixels.length / 3;
        double[] rgbPixels = new double[pixelCount * 3];

        for (int i = 0; i < pixelCount; i++) {
            int startIndex = i * 3;
            double h = pixels[startIndex]/255;
            double s = pixels[startIndex + 1]/255;
            double v = pixels[startIndex + 2]/255;

            double c = v * s;
            double x = c * (1 - Math.abs((h / 60) % 2 - 1));
            double m = v - c;

            double r, g, b;

            int sector = (int) (h / 60);
//            double[] sectorValues = {c, x, 0, 0, x, c};
//
//            r = sectorValues[sector];
//            g = sectorValues[(sector + 4) % 6];
//            b = sectorValues[(sector + 2) % 6];

            switch (sector) {
                case 0:
                    r = c;
                    g = x;
                    b = 0;
                    break;
                case 1:
                    r = x;
                    g = c;
                    b = 0;
                    break;
                case 2:
                    r = 0;
                    g = c;
                    b = x;
                    break;
                case 3:
                    r = 0;
                    g = x;
                    b = c;
                    break;
                case 4:
                    r = x;
                    g = 0;
                    b = c;
                    break;
                default:
                    r = c;
                    g = 0;
                    b = x;
            }

            rgbPixels[startIndex] = (r + m) * 255;
            rgbPixels[startIndex + 1] = (g + m) * 255;
            rgbPixels[startIndex + 2] = (b + m) * 255;
        }

        image.setColorSpace(ColorSpace.RGB);
        image.setPixels(rgbPixels);
        return image;
    }
}
