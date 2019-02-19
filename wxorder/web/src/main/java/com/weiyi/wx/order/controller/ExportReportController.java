package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.entity.OrderInfo;
import com.weiyi.wx.order.dao.entity.StoreOrder;
import com.weiyi.wx.order.dao.request.GetStoreOrderListRequest;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.ExportExcelRequest;
import com.weiyi.wx.order.response.ExportExcelResponse;
import com.weiyi.wx.order.service.api.OrderInfoService;
import com.weiyi.wx.order.service.api.StoreOrderService;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/export/report")
public class ExportReportController
{
    private Logger logger = LoggerFactory.getLogger(ExportReportController.class);

    @Autowired
    private StoreOrderService storeOrderService;

    /*
    *生成excel
    */
    @RequestMapping(value = "/excel",method = {RequestMethod.POST})
    @SecurityAnnotation()
    public void exportExcel(@RequestBody ExportExcelRequest request,HttpServletResponse httpServletResponse)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter exportExcel() func,phone num:{}",request.getUserPhone());
        }

        //初始化excel数据
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("已完成订单");
        //设置要导出的文件的名字
        String fileName = System.currentTimeMillis()  + ".xls";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;

        //headers表示excel表中第一行的表头
        String[] headers = { "订单编号", "店铺名称", "餐桌编号", "就餐人数", "订单金额", "会员金额", "实收金额",
                "会员编号", "支付方式", "点菜方式", "订单时间"};

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i = 0;i < headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        ExportExcelResponse response = new ExportExcelResponse();
        Result result = new Result();
        response.setResult(result);

        //从数据库中查询数据
        GetStoreOrderListRequest getStoreOrderListRequest = new GetStoreOrderListRequest();
        CopyProperties.copy(getStoreOrderListRequest,request);

        List<StoreOrder> storeOrders = storeOrderService.queryList(getStoreOrderListRequest);
        if (storeOrders != null && storeOrders.size() > 0){
            //在表中存放查询到的数据放入对应的列
            for (StoreOrder storeOrder : storeOrders) {
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.createCell(0).setCellValue(storeOrder.getOrderId());
                row1.createCell(1).setCellValue(storeOrder.getStoreName());
                row1.createCell(2).setCellValue(storeOrder.getTableId());
                row1.createCell(3).setCellValue(storeOrder.getPersonNum());
                row1.createCell(4).setCellValue(storeOrder.getAmount());
                row1.createCell(5).setCellValue(storeOrder.getVipAmount());
                row1.createCell(6).setCellValue(storeOrder.getRealAmount());
                row1.createCell(7).setCellValue(storeOrder.getVipNum());

                if (storeOrder.getPayType() == 2){
                    row1.createCell(8).setCellValue("前台支付");
                }else if(storeOrder.getPayType() == 3){
                    row1.createCell(8).setCellValue("扫码支付-支付宝");
                }else if(storeOrder.getPayType() == 4){
                    row1.createCell(8).setCellValue("扫码支付-微信");
                }

                if (storeOrder.getSource() == 1){
                    row1.createCell(9).setCellValue("扫描点餐");
                }else if(storeOrder.getPayType() == 2){
                    row1.createCell(9).setCellValue("前台点餐");
                }
                row1.createCell(10).setCellValue(storeOrder.getCreateTime());
                rowNum++;
            }
        }

        httpServletResponse.setContentType("application/vnd.ms-excel;charset=UTF-8");
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            httpServletResponse.flushBuffer();
            workbook.write(httpServletResponse.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
