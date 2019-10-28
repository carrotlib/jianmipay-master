package com.carrotlib.jianmipay.mapper.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "pay_channel")
public class PayChannel {
    /**
     * 渠道主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 渠道ID
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 渠道名称,如:alipay,wechat
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 渠道商户ID
     */
    @Column(name = "channel_mch_id")
    private String channelMchId;

    /**
     * 商户ID
     */
    @Column(name = "mch_id")
    private String mchId;

    /**
     * 渠道状态,0-停止使用,1-使用中
     */
    private Byte state;

    /**
     * 配置参数,json字符串
     */
    private String param;

    /**
     * 备注
     */
    private String remark;

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
     * 获取渠道主键ID
     *
     * @return id - 渠道主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置渠道主键ID
     *
     * @param id 渠道主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取渠道ID
     *
     * @return channel_id - 渠道ID
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 设置渠道ID
     *
     * @param channelId 渠道ID
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取渠道名称,如:alipay,wechat
     *
     * @return channel_name - 渠道名称,如:alipay,wechat
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 设置渠道名称,如:alipay,wechat
     *
     * @param channelName 渠道名称,如:alipay,wechat
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 获取渠道商户ID
     *
     * @return channel_mch_id - 渠道商户ID
     */
    public String getChannelMchId() {
        return channelMchId;
    }

    /**
     * 设置渠道商户ID
     *
     * @param channelMchId 渠道商户ID
     */
    public void setChannelMchId(String channelMchId) {
        this.channelMchId = channelMchId;
    }

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
     * 获取渠道状态,0-停止使用,1-使用中
     *
     * @return state - 渠道状态,0-停止使用,1-使用中
     */
    public Byte getState() {
        return state;
    }

    /**
     * 设置渠道状态,0-停止使用,1-使用中
     *
     * @param state 渠道状态,0-停止使用,1-使用中
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * 获取配置参数,json字符串
     *
     * @return param - 配置参数,json字符串
     */
    public String getParam() {
        return param;
    }

    /**
     * 设置配置参数,json字符串
     *
     * @param param 配置参数,json字符串
     */
    public void setParam(String param) {
        this.param = param;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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