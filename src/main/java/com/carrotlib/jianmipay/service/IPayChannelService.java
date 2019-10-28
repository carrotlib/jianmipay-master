package com.carrotlib.jianmipay.service;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface IPayChannelService {

    String selectPayChannel(String jsonParam);

    String getByMchIdAndChannelId(String mchId, String channelId);
}
