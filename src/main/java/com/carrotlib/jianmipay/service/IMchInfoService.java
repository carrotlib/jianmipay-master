package com.carrotlib.jianmipay.service;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface IMchInfoService {

    String selectMchInfo(String jsonParam);

    String getByMchId(String mchId);
}
