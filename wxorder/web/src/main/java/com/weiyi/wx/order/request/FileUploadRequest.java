package com.weiyi.wx.order.request;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadRequest{
    //文件
    private MultipartFile file;

    private String loginName;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
