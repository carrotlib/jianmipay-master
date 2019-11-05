package com.carrotlib.jianmipay.service;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface PayChannelService {

    Map selectPayChannel(String paramJson);

    JSONObject getByMchIdAndChannelId(String mchId, String channelId);
}
