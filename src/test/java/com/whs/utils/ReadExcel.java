package com.whs.utils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadExcel {
    //读取excel文件
    public static List<String[]> readExcel() throws IOException {
        String prourl="property/data.properties";
        String readurl=GetDataProperty.getproperdata(prourl,"readurl");
        List<String[]> readliststr=new ArrayList<String[]>();

        //开始读取
        InputStream is = ReadExcel.class.getClassLoader().getResourceAsStream(readurl);
        Workbook wb=new XSSFWorkbook(is);

        //开始解析
        Sheet sheet = wb.getSheetAt(0);     //读取sheet 0
        //获得行数信息

        int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
        int lastRowIndex = sheet.getLastRowNum();

        try{
            for(int rIndex = firstRowIndex; rIndex <=lastRowIndex; rIndex++) {   //遍历行
                //  System.out.println("rIndex: " + rIndex);
                Row row = sheet.getRow(rIndex);
//                int lastCellIndex = row.getLastCellNum();
                int colNum=sheet.getRow(firstRowIndex-1).getLastCellNum();//获取第一行列数
                if (row != null) {
                    //获取列数信息
                    String[] strarray=new String[colNum];

                    for (int cIndex = 0; cIndex < colNum; cIndex++) {   //遍历列
                        Cell cell = row.getCell(cIndex);
                        DataFormatter df=new DataFormatter();
                        strarray[cIndex]=df.formatCellValue(cell);
                    }
                    readliststr.add(strarray);
                }
            }
        }
        catch(Exception e){
            System.out.println("读取Excel文件报错！！");
            e.printStackTrace();
        }
        return readliststr;
    }


    @Test
    public void test() throws Exception {
        List<String[]> listparam=readExcel();
        Object param[][]=ChangeType.changeList(listparam);
        for(int i=0;i<param.length;i++){
            for(int j=0;j<param[i].length;j++)
            {
                System.out.println(  "param["+i+"]["+j+"]:"+param[i][j]);
            }
        }
    }

}
