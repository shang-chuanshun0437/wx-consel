package com.weiyi.wx.order.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.weiyi.wx.order.common.constant.Constant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 生成二维码
 */
public class QRCodeGenerate
{
    //二维码的宽度和高度
    private static int WIDTH = 300;
    private static int HEIGHT = 300;

    /**
     * 生成二维码
     * @param storeId 店铺编号
     * @param tableId  二维码中间的文字说明
     * @param userPhone  商家手机号
     * @return  String 二维码URL
     */
    public static String create(Long userPhone,String storeId,int tableId)
    {
        String retUrl = null;
        //定义二维码参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        //纠错等级L级：约可纠错7%的数据码字;M级：约可纠错15%的数据码字;Q级：约可纠错25%的数据码字;H级：约可纠错30%的数据码字
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN,2);

        //生成二维码
        try {
            String content = Constant.QRCODE_APP_URL + "userPhone/" + userPhone +
                    "/storeId/" + storeId + "/tableId/" + tableId;
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);

            String fileName = userPhone + "_" + storeId + "_" + tableId + "_" + System.currentTimeMillis() + ".png";
            Path file = new File(Constant.QRCODE_IMG_DIR_ROOT + fileName).toPath();

            MatrixToImageWriter.writeToPath(bitMatrix, "png", file);

            pressText(tableId + "号桌",file.toFile());

            retUrl = Constant.QRCODE_VIRTUAL_DIR_ROOT + fileName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retUrl;
    }

    /**
     * 给二维码图片加上文字
     * @param pressText 文字
     * @param qrFile  二维码文件
     */
    private static void pressText(String pressText, File qrFile) throws Exception {
        pressText = new String(pressText.getBytes(), "utf-8");
        Image src = ImageIO.read(qrFile);
        int imageW = src.getWidth(null);
        int imageH = src.getHeight(null);
        BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(src, 0, 0, imageW, imageH, null);

        //设置字体
        int fontSize = 32;
        Font font = new Font("宋体", 5, fontSize);
        FontMetrics metrics = graphics.getFontMetrics(font);
        //文字在图片中的坐标 这里设置在中间
        int startX = (WIDTH - metrics.stringWidth(pressText)) / 2;
        int startY = HEIGHT / 2;

        //先擦除中间的矩形区域
        //设置画笔的颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(startX - 10,startY - fontSize - 5,
                metrics.stringWidth(pressText) + 20,fontSize + 10);

        //中间写文字
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString(pressText, startX, startY);

        graphics.dispose();
        FileOutputStream out = new FileOutputStream(qrFile);
        ImageIO.write(image, "png", out);
        out.close();
    }
}
