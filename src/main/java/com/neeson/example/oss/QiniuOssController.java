package com.neeson.example.oss;


import com.neeson.example.dto.QiniuToken;
import com.neeson.example.properties.QiniuOssProperties;
import com.neeson.example.util.CodeUtil;
import com.neeson.example.util.exception.MyException;
import com.neeson.example.util.response.ResponseResult;
import com.neeson.example.util.response.RestResultGenerator;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MyPC
 */
@RestController
@RequestMapping("qiniu")
@Slf4j
public class QiniuOssController {

    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    private QiniuOssProperties qiniuOssProperties;

    public static final String[] pipelines = {"m3u8file", "misucfile", "pipeline", "videofile"};


    @GetMapping(value = "token/video/{userId}")
    public ResponseResult getVideoTokenV2(@PathVariable int userId) {
        try {
    Auth auth = Auth.create(qiniuOssProperties.getAccessKey(), qiniuOssProperties.getSecretKey());
            String key = userId + "/video/" + codeUtil.createUUID(12) + ".mp4";
            long expireSeconds = 360000000;
            String upToken = auth.uploadToken(qiniuOssProperties.getBucket(), key, expireSeconds, null);
            QiniuToken qiniuToken = new QiniuToken();
            qiniuToken.setOriginalKey(key);
            qiniuToken.setToken(upToken);
            return RestResultGenerator.genResult(qiniuToken,"获取token成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("获取七牛token失败");
        }
    }

    @GetMapping(value = "token/photo/{userId}/{num}")
    public ResponseResult getPhotoToken(@PathVariable int userId,
                              @PathVariable int num) {
        try {
            Auth auth = Auth.create(qiniuOssProperties.getAccessKey(), qiniuOssProperties.getSecretKey());

            List<QiniuToken> qiniuTokens = new ArrayList<>(2 * num);
            long expireSeconds = 360000000;
            String key, upToken;
            for (int i = 0; i <  num; i++) {
                key = userId + "/photo/" + codeUtil.createUUID(12) + ".jpg";
                upToken = auth.uploadToken(qiniuOssProperties.getBucket(), key, expireSeconds, null);
                QiniuToken qiniuToken = new QiniuToken();
                qiniuToken.setOriginalKey(key);
                qiniuToken.setToken(upToken);
                qiniuTokens.add(qiniuToken);
            }
           return RestResultGenerator.genResult(qiniuTokens,"获取多个七牛token");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("获取七牛token失败");
        }
    }



}
