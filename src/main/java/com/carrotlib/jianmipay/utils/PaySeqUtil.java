package com.carrotlib.jianmipay.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 生成全局唯一订单序列号工具类
 */
public class PaySeqUtil {

    private static AtomicLong PAY_SEQ = new AtomicLong(0L);

    private static String PAY_SEQ_PREDIX = "P";

    private static AtomicLong TRANS_SEQ = new AtomicLong(0L);

    private static String TRANS_SEQ_PREFIX = "T";

    private static AtomicLong REFUND_SEQ = new AtomicLong(0L);

    private static String REFUND_SEQ_PREFIX = "R";

    private static String NODE = "00";

    public static String getPay() {
        return getSeq(PAY_SEQ_PREDIX, PAY_SEQ);
    }

    public static String getTrans() {
        return getSeq(TRANS_SEQ_PREFIX, TRANS_SEQ);
    }

    public static String getRefund() {
        return getSeq(REFUND_SEQ_PREFIX, REFUND_SEQ);
    }

    private static String getSeq(String prefix, AtomicLong seq) {
        prefix += NODE;
        return String.format("%s%s%d", prefix, DateUtil.getSeqString(), (int) seq.getAndIncrement() % 1000000);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println("pay=" + getPay());
            System.out.println("trans=" + getTrans());
            System.out.println("refund=" + getRefund());
        }
    }
}
