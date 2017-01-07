package com.wmeup.util.requestid;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zy on 2016/8/17.
 */
public class UniqRequestIdGen {

    private static AtomicLong lastId  = new AtomicLong(); // 自增id，用于requestId的生成过程
    private static final String ip = LocalIpAddressUtil.resolveLocalIp(); // 本机ip地址，用于requestId的生成过程

    public static String resolveReqId() {
        // 规则： hexIp(ip)base36(timestamp)-seq
        return hexIp(ip) + Long.toString(System.currentTimeMillis(), Character.MAX_RADIX) + "-" + lastId.incrementAndGet();
    }

    // 将ip转换为定长8个字符的16进制表示形式：255.255.255.255 -> FFFFFFFF
    private static String hexIp(String ip) {
        StringBuilder sb = new StringBuilder();
        for (String seg : ip.split("\\.")) {
            String h = Integer.toHexString(Integer.parseInt(seg));
            if (h.length() == 1) sb.append("0");
            sb.append(h);
        }
        return sb.toString();
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid;
    }

}