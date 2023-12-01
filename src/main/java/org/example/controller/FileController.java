package org.example.controller;

import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping
@Controller
@CrossOrigin
public class FileController {
    @Autowired
    FileService service;

    @PostMapping("/upload")
    @ResponseBody
    public byte[] fileUpload(@RequestParam("file") MultipartFile file,
                             @RequestParam("ColorSpace") String colorSpace,
                             @RequestParam("gamma") double gamma) {
        try {
            service.uploadFile(file, colorSpace, gamma); // загрузили файл
            return service.getImage(); // и тут же его вернули
        } catch (IOException e) {
            return new byte[1];
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/getPath")
    @ResponseBody
    public String saveFile() {
        try {
            service.saveFileOnServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return service.pathToFile();
    }
}
