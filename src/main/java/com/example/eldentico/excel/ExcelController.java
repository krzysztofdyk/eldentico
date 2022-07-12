package com.example.eldentico.excel;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@AllArgsConstructor
public class ExcelController {

    ExcelService excelService;

    @GetMapping("/xlsx")
    public void getXlsx() {
        excelService.createExcel();
    }
}
