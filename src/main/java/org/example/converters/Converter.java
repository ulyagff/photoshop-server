package org.example.converters;

public interface Converter {
    double[] fromRGB(double[] rgbPixels);
    double[] toRGB(double[] pixels);
}
