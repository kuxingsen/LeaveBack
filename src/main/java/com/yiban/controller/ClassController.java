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
 * ���ڰ༶��·���������
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("class")
public class ClassController {
    @Autowired
    private ClassService classService;
    private Logger logger = LoggerFactory.getLogger(ClassController.class);

    /**
     * ��ȡ�༶���/views/index.html��
     * @param pageIndex ҳ��ҳ�룬��Ϊ��
     * @param search ���ҵİ༶�ţ����Ϊ�վͲ���ȫ������Ϊ�վͲ��Ҹð༶����ȷ����
     * @return ��json��ʽ���Ͱ༶������
     */
    @RequestMapping("/gettable")
    @ResponseBody
    public Result<ClassTable> getTable(int pageIndex,String search){
//        System.out.println("��ȡ���");
        int count = 10;
        if(search == null || search.equals("")){
            return classService.searchAllClassInPage(count,pageIndex);//��ȡָ��ҳ�������İ༶��¼
        }else {
            return classService.searchClassByClassId(search);//��ȡָ��classId�İ༶��¼
        }
    }

    /**
     * ��ȡָ��classId�İ༶��/views/modify.html��
     * @param classId �༶��ţ�һ�㲻Ϊ��
     * @return ��json��ʽ���͵����༶��¼
     */
    @RequestMapping("/getClass")
    @ResponseBody
    public AClassResult getAClass(String classId){
//        System.out.println("��ȡ�༶"+classId);
        if(classId != null)//һ�㲻Ϊ��
        {
            return classService.searchClassById(classId);//����ָ���༶��ŵĵ�����¼
        }else {
            AClassResult aClassResult = new AClassResult();
            aClassResult.setCode(-1);
            return aClassResult;
        }
    }

    /**
     * ��ȡ��Ӧ�İ༶����(/views/changecls.html)
     * @param classId �༶���
     * @return ��json��ʽ���Ͱ༶����
     */
    @RequestMapping("/getClassName")
    @ResponseBody
    public NameResult getClassName(@RequestParam("uId") String classId){
//        System.out.println("��ȡ�༶"+ classId);
        String name = classService.getClassName(classId);//��ȡ��Ӧ�İ༶����
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
     * ��Ӱ༶
     * @param classTable �����İ༶����Ϣ���༶��ţ��༶���ƣ�����Ա�������Ρ��೤���װ�id��
     * @param session ��Ҫ���ڻ�ȡaccessToken�����ڻ�ȡ�װ�id��Ӧ���ǳƣ��װ�Ľӿ����⵼���޷������ʵ���� https://openapi.yiban.cn/user/other��
     * @return ��json���سɹ���ʧ��
     */
    @RequestMapping("/addClass")
    @ResponseBody
    public IsSuccessResult addClass(ClassTable classTable,HttpSession session)
    {
//        System.out.println("��Ӱ༶"+classTable);
        String access_token = (String) session.getAttribute("accessToken");//��ȡaccessToken�����ڻ�ȡ�װ�id��Ӧ���ǳ�
        classTable = classService.setClassTableName(classTable,access_token);//��ȫclassTable��ͨ���װ�id��ȡ��Ӧ���ǳ�
        int r = classService.addClass(classTable);
        IsSuccessResult msg;
        if(r > 0){
            msg = new IsSuccessResult(0,"��ӳɹ�");
        }else {
            msg = new IsSuccessResult(-1,"���ʧ��");
        }
        return msg;
    }

    /**
     * ������Ӱ༶
     * @param file ������ļ������ļ���Ϊָ����ʽ���༶��ţ��༶���ƣ�����Ա�װ��ţ�����Ա�������ǳƣ����������α�ţ��������������೤��ţ��೤������
     *             ���ļ��ĵ�Ԫ����Ϊ�գ�����Ա�������Ρ��೤���װ�id��Ϊ��ʵid
     * @return �ɹ���ʧ��
     */
    @RequestMapping("/file")
    @ResponseBody
    public IsSuccessResult readExcel(@RequestParam(value = "file") MultipartFile file,HttpSession session) {
        if (file != null && !file.isEmpty()) {
            String access_token = (String) session.getAttribute("accessToken");
            return classService.readExcel(file,access_token);//��ȡexcel
        }
        return new IsSuccessResult(-1,"�ļ������ڻ��ļ�Ϊ��");
    }

    /**
     * ��ȡ�װ�id��Ӧ���ǳ�
     * @param uId �װ��ţ������Ǹ���Ա�������Ρ��೤��
     * @param session ��Ҫ���ڻ�ȡaccessToken�����ڻ�ȡ�װ�id��Ӧ���ǳƣ��װ�Ľӿ����⵼���޷������ʵ���� https://openapi.yiban.cn/user/other��
     * @return ��json���Ͱ༶����
     */
    @RequestMapping("/getName")
    @ResponseBody
    public NameResult getName(String uId, HttpSession session)
    {
//        System.out.println("�������");
        String access_token = (String) session.getAttribute("accessToken");
        String name = classService.getName(uId,access_token);//��ȡ�װ�id��Ӧ���ǳ�
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
     * �޸İ༶
     * @param classTable ���޸ĵİ༶����Ϣ���༶��ţ��༶���ƣ�����Ա�������Ρ��೤���װ�id��
     * @param session ��Ҫ���ڻ�ȡaccessToken�����ڻ�ȡ�װ�id��Ӧ���ǳƣ��װ�Ľӿ����⵼���޷������ʵ���� https://openapi.yiban.cn/user/other��
     * @return �ɹ���ʧ��
     */
    @RequestMapping("/modifyClass")
    @ResponseBody
    public IsSuccessResult modifyClass(ClassTable classTable,HttpSession session)
    {
//        System.out.println("�޸İ༶");
        System.out.println(classTable);
        String access_token = (String) session.getAttribute("accessToken");
        classTable = classService.setClassTableName(classTable,access_token);//��ȫclassTable��ͨ���װ�id��ȡ��Ӧ���ǳ�
        IsSuccessResult msg;
        int r = classService.modifyClass(classTable);//����ȫ�˵�classTable�������ݿ�
        if(r > 0){
            msg = new IsSuccessResult(0,"�޸ĳɹ�");
        }else {
            msg = new IsSuccessResult(-1,"�޸�ʧ��");
        }
        return msg;
    }

    /**
     * ɾ��ָ��id�İ༶
     * @param classId ��ɾ���İ༶��id
     * @return �ɹ���ʧ��
     */
    @RequestMapping("/deleteClass/{id}")
    @ResponseBody
    public IsSuccessResult deleteClass(@PathVariable("id") String classId)
    {
//        System.out.println("ɾ���༶"+classId);
        IsSuccessResult msg;
        //ǰ��δ����
        int r = classService.deleteClass(classId);
        if(r > 0){
            msg = new IsSuccessResult(0,"�޸ĳɹ�");
        }else {
            msg = new IsSuccessResult(-1,"�޸�ʧ��");
        }
        return msg;
    }
}
