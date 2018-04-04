package com.neeson.example.auth.service;

import com.neeson.example.dto.OAuthUser;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/4/2
 * Time: 11:24
 * Description:
 */
public interface CustomOAuthService extends OAuthService {

    String getOAuthType();
    OAuthUser getOAuthUser(Token accessToken);

}
