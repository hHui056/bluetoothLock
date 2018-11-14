package com.hh.bluetoothlock;

import com.hh.bluetoothlock.instruction.Instruction;
import com.hh.bluetoothlock.instruction.InstructionParser;
import com.hh.bluetoothlock.instruction.body.Body;
import com.hh.bluetoothlock.instruction.body.request.GetKeyReqBody;
import com.hh.bluetoothlock.instruction.body.response.GetKeyResBody;
import com.hh.bluetoothlock.instruction.exception.ParserException;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // - 测试构建指令
    @Test
    public void test_buildInstruction() {
        Instruction instruction = new Instruction.Builder().setCmd(Instruction.Cmd.GET_KEY).setKey((byte) 0x00).
                setBody(new GetKeyReqBody("yOTmK50z")).createInstruction();

        byte[] data = instruction.toEnciphermentByteArray();

    }

    @Test
    public void test_parseInstruction() {
        byte[] testData = {(byte) 0xFE, (byte) 0x3C, (byte) 0xF5, (byte) 0x1B,
                (byte) 0x0B, (byte) 0xF5, (byte) 0x7C, (byte) 0xC1};
        try {
            Instruction instruction = new InstructionParser().parseInstruction(testData);
            Body body = instruction.getBody();
            if (body instanceof GetKeyResBody) {
                byte a = ((GetKeyResBody) body).getKey();
                assertEquals(a, (byte) 0xFF);
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }
}