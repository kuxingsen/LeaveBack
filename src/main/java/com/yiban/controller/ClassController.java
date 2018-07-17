package com.yiban.controller;

import com.yiban.dto.AClassResult;
import com.yiban.dto.NameResult;
import com.yiban.dto.Result;
import com.yiban.dto.IsSuccessResult;
import com.yiban.entity.ClassTable;
import com.yiban.service.ClassService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * 关于班级的路径请求操作
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("class")
public class ClassController {
    @Autowired
    private ClassService classService;
    private Logger logger = LoggerFactory.getLogger(ClassController.class);

    /**
     * 获取班级表格（/views/index.html）
     * @param pageIndex 页面页码，不为空
     * @param search 查找的班级号，如果为空就查找全部，不为空就查找该班级，精确查找
     * @return 以json格式发送班级表结果集
     */
    @RequestMapping("/gettable")
    @ResponseBody
    public Result<ClassTable> getTable(int pageIndex,String search){
//        System.out.println("获取表格");
        int count = 10;
        if(search == null || search.equals("")){
            return classService.searchAllClassInPage(count,pageIndex);//获取指定页数条数的班级记录
        }else {
            return classService.searchClassByClassId(search);//获取指定classId的班级记录
        }
    }

    /**
     * 获取指定classId的班级（/views/modify.html）
     * @param classId 班级编号，一般不为空
     * @return 以json格式发送单条班级记录
     */
    @RequestMapping("/getClass")
    @ResponseBody
    public AClassResult getAClass(String classId){
//        System.out.println("获取班级"+classId);
        if(classId != null)//一般不为空
        {
            return classService.searchClassById(classId);//返回指定班级编号的单条记录
        }else {
            AClassResult aClassResult = new AClassResult();
            aClassResult.setCode(-1);
            return aClassResult;
        }
    }

    /**
     * 获取相应的班级名称(/views/changecls.html)
     * @param classId 班级编号
     * @return 以json格式发送班级名称
     */
    @RequestMapping("/getClassName")
    @ResponseBody
    public NameResult getClassName(@RequestParam("uId") String classId){
//        System.out.println("获取班级"+ classId);
        String name = classService.getClassName(classId);//获取相应的班级名称
        NameResult nameResult = new NameResult();
        if(name != null)
        {
            nameResult.setCode(0);
            nameResult.setUName(name);
        }else {
            nameResult.setCode(-1);
        }
        return nameResult;
    }

    /**
     * 添加班级
     * @param classTable 新增的班级的信息（班级编号，班级名称，辅导员、班主任、班长的易班id）
     * @param session 主要用于获取accessToken，用于获取易班id相应的昵称（易班的接口问题导致无法获得真实姓名 https://openapi.yiban.cn/user/other）
     * @return 以json返回成功或失败
     */
    @RequestMapping("/addClass")
    @ResponseBody
    public IsSuccessResult addClass(ClassTable classTable,HttpSession session)
    {
//        System.out.println("添加班级"+classTable);
        String access_token = (String) session.getAttribute("accessToken");//获取accessToken，用于获取易班id相应的昵称
        classTable = classService.setClassTableName(classTable,access_token);//补全classTable，通过易班id获取相应的昵称
        int r = classService.addClass(classTable);
        IsSuccessResult msg;
        if(r > 0){
            msg = new IsSuccessResult(0,"添加成功");
        }else {
            msg = new IsSuccessResult(-1,"添加失败");
        }
        return msg;
    }

    /**
     * 批量添加班级
     * @param file 导入的文件，该文件需为指定格式（班级编号，班级名称，辅导员易班编号，辅导员姓名（昵称？），班主任编号，班主任姓名，班长编号，班长姓名）
     *             且文件的单元格不能为空，辅导员、班主任、班长的易班id需为真实id
     * @return 成功或失败
     */
    @RequestMapping("/file")
    @ResponseBody
    public IsSuccessResult readExcel(@RequestParam(value = "file") MultipartFile file,HttpSession session) {
        if (file != null && !file.isEmpty()) {
            String access_token = (String) session.getAttribute("accessToken");
            return classService.readExcel(file,access_token);//读取excel
        }
        return new IsSuccessResult(-1,"文件不存在或文件为空");
    }

    /**
     * 获取易班id相应的昵称
     * @param uId 易班编号，可能是辅导员、班主任、班长的
     * @param session 主要用于获取accessToken，用于获取易班id相应的昵称（易班的接口问题导致无法获得真实姓名 https://openapi.yiban.cn/user/other）
     * @return 以json发送班级名称
     */
    @RequestMapping("/getName")
    @ResponseBody
    public NameResult getName(String uId, HttpSession session)
    {
//        System.out.println("获得姓名");
        String access_token = (String) session.getAttribute("accessToken");
        String name = classService.getName(uId,access_token);//获取易班id相应的昵称
        NameResult nameResult = new NameResult();
        if(name == null)
        {
            nameResult.setCode(-1);
        }else {
            nameResult.setCode(0);
            nameResult.setUName(name);
        }
        return nameResult;
    }

    /**
     * 修改班级
     * @param classTable 需修改的班级的信息（班级编号，班级名称，辅导员、班主任、班长的易班id）
     * @param session 主要用于获取accessToken，用于获取易班id相应的昵称（易班的接口问题导致无法获得真实姓名 https://openapi.yiban.cn/user/other）
     * @return 成功或失败
     */
    @RequestMapping("/modifyClass")
    @ResponseBody
    public IsSuccessResult modifyClass(ClassTable classTable,HttpSession session)
    {
//        System.out.println("修改班级");
        System.out.println(classTable);
        String access_token = (String) session.getAttribute("accessToken");
        classTable = classService.setClassTableName(classTable,access_token);//补全classTable，通过易班id获取相应的昵称
        IsSuccessResult msg;
        int r = classService.modifyClass(classTable);//将补全了的classTable填入数据库
        if(r > 0){
            msg = new IsSuccessResult(0,"修改成功");
        }else {
            msg = new IsSuccessResult(-1,"修改失败");
        }
        return msg;
    }

    /**
     * 删除指定id的班级
     * @param classId 需删除的班级的id
     * @return 成功或失败
     */
    @RequestMapping("/deleteClass/{id}")
    @ResponseBody
    public IsSuccessResult deleteClass(@PathVariable("id") String classId)
    {
//        System.out.println("删除班级"+classId);
        IsSuccessResult msg;
        //前端未完善
        int r = classService.deleteClass(classId);
        if(r > 0){
            msg = new IsSuccessResult(0,"修改成功");
        }else {
            msg = new IsSuccessResult(-1,"修改失败");
        }
        return msg;
    }
}
