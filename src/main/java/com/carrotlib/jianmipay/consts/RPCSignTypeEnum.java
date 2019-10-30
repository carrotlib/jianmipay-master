package com.carrotlib.jianmipay.consts;

/**
 * RPC通讯层签名计算方法枚举类
 */
public enum RPCSignTypeEnum {

    /**
     * 明文
     */
    NOT_SIGN(0),

    /**
     * SHA-1签名
     */
    SHA1_SIGN(1);

    private Integer code;

    private RPCSignTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    public static RPCSignTypeEnum getRPCSignTypeEnum(Integer code) {

        if (null == code) {
            return null;
        }

        RPCSignTypeEnum[] values = RPCSignTypeEnum.values();
        for (RPCSignTypeEnum e : values) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }

        return null;
    }
}
