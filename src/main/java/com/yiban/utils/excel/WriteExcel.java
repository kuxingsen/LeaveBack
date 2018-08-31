package com.yiban.utils.excel;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kuexun on 2018/7/8.
 */
public class WriteExcel {
    public static String write(MultipartFile file)
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String day = format.format(date);
        String fileName = file.getOriginalFilename();
        String filePath = "C://LeaveBack//" + day + "//" + fileName;
        File excelFile = new File(filePath);
        if (!excelFile.getParentFile().exists()) {
            boolean mk=excelFile.getParentFile().mkdirs();
            System.err.println("create:"+mk);
        }
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(),excelFile);//先把文件保存下来以免不时之需
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return filePath;
    }

}
