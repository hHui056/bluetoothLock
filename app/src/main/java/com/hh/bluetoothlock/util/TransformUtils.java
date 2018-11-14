package com.hh.bluetoothlock.util;

/**
 * Create By hHui on 2018/11/14
 */

public class TransformUtils {

    /**
     * 转成字节长度
     *
     * @param iSource   原数据
     * @param iArrayLen 输出数据
     *                  只能转整形最多4个字节
     * @return
     */
    public static byte[] toByteArray(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];
        for (int i = 0; (i < 4) && (i < iArrayLen); i++) {
            bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);
        }
        byte temp;
        int len = bLocalArr.length;
        for (int i = 0; i < len / 2; i++) {
            temp = bLocalArr[i];
            bLocalArr[i] = bLocalArr[len - 1 - i];
            bLocalArr[len - 1 - i] = temp;
        }
        return bLocalArr;
    }

    public static int bytesToint(byte[] value) {
        int ret = 0;
        for (int i = 0; i < value.length; i++) {
            ret += (value[value.length - i - 1] & 0xFF) << (i * 8);
        }
        return ret;
    }
}
