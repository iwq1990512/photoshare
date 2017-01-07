package com.wmeup.util.http;

import com.wmeup.util.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by zy on 2016/11/03
 */
public  abstract class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static final String getFilePath(String classPath){
        ClassPathResource classPathResource = new ClassPathResource(classPath);
        String path  = "";
        try {
            path = classPathResource.getFile().getPath();
        } catch (IOException e) {
            logger.error("文件路径不正确");
            throw new BusinessException("COMMCONF50000","获取文件路径异常");
        }
        return path;
    }
}
