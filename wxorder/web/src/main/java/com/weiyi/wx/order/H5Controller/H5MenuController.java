package com.weiyi.wx.order.H5Controller;

import com.weiyi.wx.order.common.Result;
import com.weiyi.wx.order.common.constant.Constant;
import com.weiyi.wx.order.common.constant.ErrorCode;
import com.weiyi.wx.order.common.exception.WxOrderAssert;
import com.weiyi.wx.order.common.exception.WxOrderException;
import com.weiyi.wx.order.common.utils.CopyProperties;
import com.weiyi.wx.order.dao.dto.H5QueryUserDto;
import com.weiyi.wx.order.dao.entity.Menu;
import com.weiyi.wx.order.dao.request.H5GetMenuRequest;
import com.weiyi.wx.order.dao.request.H5GetUserRequest;
import com.weiyi.wx.order.domain.Goods;
import com.weiyi.wx.order.request.*;
import com.weiyi.wx.order.response.*;
import com.weiyi.wx.order.service.api.MenuService;
import com.weiyi.wx.order.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/h5/store/menu")
public class H5MenuController
{
    private Logger logger = LoggerFactory.getLogger(H5MenuController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    private static final String[] categoryMap = {"推荐榜","小炒系列","凉菜系列","海鲜系列","汤系列",
            "酒水饮料","主食","其它"};

    /*
     *查询菜单列表
     */
    @RequestMapping(value = "/query/list",method = {RequestMethod.POST})
    @ResponseBody
    public H5QueryMenuResponse h5QueryMenu(@RequestBody H5QueryMenuRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("inter h5QueryMenu() func,phone num:{}",request.getUserPhone());
        }

        H5QueryMenuResponse response = new H5QueryMenuResponse();
        Result result = new Result();
        response.setResult(result);

        H5GetMenuRequest h5GetMenuRequest = new H5GetMenuRequest();
        CopyProperties.copy(h5GetMenuRequest,request);

        try {
            List<Menu> dbMenus = menuService.h5QueryMenu(h5GetMenuRequest);
            List<Goods> list = new ArrayList<Goods>();

            //目前共有八个类目,初始化各个类目
            Map<Integer,List<Menu>> menuMap = new HashMap<Integer,List<Menu>>();

            for (int i = 0; i < 8;i++){
                List<Menu> retMenu = new ArrayList<Menu>();
                menuMap.put(i,retMenu);

                Goods goods = new Goods();
                goods.setCategoryName(categoryMap[i]);
                list.add(goods);
            }
            //对数据库中的dbMenus进行分类
            if (dbMenus != null && dbMenus.size() > 0){
                for (Menu menu : dbMenus){
                    //2 表示推荐
                    if (menu.getRecommend() == 2){
                        menuMap.get(0).add(menu);
                    }
                    menuMap.get(menu.getCategory()).add(menu);
                }
            }

            for (int i = 0;i < 8;i++){
                list.get(i).setFoods(menuMap.get(i).toArray(new Menu[menuMap.get(i).size()]));
            }

            /*
             *根据商家编号、店铺ID，查询商家支付方式、店铺名称
             */
            H5GetUserRequest h5GetUserRequest = new H5GetUserRequest();
            CopyProperties.copy(h5GetUserRequest,request);

            H5QueryUserDto h5QueryUserDto = userService.h5QueryUser(h5GetUserRequest);
            WxOrderAssert.isTrue(h5QueryUserDto != null && h5QueryUserDto.getStoreName() != null ,ErrorCode.STORE_NOT_EXIST,"parameter is error.");

            response.setStoreName(h5QueryUserDto.getStoreName());
            response.setPayType(h5QueryUserDto.getPayType());
            response.setGoods(list.toArray(new Goods[list.size()]));
        }catch (WxOrderException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }

        return response;
    }
}
