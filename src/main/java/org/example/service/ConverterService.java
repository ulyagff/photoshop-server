package org.example.service;

import org.example.converters.ColorSpace;
import org.example.converters.impl.*;
import org.example.entities.ImageTypes;
import org.example.entities.PNM;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class ConverterService {
    private final Map<ColorSpace, Function<PNM, PNM>> convertersToRGB;
    private final Map<ColorSpace, Function<PNM, PNM>> convertersFromRGB;

    public ConverterService() {
        convertersToRGB = new HashMap<>();
        convertersToRGB.put(ColorSpace.RGB, ConverterRGB::toRGB);
        convertersToRGB.put(ColorSpace.HSL, ConverterHSL::toRGB);
        convertersToRGB.put(ColorSpace.HSV, ConverterHSV::toRGB);
        convertersToRGB.put(ColorSpace.CMY, ConverterCMY::toRGB);
        convertersToRGB.put(ColorSpace.YCbCr601, ConverterYCbCr601::toRGB);
        convertersToRGB.put(ColorSpace.YCbCr709, ConverterYCbCr709::toRGB);
        convertersToRGB.put(ColorSpace.YCoCg, ConverterYCoCg::toRGB);

        convertersFromRGB = new HashMap<>();
        convertersFromRGB.put(ColorSpace.RGB, ConverterRGB::fromRGB);
        convertersFromRGB.put(ColorSpace.HSL, ConverterHSL::fromRGB);
        convertersFromRGB.put(ColorSpace.HSV, ConverterHSV::fromRGB);
        convertersFromRGB.put(ColorSpace.CMY, ConverterCMY::fromRGB);
        convertersFromRGB.put(ColorSpace.YCbCr601, ConverterYCbCr601::fromRGB);
        convertersFromRGB.put(ColorSpace.YCbCr709, ConverterYCbCr709::fromRGB);
        convertersFromRGB.put(ColorSpace.YCoCg, ConverterYCoCg::fromRGB);
    }

    // мы один раз выбираем цветовое пространство - при загрузке фото на сервер
    // далее оно сохраняется в синглтоне
    // при последующих изменениях цветового пространство - из какого - это берется из синглтона
    public PNM convertToNewColorSpace(ColorSpace toColorSpace) throws CloneNotSupportedException {
        var image = ImageService.getInstance().getImage();
        if (image.getType() != ImageTypes.P6) {
            throw new RuntimeException();
        }

        convertersToRGB.get(image.getColorSpace()).apply(image);
        convertersFromRGB.get(toColorSpace).apply(image);
        ImageService.getInstance().setImage(image);
        return image.clone();
    }

    // меняет любой image, не синглтон
    public PNM convertToNewColorSpace(PNM image, ColorSpace toColorSpace) throws CloneNotSupportedException {
        if (image.getType() != ImageTypes.P6) {
            throw new RuntimeException();
        }

        var newImage = convertersToRGB.get(image.getColorSpace()).apply(image);
        newImage = convertersFromRGB.get(toColorSpace).apply(newImage);
        return newImage.clone();
    }

    //метод просто изменит значение гамма у изображения, это будет влиять только на сохранение изображения
    public void convertGamma(double newGamma) throws CloneNotSupportedException {
        ImageService.getInstance().getImage().setGamma(newGamma);
    }

    public PNM assignGamma(double newGamma) throws CloneNotSupportedException {
        var image = ImageService.getInstance().getImage();
        image.setOutputGamma(newGamma);
        return image.clone();
    }
}
