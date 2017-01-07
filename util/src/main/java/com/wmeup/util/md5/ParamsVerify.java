package com.wmeup.util.md5;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @brief 参数校验
 *
 *        判断空值，生成明文，参数的异常返回
 * 
 * @author - 2016年1月15日 付强 创建初始版本
 *
 */
public class ParamsVerify {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(ParamsVerify.class);

	/**
	 * @brief 校验是否有空值参数
	 *
	 * @author - 2016年5月6日 张基闯 创建初始版本
	 *
	 * @param parms
	 * @return
	 */
	public static boolean isBlank(Object... parms) {
		for (int i = 0; i < parms.length; i++) {
			if (parms[i] == null || StringUtils.isEmpty(parms[i].toString().replaceAll(" ", ""))) {
				return true;
			}
		}
		return false;
	}
}
