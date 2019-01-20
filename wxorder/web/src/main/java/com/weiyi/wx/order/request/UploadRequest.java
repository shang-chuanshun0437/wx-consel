package com.weiyi.wx.order.request;

public class UploadRequest extends BaseRequest{
    //图片文件
    private String multipartFile;

    //图片名称
    private String imgName;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(String multipartFile) {
        this.multipartFile = multipartFile;
    }
}
