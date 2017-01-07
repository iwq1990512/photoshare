package com.wmeup.photoshare.api.config.bo;

import com.wmeup.photoshare.api.base.bo.BaseResponseInfo;

/**
 * Created by zy on 2016/8/5.
 */
public class BankAvailRsp extends BaseResponseInfo{

    private static final long serialVersionUID = -1949370749610519824L;
    /**
     * 银行是否可用，true是， false否
     */
    private boolean flag;

    public BankAvailRsp() {
    }

    public BankAvailRsp(String version, String rspCode, String rspMessage) {
        super(version, rspCode, rspMessage);
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
