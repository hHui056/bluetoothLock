package com.hh.bluetoothlock.instruction;

import com.hh.bluetoothlock.instruction.body.Body;
import com.hh.bluetoothlock.instruction.body.GetKeyResBody;
import com.hh.bluetoothlock.instruction.exception.ParserException;

import java.util.Arrays;

/**
 * Create By hHui on 2018/11/13
 * <p>
 * 解析指令
 */

public class InstructionParser {

    private byte[] realContent; //解密过后的数据

    /**
     * 解析指令
     **/
    public Instruction parseInstruction(byte[] content) throws ParserException {
        if (content == null) throw new ParserException("content is null");
        if (!verifyCrc(content)) throw new ParserException("crc is wrong");
        decodeData(content);

        Instruction instruction = new Instruction();
        // verify stx
        if (realContent[0] != 0xFE) throw new ParserException("stx is wrong");

        instruction.num = realContent[1];
        instruction.key = realContent[2];
        //verify cmd
        if (!parseCmd(realContent[3], instruction)) throw new ParserException("cmd is wrong");
        //verify len
        instruction.len = realContent[4];

        //verify data
        byte data[] = Arrays.copyOfRange(realContent, 5, realContent.length - 2);
        if (!parseData(data, instruction)) throw new ParserException("data field is invalid");

        return instruction;
    }

    protected boolean parseCmd(byte cmd, Instruction instruction) {
        if (!Instruction.Cmd.verify(cmd)) {
            return false;
        }
        instruction.cmd = cmd;
        return true;
    }

    /**
     * CRC 校验
     *
     * @param content
     * @return
     */
    private boolean verifyCrc(byte[] content) {
        int crc = (content[content.length - 2] << 8) ^ content[content.length - 1];
        byte[] data = Arrays.copyOfRange(content, 0, content.length - 2);
        int calcCrc = CRC16.calcCRC(data);
        return crc == calcCrc;
    }


    /**
     * 解密数据  加密数据--->明文
     */
    private void decodeData(byte[] content) {
        int i = 0;
        byte[] data = new byte[content.length];
        byte num = (byte) (content[1] - 0x32);
        int len = content[4] ^ (content[1] - 0x32);
        data[i++] = (byte) 0xFE;
        data[i++] = (byte) (content[1] - 0x32);
        while (i < len + 4) {
            data[i++] = (byte) (content[i] ^ num);
            i++;
        }
        data[i++] = content[i++];
        data[i++] = content[i++];
        this.realContent = data;
    }

    protected boolean parseData(byte[] data, Instruction instruction) {
        Body body = null;
        switch (instruction.getCmd()) {
            case Instruction.Cmd.GET_KEY: //获取秘钥回复
                body = new GetKeyResBody();
                body.parseContent(data);
                body.setIsAvailable(true);
                break;
            case Instruction.Cmd.OPEN_LOCK: //开锁回复
                break;
            case Instruction.Cmd.CLOSE_LOCK://关锁回复
                break;
            case Instruction.Cmd.QUERY_LOCK_STATUS://查询锁状态回复
                break;
            case Instruction.Cmd.GET_UNUPLOAD_DATA://获取未上传的数据
                break;
            case Instruction.Cmd.CLEAR_UNUPLOAD_DATA://清除未上传的数据
                break;
        }
        return body != null && body.isAvailable();
    }
}
