package com.eleme.web;

import com.eleme.ElemeLocal;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping(value = "eleme")
public class ElemeController {
    private final static Logger logger= LoggerFactory.getLogger(ElemeController.class);
    @Resource
    private ElemeLocal elemeLocal;


    @RequestMapping(value = "getElemeHongBao", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String getElemeHongBao(String phone,String url){
        if(StringUtils.isBlank(phone)||StringUtils.isBlank(url)){
            return "手机号或者链接为空";
        }
        logger.info("url:{}",url);
        try {
            String result=elemeLocal.getMaxHongBao(phone,url);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "系统异常";
        }


    }
}
