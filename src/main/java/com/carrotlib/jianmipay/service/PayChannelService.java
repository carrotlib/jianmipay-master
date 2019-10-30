package com.carrotlib.jianmipay.service;

import net.sf.json.JSONObject;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface PayChannelService {

    JSONObject selectPayChannel(JSONObject jsonParam);

    JSONObject getByMchIdAndChannelId(String mchId, String channelId);
}
