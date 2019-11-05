package com.carrotlib.jianmipay.service;

import com.carrotlib.jianmipay.consts.PayConstant;
import com.carrotlib.jianmipay.mapper.mapper.MchInfoMapper;
import com.carrotlib.jianmipay.mapper.mapper.PayChannelMapper;
import com.carrotlib.jianmipay.mapper.mapper.PayOrderMapper;
import com.carrotlib.jianmipay.mapper.model.MchInfo;
import com.carrotlib.jianmipay.mapper.model.PayChannel;
import com.carrotlib.jianmipay.mapper.model.PayOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fenghaitao on 2019/10/28
 */
@Service
public class BasePayService {

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private MchInfoMapper mchInfoMapper;

    @Resource
    private PayChannelMapper payChannelMapper;

    public MchInfo baseSelectMchInfo(String mchId) {
        return mchInfoMapper.selectByPrimaryKey(mchId);
    }

    public PayChannel baseSelectPayChannel(String mchId, String channelId) {

        Example example = new Example(PayChannel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("channelId", channelId);
        criteria.andEqualTo("mchId", mchId);

        List<PayChannel> payChannelList = payChannelMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(payChannelList)) {
            return null;
        }

        return payChannelList.get(0);
    }

    public int baseCreatePayOrder(PayOrder payOrder) {
        return payOrderMapper.insertSelective(payOrder);
    }

    public PayOrder baseSelectPayOrder(String payOrderId) {
        return payOrderMapper.selectByPrimaryKey(payOrderId);
    }

    public PayOrder baseSelectPayOrderByMchIdAndPayOrderId(String mchId, String payOrderId) {

        Example example = new Example(PayOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mchId", mchId);
        criteria.andEqualTo("payOrderId", payOrderId);

        List<PayOrder> payOrderList = payOrderMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(payOrderList)) {
            return null;
        }

        return payOrderList.get(0);
    }

    public PayOrder baseSelectpayOrderByMchIdAndMchOrderNo(String mchId, String mchOrderNo) {

        Example example = new Example(PayOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mchId", mchId);
        criteria.andEqualTo("mchOrderNo", mchOrderNo);

        List<PayOrder> payOrderList = payOrderMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(payOrderList)) {
            return null;
        }

        return payOrderList.get(0);
    }

    public int baseUpdateStatus4Ing(String payOrderId, String channelOrderNo) {

        PayOrder payOrder = new PayOrder();
        payOrder.setStatus(PayConstant.PAY_STATUS_PAYING);
        if (null != channelOrderNo) {
            payOrder.setChannelOrderNo(channelOrderNo);
        }
        payOrder.setPaySuccTime(System.currentTimeMillis());

        Example example = new Example(PayOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("payOrderId", payOrderId);
        criteria.andEqualTo("status", PayConstant.PAY_STATUS_INIT);

        return payOrderMapper.updateByExampleSelective(payOrder, example);
    }

    public int baseUpdateStatus4Success(String payOrderId, String channelOrderNo) {

        PayOrder payOrder = new PayOrder();
        payOrder.setParam2(payOrderId);
        payOrder.setStatus(PayConstant.PAY_STATUS_SUCCESS);
        if (null != channelOrderNo) {
            payOrder.setChannelOrderNo(channelOrderNo);
        }
        payOrder.setPaySuccTime(System.currentTimeMillis());

        Example example = new Example(PayOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("payOrderId", payOrderId);
        criteria.andEqualTo("status", PayConstant.PAY_STATUS_PAYING);

        return payOrderMapper.updateByExampleSelective(payOrder, example);
    }

    public int baseUpdateStatus4Complete(String payOrderId) {

        PayOrder payOrder = new PayOrder();
        payOrder.setPayOrderId(payOrderId);
        payOrder.setStatus(PayConstant.PAY_STATUS_COMPLETE);

        Example example = new Example(PayOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("payOrderId", payOrderId);
        criteria.andEqualTo("status", PayConstant.PAY_STATUS_SUCCESS);

        return payOrderMapper.updateByExampleSelective(payOrder, example);
    }

    public int baseUpdateNotify(String payOrderId, Byte count) {

        PayOrder newPayOrder = new PayOrder();
        newPayOrder.setNotifyCount(count);
        newPayOrder.setLastNotifyTime(System.currentTimeMillis());
        newPayOrder.setPayOrderId(payOrderId);

        return payOrderMapper.updateByPrimaryKey(newPayOrder);
    }

    public int baseUpdateNotify(PayOrder payOrder) {
        return payOrderMapper.updateByPrimaryKey(payOrder);
    }
}
