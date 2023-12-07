package org.example.controller;

import org.example.service.HistogramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@CrossOrigin
@RequestMapping
public class HistogramController {
    @Autowired
    HistogramService service;

    @GetMapping("/histogram")
    @ResponseBody
    public byte[] generateHistogram() {
        try {
            return service.getHistograms();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/autocorrection")
    @ResponseBody
    public byte[] autocorrection(@RequestParam("boarder") double boarder) {
        return service.autocorrection(boarder);
    }
}
