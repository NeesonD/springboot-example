package com.neeson.example.base.http;

import lombok.Data;

/**
 * Create on 2019-09-23
 *
 * @author DaiLe
 */
@Data
public class JwtContext {

    private String token;

    public static JwtContext newOne() {
        return new JwtContext();
    }


}
