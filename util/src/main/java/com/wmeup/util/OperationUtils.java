package com.wmeup.util;

import java.math.BigDecimal;

/**
 * 运算工具类
 * Created by zy on 2016/11/9.
 */
public class OperationUtils {

    /**
     * 进行除法运算，d除以d2
     * @param d
     * @param d2
     * @return
     */
    public static int div(double d,int d2) {
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(d2);
        int s = b1.divide(b2, 0, BigDecimal.ROUND_HALF_UP).intValue();
        return s;
    }
}
