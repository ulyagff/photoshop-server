package org.example.controller;

import org.example.converters.ColorSpace;
import org.example.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/filter")
@Controller
@CrossOrigin
public class FilterController {
    @Autowired
    private FilterService service;

    @PostMapping(value = "/oneChannel")
    @ResponseBody
    public byte[] oneChannelFilter(@RequestParam("channelNumber") int channel) throws CloneNotSupportedException {
        return service.oneChannelFilter(channel);
    }
}
