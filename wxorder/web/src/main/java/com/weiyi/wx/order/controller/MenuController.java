package com.weiyi.wx.order.controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.common.utils.FileFactory;
import com.weiyi.wx.order.dao.entity.Menu;
import com.weiyi.wx.order.dao.request.GetMenuRequest;
import com.weiyi.wx.order.interceptor.SecurityAnnotation;
import com.weiyi.wx.order.request.*;
import com.weiyi.wx.order.response.*;
import com.weiyi.wx.order.service.api.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/store/menu")
public class MenuController
{
    private Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    /*
    *添加菜单食物
    */
    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AddMenuResponse addMenu(@RequestBody AddMenuRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter addMenu() func,phone num:{}",request.getUserPhone());
        }

        AddMenuResponse response = new AddMenuResponse();
        Result result = new Result();
        response.setResult(result);

        Menu menu = new Menu();
        CopyProperties.copy(menu,request);
        //存入数据库的商品图片名为原始图片名，不带有时间戳
        menu.setImgName(FileFactory.getFileName(request.getImgName()));
        try {
            menuService.addMenu(menu);
        }catch (WxOrderException e){
            //菜单添加失败后，要删除对应的商品图片
            FileFactory.delFile(Constant.FOOD_IMG_DIR_ROOT + request.getImgName());
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *删除菜单食物
     */
    @RequestMapping(value = "/delete",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public DeleteMenuResponse deleteMenu(@RequestBody DeleteMenuRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter deleteMenu() func,phone num:{}",request.getUserPhone());
        }
        DeleteMenuResponse response = new DeleteMenuResponse();
        Result result = new Result();
        response.setResult(result);

        Menu menu = new Menu();
        CopyProperties.copy(menu,request);

        try {
            menuService.deleteMenu(menu);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *更新菜单食物
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateMenuResponse updateMenu(@RequestBody UpdateMenuRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateMenu() func,phone num:{}",request.getUserPhone());
        }

        UpdateMenuResponse response = new UpdateMenuResponse();
        Result result = new Result();
        response.setResult(result);

        Menu menu = new Menu();
        CopyProperties.copy(menu,request);
        try {
            menuService.updateMenu(menu);
        }catch (WxOrderException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }
        return response;
    }
    /*
     *更新菜单食物----更新是否售罄、是否推荐
     */
    @RequestMapping(value = "/update/statusAndRecommend",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public UpdateStatusAndRecommendRes updateStatusAndRecommend(@RequestBody UpdateStatusAndRecommendReq request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter updateStatusAndRecommend() func,phone num:{}",request.getUserPhone());
        }

        UpdateStatusAndRecommendRes response = new UpdateStatusAndRecommendRes();
        Result result = new Result();
        response.setResult(result);

        Menu menu = new Menu();
        CopyProperties.copy(menu,request);
        try {
            menuService.updateStatusAndRecommend(menu);
        }catch (WxOrderException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }
        return response;
    }
    /*
     *查询菜单列表
     */
    @RequestMapping(value = "/query/list",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryMenuResponse queryMenu(@RequestBody QueryMenuRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryMenu() func,phone num:{}",request.getUserPhone());
        }

        QueryMenuResponse response = new QueryMenuResponse();
        Result result = new Result();
        response.setResult(result);

        GetMenuRequest getMenuRequest = new GetMenuRequest();
        CopyProperties.copy(getMenuRequest,request);
        if (getMenuRequest.getCurrentPage() != null){
            getMenuRequest.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        }
        try {
            int total = menuService.queryMenuCount(getMenuRequest);
            List<Menu> menus = menuService.queryMenu(getMenuRequest);

            if (menus != null && menus.size() > 0){
                response.setMenus(menus.toArray(new Menu[menus.size()]));
            }
            response.setTotal(total);
        }catch (WxOrderException e){
            result.setRetMsg(e.getMsg());
            result.setRetCode(e.getCode());
        }
        return response;
    }

    /*
     *查询菜单单个商品
     */
    @RequestMapping(value = "/query",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryMenuByIdResponse queryMenuById(@RequestBody QueryMenuByIdRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryMenuById() func,phone num:{}",request.getUserPhone());
        }

        QueryMenuByIdResponse response = new QueryMenuByIdResponse();
        Result result = new Result();
        response.setResult(result);

        Menu menu = new Menu();
        CopyProperties.copy(menu,request);

        Menu dbMenu = menuService.queryMenuById(menu);

        response.setMenu(dbMenu);
        return response;
    }
}
