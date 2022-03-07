package com.whs.utils;

import org.apache.log4j.Logger;

/**
 * @author gongru
 * @date 2021/5/1917:17
 */
public class LogUtil {
    public static  void  loguu(String classname,String contentline,String funname){
        Logger logger= Logger.getLogger(classname);

       // logger.info( "classname:"+classname+"...test_funciton_name:"+funname+";******LogMessage:"+contentline);

        logger.info( "\nclassname:"+classname+";...\ntest_funciton_name:"+funname+";\nLogMessage:"+contentline);


    }
}
