package org.example.service;

import org.example.converters.ColorSpace;
import org.example.filters.Channel;
import org.example.filters.OneChannelFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {

    @Autowired
    ConverterService converterService;

    public byte[] oneChannelFilter(int channel) throws CloneNotSupportedException {
        ImageService.getInstance().getImage().setChannel(Channel.fromInteger(channel));
        var image = ImageService.getInstance().getImage().clone();
        if (image.getColorSpace() == ColorSpace.CMY || image.getColorSpace() == ColorSpace.RGB) {
            OneChannelFilter.applyFilterZero(image, channel);
        }
        if (image.getColorSpace() == ColorSpace.YCoCg) {
            OneChannelFilter.applyFilter(image, channel);
        }
        if (image.getColorSpace() == ColorSpace.HSV) {
            OneChannelFilter.applyFilterHSV(image, channel);
        }
        if (image.getColorSpace() == ColorSpace.HSL) {
            OneChannelFilter.applyFilterHSL(image, channel);
        }
        if (image.getColorSpace() == ColorSpace.YCbCr601 || image.getColorSpace() == ColorSpace.YCbCr709
        ) {
            OneChannelFilter.applyFilterYCbCr(image, channel);
        }
        converterService.convertToNewColorSpace(image, ColorSpace.RGB);
        if (ImageService.getInstance().getImage().getColorSpace() == ColorSpace.YCoCg) {
            OneChannelFilter.applyFilterBW(image, channel);
        }
        return image.extractBytesForShow();
    }
}
