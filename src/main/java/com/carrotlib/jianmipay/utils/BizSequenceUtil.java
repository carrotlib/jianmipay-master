package com.carrotlib.jianmipay.utils;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 业务流水号工具类
 */
public class BizSequenceUtil {

    private static Object lock = new Object();

    private static BizSequenceUtil instance;

    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

    private static final NumberFormat numberFormat = new DecimalFormat("00000000");

    private static AtomicInteger seq = new AtomicInteger(1);

    private static final int MAX = 99999999;

    private BizSequenceUtil() {

    }

    public static BizSequenceUtil getInstance() {
        if (null == instance) {
            synchronized (lock) {
                if (null == instance) {
                    instance = new BizSequenceUtil();
                }
            }
        }
        return instance;
    }

    public String genBizSequenceNo(String bizSequenceNoPrefix) {
        StringBuffer bizSequenceNo = new StringBuffer();
        bizSequenceNo.append(bizSequenceNoPrefix)
                .append(DateUtils.getCurrentTimeStr("yyMMddHHmmss"))
                .append(getSequence());
        return bizSequenceNo.toString();
    }

    private String getSequence() {
        StringBuffer stringBuffer = new StringBuffer();
        numberFormat.format(seq, stringBuffer, HELPER_POSITION);
        if(!seq.compareAndSet(MAX, 0)) {
            seq.incrementAndGet();
        }
        return stringBuffer.toString();
    }
}
