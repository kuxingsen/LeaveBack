package com.yiban.controller;

import com.yiban.dto.Result;
import com.yiban.entity.Info;
import com.yiban.service.LeaveService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 *
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;
    private Logger logger = LoggerFactory.getLogger(LeaveController.class);

    /**
     *获取所有请假信息（/views/show.html）
     * @param pageIndex 页面页码，不为空
     * @param search 查找的学生学号，如果为空就查找全部，不为空就查找该学生的请假记录，精确查找
     * @return 以json格式发送请假记录
     */
    @RequestMapping("/getAllInfo")
    @ResponseBody
    public Result<Info> getAllInfo(int pageIndex,String search)
    {
        int count = 10;//每页显示的条数
        System.out.println("获取请假记录");
        if(search == null || search.equals("")){
            return leaveService.getAllInfoInPage(count,pageIndex);//获取指定页数条数的请假记录
        }else {
            return leaveService.searchInfoByStudentId(search);//获取指定studentId的请假记录
        }
    }

    /**
     * 导出所有学生请假信息的excel文件（光赐&达成完成）
     * @param session 用于获取路径
     * @return
     * @throws IOException 如果下载失败
     */
    @RequestMapping("/downloadExcel")
    public ResponseEntity <byte[]> downloadExcel(HttpSession session) throws IOException {
        String path = session.getServletContext().getRealPath("/temp/");
        String filePath = "学生请假信息表" + System.currentTimeMillis() + ".xls";
        File downFile = new File(path, filePath);

        if (!downFile.getParentFile().exists()) {
            boolean mk=downFile.getParentFile().mkdirs();
            System.err.println("create:"+mk);
        }

        if (leaveService.exportInformation(path + File.separator + filePath)) {
            File file = new File(path + File.separator + filePath);

            HttpHeaders headers = new HttpHeaders();

            //下载显示的文件名，解决中文名称乱码问题
            String downloadFielName = new String(filePath.getBytes("UTF-8"),
                    "iso-8859-1");
            //通知浏览器以attachment（下载方式）
            headers.setContentDispositionFormData("attachment", filePath);
            //application/octet-stream ： 二进制流数据（最常见的文件下载）。
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            /*
              解决IE不能下载文件问题
             */
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping("/search/{studentId}")
    @ResponseBody
    public Result<Info> searchClassByClassId(@PathVariable("studentId") String studentId)
    {
        return leaveService.searchInfoByStudentId(studentId);
    }
}
