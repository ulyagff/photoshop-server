package org.example.autocorrection;

import org.example.entities.PNM;

public class Histograms {

    public static double[] calculate(PNM image, int channel) {
        double[] histogram = new double[256]; // Для каждого канала (от 0 до 255)
        for (int i = 0; i < image.getPixels().length; i+=3) {
            double intensity = image.getPixels()[i + channel - 1];
            histogram[(int)intensity]++;
        }
        for (int i = 0; i < histogram.length; i++) {
            histogram[i] /= (image.getHeight() * image.getWidth());
        }
        double check = 0;
        for (int i = 0; i < histogram.length; i++) {
            check += histogram[i];
        }
        return histogram;
    }
}
