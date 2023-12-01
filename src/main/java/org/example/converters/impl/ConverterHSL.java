package org.example.converters.impl;

import org.example.converters.ColorSpace;
import org.example.entities.PNM;
public class ConverterHSL {
    public static PNM fromRGB(PNM image) {
        var rgbPixels = image.getPixels();
        double[] hslPixels = new double[rgbPixels.length]; // 3 компоненты: H, S, L - оттенок, насыщенность, светлота

        for (int i = 0; i < rgbPixels.length / 3; i++) {
            int startIndex = i * 3;
            double rd = rgbPixels[startIndex] / 255.0;
            double gd = rgbPixels[startIndex + 1] / 255.0;
            double bd = rgbPixels[startIndex + 2] / 255.0;

            double cmax = Math.max(rd, Math.max(gd, bd));
            double cmin = Math.min(rd, Math.min(gd, bd));
            double h;
            double s = 0;
            var l = (cmax + cmin) / 2;

            if (cmax == cmin) {
                h = 0;
            } else {
                var d = cmax - cmin;
                s = l > 0.5 ? d / (2 - cmax - cmin) : d / (cmax + cmin);

                if (cmax == rd) {
                    h = (gd - bd) / d + (gd < bd ? 6 : 0);
                } else if (cmax == gd) {
                    h = (bd - rd) / d + 2;
                } else {
                    h = (rd - gd) / d + 4;
                }
            }

            h /= 6;
            hslPixels[startIndex] = h * 255;
            hslPixels[startIndex + 1] = s * 255;
            hslPixels[startIndex + 2] = l * 255;
        }

        image.setColorSpace(ColorSpace.HSL);
        image.setPixels(hslPixels);
        return image;
    }

    public static PNM toRGB(PNM image) {
        var hslPixels = image.getPixels();
        double[] rgbPixels = new double[hslPixels.length]; // 3 компонента: R, G и B

        for (int i = 0; i < hslPixels.length / 3; i++) {
            int startIndex = i * 3;
            double h = hslPixels[startIndex] / 255.0;
            double s = hslPixels[startIndex + 1] / 255.0;
            double l = hslPixels[startIndex + 2] / 255.0;

            if (s == 0) {
                var gray = l * 255;
                rgbPixels[startIndex] = gray;
                rgbPixels[startIndex + 1] = gray;
                rgbPixels[startIndex + 2] = gray;
                continue;
            }

            var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            var p = 2 * l - q;

            var r = hueToRgb(p, q, h + (double) 1 / 3);
            var g = hueToRgb(p, q, h);
            var b = hueToRgb(p, q, h - (double) 1 / 3);

            rgbPixels[startIndex] = r * 255;
            rgbPixels[startIndex + 1] = g * 255;
            rgbPixels[startIndex + 2] = b * 255;
        }

        image.setColorSpace(ColorSpace.RGB);
        image.setPixels(rgbPixels);
        return image;
    }

    private static double hueToRgb(double p, double q, double t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < (double) 1 / 6) return p + (q - p) * 6 * t;
        if (t < (double) 1 / 2) return q;
        if (t < (double) 2 / 3) return p + (q - p) * ((double) 2 / 3 - t) * 6;
        return p;
    }
}