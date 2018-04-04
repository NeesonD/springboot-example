package com.neeson.example.auth.service;

import com.neeson.example.util.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/30
 * Time: 19:52
 * Description:获取具体OAuthService
 */
@Service
@Slf4j
public class OAuthServices {

    @Autowired
    List<CustomOAuthService> customOAuthServices;

    public CustomOAuthService getOAuthService(String type) {
        Optional<CustomOAuthService> oAuthServiceDecorator = customOAuthServices.stream()
                .filter(o -> o.getOAuthType().equals(type))
                .findFirst();

        if (oAuthServiceDecorator.isPresent()) {
            return oAuthServiceDecorator.get();
        } else {
            throw new MyException("======>没有对应的第三方");
        }
    }


    public List<CustomOAuthService> getCustomOAuthServices() {
        return customOAuthServices;
    }
}
