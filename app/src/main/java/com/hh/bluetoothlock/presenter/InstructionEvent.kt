package com.hh.bluetoothlock.presenter

import com.hh.bluetoothlock.instruction.Instruction
import com.hh.bluetoothlock.util.RxBus

/**
 *Create By hHui on 2018/11/16
 */
class InstructionEvent(val instruction: Instruction) : RxBus.EventType()