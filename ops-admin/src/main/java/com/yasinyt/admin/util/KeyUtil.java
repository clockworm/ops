package com.yasinyt.admin.util;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * @author TangLingYun
 * @describe 数据持久化主键生成策略
 */
public class KeyUtil {

    public static synchronized String genUniqueNumKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
    
    public static synchronized String genUniqueID(){
        return UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);
    }

}