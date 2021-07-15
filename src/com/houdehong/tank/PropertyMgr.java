package com.houdehong.tank;


import java.io.IOException;
import java.util.Properties;

/**
 * @author houdehong
 * @date 2021-07-14
 * @description 配置文件加载类
 */
public class PropertyMgr {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key返回value
     * @param key
     * @return
     */
    public static Object getKey(String key){
        return properties.get(key);
    }
}
