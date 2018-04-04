package com.neeson.example.controller;

import com.neeson.example.auth.service.CustomOAuthService;
import com.neeson.example.auth.service.OAuthServices;
import com.neeson.example.dto.OAuthUser;
import com.neeson.example.service.UserService;
import com.neeson.example.util.response.ResponseResult;
import com.neeson.example.util.response.RestResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/30
 * Time: 17:52
 * Description:
 */
@Api("用户相关")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private OAuthServices oAuthServices;
    @Autowired
    private UserService userService;


    @ApiOperation("第三方授权回调接口")
    @ApiImplicitParams({
            @ApiImplicitParam (name = "type",value = "第三方类型",paramType = "path",required = true,dataType = "String"),
            @ApiImplicitParam (name = "code",value = "鉴权码",required = true,dataType = "String")
    })
    @GetMapping("/oauth/{type}/callback")
    public ResponseResult callback(@PathVariable String type, @RequestParam String code){
        CustomOAuthService oAuthService = oAuthServices.getOAuthService(type);
        Token accessToken = oAuthService.getAccessToken(null, new Verifier(code));
        OAuthUser oAuthUser = oAuthService.getOAuthUser(accessToken);
        return RestResultGenerator.genResult(null,"");
    }







}
