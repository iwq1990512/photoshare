package com.wmeup.util.date;

import org.slf4j.Logger;

/**
 * <p>Title: TimeLogUtil</p>
 * <p>Description: 记录日志格式化工具类<p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author zy
 * @version 1.0
 * @date 16/5/30
 */

public class LogFormatUtil {

    public static void serviceTimeLog(String serviceName, Long startTime, Long endTime, Logger logger) {
        logger.info(serviceName + "调用耗时{}毫秒", endTime - startTime);
    }
}
