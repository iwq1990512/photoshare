package com.wmeup.photoshare.consumer.property;

/**
 * Created by yzf on 2016/5/20.
 */
public class ConsumerProClass {
	private  String certUrl;
	private  String certCharsetType;
	private  String certSecretKey;
	private  String certVersion;
	private  String certSignType;
	private  String payVersion;
	private  String payUrl;
	private  String paySignType;
	private  String payCertPath;
	private  String payMerchantId;
	private String payCertPassword;
	private String payPassType;

	public ConsumerProClass(String certUrl, String certCharsetType,  String certSecretKey,
							String certVersion,  String certSignType,String payVersion, String payUrl,
							String paySignType, String payCertPath, String payMerchantId,String payCertPassword,
							String payPassType) {
		super();
		this.certUrl = certUrl;
		this.certCharsetType = certCharsetType;
		this.certSecretKey = certSecretKey;
		this.certVersion = certVersion;
		this.certSignType = certSignType;
		this.payVersion = payVersion;
		this.payUrl = payUrl;
		this.paySignType = paySignType;
		this.payCertPath = payCertPath;
		this.payMerchantId = payMerchantId;
		this.payCertPassword = payCertPassword;
		this.payPassType = payPassType;
	}
	public String getCertUrl() {
		return certUrl;
	}
	public String getCertCharsetType() {
		return certCharsetType;
	}
	public String getCertSecretKey() {
		return certSecretKey;
	}
	public String getCertVersion() {
		return certVersion;
	}
	public String getCertSignType() {
		return certSignType;
	}

	public String getPayVersion() {
		return payVersion;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public String getPaySignType() {
		return paySignType;
	}

	public String getPayCertPath() {
		return payCertPath;
	}

	public String getPayMerchantId() {
		return payMerchantId;
	}

	public String getPayCertPassword() {
		return payCertPassword;
	}

	public String getPayPassType() {
		return payPassType;
	}
}
