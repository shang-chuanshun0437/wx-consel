package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.utils.Base64;
import com.weiyi.wx.order.common.utils.FileFactory;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.UploadRequest;
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
public class UploadController
{
    private Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private MenuService menuService;

    /*
    *上传图片
    */
    @RequestMapping(value = "/img",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UploadResponse uploadImg(@RequestBody UploadRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter uploadImg() func,phone num:{}",request.getUserPhone());
        }

        UploadResponse response = new UploadResponse();
        Result result = new Result();
        response.setResult(result);

        FileOutputStream fos = null;

        try {
            String base64Data =  request.getMultipartFile().split(",")[1];
            byte[] bytes = Base64.decode(base64Data);

             //字节流转文件,
            String fileName = System.currentTimeMillis() + "_" + FileFactory.getFileNameNoEx(request.getImgName()) + ".png";

            //图片文件保存在  /usr/data/images/foodimages/     目录下
            File file = FileFactory.createFile(Constant.FOOD_IMG_DIR_ROOT + fileName);
            fos = new FileOutputStream(file);
            fos.write(bytes);

            //在tomcat的server.xml配置了虚拟目录
            response.setImgUrl(Constant.IMG_VIRTUAL_DIR_ROOT + fileName);
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
