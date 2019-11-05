package com.carrotlib.jianmipay.model.domain;

import com.carrotlib.jianmipay.consts.RPCSignTypeEnum;
import com.carrotlib.jianmipay.utils.BizSequenceUtil;
import com.carrotlib.jianmipay.utils.DateUtils;
import com.carrotlib.jianmipay.utils.RPCSignUtil;
import com.carrotlib.jianmipay.utils.RandomStrUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务接口调用入参基类
 */
public class RPCBaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 调用方ID（由RPC服务端分配）
     */
    private String rpcSourceSysId;

    /**
     * 业务调用当前时间， 格式：yyyyMMddHHmmssSSS
     */
    private String rpcDateTime;

    /**
     * 随机通讯码，要求一段时间内不重复
     */
    private String rpcSequenceNo;

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
    private String bizSequenceNo;

    /**
     * 业务签名，计算由各业务系统定义
     */
    private String bizSign;

    public RPCBaseParam() {};

    /**
     * 不需要业务签名的构造器
     * @param rpcSourceSysId
     * @param rpcSignKey
     * @param bizSeqNoPrefix
     */
    public RPCBaseParam(String rpcSourceSysId, String rpcSignKey, String bizSeqNoPrefix) {

        this.rpcSourceSysId = rpcSourceSysId;
        this.rpcDateTime = DateUtils.getCurrentTimeStrDefault();
        this.rpcSequenceNo = RandomStrUtil.getInstance().getRandomString();
        this.rpcSignType = RPCSignTypeEnum.SHA1_SIGN.getCode();
        this.bizSequenceNo = BizSequenceUtil.getInstance().genBizSequenceNo(bizSeqNoPrefix);

        StringBuffer descrptBuffer = new StringBuffer();
        descrptBuffer.append(rpcSignKey)
                .append(this.rpcSourceSysId)
                .append(this.rpcDateTime)
                .append(this.rpcSignType)
                .append(this.bizSequenceNo);

        this.rpcSign = RPCSignUtil.sha1(descrptBuffer.toString());
    }

    /**
     * 需要业务签名的构造器
     * @param rpcSourceSysId
     * @param rpcSignKey
     * @param bizSeqNoPrefix
     * @param bizSign
     */
    public RPCBaseParam(String rpcSourceSysId, String rpcSignKey, String bizSeqNoPrefix, String bizSign) {

        this.rpcSourceSysId = rpcSourceSysId;
        this.rpcDateTime = DateUtils.getCurrentTimeStrDefault();
        this.rpcSequenceNo = RandomStrUtil.getInstance().getRandomString();
        this.rpcSignType = RPCSignTypeEnum.SHA1_SIGN.getCode();
        this.bizSequenceNo = BizSequenceUtil.getInstance().genBizSequenceNo(bizSeqNoPrefix);
        this.bizSign= bizSign;

        StringBuffer descriptBuffer = new StringBuffer();
        descriptBuffer.append(rpcSignKey)
                .append(rpcSourceSysId)
                .append(rpcDateTime)
                .append(rpcSignType)
                .append(bizSequenceNo)
                .append(bizSign);

        this.rpcSign = RPCSignUtil.sha1(descriptBuffer.toString());
    }

    public String getRpcSourceSysId() {
        return rpcSourceSysId;
    }

    public void setRpcSourceSysId(String rpcSourceSysId) {
        this.rpcSourceSysId = rpcSourceSysId;
    }

    public String getRpcDateTime() {
        return rpcDateTime;
    }

    public void setRpcDateTime(String rpcDateTime) {
        this.rpcDateTime = rpcDateTime;
    }

    public String getRpcSequenceNo() {
        return rpcSequenceNo;
    }

    public void setRpcSequenceNo(String rpcSequenceNo) {
        this.rpcSequenceNo = rpcSequenceNo;
    }

    public Integer getRpcSignType() {
        return rpcSignType;
    }

    public void setRpcSignType(Integer rpcSignType) {
        this.rpcSignType = rpcSignType;
    }

    public String getRpcSign() {
        return rpcSign;
    }

    public void setRpcSign(String rpcSign) {
        this.rpcSign = rpcSign;
    }

    public String getBizSequenceNo() {
        return bizSequenceNo;
    }

    public void setBizSequenceNo(String bizSequenceNo) {
        this.bizSequenceNo = bizSequenceNo;
    }

    public String getBizSign() {
        return bizSign;
    }

    public void setBizSign(String bizSign) {
        this.bizSign = bizSign;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RPCBaseParam{");
        sb.append("rpcSourceSysId='").append(rpcSourceSysId).append('\'');
        sb.append(", rpcDateTime='").append(rpcDateTime).append('\'');
        sb.append(", rpcSequenceNo='").append(rpcSequenceNo).append('\'');
        sb.append(", rpcSignType=").append(rpcSignType);
        sb.append(", rpcSign='").append(rpcSign).append('\'');
        sb.append(", bizSequenceNo='").append(bizSequenceNo).append('\'');
        sb.append(", bizSign='").append(bizSign).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Map<String, Object> convert2Map() {
        Map<String, Object> rpcMap = new HashMap<>();
        rpcMap.put("rpcSourceSysId", rpcSourceSysId);
        rpcMap.put("rpcDateTime", rpcDateTime);
        rpcMap.put("rpcSequenceNo", rpcSequenceNo);
        rpcMap.put("rpcSignType", rpcSignType);
        rpcMap.put("rpcSign", rpcSign);
        rpcMap.put("bizSequenceNo", bizSequenceNo);
        rpcMap.put("bizSign", bizSign);
        return rpcMap;
    }
}
