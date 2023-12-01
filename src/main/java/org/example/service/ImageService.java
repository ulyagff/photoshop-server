package org.example.service;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.PNM;

@Getter
public class ImageService {
    @Setter
    private PNM image;

    private static ImageService instance;

    private ImageService() {
        this.image = new PNM();
    }

    public static ImageService getInstance() {
        if (instance == null) {
            instance = new ImageService();
        }
        return instance;
    }
}
