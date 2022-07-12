package com.example.eldentico.word;

import com.example.eldentico.transfer.entity.TransferEntity;
import com.example.eldentico.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class WordService {

    public void createSingleWord(TransferEntity transferEntity, UserEntity userEntity){
        try {
            log.info("Start creating single word document for 1 transfer.");
            XWPFDocument document = new XWPFDocument();
            makeHeader(document, "Transfer confirmation");
            makeText(document, "Sender: " + userEntity.getFirstName() + " " + userEntity.getLastName());
            String amountCurrency = "Amount: " + String.valueOf(transferEntity.getAmount()) + " " + transferEntity.getName();
            makeText(document, amountCurrency);
            log.info("Processing single word document for 1 transfer.");
            LocalDate localDate = transferEntity.getLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedDate = "Executed: " + localDate.format(formatter);
            makeText(document, formattedDate);
            timeStamp(document);
            saveWord(document, userEntity.getLastName());
            log.info("End creating single word document for 1 transfer.");
        }catch(Exception e){
            System.out.println("Error occurred while executing: createSingleWord.");
        }
    }

    public void createWordByUser(UserEntity user){
        log.info("Start creating word document for 1 user.");
        XWPFDocument document = new XWPFDocument();
        makeHeader(document,"History of all transfers of user: " +user.getFirstName()+" "+user.getLastName());
        List<TransferEntity> listTransfers = user.getTransferList();
        for (TransferEntity transferEntity : listTransfers) {
            createTextWord(document,transferEntity);
        }
        try {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String stringDate = localDate.format(formatterLocalDate);
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
            String stringTime = localTime.format(formatterLocalTime);
            File file = new File("D:/AllTransfers_" + user.getLastName() + "_" + stringDate + "_" + stringTime + ".docx");
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            document.write(out);
            out.close();
            log.info("End creating word document for 1 user.");
        } catch (Exception e){
            System.out.println("\"Error occurred while executing: createWordByUser");
        }
    }

    private void createTextWord(XWPFDocument document,TransferEntity transferEntity){
            makeText(document, "Transfer ID: " + transferEntity.getId());
            String amountCurrency = "Amount: " + String.valueOf(transferEntity.getAmount()) + " " + transferEntity.getName();
            makeText(document, amountCurrency);
            LocalDate localDate = transferEntity.getLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedDate = "Executed: " + localDate.format(formatter);
            makeText(document, formattedDate);
    }

    private void makeHeader(XWPFDocument document, String title_name){
        XWPFParagraph paragraphTitle = document.createParagraph();
        paragraphTitle.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphTitleRun = paragraphTitle.createRun();
        paragraphTitleRun.setText(title_name);
        paragraphTitleRun.setBold(true);
        paragraphTitleRun.setFontFamily("Arial");
        paragraphTitleRun.setFontSize(16);
    }

    private void makeText(XWPFDocument document, String text) {
        XWPFParagraph paragraphText = document.createParagraph();
        paragraphText.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphTextRun = paragraphText.createRun();
        paragraphTextRun.setText(text+ "\r\n"+System.lineSeparator()+"\r\n");
    }

    private void timeStamp(XWPFDocument document){
        XWPFParagraph paragraphText = document.createParagraph();
        paragraphText.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphTextRun = paragraphText.createRun();
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
        String stringTime = localTime.format(formatterLocalTime);
        paragraphTextRun.setText("Time: "+ stringTime);
    }

    private void saveWord(XWPFDocument document, String name) throws IOException {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String stringDate =localDate.format(formatterLocalDate);
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH-mm-ss");
        String stringTime = localTime.format(formatterLocalTime);
        File file = new File("D:/Transfer_"+name+"_"+stringDate +"_"+stringTime+".docx");
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        document.write(out);
        out.close();
    }
}
