package com.neeson.example.controller.deprecated;

import com.neeson.example.auth.service.OAuthServices;
import com.neeson.example.dto.EntrustQueue;
import com.neeson.example.util.response.ResponseResult;
import com.neeson.example.util.response.RestResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/21
 * Time: 15:45
 * Description:
 */
@RestController
@RequestMapping("test")
@Api(value = "测试API")
@Slf4j
public class TestController {

    @Autowired
    private OAuthServices oAuthServices;





    @ApiOperation(value = "测试接口")
    @ApiImplicitParam(name = "entrustQueue", value = "用户实体", required = true, dataType = "EntrustQueue")
    @PostMapping("/entrusQueueFeign")
    public ResponseResult addEntrust2(@RequestBody  EntrustQueue entrustQueue) {

        log.info("entrusQueueFeign:" + entrustQueue.toString());

        return RestResultGenerator.genResult(oAuthServices.getCustomOAuthServices().size(), "");
    }


}
