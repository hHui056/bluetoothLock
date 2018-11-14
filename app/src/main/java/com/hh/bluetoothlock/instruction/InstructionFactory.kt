package com.hh.bluetoothlock.instruction

import com.hh.bluetoothlock.instruction.body.request.GetKeyReqBody
import com.hh.bluetoothlock.instruction.body.request.OpenLockReqBody
import java.util.*

/**
 *Create By hHui on 2018/11/14
 *
 * 指令工厂 用于产生对应操作的指令
 */
object InstructionFactory {
    var communicationKey = 0x00 as Byte //通信秘钥  //TODO 获取到通信秘钥过后需要更新
    /** 获取通信秘钥指令**/
    val getKeyInstruction = Instruction.Builder().setBody(GetKeyReqBody()).setCmd(Instruction.Cmd.GET_KEY).createInstruction()
    /** 开锁指令 **/
    val openLockInstruction = Instruction.Builder().setCmd(Instruction.Cmd.OPEN_LOCK).setKey(communicationKey)
            .setBody(OpenLockReqBody(1, Date(), 0)).createInstruction()

    /** 开锁回复 **/
    val openLockReplyInstruction = Instruction.Builder().setCmd(Instruction.Cmd.OPEN_LOCK).setKey(communicationKey).createInstruction()
    /** 关锁回复 **/
    val closeLockReplyInstruction = Instruction.Builder().setCmd(Instruction.Cmd.CLOSE_LOCK).setKey(communicationKey).createInstruction()
    /** 查询锁状态 **/
    val queryLockInfoInstruction = Instruction.Builder().setCmd(Instruction.Cmd.QUERY_LOCK_STATUS).setKey(communicationKey).createInstruction()
    /** 获取未上传数据 **/
    val getUnUploadDataInstruction = Instruction.Builder().setCmd(Instruction.Cmd.GET_UNUPLOAD_DATA).setKey(communicationKey).createInstruction()
    /** 清除未上传数据 **/
    val clearUnUpLoadDataInstruction = Instruction.Builder().setCmd(Instruction.Cmd.CLEAR_UNUPLOAD_DATA).setKey(communicationKey).createInstruction()
}