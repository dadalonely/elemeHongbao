package com.eleme.service.impl;

import com.eleme.service.ElemeService;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class ElemeServiceImpl implements ElemeService {
//    private static  final Logger

    @Override
    public void changePhoneNum(String phone) {
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
//            HttpPut httpPut = new HttpPut("https://h5.ele.me/restapi/v1/weixin/"+this.avatar+"/phone");
//            httpPut.setEntity(new StringEntity("{\"sign\":\""+this.sign+"\",\"phone\":\""+phoneNum+"\"}"));
//
//            System.out.println(phoneNum);
////            System.out.println("id="+id);
//            // Create a custom response handler
//            ResponseHandler<String> responseHandler = response -> {
//                HttpEntity entity = response.getEntity();
//                return entity != null ? EntityUtils.toString(entity) : null;
//            };
//            String responseBody = httpClient.execute(httpPut, responseHandler);
//            if(responseBody==null){
//                System.out.println("Change phonenumber success!");
//            }else{
//                System.out.println(responseBody);
//                System.out.println("Change phonenumbe failed!");
//            }
//            System.out.println("--------------------------------------------");
//            return;
//        }catch (Exception e){
//
//        }
    }
}
