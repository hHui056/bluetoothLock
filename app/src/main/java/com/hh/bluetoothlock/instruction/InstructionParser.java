package com.hh.bluetoothlock.instruction;

import com.hh.bluetoothlock.instruction.body.Body;
import com.hh.bluetoothlock.instruction.body.response.ClearUnUpLoadDataResBody;
import com.hh.bluetoothlock.instruction.body.response.CloseLockResBody;
import com.hh.bluetoothlock.instruction.body.response.GetKeyResBody;
import com.hh.bluetoothlock.instruction.body.response.GetUnUpLoadDataResBody;
import com.hh.bluetoothlock.instruction.body.response.OpenLockResBody;
import com.hh.bluetoothlock.instruction.body.response.QueryLockInfoResBody;
import com.hh.bluetoothlock.instruction.exception.ParserException;

import java.util.Arrays;

/**
 * Create By hHui on 2018/11/13
 * <p>
 * 解析指令
 */

public class InstructionParser {

    private byte[] realContent; //解密过后的数据

    public InstructionParser() {
    }

    /**
     * 解析指令
     **/
    public Instruction parseInstruction(byte[] content) throws ParserException {
        if (content == null) throw new ParserException("content is null");
        if (!verifyCrc(content)) throw new ParserException("crc is wrong");
        decodeData(content);

        Instruction instruction = new Instruction();
        // verify stx
        if (realContent[0] != (byte) 0xFE) throw new ParserException("stx is wrong");

        instruction.num = realContent[1];
        instruction.key = realContent[2];
        //verify cmd
        if (!parseCmd(realContent[3], instruction)) throw new ParserException("cmd is wrong");
        //verify len
        instruction.len = realContent[4];

        //verify data 解析data时将整段指令放入（包括stx、cmd等信息），方便对位取数据
        //   byte data[] = Arrays.copyOfRange(realContent, 5, realContent.length - 2);
        if (!parseData(realContent, instruction))
            throw new ParserException("data field is invalid");
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
        byte[] crcBytes = Arrays.copyOfRange(content, content.length - 2, content.length);
        byte[] data = Arrays.copyOfRange(content, 0, content.length - 2);
        int calcCrc = CRC16.calcCRC(data);
        byte a = (byte) ((calcCrc >> 8) & 0xff);
        byte b = (byte) (calcCrc & 0xff);
        if (crcBytes[0] == a && crcBytes[1] == b) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 解密数据  加密数据--->明文
     */
    private void decodeData(byte[] content) {
        byte[] data = new byte[content.length];
        byte num = (byte) (content[1] - 0x32);
        int len = content[4] ^ (content[1] - 0x32);
        data[0] = (byte) 0xFE;
        data[1] = (byte) (content[1] - 0x32);
        for (int i = 2; i <= len + 4; i++) {
            data[i] = (byte) (content[i] ^ num);
        }
        data[content.length - 2] = content[content.length - 2];
        data[content.length - 1] = content[content.length - 1];
        this.realContent = data;
    }

    protected boolean parseData(byte[] data, Instruction instruction) {
        Body body = null;
        switch (instruction.getCmd()) {
            case Instruction.Cmd.GET_KEY: //获取秘钥回复
                body = new GetKeyResBody();
                body.parseContent(data);
                instruction.body = body;
                body.setIsAvailable(true);
                break;
            case Instruction.Cmd.OPEN_LOCK: //开锁回复
                body = new OpenLockResBody();
                body.parseContent(data);
                instruction.body = body;
                body.setIsAvailable(true);
                break;
            case Instruction.Cmd.CLOSE_LOCK://关锁回复
                body = new CloseLockResBody();
                body.parseContent(data);
                instruction.body = body;
                body.setIsAvailable(true);
                break;
            case Instruction.Cmd.QUERY_LOCK_STATUS://查询锁状态回复
                body = new QueryLockInfoResBody();
                body.parseContent(data);
                instruction.body = body;
                body.setIsAvailable(true);
                break;
            case Instruction.Cmd.GET_UNUPLOAD_DATA://获取未上传的数据
                body = new GetUnUpLoadDataResBody();
                body.parseContent(data);
                instruction.body = body;
                body.setIsAvailable(true);
                break;
            case Instruction.Cmd.CLEAR_UNUPLOAD_DATA://清除未上传的数据
                body = new ClearUnUpLoadDataResBody();
                body.parseContent(data);
                instruction.body = body;
                body.setIsAvailable(true);
                break;
        }
        return body != null && body.isAvailable();
    }
}
