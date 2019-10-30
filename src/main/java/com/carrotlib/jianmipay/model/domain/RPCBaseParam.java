package com.carrotlib.jianmipay.model.domain;

import com.carrotlib.jianmipay.utils.DateUtils;
import com.carrotlib.jianmipay.utils.RandomStrUtil;

import java.io.Serializable;

/**
 * 服务接口调用入参基类
 */
public class RPCBaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 调用方ID（由RPC服务端分配）
     */
    private String rpcSrcSysId;

    /**
     * 业务调用当前时间， 格式：yyyyMMddHHmmssSSS
     */
    private String rpcDateTime;

    /**
     * 随机通讯码，要求一段时间内不重复
     */
    private String rpcSeqNo;

    /**
     * 签名计算方法， 0：明文， 1：SHA-1
     */
    private Integer rpcSignType;

    /**
     * 签名，用于验证调用方的合法性
     * 签名计算方法：签名方法(key(由RPC服务端分配,不在通讯中传递)+scrSysId+rpcDateTime(yyyyMMddHHmmssSSS)+rpcSignType+bizSeqNo+bizSign),如果字段为null则不参与
     * eg：sha1(key+srcSysId+rpcDateTime+rpcSignType+bizSeqNo+bizSign)
     */
    private String rpcSign;

    /**
     * 业务流水号，唯一标识一笔业务
     * 由业务前缀(2字符,参见Constant.MP_BIZ_SEQUENCE_NO_PREFIX)+日期时间(yyyyMMddHHmmss)+流水号(6位数字)组成
     * eg.  Constant.MP_BIZ_SEQUENCE_NO_PREFIX)
     *          +DateUtils.getCurrentTimeStr("yyyyMMddHHmmss")
     *          +BizSequenceUtils.getInstance().generateBizSeqNo()
     */
    private String bizSeqNo;

    /**
     * 业务签名，计算由各业务系统定义
     */
    private String bizSign;

    public RPCBaseParam() {};

    /**
     * 不需要业务签名的构造器
     * @param rpcSrcSysId
     * @param rpcSignKey
     * @param bizSeqNoPrefix
     */
    public RPCBaseParam(String rpcSrcSysId, String rpcSignKey, String bizSeqNoPrefix) {
        this.rpcSrcSysId = rpcSrcSysId;
        this.rpcDateTime = DateUtils.getCurrentTimeStrDefault();
        this.rpcSeqNo = RandomStrUtil.getInstance().getRandomString();
        this.rpcSignType =
    }
}
