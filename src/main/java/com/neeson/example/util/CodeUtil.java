package com.neeson.example.util;

import com.neeson.example.properties.AliLiveProperties;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/21
 * Time: 15:23
 * Description:
 */
@Component
public class CodeUtil {


    @Autowired
    private AliLiveProperties aliLiveProperties;


    /**
     * 生成6位验证码
     * @return
     */
    public  String createSmsCode(){

        Random random = new Random();

        StringBuffer code = new StringBuffer(4);

        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10)) ;
        }

        return code.toString();
    }



    public  String createUUID(int len) {

        String uuid = java.util.UUID.randomUUID().toString()
                .replaceAll("-", "");
        return uuid.substring(0, len);
    }

    /**
     * md5加密
     * @param streamName
     * @return
     */
    public String md5sum(String streamName) {
        String md5String = String.format("/%s/%s-%s-0-0-%s", aliLiveProperties.getAppName(), streamName,
                Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli() / 1000, aliLiveProperties.getPrivateKey());
        String s = DigestUtils.md5Hex(md5String);
        return s;
    }

    /**
     * 鉴权
     * @param streamName
     * @return
     */
    public String getAuthKey(String streamName){
        String s = md5sum(streamName);
        String authKey = String.format("%s-0-0-%s", Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli() / 1000, s);
        return authKey;
    }

    /**
     * 获取推流地址
     * @param streamName
     * @return
     */
    public Map<String,String>  getPushStream(String streamName){
        Map<String,String> vo = new HashMap<>(2);
        vo.put("url",aliLiveProperties.getRtmpPushStream()+aliLiveProperties.getAppName());
        vo.put("streamName",String.format("%s?vhost=%s&auth_key=%s",streamName,aliLiveProperties.getDomainName(),getAuthKey(streamName)));
        return vo;
    }

}
