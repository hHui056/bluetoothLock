package com.hh.bluetoothlock.instruction;

import com.hh.bluetoothlock.instruction.body.Body;

import java.io.Serializable;
import java.util.Random;

/**
 * Create By hHui on 2018/11/13
 */

public class Instruction implements Serializable {
    // ===========================================================================================================
    //  filed             length               description
    //  STX                 1               固定头部，0xFE
    //  NUM                 1               随机数，数据发送方产生，用于加密数据
    //  KEY                 1               通信秘钥， 由车锁随机产生， APP 通过（ 0x11）命令获得
    //  CMD                 1               命令字
    //  LEN                 1               数据长度 ---> DATA的长度
    //  DATA                n               数据
    //  CRC                 2               CRC16校验和（需要加密过后才计算，不包括在原始数据中）
    // ===========================================================================================================
    // 1 byte
    protected byte stx = (byte) 0xFE;
    // 1 byte
    protected byte num;
    // 1 byte
    protected byte key;
    // 1 byte
    protected byte cmd;
    // 1 byte
    protected byte len;
    // n bytes
    protected Body body;
    // 2 bytes
    protected byte[] crc = new byte[2];

    public int getLength() {
        return len & 0xFF;
    }

    public byte getCmd() {
        return cmd;
    }

    public byte getKey() {
        return key;
    }

    public Body getBody() {
        return body;
    }

    public int getNum() {
        return num & 0xFF;
    }

    private byte[] toByteArray() {
        int i = 0;
        byte[] input = new byte[getLength() + 5];
        input[i++] = this.stx;
        input[i++] = this.num;
        input[i++] = this.key;
        input[i++] = this.cmd;
        input[i++] = this.len;
        if (this.body != null) {
            byte[] data = this.body.toByteArray();
            for (int j = 0; j < data.length; j++) {
                input[i++] = data[j];
            }
        }
        return input;
    }

    // - 返回加密过后的bytes
    public byte[] toEnciphermentByteArray() {
        int i = 0;
        byte[] originalData = toByteArray();//原始数据
        byte num = originalData[1];

        byte[] encrpData = new byte[originalData.length + 2];//用于存加密后的数据 (多了两个字节的crc校验)
        encrpData[i++] = originalData[0]; //stx
        encrpData[i++] = (byte) (num + 0x32);//num1
        while (i < originalData.length) {
            encrpData[i] = (byte) (originalData[i] ^ num);
            i++;
        }
        // 对crc之前的数据做CRC校验填到最后两位
        byte[] temporaryByte = new byte[originalData.length];
        for (int j = 0; j < originalData.length; j++) {
            temporaryByte[j] = encrpData[j];
        }
        int crcInt = CRC16.calcCRC(temporaryByte);//CRC校验和
        encrpData[i++] = (byte) ((crcInt >> 8) & 0xff);
        encrpData[i++] = (byte) (crcInt & 0xff);
        return encrpData;
    }

    /**
     * Cmd：指令
     */
    public static class Cmd {
        public static final byte GET_KEY = (byte) 0X11;//获取通信KEY
        public static final byte OPEN_LOCK = (byte) 0x21;//开锁
        public static final byte CLOSE_LOCK = (byte) 0x22;//上锁
        public static final byte QUERY_LOCK_STATUS = (byte) 0x31;//查询锁状态
        public static final byte GET_UNUPLOAD_DATA = (byte) 0x51;//获取未上传的数据
        public static final byte CLEAR_UNUPLOAD_DATA = (byte) 0x52;//清除未上传的数据

        public static boolean verify(byte cmd) {
            if (cmd != GET_KEY && cmd != OPEN_LOCK && cmd != CLOSE_LOCK &&
                    cmd != QUERY_LOCK_STATUS && cmd != GET_UNUPLOAD_DATA && cmd != CLEAR_UNUPLOAD_DATA) {
                return false;
            }
            return true;
        }
    }

    /**
     * 生成指令
     */
    public static class Builder {
        // 1 byte
        private static final byte stx = (byte) 0xFE;
        // 1 byte
        protected byte num = 0;
        // 1 byte
        protected byte key = (byte) 0x00; //默认为0x00 客户端需要保存用于后续数据传递(断开重连过后需要重新获取)
        // 1 byte
        protected byte cmd;
        // 1 byte
        protected byte len;
        // n bytes
        protected Body body = null;

        public Builder() {
        }

        public Builder setKey(byte key) {
            this.key = key;
            return this;
        }

        public Builder setCmd(byte cmd) {
            this.cmd = cmd;
            return this;
        }

        public Builder setBody(Body body) {
            this.body = body;
            return this;
        }

        /**
         * 根据设置的字段值，生成一个条新指令。
         *
         * @return 生成的指令，或者返回null。
         */
        public Instruction createInstruction() {
            if (!Cmd.verify(this.cmd)) {
                return null;
            }
            Instruction instruction = new Instruction();
            instruction.stx = this.stx;
            generateRandomNum();//生成随机数
            instruction.num = this.num;
            instruction.key = this.key;
            instruction.cmd = this.cmd;
            calculateLength();
            instruction.len = this.len;
            instruction.body = this.body;
            return instruction;
        }

        /**
         * 生成随机数
         *
         * @return
         */
        private void generateRandomNum() {
            Random random = new Random();
            this.num = (byte) (random.nextInt());
        }

        private void calculateLength() {
            this.len = (byte) body.getLength();
        }
    }

}
