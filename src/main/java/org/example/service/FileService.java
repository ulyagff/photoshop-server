package org.example.service;
import org.example.converters.ColorSpace;
import org.example.dao.FileRepository;
import org.example.entities.CreatorPNM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ConverterService converterService;

    public void uploadFile(MultipartFile file, String colorSpace, double gamma) throws IOException, CloneNotSupportedException {
        var imageService = ImageService.getInstance();
        var newImage = CreatorPNM.createPNM(file.getBytes(), ColorSpace.valueOf(colorSpace), gamma);
        // после загрузки файла мы его сохраняем такой, какой он есть
        var clone = newImage.clone();
        var bytes = clone.extractBytesForSave();
        fileRepository.save(bytes);
        imageService.setImage(newImage);
    }

    // у копии image переводим байты в rbg
    public byte[] getImage() throws CloneNotSupportedException {
        var image = ImageService.getInstance().getImage().clone();
        image = converterService.convertToNewColorSpace(image, ColorSpace.RGB);
        return image.extractBytesForShow();
    }

    public void saveFileOnServer() throws IOException, CloneNotSupportedException {
        fileRepository.save(ImageService.getInstance().getImage().clone().extractBytesForSave());
    }

    public String pathToFile() {
        return fileRepository.getPathToFile();
    }
}
