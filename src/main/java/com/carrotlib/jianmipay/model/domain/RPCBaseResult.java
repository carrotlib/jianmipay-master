package com.carrotlib.jianmipay.model.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务接口调用返回值基类
 */
public class RPCBaseResult extends RPCBaseParam {

    /**
     * RPC调用返回码
     * 0000：成功
     * 其他：失败
     */
    private String rpcRetCode;

    /**
     * RPC调用返回错误描述
     */
    private String rpcRetMsg;

    /**
     * DB返回的错误码
     */
    private String dbErrorCode;

    /**
     * DB返回的错误信息
     */
    private String dbErrorMsg;

    public String getRpcRetCode() {
        return rpcRetCode;
    }

    public void setRpcRetCode(String rpcRetCode) {
        this.rpcRetCode = rpcRetCode;
    }

    public String getRpcRetMsg() {
        return rpcRetMsg;
    }

    public void setRpcRetMsg(String rpcRetMsg) {
        this.rpcRetMsg = rpcRetMsg;
    }

    public String getDbErrorCode() {
        return dbErrorCode;
    }

    public void setDbErrorCode(String dbErrorCode) {
        this.dbErrorCode = dbErrorCode;
    }

    public String getDbErrorMsg() {
        return dbErrorMsg;
    }

    public void setDbErrorMsg(String dbErrorMsg) {
        this.dbErrorMsg = dbErrorMsg;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RPCBaseResult{");
        sb.append("rpcSourceSysId='").append(super.getRpcSourceSysId()).append('\'');
        sb.append(", rpcDateTime='").append(super.getRpcDateTime()).append('\'');
        sb.append(", rpcSequenceNo='").append(super.getRpcSequenceNo()).append('\'');
        sb.append(", rpcSignType=").append(super.getRpcSignType());
        sb.append(", rpcSign='").append(super.getRpcSign()).append('\'');
        sb.append(", bizSequenceNo='").append(super.getBizSequenceNo()).append('\'');
        sb.append(", bizSign='").append(super.getBizSign()).append('\'');
        sb.append(", rpcRetCode='").append(rpcRetCode).append('\'');
        sb.append(", rpcRetMsg='").append(rpcRetMsg).append('\'');
        sb.append(", dbErrorCode='").append(dbErrorCode).append('\'');
        sb.append(", dbErrorMsg='").append(dbErrorMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Map<String, Object> convert2Map() {
        Map<String, Object> rpcMap = new HashMap<String, Object>();
        rpcMap.put("rpcSourceSysId", super.getRpcSourceSysId());
        rpcMap.put("rpcDateTime", super.getRpcDateTime());
        rpcMap.put("rpcSequenceNo", super.getRpcSequenceNo());
        rpcMap.put("rpcSignType", super.getRpcSignType());
        rpcMap.put("rpcSign", super.getRpcSign());
        rpcMap.put("bizSequenceNo", super.getBizSequenceNo());
        rpcMap.put("bizSign", super.getBizSign());
        rpcMap.put("rpcRetCode", rpcRetCode);
        rpcMap.put("rpcRetMsg", rpcRetMsg);
        rpcMap.put("dbErrorCode", dbErrorCode);
        rpcMap.put("dbErrorMsg", dbErrorMsg);
        return rpcMap;
    }
}
