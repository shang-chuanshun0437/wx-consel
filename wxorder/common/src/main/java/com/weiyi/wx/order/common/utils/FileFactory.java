package com.weiyi.wx.order.common.utils;

import java.io.File;
import java.io.IOException;

/**
 * 保存文件到本地
 */
public class FileFactory
{
    //创建文件
    public static File createFile(String fileName) throws IOException
    {
        // 指定路径如果没有则创建并添加
        File file = new File(fileName);
        //获取父目录
        File fileParent = file.getParentFile();
        //判断是否存在
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        file.createNewFile();
        return file;
    }

    /*
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
    /*
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1 ) && ( dot < filename.length())) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取文件名
     */
    public static String getFileName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('_');
            if ((dot > -1 ) && ( dot < filename.length())) {
                return filename.substring(dot, filename.length());
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取文件名(带有时间戳)
     */
    public static String getFileNameWithTime(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('/');
            if ((dot > -1 ) && ( dot < filename.length())) {
                return filename.substring(dot, filename.length());
            }
        }
        return filename;
    }

    //删除文件
    public static void delFile(String filename){
        File file = new File(filename);
        if(file.exists() && file.isFile()){
            file.delete();
        }
    }
}
