package org.example.service;

import org.example.autocorrection.HistogramEqualization;
import org.example.autocorrection.Histograms;
import org.example.converters.impl.ConverterYCbCr601;
import org.example.converters.impl.ConverterYCbCr709;
import org.example.entities.PNM;
import org.example.filters.Channel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class HistogramService {
    public byte[] getHistograms() throws IOException {
        var image = ImageService.getInstance().getImage();
        BufferedImage imageHist;
        if (image.getChannel() == Channel.NONE) {
            imageHist = multiHistogram(image);
        }
        else {
            imageHist = oneHistogram(image);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(imageHist, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private BufferedImage oneHistogram(PNM image) {
        double[] histogram = Histograms.calculate(image, Channel.toInteger(image.getChannel()));
        int width = 256;
        int height = 100;
        BufferedImage imageHist = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imageHist.createGraphics();

        g2d.setColor(Color.BLACK);
        drawHistogram(g2d, histogram, 1);
        g2d.dispose();
        return imageHist;
    }

    private BufferedImage multiHistogram(PNM image) {
        double[] histogramRed = Histograms.calculate(image, 1);
        double[] histogramGreen = Histograms.calculate(image, 2);
        double[] histogramBlue = Histograms.calculate(image, 3);

        int width = 256;
        int height = 300; // Высота для трех гистограмм
        BufferedImage imageHist = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imageHist.createGraphics();

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.RED);
        drawHistogram(g2d, histogramRed, 1);
//
        g2d.setColor(Color.GREEN);
        drawHistogram(g2d, histogramGreen, 2);

        g2d.setColor(Color.BLUE);
        drawHistogram(g2d, histogramBlue, 3);

        g2d.dispose();
        return imageHist;
    }

    private static void drawHistogram(Graphics2D g2d, double[] histogram, int numHistogram) {
        int barWidth = 1; // Ширина столбца гистограммы
        int maxBarHeight = 100; // Максимальная высота столбца

        double maxHistogramValue = 0;
        for (double value : histogram) {
            maxHistogramValue = Math.max(maxHistogramValue, value);
        }


        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) (histogram[i] / maxHistogramValue * maxBarHeight);
            int y = maxBarHeight - barHeight + (numHistogram - 1)*100;
            g2d.fillRect(i, y, barWidth, barHeight);
        }
    }

    public byte[] autocorrection(double boarder) {
        var image = ImageService.getInstance().getImage();
        if (image.getChannel()!=Channel.NONE) {
            HistogramEqualization.apply(image, Channel.toInteger(image.getChannel()), boarder);
        }
        else {
            ConverterYCbCr709.fromRGB(image);
            HistogramEqualization.apply(image, 1, boarder);
            ConverterYCbCr709.toRGB(image);
        }
        return image.extractBytesForShow();
    }
}
