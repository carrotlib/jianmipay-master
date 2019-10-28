package com.carrotlib.jianmipay.mapper.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mch_info")
public class MchInfo {
    /**
     * 商户ID
     */
    @Id
    @Column(name = "mch_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String mchId;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 请求私钥
     */
    @Column(name = "req_key")
    private String reqKey;

    /**
     * 响应私钥
     */
    @Column(name = "res_key")
    private String resKey;

    /**
     * 商户状态,0-停止使用,1-使用中
     */
    private Byte state;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取商户ID
     *
     * @return mch_id - 商户ID
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * 设置商户ID
     *
     * @param mchId 商户ID
     */
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取请求私钥
     *
     * @return req_key - 请求私钥
     */
    public String getReqKey() {
        return reqKey;
    }

    /**
     * 设置请求私钥
     *
     * @param reqKey 请求私钥
     */
    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    /**
     * 获取响应私钥
     *
     * @return res_key - 响应私钥
     */
    public String getResKey() {
        return resKey;
    }

    /**
     * 设置响应私钥
     *
     * @param resKey 响应私钥
     */
    public void setResKey(String resKey) {
        this.resKey = resKey;
    }

    /**
     * 获取商户状态,0-停止使用,1-使用中
     *
     * @return state - 商户状态,0-停止使用,1-使用中
     */
    public Byte getState() {
        return state;
    }

    /**
     * 设置商户状态,0-停止使用,1-使用中
     *
     * @param state 商户状态,0-停止使用,1-使用中
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}