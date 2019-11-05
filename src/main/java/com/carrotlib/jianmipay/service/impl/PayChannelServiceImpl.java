package com.carrotlib.jianmipay.service.impl;

import com.carrotlib.jianmipay.consts.RetEnum;
import com.carrotlib.jianmipay.mapper.model.PayChannel;
import com.carrotlib.jianmipay.model.domain.BaseParam;
import com.carrotlib.jianmipay.service.BasePayService;
import com.carrotlib.jianmipay.service.PayChannelService;
import com.carrotlib.jianmipay.utils.GsonUtil;
import com.carrotlib.jianmipay.utils.ObjectValidUtil;
import com.carrotlib.jianmipay.utils.RPCUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
@Service
public class PayChannelServiceImpl extends BasePayService implements PayChannelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayChannelServiceImpl.class);

    @Override
    public Map selectPayChannel(String paramJson) {
        BaseParam baseParam = GsonUtil.getInstance().fromJson(paramJson, BaseParam.class);
        Map<String, Object> bizParamMap = baseParam.getBizParamMap();
        if (ObjectValidUtil.isInvalid(bizParamMap)) {
            LOGGER.debug("查询支付渠道信息失败, {}. paramJson={}", RetEnum.RET_PARAM_NOT_FOUND.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_NOT_FOUND);
        }
        String mchId = baseParam.isNullValue("mchId") ? null : bizParamMap.get("mchId").toString();
        String channelId = baseParam.isNullValue("channelId") ? null : bizParamMap.get("channelId").toString();
        if (ObjectValidUtil.isInvalid(mchId, channelId)) {
            LOGGER.debug("查询支付渠道信息失败, {}. paramJson={}", RetEnum.RET_PARAM_INVALID.getMessage(), paramJson);
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_PARAM_INVALID);
        }

        PayChannel payChannel = super.baseSelectPayChannel(mchId, channelId);

        if(payChannel == null) {
            return RPCUtil.createFailResult(baseParam, RetEnum.RET_BIZ_DATA_NOT_EXISTS);
        }

        String jsonResult = GsonUtil.getInstance().toJson(payChannel);

        return RPCUtil.createBizResult(baseParam, jsonResult);
    }

    public JSONObject getByMchIdAndChannelId(String mchId, String channelId) {

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("mchId", mchId);
        paramMap.put("channelId", channelId);
        String jsonParam = RPCUtil.createBaseParam(paramMap);
        Map<String, Object> result = selectPayChannel(jsonParam);
        String s = RPCUtil.mkRet(result);

        if(s == null) {
            return null;
        }
        return JSONObject.fromObject(s);
    }
}
