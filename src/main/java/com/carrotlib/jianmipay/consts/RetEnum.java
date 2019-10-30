package com.carrotlib.jianmipay.consts;

/**
 * RPC调用返回码枚举
 */
public enum RetEnum {

    /**
     * 0000:成功
     */
    RET_SUCCESS("0000", "success"),

    /**
     * 通讯层相关错误码，00开始进行标识
     */
    RET_REMOTE_UNAVAILABLE("0001", "远程服务不可用"),
    RET_REMOTE_INVALID(),
    RET


    private String code;

    private String message;

    private RetEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static RetEnum getRetEnum(String code) {

        if (null == code) {
            return null;
        }

        RetEnum[] values = RetEnum.values();
        for(RetEnum e : values) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }

        return null;
    }
}
