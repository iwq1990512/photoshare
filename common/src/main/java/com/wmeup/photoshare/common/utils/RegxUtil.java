package com.wmeup.photoshare.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 * Created by zhangyong
 * DATE: 2016/6/3.
 */
public class RegxUtil {

    /**
     * 判断是否匹配
     * @param regx 正则表达式
     * @param matcherString 待匹配的String
     * @return
     */
    public static boolean isMatches(String regx, String matcherString) {
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(matcherString);
        return matcher.matches();
    }
}
