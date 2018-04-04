package com.neeson.example.util.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daile on 2017/8/24.
 */
public enum ResponseErrorEnum {

    ILLEGAL_PARAMS("ILLEGAL_PARAMS","请求参数不合法"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "接口内部异常!"),
    MY_EXCEPTION("MY_EXCEPTION","自定义异常");

    private String code;
    private String message;

    ResponseErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private Map<String,Object> serialize(){
        Map<String,Object> valueMap = new HashMap<>(2);
        valueMap.put("code",this.code);
        valueMap.put("message",this.message);
        return valueMap;
    }

    public String getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }


}
