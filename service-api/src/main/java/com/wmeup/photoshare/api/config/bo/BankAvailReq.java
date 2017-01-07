package com.wmeup.photoshare.api.config.bo;

import com.wmeup.photoshare.api.base.bo.BaseRequestInfo;

/**
 * Created by zy on 2016/8/5.
 */
public class BankAvailReq extends BaseRequestInfo {
    private static final long serialVersionUID = 2248217676651894715L;
    /**
     * 银行代码
     */
    private String bankCode;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
