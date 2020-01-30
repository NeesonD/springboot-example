package com.neeson.example.base.http;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.stereotype.Component;

/**
 * Create on 2019-09-23
 *
 * @author DaiLe
 */
@Component
public class JwtContextService {


    private static TransmittableThreadLocal<JwtContext> loginJwtContextThreadLocal = new TransmittableThreadLocal<>();

    /**
     * 设置当前的JWT
     *
     * @param jwtContext
     */
    public void setJwtContext(JwtContext jwtContext) {
        loginJwtContextThreadLocal.set(jwtContext);
    }

    /**
     * 清除JWT
     */
    public void clearWeChatLoginContext() {
        loginJwtContextThreadLocal.remove();
    }

    /**
     * 获取当前线程中的JWT
     *
     * @return
     */
    public JwtContext getJwtContext() {
        return loginJwtContextThreadLocal.get();
    }

}
