package com.hh.bluetoothlock.instruction.body;

/**
 * Create By hHui on 2018/11/13
 */

public class Body {
    /**
     * 消息体的长度，字节数。
     */
    public int length = 0;
    /**
     * 消息体内容格式是否合法
     */
    protected boolean mContentAvailable = true;

    /**
     * 消息的内容，可以为null。注意：此处是消息体的明文
     */
    protected byte[] mContent = null;

    /**
     * 设置消息体内容格式是否正确。
     *
     * @param available true 正确； false 错误。
     */
    public void setIsAvailable(boolean available) {
        mContentAvailable = available;
    }

    /**
     * 消息体格式是否正确。
     *
     * @return ture，正确；false，错误。
     */
    public boolean isAvailable() {
        return mContentAvailable;
    }

    /**
     * 转换成要发送给设备的字节数组。
     *
     * @return null，表示消息体格式错误，不能发送到设备，需要通知UI消息体不正确。
     */
    public byte[] toByteArray() {
        return mContent;
    }

    /**
     * 解析应答消息体
     *
     * @param content 设备返回的消息体字节流
     */
    public void parseContent(byte[] content) {
        mContent = content;
    }


    public int getLength() {
        return mContent == null ? 0 : mContent.length;
    }
}
