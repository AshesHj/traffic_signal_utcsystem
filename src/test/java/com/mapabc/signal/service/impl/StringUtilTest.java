package com.mapabc.signal.service.impl;

import java.text.MessageFormat;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/7 10:46
 * @description: []
 */
public class StringUtilTest {

    public static void main(String[] args) {
//        String urlT = "http://www.baidu.com/{0}/test";
//        String format = MessageFormat.format(urlT, "123");
//        System.out.println(format);
        for (int i = 0; i < 10; i++) {
            try {
                if (i == 5) {
                    throw new RuntimeException("test");
                }
                System.out.println(i);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
