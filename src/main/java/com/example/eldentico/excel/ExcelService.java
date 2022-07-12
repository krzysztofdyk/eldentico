package com.example.eldentico.excel;

import com.example.eldentico.transfer.entity.TransferEntity;
import com.example.eldentico.transfer.repository.TransferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@AllArgsConstructor
@Service
@Slf4j
public class ExcelService {

    TransferRepository transferRepository;

    public void createExcel(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.getSheet("Transfers");
        try {
            buildHeader(spreadsheet);
            buildBody(spreadsheet);
            saveFile(spreadsheet.getWorkbook());
        } catch(Exception e){
            System.out.println("Error occurred while executing: createExcel.");
        }
    }

    private void buildHeader(XSSFSheet sheet) {
        XSSFRow row = sheet.getRow(0);
        buildCells(row,1,blackCellStyle(sheet.getWorkbook()),"LP" );
        buildCells(row,2,blackCellStyle(sheet.getWorkbook()),"NAME" );
        buildCells(row,3,blackCellStyle(sheet.getWorkbook()),"DATE OF BIRTH" );
    }

    private void buildCells(XSSFRow row, int columnIndex,  XSSFCellStyle cellStyle, String columnValue ) {
        XSSFCell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(columnValue);
    }

    private XSSFCellStyle blackCellStyle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private Object[] mapTransferEntityToObjectArr(TransferEntity transferEntity){
        Object[] returnValue = new Object[] {transferEntity.getId(),transferEntity.getAmount(),transferEntity.getName()};
        return returnValue;
    }

    private void buildBody(XSSFSheet sheet){
        List<TransferEntity> transferEntityList = transferRepository.findAll();
        Map<Long, Object[]> transferEntityMap = new TreeMap<>();
        for (TransferEntity transferEntity : transferEntityList) {
            transferEntityMap.put(transferEntity.getId(), mapTransferEntityToObjectArr(transferEntity));
            }
        Set<Long> transferData = transferEntityMap.keySet();
        int rowid = 1;

        for (Long transferDataId : transferData) {
            XSSFRow row = sheet.createRow(rowid++);
            Object[] objectArr = transferEntityMap.get(transferDataId);
            int cellid = 1;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }
    }

    private void saveFile(XSSFWorkbook workbook){
        try{
            FileOutputStream out = new FileOutputStream( new File("D:/Transfers_list.xlsx"));
            workbook.write(out);
            out.close();}
        catch (IOException e){
            System.out.println("Error occurred while executing: saveFile.");
        }
    }

}
