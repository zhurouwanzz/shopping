package com.team6.controller;

import com.team6.service.OrderInfo.OrderInfoService;
import com.team6.service.login.LoginService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class OrderInfoController {
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/order/success/{oId}")
    public Object toSubmitSuccess(@PathVariable("oId")int oId){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("ProductAndCart/SubmitSuccess");
        mv.addObject("oId",oId);
        return mv;
    }

    /**
     * 插入订单信息
     * @param request
     * @param goodsId  商品的id数组
     * @param count    对应商品id的数量数组
     * @param sellerId 对应商品id所属卖家的id数组
     * @param addressId 收货地址id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/order/add",method = RequestMethod.POST)
    public Object insertOrderInfo(HttpServletRequest request,
                                   @RequestParam("goodsId[]") int[] goodsId,
                                   @RequestParam("count[]") int[] count,
                                   @RequestParam("sellerId[]") int[] sellerId,
                                   @RequestParam("addressId") int addressId
                                   ){
        //用户信息
        Map<String,Object> map=loginService.getCurrentUserInfo(request);
        Integer userid=(Integer)map.get("userid");//获取下单的用户id
        return orderInfoService.insertOrderInfo(goodsId,count,sellerId,userid,addressId);
    }

    @ResponseBody
    @RequestMapping(value="/order/del/{orderId}")
    public Object OrderDel(@PathVariable("orderId")int orderId, HttpServletRequest request){
        return orderInfoService.delOrderInfo(orderId,request);
    }

    @ResponseBody
    @RequestMapping(value="/order/pay/{orderId}")
    public Object OrderPay(@PathVariable("orderId")int orderId,HttpServletRequest request){
        return orderInfoService.OrderPay(orderId,request);
    }

    /**
     * 查询物流信息
     */
    @RequestMapping(value="/order/LogisticsData/{num}")
    public ModelAndView LogisticsData(@PathVariable("num") String num){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("Personality/Buyer/QueryLogisticsData");
        mv.addObject("num",num);
        return mv;
    }

}
