package com.neeson.example.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Create on 2019-09-20
 *
 * @author DaiLe
 */
@Slf4j
@Component
public class PropertiesUtils implements EnvironmentAware {

    private static Environment onlyReadEnvironment;

    public static String getStringValue(String key) {
        return onlyReadEnvironment.getProperty(key);
    }

    @Override
    public void setEnvironment(Environment environment) {
        onlyReadEnvironment = environment;
    }
}
