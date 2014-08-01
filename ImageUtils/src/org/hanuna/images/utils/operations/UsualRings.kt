package org.hanuna.images.utils.operations

import org.hanuna.images.common.operations.RingOperation

/**
 * Created by smevok on 8/2/14.
 */

public object IntRingOperation : RingOperation<Int> {
    override val nTimes: Int.(Int) -> Int = { this * it }
    override val times: Int.(Int) -> Int = { this * it }
    override val plus: Int.(Int) -> Int = { this + it }
    override val minus: Int.(Int) -> Int = { this - it }
}

public object FloatRingOperation : RingOperation<Float> {
    override val nTimes: Int.(Float) -> Float = { this * it }
    override val times: Float.(Float) -> Float = { this * it }
    override val plus: Float.(Float) -> Float = { this + it }
    override val minus: Float.(Float) -> Float = { this - it }
}

public object DoubleRingOperation : RingOperation<Double> {
    override val nTimes: Int.(Double) -> Double = { this * it }
    override val times: Double.(Double) -> Double = { this * it }
    override val plus: Double.(Double) -> Double = { this + it }
    override val minus: Double.(Double) -> Double = { this - it }
}

public object LongRingOperation : RingOperation<Long> {
    override val nTimes: Int.(Long) -> Long = { this * it }
    override val times: Long.(Long) -> Long = { this * it }
    override val plus: Long.(Long) -> Long = { this + it }
    override val minus: Long.(Long) -> Long = { this - it }
}

public object ShortRingOperation : RingOperation<Short> {
    override val nTimes: Int.(Short) -> Short = { (this * it).toShort() }
    override val times: Short.(Short) -> Short = { (this * it).toShort() }
    override val plus: Short.(Short) -> Short = { (this + it).toShort() }
    override val minus: Short.(Short) -> Short = { (this - it).toShort() }
}

public object ByteRingOperation : RingOperation<Byte> {
    override val nTimes: Int.(Byte) -> Byte = { (this * it).toByte() }
    override val times: Byte.(Byte) -> Byte = { (this * it).toByte() }
    override val plus: Byte.(Byte) -> Byte = { (this + it).toByte() }
    override val minus: Byte.(Byte) -> Byte = { (this - it).toByte() }
}