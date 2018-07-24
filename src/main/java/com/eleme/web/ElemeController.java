package com.eleme.web;

import com.eleme.ElemeLocal;
import com.eleme.constant.StatusCode;
import com.eleme.dto.ResponseEntityDto;
import com.eleme.service.ElemeUrlService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value = "eleme")
public class ElemeController {
    private final static Logger logger = LoggerFactory.getLogger(ElemeController.class);
    @Resource
    private ElemeLocal elemeLocal;
    @Resource
    private ElemeUrlService elemeUrlService;


    //    @RequestMapping(value = "getElemeHongBao", method = RequestMethod.POST)
    @PostMapping("getElemeHongBao")
    @ResponseBody
    public String getElemeHongBao(String phone, String url) {
        logger.info("phone:{},url:{}", phone, url);
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(url)) {
            return "手机号或者链接为空";
        }
        try {
            String result = elemeLocal.getMaxHongBao(phone, url);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "系统异常";
        }


    }


    @PostMapping("getVipMaxHongBao")
    @ResponseBody
    public String getVipMaxHongBao(String phone) {
        logger.info("phone:{}", phone);
        if (StringUtils.isBlank(phone) ) {
            phone="17721016460";
        }
        try {
            String result = elemeLocal.getVipMaxHongBao(phone);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "系统异常";
        }

    }


    @RequestMapping(value = "insertElemeUrl", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResponseEntityDto insertElemeUrl(String url){
        ResponseEntityDto responseEntityDto=new ResponseEntityDto();
        responseEntityDto.setStatus(StatusCode.OK);
        logger.info("链接url：{}",url);
        try{
            elemeUrlService.insertElemeUrl(url);
            responseEntityDto.setMsg("成功");

        }catch (Exception e){
            logger.error("系统异常：{}",e);
            responseEntityDto.setStatus(StatusCode.FAIL);
            responseEntityDto.setMsg("系统异常");
        }


        return responseEntityDto;
    }



}
