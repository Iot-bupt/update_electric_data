package com.bupt.updateelectric.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/updateelectric")
public class ImportExcelController {


    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public String importExcel(@RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        return "success";
    }
}
