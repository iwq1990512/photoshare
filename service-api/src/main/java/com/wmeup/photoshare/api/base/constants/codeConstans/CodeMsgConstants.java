package com.wmeup.photoshare.api.base.constants.codeConstans;

/**
 * <p>Title: CodeMsgConstants</p>
 * <p>Description: 错误信息静态类<p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author zy
 * @version 1.0
 * @date 16/5/11
 */

public class CodeMsgConstants {
    public static class SUCCESS_CODE {
        public static final String QUERY_SUCCESS ="查询成功";
        public static final String ADD_SUCCESS = "添加成功";
        public static final String UPDATE_SUCCESS = "更新成功";
        public static final String DELETE_SUCCESS = "删除成功";
    }
    public static class SYSTEM_ERR {
        public static final String SYS = "系统错误";
    }
    public static class PARAM_ERR {
    	public static final String PARAM_NULL = "参数不可为空";
        public static final String PARAM_VERNN = "参数version不可为空";
        public static final String PARAM_VERNM= "参数version不是正确版本号";
        public static final String PARAM_REQIDNN = "参数requestId不可为空";
        public static final String PARAM_REQID_TOOLONG= "参数requestId长度过长";
        public static final String PARAM_SIGNTYPNN = "参数signType不可为空";
        public static final String PARAM_SIGNTYP_NM = "参数signType不是正确签名类型";
        public static final String PARAM_SIGNTYP_TOOLONG= "参数signType长度过长";
        public static final String PARAM_SIGNNN = "参数sign不可为空";
        public static final String PARAM_CHANNELNN = "参数channel不可为空";
        public static final String PARAM_CHANNEL_NM = "参数channel不是正确渠道";
        public static final String PARAM_MD5_FAIL = "md5校验失败";

    }
    public static class QUERY_ERR {
        public static final String BANKCONF_QRY_NULL = "银行限额配置信息查询为空";
        public static final String WHITE_QRY_NULL = "白名单查询为空";
        public static final String BANK_COMMON_QRY_NULL = "银行配置信息查询为空";
    }
    public static class UPDATE_ERR {
    }
    public static class FIN_ERR {

    }
    public static class CHECK_MD5 {
        public static final String CHECK_ERR = "校验比对失败";


    }
    /**
     * 插入数据错误信息
     */
    public static class  INSTER_ERR {
        public static final String BANKCONF_EXIST = "银行限额配置信息已存在";
        public static final String BANK_COMMON_EXIST = "银行配置信息已存在";
        public static final String WHITECONF_EXIST = "白名单配置信息已存在";
        public static final String CONFIG_EXIST = "配置信息已存在";
        public static final String USER_NOT_EXIST = "该用户不存在";
        public static final String GENERAL_CONFIG_EXIST = "通用配置信息已存在";
        public static final String WHITECONF_EXIST_BATCHADD = "用户白名单信息已存在，不能重复添加";
    }

    /**
     * 删除数据错误信息
     */
    public static class DELETE_ERR {
        public static final String BANKCONF_NOT_EXIST = "银行限额配置不存在";
        public static final String INNERUSER_NOT_EXIST = "白名单配置不存在";
        public static final String CONFIG_NOT_EXIST = "是否可用配置不存在";
    }
    /**
     * 身份校验失败
     */
    public static class  CHECK_USRID {

    }
    /**
     * 实名认证异常
     */
    public static class  IDAUTH_ERR{

    }

    /**
     * 支付系统异常
     */
    public static class PAYMPS_ERR{
        public static final String DATA_NOT_EXIST = "支付中心系统返回数据为空";
    }
    /**
     * 通信异常
     */
    public static class HTTP_ERR{

    }

    /**
     * 业务异常
     */
    public  static class BIZ_ERR{

    }
}
