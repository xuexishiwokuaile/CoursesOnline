package com.xjt.model;

import java.util.Random;

public class RamdomString
{
    public static final String ALLCHAR = "0123456789";
    public static String generateString() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        //生成十位随机字符串
        for (int i = 0; i < 11; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }
}
