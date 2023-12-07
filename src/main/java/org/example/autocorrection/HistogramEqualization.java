package org.example.autocorrection;

import org.example.entities.PNM;

public class HistogramEqualization {
    public static void apply(PNM image, int channel, double boarder) {
        var histogram = Histograms.calculate(image, channel);
        int minHistogram = 0;
        while (minHistogram < histogram.length) {
            if (histogram[minHistogram] == 0) {
                minHistogram++;
            }
            else {
                break;
            }
        }

        int maxHistogram = 255;
        while (maxHistogram > 0) {
            if (histogram[maxHistogram] == 0) {
                maxHistogram--;
            }
            else {
                break;
            }
        }

        //TODO: обработка случаев границ гистограммы

//        int range = maxHistogram - minHistogram;
//        int min = (int)(minHistogram + (range * boarder));
//        int max = (int)(maxHistogram - (range * boarder));

        int min = minHistogram;
        int max = maxHistogram;

        for (int i = 0; i < image.getPixels().length; i+=3) {
            double y = image.getPixels()[i + channel - 1];
            y = ((y - min)*255)/(max - min);
            image.getPixels()[i + channel - 1] = y;
        }

//        for (int i = 0; i < image.getPixels().length; i+=3) {
//            double y = image.getPixels()[i + channel - 1];
//            y = histogram[(int6(histogram.length*y)];
//            image.getPixels()[i + channel - 1] = y;
//        }

    }
}
