package org.hanuna.images.common.test

import org.hanuna.images.common.Matrix
import java.util.ArrayList
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.common.forAll
import org.hanuna.images.common.ImageVector
import org.hanuna.images.common.Vector
import org.junit.Test
import kotlin.test.assertEquals
import org.hanuna.images.common.operations.RingOperation

/**
 * Created by smevok on 7/27/14.
 */

public fun withStringBuilder(f: StringBuilder.() -> Unit): String {
    val s = StringBuilder()
    s.f()
    return s.toString()
}

public fun <T> vector(vararg t: T): ImageVector<T> {
    assert(t.isNotEmpty(), "You monster! Empty vector.")
    return object : ImageVector<T> {
        override val size: Int = t.size
        override fun get_correct(index: Int): T = t[index]
    }
}

class MatrixBuilder<T> {
    val rows = ArrayList<Array<T>>()

    fun isEmpty(): Boolean = rows.isNotEmpty()

    public fun row(vararg t: T) {
        assert(t.size != 0, "Ai-ai-ai! Empty row!")
        if (rows.isNotEmpty()) {
            assert(rows.first!!.size == t.size, ":'(... Row lengths should be uniform")
        }
        rows.add(t)
    }
}

public fun <T> matrix(f: MatrixBuilder<T>.() -> Unit): ImageMatrix<T> {
    val builder = MatrixBuilder<T>()
    builder.f()
    assert(builder.rows.isNotEmpty(), "Please, give me row!")

    return object : ImageMatrix<T> {
        override val cols: Int = builder.rows.first!!.size
        override val rows: Int = builder.rows.size
        override fun get_correct(col: Int, row: Int): T = builder.rows[row].get(col)
    }
}

public fun <T> Vector<T>.toStr(toStr: (T) -> String = {it.toString()}): String { // 0:1:2
    return withStringBuilder {
        forAll {
            if (it != 0) append(':')
            append(toStr(this@toStr[it]))
        }
    }
}

public fun <T> Matrix<T>.toStr(toStr: (T) -> String = {it.toString()}): String { // 0:1:2 3:4:5
    return withStringBuilder {
        forAll {
            if (it.col != 0) append(':')
            if (it.row != 0 && it.col == 0) append(' ')
            append(toStr(this@toStr[it]))
        }
    }
}

object IntRingOperation : RingOperation<Int> {
    override val minus: Int.(Int) -> Int = { this - it }
    override val plus: Int.(Int) -> Int = { this + it }
    override val nTimes: Int.(Int) -> Int = { this * it }
    override val times: Int.(Int) -> Int = { this * it }
}

class TestStr {
    Test fun vectorTest() {
        val vector = vector(1, 2, 3)
        assertEquals("1:2:3", vector.toStr())
    }

    Test fun matrixTest() {
        val matrix = matrix<Int> {
            row(1, 2, 3)
            row(4, 5, 6)
        }
        assertEquals("1:2:3 4:5:6", matrix.toStr())
    }
}