package org.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.converters.ColorSpace;
import org.example.exceptions.ReadFileException;
import org.example.filters.Channel;
import org.example.filters.OneChannelFilter;

@Getter
@Setter
@NoArgsConstructor
public class PNM implements Image, Cloneable {
    private int maxDepth;
    private int height;
    private int width;
    private double[] pixels;
    private ColorSpace colorSpace;
    private Channel channel;
    private ImageTypes type;
    private double gamma;
    private double outputGamma;

    public PNM(ImageTypes type, byte[] byteStream, ColorSpace colorSpace, double gamma) {
        this.colorSpace = colorSpace;
        this.type = type;
        this.outputGamma = gamma;
        this.gamma = gamma;
        this.channel = Channel.NONE;
        int startPosition = setHeader(byteStream);
        convert(startPosition, byteStream);
        disableGamma(); //учитывая гамму, преобразовали пиксели к линейному ввиду
                           // (обратное преобразование относительно гаммы)
    }

    private int setHeader(byte[] byteStream) {
        int currentPosition = 3;
        StringBuilder buffer = new StringBuilder();
        while (currentPosition < byteStream.length && byteStream[currentPosition] != 32 && byteStream[currentPosition] != 10) {
            buffer.append((char) byteStream[currentPosition]);
            currentPosition++;
        }
        width = Integer.parseInt(buffer.toString());
        buffer.setLength(0);
        currentPosition++;

        while (currentPosition < byteStream.length && byteStream[currentPosition] != 10) {
            buffer.append((char) byteStream[currentPosition]);
            currentPosition++;
        }
        height = Integer.parseInt(buffer.toString());
        buffer.setLength(0);
        currentPosition++;

        while (currentPosition < byteStream.length && byteStream[currentPosition] != 10) {
            buffer.append((char) byteStream[currentPosition]);
            currentPosition++;
        }
        maxDepth = Integer.parseInt(buffer.toString());
        buffer.setLength(0);

        if (currentPosition == byteStream.length - 1) {
            throw ReadFileException.UnexpectedEndOfFIle();
        }
        return currentPosition;
    }

    private void convert(int startPosition, byte[] byteStream) {
        int currentPosition = startPosition + 1;

        int capacity = switch (type) {
            case P5 -> height * width;
            case P6 -> height * width * 3;
        };

        if (byteStream.length - currentPosition != capacity) {
            throw ReadFileException.UnexpectedEndOfFIle();
        }

        pixels = new double[capacity];
        int indexOfPixelArray = 0;
        while (currentPosition < byteStream.length) {
            pixels[indexOfPixelArray] = byteStream[currentPosition] & 0xFF;
            indexOfPixelArray++;
            currentPosition++;
        }
    }

    private byte[] whiteHeader() {
        int headerInfoSize = 6 + String.valueOf(width).length() + String.valueOf(height).length() + String.valueOf(maxDepth).length();
        byte[] rawFile = new byte[pixels.length + headerInfoSize];
        rawFile[0] = (byte) 'P';
        switch (type) {
            case P5 -> rawFile[1] = (byte) '5';
            case P6 -> rawFile[1] = (byte) '6';
        }
        return rawFile;
    }

    public byte[] extractBytesForSave() {
        applyGamma(gamma);
        if (channel != Channel.NONE) {
            OneChannelFilter.applyFilter(this, Channel.toInteger(channel));
        }
        return extractBytes();
    }

    public byte[] extractBytesForShow() {
        applyGamma(outputGamma);
        return extractBytes();
    }

    public byte[] extractBytes() {
        byte[] rawFile = whiteHeader();
        int headerInfoSize = 6 + String.valueOf(width).length() + String.valueOf(height).length() + String.valueOf(maxDepth).length();
        rawFile[2] = 10;
        int currentPosition = 3;
        for (int i = 0; i < String.valueOf(width).length(); i++) {
            rawFile[currentPosition++] = (byte) String.valueOf(width).charAt(i);
        }
        rawFile[currentPosition++] = (byte) ' ';
        for (int i = 0; i < String.valueOf(height).length(); i++) {
            rawFile[currentPosition++] = (byte) String.valueOf(height).charAt(i);
        }
        rawFile[currentPosition++] = 10;

        for (int i = 0; i < String.valueOf(maxDepth).length(); i++) {
            rawFile[currentPosition++] = (byte) String.valueOf(maxDepth).charAt(i);
        }
        rawFile[currentPosition++] = 10;

        while (currentPosition < pixels.length + headerInfoSize) {
            rawFile[currentPosition] = (byte) pixels[currentPosition - headerInfoSize];
            currentPosition++;
        }
        return rawFile;
    }

    @Override
    public PNM clone() throws CloneNotSupportedException {
        var copy = (PNM) super.clone();
        copy.pixels = this.pixels.clone();
        return copy;
    }

    public void disableGamma() {
        for (int i = 0; i < pixels.length / 3; i++) {
            int startIndex = i * 3;
            var rLinear = Math.pow(pixels[startIndex] / 255.0, 1 / gamma);
            var gLinear = Math.pow(pixels[startIndex + 1] / 255.0, 1 / gamma);
            var bLinear = Math.pow(pixels[startIndex + 2] / 255.0, 1 / gamma);

            pixels[startIndex] = rLinear * 255.0;
            pixels[startIndex + 1] = gLinear * 255.0;
            pixels[startIndex + 2] = bLinear * 255.0;
        }
    }

    public void applyGamma(double gamma) {
        for (int i = 0; i < pixels.length / 3; i++) {
            int startIndex = i * 3;
            var rLinear = Math.pow(pixels[startIndex] / 255.0, gamma);
            var gLinear = Math.pow(pixels[startIndex + 1] / 255.0, gamma);
            var bLinear = Math.pow(pixels[startIndex + 2] / 255.0, gamma);

            pixels[startIndex] = rLinear * 255.0;
            pixels[startIndex + 1] = gLinear * 255.0;
            pixels[startIndex + 2] = bLinear * 255.0;
        }
    }

}

