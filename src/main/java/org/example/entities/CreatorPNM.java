package org.example.entities;

import org.example.converters.ColorSpace;
import org.example.exceptions.ReadFileException;

public class CreatorPNM {
    public static PNM createPNM(byte[] byteStream, ColorSpace colorSpace, double gamma) {
        if (byteStream.length >= 2 && (char) byteStream[0] == 'P') {
            if ((char) byteStream[1] == '5') {
                return new PNM(ImageTypes.P5, byteStream, ColorSpace.NONE, gamma);
            } else if ((char) byteStream[1] == '6') {
                return new PNM(ImageTypes.P6, byteStream, colorSpace, gamma);
            } else {
                throw ReadFileException.NotPNMFIle();
            }
        } else {
            throw ReadFileException.UnexpectedEndOfFIle();
        }
    }
}
