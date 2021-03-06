package com.carrotlib.jianmipay.utils;

import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.consts.PayEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 支付工具类
 */
public class PayUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayUtil.class);

    public static Map<String, Object> genRetMap(String retCode, String retMsg, String resCode, String errCode, String errCodeDesc) {

        Map<String, Object> retMap = new HashMap<String, Object>();

        if (null != resCode) {
            retMap.put(PayConstant.RETURN_PARAM_RETCODE, retCode);
        }

        if (null != retMsg) {
            retMap.put(PayConstant.RETURN_PARAM_RETMSG, retMsg);
        }

        if (null != resCode) {
            retMap.put(PayConstant.RESULT_PARAM_RESCODE, resCode);
        }

        if (null != errCode) {
            retMap.put(PayConstant.RESULT_PARAM_ERRCODE, errCode);
        }

        if (null != errCodeDesc) {
            retMap.put(PayConstant.RESULT_PARAM_ERRCODEDES, errCodeDesc);
        }

        return retMap;
    }

    public static Map<String, Object> genRetMap(String retCode, String retMsg, String resCode, PayEnum payEnum) {

        Map<String, Object> retMap = new HashMap<String, Object>();

        if (null != resCode) {
            retMap.put(PayConstant.RETURN_PARAM_RETCODE, retCode);
        }

        if (null != retMsg) {
            retMap.put(PayConstant.RETURN_PARAM_RETMSG, retMsg);
        }

        if (null != resCode) {
            retMap.put(PayConstant.RESULT_PARAM_RESCODE, resCode);
        }

        if(null != payEnum) {
            retMap.put(PayConstant.RESULT_PARAM_ERRCODE, payEnum.getCode());
            retMap.put(PayConstant.RESULT_PARAM_ERRCODEDES, payEnum.getMessage());
        }

        return retMap;
    }

    public static String genRetData(Map retMap, String resKey) {

        if(retMap.get(PayConstant.RETURN_PARAM_RETCODE).equals(PayConstant.RETURN_VALUE_SUCCESS)) {
            String sign = PayDigestUtil.getSign(retMap, resKey, "payParams");
            retMap.put(PayConstant.RESULT_PARAM_SIGN, sign);
        }
        LOGGER.debug("生成响应数据:{}", GsonUtil.getInstance().toJson(retMap));

        return GsonUtil.getInstance().toJson(retMap);
    }

    public static String genRetFail(Map retMap) {
        LOGGER.debug("生成响应数据:{}", GsonUtil.getInstance().toJson(retMap));
        return GsonUtil.getInstance().toJson(retMap);
    }

    /**
     * 验证支付中心签名
     * @param params
     * @return
     */
    public static boolean verifyPaySign(Map<String,Object> params, String key) {
        String sign = (String)params.get("sign"); // 签名
        params.remove("sign");	// 不参与签名
        String checkSign = PayDigestUtil.getSign(params, key);
        if (!checkSign.equalsIgnoreCase(sign)) {
            return false;
        }
        return true;
    }

    public static String genUrlParams(Map<String, Object> paraMap) {
        if(paraMap == null || paraMap.isEmpty()) return "";
        StringBuffer urlParam = new StringBuffer();
        Set<String> keySet = paraMap.keySet();
        int i = 0;
        for(String key:keySet) {
            urlParam.append(key).append("=").append(paraMap.get(key));
            if(++i == keySet.size()) break;
            urlParam.append("&");
        }
        return urlParam.toString();
    }

    /**
     * 发起HTTP/HTTPS请求(method=POST)
     * @param url
     * @return
     */
    public static String call4Post(String url) {
        try {
            URL url1 = new URL(url);
            if("https".equals(url1.getProtocol())) {
                return HttpClient.callHttpsPost(url);
            }else if("http".equals(url1.getProtocol())) {
                return HttpClient.callHttpPost(url);
            }else {
                return "";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
