package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.utils.Base64;
import com.weiyi.wx.order.common.utils.FileFactory;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.FileUploadRequest;
import com.weiyi.wx.order.request.UploadRequest;
import com.weiyi.wx.order.response.FileUploadResponse;
import com.weiyi.wx.order.response.UploadResponse;
import com.weiyi.wx.order.service.api.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class FileUploadController
{
    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private MenuService menuService;

    //菜图片存放根目录
    private static final String FOOD_IMG_DIR_ROOT = "/usr/data/images/foodimages/";

    //菜图片存放的虚拟根目录
    private static final String IMG_VIRTUAL_DIR_ROOT = "http://192.168.1.3:8080/images/foodimages/";
    /*
    *上传文件
    */
    @RequestMapping(value = "/file",method = {RequestMethod.POST})
    @ResponseBody
    public FileUploadResponse uploadFile( FileUploadRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter uploadFile() func,login name is:{}",request.getLoginName());
        }

        FileUploadResponse response = new FileUploadResponse();
        Result result = new Result();
        response.setResult(result);

        FileOutputStream fos = null;

        try {
            MultipartFile multipartFile =  request.getFile();
            String originFileName = multipartFile.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originFileName;

            //图片文件保存在  /usr/data/images/foodimages/     目录下
            File file = FileFactory.createFile(FOOD_IMG_DIR_ROOT + fileName);
            fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            //在tomcat的server.xml配置了虚拟目录
            response.setImgUrl(IMG_VIRTUAL_DIR_ROOT + fileName);
        }catch (IOException e){
            logger.error("save file error,the reason is :{}",e.getMessage());
            result.setRetCode(ErrorCode.UPLOAD_IMAGE_FAIL);
            result.setRetMsg("upload image error.");
        }finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
}
