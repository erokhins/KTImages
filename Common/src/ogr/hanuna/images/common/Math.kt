package org.hanuna.math

/**
 * Created by smevok on 5/11/14.
 */

public fun Int.myMod(module: Int): Int {
    val java_mod = this % module
    return if (java_mod >= 0)
        java_mod
    else
        java_mod + module
}

/**
 * Example:
 *      -1, 5 -> (mod2size = 9) ->  10 - 1 - 9 = 0
 *      5, 5 -> (mod2size = 5) -> 10 - 1 - 5 = 4
 */
public fun Int.toCorrectImageNumber(size: Int): Int {
    val mod2size = myMod(2 * size)
    return if (mod2size < size)
        mod2size
    else
        2 * size - 1 - mod2size
}

public fun Byte.to255Int(): Int = this.toInt() and 0xff

public fun IntRange.reverse(reverse: Boolean = true): Progression<Int> =
        if(reverse)
            IntProgression(end, start, -1)
        else
            this