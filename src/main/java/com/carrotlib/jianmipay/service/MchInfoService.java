package com.carrotlib.jianmipay.service;

import net.sf.json.JSONObject;

/**
 * @author fenghaitao on 2019/10/28
 */
public interface MchInfoService {

    JSONObject selectMchInfo(JSONObject jsonParam);

    JSONObject getByMchId(String mchId);
}
