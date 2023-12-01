package org.example.service;

import org.example.converters.ColorSpace;
import org.example.filters.OneChannelFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {

    @Autowired
    ConverterService converterService;

    public byte[] oneChannelFilter(int channel) throws CloneNotSupportedException {
        var image = ImageService.getInstance().getImage().clone();
        if (image.getColorSpace() == ColorSpace.CMY || image.getColorSpace() == ColorSpace.RGB
                || ((image.getColorSpace() == ColorSpace.HSV || image.getColorSpace() == ColorSpace.HSL)
                && channel == 3)  ) {
            OneChannelFilter.applyFilterZero(image, channel);
        }
        if (image.getColorSpace() == ColorSpace.CMY || image.getColorSpace() == ColorSpace.RGB) {

        }
        var imageRgb = converterService.convertToNewColorSpace(image, ColorSpace.RGB);
        return imageRgb.extractBytesForShow();
    }
}
