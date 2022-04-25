package com.whs.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class WriteExcel {

    String prourl="property/data.properties";
    String writeurl= PropertyManage.getproperdata(prourl,"writeurl");
    public void write(String str[]) throws Exception{
        FileInputStream in= new FileInputStream(writeurl);


        XSSFWorkbook workbook = new XSSFWorkbook(in);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row=sheet.getRow(1);

        FileOutputStream out = new FileOutputStream(writeurl);

        //追加行数据
        row=sheet.createRow((short)(sheet.getLastRowNum()+1)); //在现有行号后追加数据
        // row.createCell(0).setCellValue("测试数据"); //设置第一个（从0开始）单元格的数据
        //  row.createCell(1).setCellValue("haha"); //设置第二个（从0开始）单元格的数据*/
        for(int j=0;j<str.length;j++){

            row.createCell(j).setCellValue(str[j]);
        }

        try {
            out.flush();
            workbook.write(out);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
