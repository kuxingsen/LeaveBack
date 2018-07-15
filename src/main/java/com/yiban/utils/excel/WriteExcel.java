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
    public static boolean write(MultipartFile file)
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String day = format.format(date);
        String fileName = file.getOriginalFilename();
        String filePath = "D://TestLeave2//" + day + "//" + fileName;
        File excelFile = new File(filePath);
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(),excelFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
