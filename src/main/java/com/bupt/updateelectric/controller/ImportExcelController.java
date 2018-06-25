package com.bupt.updateelectric.controller;

import com.bupt.updateelectric.service.ImportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/updateelectric")
public class ImportExcelController {

    @Autowired
    ImportExcelService importExcelService;

    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public String importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        importExcelService.importExcel(fileName,file);
        return "true";
    }
}
