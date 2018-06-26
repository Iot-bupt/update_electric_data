package com.bupt.updateelectric.service;

import com.bupt.updateelectric.entity.DeviceData;
import com.bupt.updateelectric.process.GetToken;
import com.bupt.updateelectric.process.Publish;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;

@Service
public class ImportExcelService {

    private Publish publish = new Publish();
    private GetToken getToken = new GetToken();

    public boolean importExcel(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;

        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }

        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }

        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }

        DeviceData deviceData;
        for (int r = 1; r <= sheet.getLastRowNum(); r++){
            Row row = sheet.getRow(r);
            if(row == null){
                continue;
            }

            String deviceName = row.getCell(0).getStringCellValue();
            String dataKey;
            if(row.getCell(1)==null|| row.getCell(1).getStringCellValue().equals("") || row.getCell(1).getStringCellValue().equals("性能事件")){
                continue;
            }else{
                dataKey = row.getCell(1).getStringCellValue();
            }
            String cycle = row.getCell(2).getStringCellValue();

            String date = row.getCell(3).getStringCellValue();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Long ts = simpleDateFormat.parse(date).getTime();

            Double maxData;
            if(row.getCell(4).getStringCellValue().equals("-")){
                maxData = null;
            } else {
                maxData = Double.parseDouble(row.getCell(4).getStringCellValue());
            }
            Double normalData = Double.parseDouble(row.getCell(5).getStringCellValue());
            Double minData;
            if(row.getCell(6).getStringCellValue().equals("-")){
                minData = null;
            } else {
                minData = Double.parseDouble(row.getCell(6).getStringCellValue());
            }

            deviceData = new DeviceData(deviceName, dataKey, cycle, ts, maxData, normalData, minData);
            System.out.println(deviceData);

            String token = getToken.getToken(deviceData);

            publish.postElectricData(deviceData,token);
        }
        return true;
    }
}
