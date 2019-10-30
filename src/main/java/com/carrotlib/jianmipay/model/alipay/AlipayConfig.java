package com.carrotlib.jianmipay.model.alipay;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config.ali")
public class AlipayConfig {

    /**
     * 商户appid
     */
    private String appId;

    /**
     * 私钥 pkcs8格式
     */
    private String rsaPrivateKey;

    /**
     * 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    private String notifyUrl;

    /**
     * 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
     */
    private String returnUrl;

    /**
     * 请求网关地址
     */
    private String url;

    /**
     * 编码
     */
    public static String CHARSET = "UTF-8";

    /**
     * 返回格式
     */
    public static String FORMAT = "json";

    /**
     * 支付宝公钥
     */
    public String alipayPublicKey;

    /**
     * RSA2
     */
    public static String SIGNTYPE = "RSA2";

    /**
     * 是否沙箱环境,1:沙箱,0:正式环境
     */
    private int isSandbox = 0;

    /**
     * 初始化支付宝配置
     * @param configParam
     * @return
     */
    public AlipayConfig init(String configParam) {

        if (StringUtils.isBlank(configParam)) {
            return this;
        }

        JSONObject paramJson = JSONObject.fromObject(configParam);

        if (null == paramJson) {
            return this;
        }

        this.setAppId(paramJson.optString("appid"));
        this.setRsaPrivateKey(paramJson.optString("privateKey"));
        this.setAlipayPublicKey(paramJson.optString("alipayPublicKey"));
        this.setIsSandbox(paramJson.optInt("isSandBox"));

        if (this.getIsSandbox() == 1) {
            this.setUrl("https://openapi.alipaydev.com/gateway.do");
        }

        return this;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String getCHARSET() {
        return CHARSET;
    }

    public static void setCHARSET(String CHARSET) {
        AlipayConfig.CHARSET = CHARSET;
    }

    public static String getFORMAT() {
        return FORMAT;
    }

    public static void setFORMAT(String FORMAT) {
        AlipayConfig.FORMAT = FORMAT;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public static String getSIGNTYPE() {
        return SIGNTYPE;
    }

    public static void setSIGNTYPE(String SIGNTYPE) {
        AlipayConfig.SIGNTYPE = SIGNTYPE;
    }

    public int getIsSandbox() {
        return isSandbox;
    }

    public void setIsSandbox(int isSandbox) {
        this.isSandbox = isSandbox;
    }
}
