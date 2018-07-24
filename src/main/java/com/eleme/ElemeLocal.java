package com.eleme;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eleme.constant.StatusCode;
import com.eleme.dto.ResponseEntityDto;
import com.eleme.entity.ElemeUrl;
import com.eleme.service.ElemeUrlService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ElemeLocal {

    private final static String url = "https://h5.ele.me/hongbao/#hardware_id=&is_lucky_group=True&lucky_number=8&track_id=&platform=0&sn=2a00c9ef5f1e6c39&theme_id=2833&device_id=&refer_user_id=28879908";
    private final static String avatar = "A77F4A1981987D0B368130493C1FCE8B";
    private final static String sign = "da652fbe1b8b681505b0e64c883c4730";
//        private final static String phoneNum = "18221741421";//骆云辉
    private final static String phoneNum = "17721016460";//最帅的
//private final static String phoneNum = "18521068247";//王会
    /**
     * 轻舞飞扬
     */
    private final static String cookie1 = "perf_ssid=54m3ygak5hvms6tj5bf00qtq665ky429_2018-06-06; ubt_ssid=ayx9x7bsmklbow5mzwfazvxlcpbpanfa_2018-06-06; _utrace=a7f8d5f9dbef47ef54cd50fc0ce968d3_2018-06-06; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22eleme_key%22%3A%2216ef68c28b9a08426dbe9d7f6a6a5584%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F72CECABFB39F2DC214A9C354648CE258%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F72CECABFB39F2DC214A9C354648CE258%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F72CECABFB39F2DC214A9C354648CE258%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F72CECABFB39F2DC214A9C354648CE258%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F72CECABFB39F2DC214A9C354648CE258%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E8%BD%BB%E8%88%9E%E9%A3%9E%E6%89%AC%22%2C%22openid%22%3A%2272CECABFB39F2DC214A9C354648CE258%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%221990%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E8%BD%BB%E8%88%9E%E9%A3%9E%E6%89%AC%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F72CECABFB39F2DC214A9C354648CE258%2F40%22%7D";
    /**
     * 春暖花开
     */
    private final static String cookie2 = "perf_ssid=54m3ygak5hvms6tj5bf00qtq665ky429_2018-06-06; ubt_ssid=ayx9x7bsmklbow5mzwfazvxlcpbpanfa_2018-06-06; _utrace=a7f8d5f9dbef47ef54cd50fc0ce968d3_2018-06-06; snsInfo[101204453]=%7B%22city%22%3A%22%E6%B5%B7%E6%B7%80%22%2C%22eleme_key%22%3A%2221f46d91e39572beef35009e5baf886e%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FEA596CEA62B870E021C85C6B0B17DDE4%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FEA596CEA62B870E021C85C6B0B17DDE4%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FEA596CEA62B870E021C85C6B0B17DDE4%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FEA596CEA62B870E021C85C6B0B17DDE4%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FEA596CEA62B870E021C85C6B0B17DDE4%2F100%22%2C%22gender%22%3A%22%E5%A5%B3%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E6%98%A5%E6%9A%96%E8%8A%B1%E5%BC%80%22%2C%22openid%22%3A%22EA596CEA62B870E021C85C6B0B17DDE4%22%2C%22province%22%3A%22%E5%8C%97%E4%BA%AC%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%222000%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E6%98%A5%E6%9A%96%E8%8A%B1%E5%BC%80%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FEA596CEA62B870E021C85C6B0B17DDE4%2F40%22%7D";
    /**
     * 飘雪无痕
     */
    private final static String cookie3 = "perf_ssid=5k3dq4futhfhr534s2dfda5v8xo4fbfr_2018-06-07; ubt_ssid=ofo879seroylcmsuivfcybb6fd641tjt_2018-06-07; _utrace=a4ca7b788f8c1f55b79d81047f55dde8_2018-06-07; track_id=1528357175|6e203c7f0763d896088a5721df995b288e4c20eea28f450135|f55b32a47a6461026f4d68535b60b90b; snsInfo[101204453]=%7B%22city%22%3A%22%E5%AE%9C%E6%98%8C%22%2C%22eleme_key%22%3A%223f27bc13390d71c200fd5da035c99097%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E9%A3%98%E9%9B%AA%E6%97%A0%E7%97%95%22%2C%22openid%22%3A%22A14EA9AB5E8036A582B6E968555D59D2%22%2C%22province%22%3A%22%E6%B9%96%E5%8C%97%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%221989%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E9%A3%98%E9%9B%AA%E6%97%A0%E7%97%95%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F40%22%7D";
    /**
     * 糊远远
     */
    private final static String cookie4 = "perf_ssid=0ky9ubh0his70brawx8vtjcxxbrr1e4k_2018-06-07; ubt_ssid=qcnrya79oa8yogglixmgdywv6qxctpnk_2018-06-07; _utrace=78eac52137b029470c826dc15663c895_2018-06-07; track_id=1528357929|a546a8af6a7e60ca7e61b14bb1e87c1b1f7d57382cfa11402a|d70a71c966f6fd9cdd2343ada6dda88c; snsInfo[101204453]=%7B%22city%22%3A%22%E5%AE%9C%E6%98%8C%22%2C%22eleme_key%22%3A%228c427198a6578e0c1db172a81f5c00a2%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F4081BAC79C018D7FDC3774A5634C6256%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F4081BAC79C018D7FDC3774A5634C6256%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F4081BAC79C018D7FDC3774A5634C6256%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F4081BAC79C018D7FDC3774A5634C6256%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F4081BAC79C018D7FDC3774A5634C6256%2F100%22%2C%22gender%22%3A%22%E5%A5%B3%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E7%B3%8A%E8%BF%9C%E8%BF%9C%22%2C%22openid%22%3A%224081BAC79C018D7FDC3774A5634C6256%22%2C%22province%22%3A%22%E6%B9%96%E5%8C%97%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%221989%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E7%B3%8A%E8%BF%9C%E8%BF%9C%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F4081BAC79C018D7FDC3774A5634C6256%2F40%22%7D";
    private final static String cookie5 = "perf_ssid=cgec9q8phn9j5vqkxn9gzhw3335z67f9_2018-06-07; ubt_ssid=wqibwi48vews42j0dbqsx8v5ixaenfp1_2018-06-07; _utrace=b98c5f68478741f972a81a2fecd2a178_2018-06-07; track_id=1528360724|57a494c34d84aeacea5ebbafff03af86333a03b45b61ea9fe7|1875ae7742b147b004fbcaef5f760112; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22eleme_key%22%3A%221679fdc8205c93070901f7ad5bc3ef72%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F083B145F6F74E2B9A653E6F46EB5279F%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F083B145F6F74E2B9A653E6F46EB5279F%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F083B145F6F74E2B9A653E6F46EB5279F%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F083B145F6F74E2B9A653E6F46EB5279F%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F083B145F6F74E2B9A653E6F46EB5279F%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22snow%22%2C%22openid%22%3A%22083B145F6F74E2B9A653E6F46EB5279F%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22snow%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F083B145F6F74E2B9A653E6F46EB5279F%2F40%22%7D";
    private final static String cookie6 = "perf_ssid=3ks44hbcszp9ded6tb5etit6e0mwda21_2018-06-07; ubt_ssid=jp2gypfd4mn0sbofs5whltw1tlxfjca3_2018-06-07; _utrace=afe9d029d407fe092b5087fce9099893_2018-06-07; track_id=1528361232|fcb71235d7afccb9c3835024ab9946e295c8fc79b02980d73a|6357dd3145e0ad1ac3b7e9edf2f08559; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22eleme_key%22%3A%22d23fb5c0f935abbc289842cebac7852a%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F5B139DD8F022E7B0A9AE1C124CB92E64%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F5B139DD8F022E7B0A9AE1C124CB92E64%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F5B139DD8F022E7B0A9AE1C124CB92E64%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F5B139DD8F022E7B0A9AE1C124CB92E64%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F5B139DD8F022E7B0A9AE1C124CB92E64%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E9%A3%98%E9%9B%AA%22%2C%22openid%22%3A%225B139DD8F022E7B0A9AE1C124CB92E64%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E9%A3%98%E9%9B%AA%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F5B139DD8F022E7B0A9AE1C124CB92E64%2F40%22%7D";
    private final static String cookie7 = "perf_ssid=9mdvztuavootfzrjfojvssa01p0k312b_2018-06-07; ubt_ssid=m7x529xsntx4a0820mtzyygkm1i2txzf_2018-06-07; _utrace=ac6410ec1c4da454329a33000cc6d16b_2018-06-07; track_id=1528361572|eab2e99623e77c340723ee59c2dff4fe53475056d016a23ef6|d59ad86aa019a139214adf941d8f0b6d; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22eleme_key%22%3A%228e963c48fad0ae07d1232ca7e9355ede%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F37BE7E5318172091F827543DC564C45D%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F37BE7E5318172091F827543DC564C45D%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F37BE7E5318172091F827543DC564C45D%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F37BE7E5318172091F827543DC564C45D%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F37BE7E5318172091F827543DC564C45D%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22snow1%22%2C%22openid%22%3A%2237BE7E5318172091F827543DC564C45D%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22snow1%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F37BE7E5318172091F827543DC564C45D%2F40%22%7D";
    private final static String cookie8 = "perf_ssid=lnpljsdyhcg2lupzuiyndjlxezvcz20e_2018-06-08; ubt_ssid=omvw5gjicuctpecbiznldx3v16z6mzv1_2018-06-08; _utrace=6d00b08fc36ddd46e2e138e573f74656_2018-06-08; track_id=1528421811|f25f3883d7689aaa4417d86421a4757da3ededff91138d49bd|8328204d74178a88c771d9fcc728f0a9; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22eleme_key%22%3A%22137fe88d1177948bb756521320b58c4f%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F739743B961BDDE05CF35956EF46C13EA%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F739743B961BDDE05CF35956EF46C13EA%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F739743B961BDDE05CF35956EF46C13EA%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F739743B961BDDE05CF35956EF46C13EA%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F739743B961BDDE05CF35956EF46C13EA%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22snow2%22%2C%22openid%22%3A%22739743B961BDDE05CF35956EF46C13EA%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22snow2%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F739743B961BDDE05CF35956EF46C13EA%2F40%22%7D";
    private final static String cookie9 = "perf_ssid=krjmuvlzpfhjz9m0akq8rwpncr22kktv_2018-06-11; ubt_ssid=v429tc5z46n0irskwbefaillige3b8lt_2018-06-11; _utrace=3962e6a1da18e7b44a2c879d7ab931cc_2018-06-11; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22eleme_key%22%3A%22f4d080f2ac8b4826b67d9e8556da4a0f%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FF3AB40C4180C80B8EB6A9FC376EC5784%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FF3AB40C4180C80B8EB6A9FC376EC5784%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FF3AB40C4180C80B8EB6A9FC376EC5784%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FF3AB40C4180C80B8EB6A9FC376EC5784%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FF3AB40C4180C80B8EB6A9FC376EC5784%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22snow3%22%2C%22openid%22%3A%22F3AB40C4180C80B8EB6A9FC376EC5784%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22snow3%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FF3AB40C4180C80B8EB6A9FC376EC5784%2F40%22%7D";
    private final static String cookie10 = "perf_ssid=q6qssrw4c8zfd7jty5kbk8wpg1ujoeq5_2018-06-11; ubt_ssid=8a497w7m435k13b3ve7inoxm1bzxb3gp_2018-06-11; _utrace=0bc77e5e6b9701ad42a04e42a2e64349_2018-06-11; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22eleme_key%22%3A%221a11e4db3a2139ba6b14f7f711cd0e55%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F48F7BA23228295F9A7795255F9AA4895%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F48F7BA23228295F9A7795255F9AA4895%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F48F7BA23228295F9A7795255F9AA4895%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F48F7BA23228295F9A7795255F9AA4895%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F48F7BA23228295F9A7795255F9AA4895%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22snow3%22%2C%22openid%22%3A%2248F7BA23228295F9A7795255F9AA4895%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22snow3%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F48F7BA23228295F9A7795255F9AA4895%2F40%22%7D";
    private final static String cookie11 = "perf_ssid=i3d1l6vkls37o9pcl6y0425md5nf7zgz_2018-06-11; ubt_ssid=8kdxhd9bz1gfzkeo7cb0frk1d14c3i1k_2018-06-11; _utrace=c11a48a2427df945efa835d9236b4340_2018-06-11; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22eleme_key%22%3A%22cd450cfc45967258d301e3d536c42719%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F2BD0E4EC0A75546F877F9C1115496F52%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F2BD0E4EC0A75546F877F9C1115496F52%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2F2BD0E4EC0A75546F877F9C1115496F52%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F2BD0E4EC0A75546F877F9C1115496F52%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F2BD0E4EC0A75546F877F9C1115496F52%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22snow4%22%2C%22openid%22%3A%222BD0E4EC0A75546F877F9C1115496F52%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22snow4%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2F2BD0E4EC0A75546F877F9C1115496F52%2F40%22%7D";
    private final static String cookie12 = "perf_ssid=gh2wcam4kth49n0r7ecgcavx94fvnwd6_2018-06-11; ubt_ssid=pm9dnnpi9h9jlk3nqt37yjjeolpc484a_2018-06-11; _utrace=2766243061d662c17cce9373dc030f08_2018-06-11; snsInfo[101204453]=%7B%22city%22%3A%22%22%2C%22eleme_key%22%3A%22690020bf0c75510c230939fced9ac31a%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FF377337EFE0499889CE769E39573EE8F%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FF377337EFE0499889CE769E39573EE8F%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FF377337EFE0499889CE769E39573EE8F%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FF377337EFE0499889CE769E39573EE8F%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FF377337EFE0499889CE769E39573EE8F%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22snow5%22%2C%22openid%22%3A%22F377337EFE0499889CE769E39573EE8F%22%2C%22province%22%3A%22%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%220%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22snow5%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FF377337EFE0499889CE769E39573EE8F%2F40%22%7D";
    private final static String cookie13 = "perf_ssid=xmyitf497rqp1zt9rt13qjkk4baik5pl_2018-06-13; ubt_ssid=l6s188bw1q2516b1w8q6cpq9uhr2m6aq_2018-06-13; _utrace=0b87d0024ef319606e0be41cf9624006_2018-06-13; snsInfo[101204453]=%7B%22city%22%3A%22%E6%B1%A0%E5%B7%9E%22%2C%22eleme_key%22%3A%22da652fbe1b8b681505b0e64c883c4730%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA77F4A1981987D0B368130493C1FCE8B%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA77F4A1981987D0B368130493C1FCE8B%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA77F4A1981987D0B368130493C1FCE8B%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA77F4A1981987D0B368130493C1FCE8B%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA77F4A1981987D0B368130493C1FCE8B%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22HUI%22%2C%22openid%22%3A%22A77F4A1981987D0B368130493C1FCE8B%22%2C%22province%22%3A%22%E5%AE%89%E5%BE%BD%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%221899%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22HUI%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA77F4A1981987D0B368130493C1FCE8B%2F40%22%7D";


    private final static String test = "perf_ssid=ujxur6ejwe8n68ytyp6lqx4v5kr7eypl_2018-06-26; ubt_ssid=mpvpge5y7ecz3lxfn0nwoz02gwoxy3tq_2018-06-26; _utrace=076ae99d6f095625d3ef606f52b4f29a_2018-06-26; snsInfo[101204453]=%7B%22city%22%3A%22%E5%AE%9C%E6%98%8C%22%2C%22eleme_key%22%3A%223f27bc13390d71c200fd5da035c99097%22%2C%22figureurl%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F30%22%2C%22figureurl_1%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F50%22%2C%22figureurl_2%22%3A%22http%3A%2F%2Fqzapp.qlogo.cn%2Fqzapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F100%22%2C%22figureurl_qq_1%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F40%22%2C%22figureurl_qq_2%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F100%22%2C%22gender%22%3A%22%E7%94%B7%22%2C%22is_lost%22%3A0%2C%22is_yellow_vip%22%3A%220%22%2C%22is_yellow_year_vip%22%3A%220%22%2C%22level%22%3A%220%22%2C%22msg%22%3A%22%22%2C%22nickname%22%3A%22%E9%A3%98%E9%9B%AA%E6%97%A0%E7%97%95%22%2C%22openid%22%3A%22A14EA9AB5E8036A582B6E968555D59D2%22%2C%22province%22%3A%22%E6%B9%96%E5%8C%97%22%2C%22ret%22%3A0%2C%22vip%22%3A%220%22%2C%22year%22%3A%221989%22%2C%22yellow_vip_level%22%3A%220%22%2C%22name%22%3A%22%E9%A3%98%E9%9B%AA%E6%97%A0%E7%97%95%22%2C%22avatar%22%3A%22http%3A%2F%2Fthirdqq.qlogo.cn%2Fqqapp%2F101204453%2FA14EA9AB5E8036A582B6E968555D59D2%2F40%22%7D";

    private static List<String> userList;
    private static ElemeLocal elemeLocal;
    private static List<Map<String, String>> userMap;


    @Resource
    private ElemeUrlService elemeUrlService;

    static {
//        userList = new ArrayList();
//        userList.add(cookie1);
//        userList.add(cookie2);
//        userList.add(cookie3);
//        userList.add(cookie4);
//        userList.add(cookie5);
//        userList.add(cookie6);
//        userList.add(cookie7);
//        userList.add(cookie8);
//        userList.add(cookie9);
//        userList.add(cookie10);
//        userList.add(cookie11);
//        userList.add(cookie12);
//        userList.add(cookie13);

        try {
            userMap = new ArrayList();
            Map<String, String> map1 = formatConversion(cookie1);
            Map<String, String> map2 = formatConversion(cookie2);
            Map<String, String> map3 = formatConversion(cookie3);
            Map<String, String> map4 = formatConversion(cookie4);
            Map<String, String> map5 = formatConversion(cookie5);
            Map<String, String> map6 = formatConversion(cookie6);
            Map<String, String> map7 = formatConversion(cookie7);
            Map<String, String> map8 = formatConversion(cookie8);
            Map<String, String> map9 = formatConversion(cookie9);
            Map<String, String> map10 = formatConversion(cookie10);
            Map<String, String> map11 = formatConversion(cookie11);
            Map<String, String> map12 = formatConversion(cookie12);
            Map<String, String> map13 = formatConversion(cookie13);
            userMap.add(map1);
            userMap.add(map2);
            userMap.add(map3);
            userMap.add(map4);
            userMap.add(map5);
            userMap.add(map6);
            userMap.add(map7);
            userMap.add(map8);
            userMap.add(map9);
            userMap.add(map10);
            userMap.add(map11);
            userMap.add(map12);
            userMap.add(map13);


        } catch (IOException e) {
            e.printStackTrace();
        }


        elemeLocal = new ElemeLocal();

    }

    private final static Logger logger = LoggerFactory.getLogger(ElemeLocal.class);


    public static void main(String[] args) throws IOException {
        String msg = elemeLocal.getMaxHongBao(phoneNum, url);
        System.out.println("msg" + msg);
//        System.out.println(elemeLocal.randomPhoneNum());
//        formatConversion(cookie1);
//        formatConversion(test);
//        logger.info("ssss");
//        getHongbao(url,"72CECABFB39F2DC214A9C354648CE258","16ef68c28b9a08426dbe9d7f6a6a5584",elemeLocal.randomPhoneNum(),"飘雪无痕","");

//        String response="{\"account\":\"13331560705\",\"is_lucky\":false,\"is_new_supervip\":false,\"lucky_status\":3,\"promotion_items\":[{\"amount\":1,\"expire_date\":\"2018-06-28\",\"hongbao_variety\":[\"全品类\"],\"item_type\":1,\"name\":\"品质联盟专享红包\",\"phone\":\"13319505901\",\"source\":\"qq_share_hongbao\",\"sum_condition\":30,\"validity_periods\":\"2018-06-28到期\"},{\"amount\":15,\"expire_date\":\"2018-06-29\",\"hongbao_variety\":[\"限新用户首单使用\"],\"item_type\":1,\"name\":\"分享红包\",\"phone\":\"13319505901\",\"source\":\"qq_share_hongbao\",\"sum_condition\":15,\"validity_periods\":\"2018-06-29到期\"},{\"amount\":1,\"expire_date\":\"2018-06-29\",\"hongbao_variety\":[\"全品类\"],\"item_type\":1,\"name\":\"帮买帮送跑腿红包\",\"phone\":\"13319505901\",\"source\":\"lpd_tcs\",\"sum_condition\":10,\"validity_periods\":\"2018-06-29到期\"}],\"promotion_records\":[{\"amount\":1,\"created_at\":1530083439,\"is_lucky\":false,\"sns_avatar\":\"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKStZQZ1VYrmJADT9wCr8BjWia8BicrGTtNeVdkGujgfRMGH84DUUPXqHtplKDybosdiaHLCDsFMZPnA/0\",\"sns_username\":\"Autism®\"},{\"amount\":1,\"created_at\":1530084732,\"is_lucky\":false,\"sns_avatar\":\"http://thirdqq.qlogo.cn/qqapp/101204453/2BD0E4EC0A75546F877F9C1115496F52/40\",\"sns_username\":\"snow4\"},{\"amount\":1.8,\"created_at\":1530084740,\"is_lucky\":false,\"sns_avatar\":\"http://thirdqq.qlogo.cn/qqapp/101204453/A14EA9AB5E8036A582B6E968555D59D2/40\",\"sns_username\":\"飘雪无痕\"},{\"amount\":1,\"created_at\":1530084827,\"is_lucky\":false,\"sns_avatar\":\"http://thirdqq.qlogo.cn/qqapp/101204453/37BE7E5318172091F827543DC564C45D/40\",\"sns_username\":\"snow1\"},{\"amount\":1,\"created_at\":1530084841,\"is_lucky\":false,\"sns_avatar\":\"http://thirdqq.qlogo.cn/qqapp/101204453/72CECABFB39F2DC214A9C354648CE258/40\",\"sns_username\":\"轻舞飞扬\"},{\"amount\":1.8,\"created_at\":1530084878,\"is_lucky\":false,\"sns_avatar\":\"http://thirdqq.qlogo.cn/qqapp/101204453/5B139DD8F022E7B0A9AE1C124CB92E64/40\",\"sns_username\":\"飘雪\"},{\"amount\":5.6,\"created_at\":1530084878,\"is_lucky\":true,\"sns_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJZm9ichCcjvDJGkabJ6DEf089OXoCLJZezvq7jlfFTvx82AHBOEkVAnxHUHgt5dAgyk5ErHTb15kw/132\",\"sns_username\":\"飘雪\"},{\"amount\":1.8,\"created_at\":1530085481,\"is_lucky\":false,\"sns_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK3MFIZxaDqh6IWD1kW5AlfnoFq6SZlUL1FwIic8p7tPKS2XTibAkmfYYSezQyMQTaLW3ASllCSRWUA/132\",\"sns_username\":\"lee\"},{\"amount\":1.6,\"created_at\":1530085851,\"is_lucky\":false,\"sns_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJsX1Nx8icxHXfvWSFy5gm8P1ZCH3u6x9kxzCeZ5Tol5Ua9bGDGPHibhAibZRl7SNR3CiaM2VPu4yPzCA/132\",\"sns_username\":\"? 花语\"},{\"amount\":1.6,\"created_at\":1530086052,\"is_lucky\":false,\"sns_avatar\":\"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJfB1ibeMtSmJoMec3icynaPACOib60FSsrsKZicEdKXFWbU6xl2XR7X3VcKMDqACZopLLSiaS3RHz3hFA/132\",\"sns_username\":\"Suri\"},{\"amount\":2,\"created_at\":1530086911,\"is_lucky\":false,\"sns_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/qTlfVhIBVibadzXZoicibZjJWC1eYRS2soVNvhKgaMhwCtk5u2fhSApdTXrMsQibl2RRBS5wJDaXibcJgCKdxaTb5vg/132\",\"sns_username\":\"eadie\"},{\"amount\":1.6,\"created_at\":1530088133,\"is_lucky\":false,\"sns_avatar\":\"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJGyOYkcD9HMS29odJhM4oBs9KhaF4xZhsICQ1wt9CfUIEK7VTERoibrtktwhPPhZOibDPGFHK4SSDg/132\",\"sns_username\":\"Summer\"},{\"amount\":1.6,\"created_at\":1530088974,\"is_lucky\":false,\"sns_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK5BknjdOhMtYMpEVaIBHef0meq7HpUT9ibaC8wKf1GSmvKuvgq4WQRDM9DzsbNMxoMylnOnhvsrIA/132\",\"sns_username\":\"Miss.Soldout\"}],\"ret_code\":2,\"theme_id\":2689}";
//        JSONObject res=JSONObject.parseObject(response);
//        System.out.println(res.getString("ret_code"));

    }


    public String getMaxHongBao(String phoneNum, String url) throws IOException {
        int errorUrlExponent = 0;    //错误链接指数，如果该指数等于三表明这是一个异常的链接
        int suspectedErrorId = 0;    //疑似错误Id
        int lastResidueNum = 16;    //上一次领取时的剩余次数,默认为16,因为红包最大个数为15
        int change = 0;
        Object[] residueNumAndMoney = {3, 0};  //rt[0]为还需要领取的次数                  rt[1]为领取到的红包金额
        while ((int) residueNumAndMoney[0] > 0) {        //进行第一次判断，如果剩余需要领取次数小于等于0则表示红包已被领取，返回失败提示，否则只要剩余需要领取次数大于0则一直运行，
//            while(true){
            if (change != 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (change > 11) {
                change = 0;
            }
//                if(change>11){
//                    return  "次数用尽";
//                }
//                if(errorUrlExponent>0){
//                    return  "异常链接";
//                }
//                Map<String, String> map = formatConversion(userList.get(change));
            Map<String, String> map = userMap.get(change);
            if (null == map || map.size() == 0) {
                System.out.println("根据cookie获取为空");
                return "根据cookie获取为空";
            }
//                String avatar=list.get(0);
//                String sign=list.get(1);
//            map.put("avatar",avatar);
//            map.put("elemeKey",elemeKey);
//            map.put("name",userInfo.getString("nickname"));
//            map.put("weixin_avatar",userInfo.getString("avatar"));

//
            String rnadomPhone = randomPhoneNum();
//                changePhoneNum(rnadomPhone, map.get("avatar"), map.get("elemeKey"));    //修改为目标手机号
            residueNumAndMoney = getHongbao("", url, map.get("avatar"), map.get("elemeKey"), rnadomPhone, map.get("name"), map.get("weixin_avatar"));     //调用领红包方法兵获取领红包方法返回的剩余领取次数与金额

//                if((int)residueNumAndMoney[0] == lastResidueNum){		//判断剩余领取次数是否与上次相同，如果相同则领取错误次数+1
//
//                    errorUrlExponent++;
//                }else{
//                    errorUrlExponent = 0;
//                }
//                if(errorUrlExponent == 3){
//
//                    return "异常链接，请再次尝试领取，如果第二次尝试仍然失败请不要再使用此链接";
//                }
//                lastResidueNum = (int)residueNumAndMoney[0];
            change++;
            errorUrlExponent++;
            if ((int) residueNumAndMoney[0] == 1) {        //如果剩余次数等于一，此时需要将小号的PhoneNum修改为目标手机号再领取


                changePhoneNum(phoneNum, this.avatar, this.sign);    //修改为目标手机号
                residueNumAndMoney = getHongbao("", url, this.avatar, this.sign, phoneNum, "飘雪", "");        //调用领红包方法兵获取领红包方法返回的剩余领取次数与金额
                changePhoneNum(randomPhoneNum(), this.avatar, this.sign);    //修改为目标手机号

                if ((int) residueNumAndMoney[0] >= 1) {        //手机已经领取过此红包 或 你的手机号今日领取次数已达上限或小号领取次数耗尽，下面进行第二次尝试。
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    residueNumAndMoney = getHongbao("", url, this.avatar, this.sign, phoneNum, "飘雪", "");        //调用领红包方法兵获取领红包方法返回的剩余领取次数与金额

                }
                if ((int) residueNumAndMoney[0] >= 1) {    //第二次如果仍然领取失败

                    return ("你的手机已经领取过此红包 或 你的手机号今日领取次数已达上限，亦或是系统错误，本红包下一个为大红包，可以发给你的朋友领哦~");
                }
                if ((int) residueNumAndMoney[0] == 0) {        //至此，红包领取成功，返回成功信息
                    if (suspectedErrorId != 0) {        //如果疑似错误id不等于0说明上一次领取错误，将疑似错误Id的领取次数+1

                    }

                    System.out.println("get bigHongbao succeed!");
                    return ("红包领取成功,红包金额为：" + (String) residueNumAndMoney[1] + "元");
                }
                if ((int) residueNumAndMoney[0] < 0) {        //发生了未知的问题，有可能是被被人抢领了，这种情况几率比较小但是还是有可能的，建议使用自己发的红包。

                    System.out.println("unknow error!");
                    return "发生了不可预见的错误";
                }
            }
        }
//            if ((int) residueNumAndMoney[0] == -400) {
//
//                System.out.println("malicious url!");
//                return "错误的链接，请检查你的链接是否可用";
//            }

        System.out.println("not have bigHongbao!");
        return "此红包大红包已被领取!";
    }


    public String getVipMaxHongBao(String phoneNum) throws IOException {
//        ResponseEntityDto responseEntityDto=new ResponseEntityDto();
        List<ElemeUrl> list = elemeUrlService.getAllEffectUrl();
        logger.info("链接：{}", JSONObject.toJSONString(list));
        if (null == list || list.size() == 0) {

            return "链接用尽";
        }
        String url = "";
        String id = "";
        for (ElemeUrl elemeUrl : list) {
            if (elemeUrl.getUrl().contains("https://h5.ele.me/hongbao/#hardware_id=")) {
                url = elemeUrl.getUrl();
                id = elemeUrl.getId();
                break;
            }

        }

        if (StringUtils.isBlank(url)) {
            return "没可用链接";
        }

        int change = 0;
        Object[] residueNumAndMoney = {3, 0};  //rt[0]为还需要领取的次数                  rt[1]为领取到的红包金额
        while ((int) residueNumAndMoney[0] > 0) {        //进行第一次判断，如果剩余需要领取次数小于等于0则表示红包已被领取，返回失败提示，否则只要剩余需要领取次数大于0则一直运行，

            if (change != 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (change > 11) {
                change = 0;
            }

            Map<String, String> map = userMap.get(change);
            if (null == map || map.size() == 0) {
                System.out.println("根据cookie获取为空");

                return "根据cookie获取为空";

            }


//
            String rnadomPhone = randomPhoneNum();

            residueNumAndMoney = getHongbao(id, url, map.get("avatar"), map.get("elemeKey"), rnadomPhone, map.get("name"), map.get("weixin_avatar"));     //调用领红包方法兵获取领红包方法返回的剩余领取次数与金额


            change++;
            if ((int) residueNumAndMoney[0] == 1) {        //如果剩余次数等于一，此时需要将小号的PhoneNum修改为目标手机号再领取


                changePhoneNum(phoneNum, this.avatar, this.sign);    //修改为目标手机号
                residueNumAndMoney = getHongbao(id, url, this.avatar, this.sign, phoneNum, "飘雪", "");        //调用领红包方法兵获取领红包方法返回的剩余领取次数与金额
                changePhoneNum(randomPhoneNum(), this.avatar, this.sign);    //修改为目标手机号

                if ((int) residueNumAndMoney[0] >= 1) {        //手机已经领取过此红包 或 你的手机号今日领取次数已达上限或小号领取次数耗尽，下面进行第二次尝试。

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    residueNumAndMoney = getHongbao(id, url, this.avatar, this.sign, phoneNum, "飘雪", "");        //调用领红包方法兵获取领红包方法返回的剩余领取次数与金额

                }

                if ((int) residueNumAndMoney[0] == 0) {        //至此，红包领取成功，返回成功信息
                    System.out.println("get bigHongbao succeed!");

                    return "\"红包领取成功,红包金额为：\" + (String) residueNumAndMoney[1] + \"元\"";

                }


            }
        }


        System.out.println("not have bigHongbao!");
        return "not have bigHongbao!";
    }


    public Object[] getHongbao(String id, String url, String avatar, String sign, String phoneNum, String userName, String weixin_avatar) throws
            IOException {
        logger.info("****************************************************{}领取开始*******************************************************************************", userName);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if (StringUtils.isBlank(weixin_avatar)) {
                weixin_avatar = "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJZm9ichCcjvDJGkabJ6DEf089OXoCLJZezvq7jlfFTvx82AHBOEkVAnxHUHgt5dAgyk5ErHTb15kw/132";
            }
            String sn = url;
            String regSn = "&sn=[0-9,a-z]+";
            Pattern pSn = Pattern.compile(regSn);
            Matcher mSn = pSn.matcher(sn);
            while (mSn.find()) {
                sn = (mSn.group());
            }
            sn = sn.substring(4, sn.length());
            HttpPost httpPost = new HttpPost("https://h5.ele.me/restapi//marketing/promotion/weixin/" + avatar);        //提交请求
            StringEntity stringEntity = new StringEntity(
                    "{\"group_sn\":\"" + sn + "\"," +
                            "\"sign\":\"" + sign + "\"," +
//                            "\"phone\":\""+phoneNum+"\","+
                            "\"phone\":\"\"," +
                            "\"method\":\"phone\"," +
                            "\"platform\":4," +
                            "\"weixin_avatar\":\"" + weixin_avatar + "\"," +
//                                "\"weixin_avatar\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJZm9ichCcjvDJGkabJ6DEf089OXoCLJZezvq7jlfFTvx82AHBOEkVAnxHUHgt5dAgyk5ErHTb15kw/132\"," +
                            "\"weixin_username\":\"" + userName + "\"}", "UTF-8");
            httpPost.setEntity(stringEntity);            //设置提交信息
            //返回的信息responseHandler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 500) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected  response status: " + status);
                }
            };
            logger.info("请求参数：{}", JSON.toJSONString(stringEntity));
            String responseBody = httpClient.execute(httpPost, responseHandler);
            logger.info("返回参数:{}", responseBody);
            JSONObject res = JSONObject.parseObject(responseBody);
            String ret_code = res.getString("ret_code");
            System.out.println("状态码ret_code：" + ret_code);
            if ("10".equals(ret_code)) {
                if (!phoneNum.equals(elemeLocal.phoneNum)) {
                    String rnadomPhone = elemeLocal.randomPhoneNum();
                    elemeLocal.changePhoneNum(rnadomPhone, avatar, sign);    //修改为目标手机号
                }

            }
//                boolean is_luck=res.getBoolean("is_lucky");
//                if(StringUtils.isNotBlank(id) &&(is_luck=true)){
//
//                    elemeUrlService.updateElemeUrlById(id);
//
//                }
            //识别已领取红包数量
            int count = StringUtils.countMatches(responseBody, "\"sns_username\"");
            logger.info("识别已领取红包数量:{}", count);
            String luckyNum = getLuckyNum(url);     //识别第几个为大红包
            logger.info("识别第几个为大红包:{}", luckyNum);
            //识别本次领取红包金额
            String hongbaoSum = getHongbaoSum(responseBody);
            logger.info("还剩几个:{}", Integer.parseInt(luckyNum) - count);
            if (Integer.parseInt(luckyNum) - count <= 0&&StringUtils.isNotBlank(id)) {

                elemeUrlService.updateElemeUrlById(id);

            }
            logger.info("****************************************************{}领取结束*******************************************************************************", userName);
            Object[] rt = {Integer.parseInt(luckyNum) - count, hongbaoSum};
            Object[] rtFalse = {400, 0};   //防止恶意提交链接
            if (count == 0) {
                return rtFalse;
            } else {
                return rt;
            }
        }
    }


    public String randomPhoneNum() {
        String rpn = "133" + (int) ((Math.random() * 90000000 + 9999999));
        logger.info("随机号码是：{}", rpn);
        return rpn;
    }

    //识别第几个为大红包,返回String
    public static String getLuckyNum(String url) {
        String luckyNum = url;
        String regLuckyNum = "lucky_number=[0-9]+";      //匹配lucky_number
        Pattern pLuckyNum = Pattern.compile(regLuckyNum);
        Matcher mLuckyNum = pLuckyNum.matcher(luckyNum);
        if (mLuckyNum.find()) {
            luckyNum = mLuckyNum.group();
        }
        luckyNum = luckyNum.substring(13, luckyNum.length());    //substring()方法去除前13个字符，也就是"lucky_number="，剩下就是目标红包数
        return luckyNum;
    }


    //识别本次领取红包金额，如果不是大红包，返回0。如果是大红包，返回大红包金额
    public static String getHongbaoSum(String responseBody) {
        String hongbaoSum1 = responseBody;        //正则第一部分
        String regHongbaoSum1 = "\"amount\":[0-9,.]+,\"(expire_date|hongbao_variety\":\\[\"全品类\")";
        Pattern pHongbaoSum1 = Pattern.compile(regHongbaoSum1);
        Matcher mHongbaoSum1 = pHongbaoSum1.matcher(hongbaoSum1);
        if (mHongbaoSum1.find()) {
            hongbaoSum1 = (mHongbaoSum1.group());
        } else {
            return "0";
        }

        String hongbaoSum2 = hongbaoSum1;        //正则第二部分
        String regHongbaoSum2 = "(?<!\\d|^)([1-9]\\d+|\\d)(\\.\\d+)?((?!\\d)|$)";
        Pattern pHongbaoSum2 = Pattern.compile(regHongbaoSum2);
        Matcher mHongbaoSum2 = pHongbaoSum2.matcher(hongbaoSum2);
        if (mHongbaoSum2.find()) {
            hongbaoSum2 = (mHongbaoSum2.group());
        }
        return hongbaoSum2;
    }


    public static Map<String, String> formatConversion(String urlCookie) throws IOException {
        if (urlCookie == null) {
            return null;
        }
//        List<String> list = new ArrayList<String>();
        Map<String, String> map = new HashMap<>();
        String cookie = "";
        String avatar = "";
        String elemeKey = "";
        cookie = URLDecoder.decode(urlCookie, "UTF-8");
        System.out.println("cookie:" + cookie);
        avatar = cookie;        //匹配Avatar
        String regAvatar = "\"avatar\":\"http://thirdqq.qlogo.cn/[a-z]+/[0-9]+/[0-9,A-Z]+";
        Pattern pAvatar = Pattern.compile(regAvatar);
        Matcher mAvatar = pAvatar.matcher(avatar);
        if (mAvatar.find()) {
            avatar = (mAvatar.group());
        } else {
            System.out.println("not find");
            return null;
        }
        avatar = avatar.substring(50, avatar.length());

        elemeKey = cookie;        //匹配elemeKey
        String regElemeKey = "\"eleme_key\":\"[0-9,a-z]+";
        Pattern pElemeKey = Pattern.compile(regElemeKey);
        Matcher mElemeKey = pElemeKey.matcher(elemeKey);
        if (mElemeKey.find()) {
            elemeKey = (mElemeKey.group());
        } else {
            return null;
        }
        elemeKey = elemeKey.substring(13, elemeKey.length());
//        list.add(avatar);
//        list.add(elemeKey);
        String snsInfo = cookie.substring(cookie.indexOf("snsInfo[101204453]=") + "snsInfo[101204453]=".length(), cookie.length());
        JSONObject userInfo = JSONObject.parseObject(snsInfo);
        System.out.println("snsInfo=" + snsInfo);
        System.out.println("*************user***************" + userInfo.getString("nickname"));
        System.out.println("avatar   = " + avatar);
        System.out.println("elemeKey = " + elemeKey);
        map.put("avatar", avatar);
        map.put("elemeKey", elemeKey);
        map.put("name", userInfo.getString("nickname"));
        map.put("weixin_avatar", userInfo.getString("avatar"));
        return map;
    }

    public void changePhoneNum(String phoneNum, String avatar, String sign) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut("https://h5.ele.me/restapi/v1/weixin/" + avatar + "/phone");
            httpPut.setEntity(new StringEntity("{\"sign\":\"" + sign + "\",\"phone\":\"" + phoneNum + "\"}"));
//            altDao.changePhoneNum(id, phoneNum);
            System.out.println(phoneNum);
//            System.out.println("id="+id);
            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            };
            String responseBody = httpClient.execute(httpPut, responseHandler);
            if (responseBody == null) {
                System.out.println("Change phonenumber success!");
            } else {
                System.out.println(responseBody);
                System.out.println("Change phonenumbe failed!");
            }
            System.out.println("--------------------------------------------");
            return;
        }
    }

}
