package org.example.controller;

import org.example.converters.ColorSpace;
import org.example.service.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/convert")
@Controller
@CrossOrigin
public class ConverterController {
    @Autowired
    private ConverterService service;

    @PostMapping("/newColorSpace")
    @ResponseBody
    public byte[] convertToNewColorSpace(@RequestParam("toColorSpace") String toColorSpace) throws CloneNotSupportedException {
        var image = service.convertToNewColorSpace(ColorSpace.valueOf(toColorSpace));
        return service.convertToNewColorSpace(image, ColorSpace.RGB).extractBytesForShow();
    }

    @PostMapping("/convertGamma")
    @ResponseBody
    public ResponseEntity<?> convertGamma(@RequestParam("newGamma") double newGamma) throws CloneNotSupportedException {
        if (newGamma < 0.0) {
            throw new IllegalArgumentException("newGamma должно быть >= 0.0");
        }
        try {
            service.convertGamma(newGamma);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
//        return service.convertGamma(newGamma).extractBytes();
    }

    @PostMapping("/assignGamma")
    @ResponseBody
    public byte[] assignGamma(@RequestParam("newGamma") double newGamma) throws CloneNotSupportedException {
        return service.assignGamma(newGamma).extractBytesForShow();
    }
}
