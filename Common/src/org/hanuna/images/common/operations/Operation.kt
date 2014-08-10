package org.hanuna.images.common.operations

import org.hanuna.images.common.Matrix
import org.hanuna.images.common.ImageVector
import org.hanuna.images.common.ImageMatrix
import org.hanuna.images.common.Vector
import org.hanuna.images.common.FIRST
import org.hanuna.images.common.equalSize
import org.hanuna.images.common.forAll
import org.hanuna.images.common.getCol
import org.hanuna.images.common.getRow
import org.hanuna.images.common.isNotEmpty
import org.hanuna.images.common.coord
import org.hanuna.images.common.toI
import org.hanuna.images.common.getArea
import org.hanuna.images.common.rangeTo

/**
 * Created by smevok on 7/26/14.
 */

public fun <A, B, C> matrixElementOperation(m1: Matrix<A>, m2: Matrix<B>, operation: A.(B) -> C): ImageMatrix<C> {
    assert(m1.equalSize(m2), "Size of matrix non equal: (${m1.cols}, ${m1.rows}) <> (${m2.cols}, ${m2.rows})")

    return object : ImageMatrix<C> {
        override val cols: Int = m1.cols
        override val rows: Int = m1.rows
        override fun get_correct(col: Int, row: Int): C = m1[col, row].operation(m2[col, row])
    }
}

public fun <A, B, C> matrixElementOperation(b: B, m: Matrix<A>, operation: B.(A) -> C): ImageMatrix<C> {
    return object : ImageMatrix<C> {
        override val cols: Int = m.cols
        override val rows: Int = m.rows
        override fun get_correct(col: Int, row: Int): C = b.operation(m[col, row])
    }
}

public fun <A, B, C> vectorElementOperation(v1: Vector<A>, v2: Vector<B>, operation: (A, B) -> C): ImageVector<C> {
    assert(v1.equalSize(v2), "Size of vectors non equal: ${v1.size} <> ${v2.size}")

    return object : ImageVector<C> {
        override val size: Int = v1.size

        override fun get_correct(index: Int): C = operation(v1[index], v2[index])
    }
}

public fun <A, B, C> vectorElementOperation(v: Vector<A>, b: B, operation: (A, B) -> C): ImageVector<C> {
    return object : ImageVector<C> {
        override val size: Int = v.size

        override fun get_correct(index: Int): C = operation(v[index], b)
    }
}

public fun <A, B, C> vectorMultiplyOperation(v1: Vector<A>, v2: Vector<B>, times: A.(B) -> C, plus: C.(C) -> C): C {
    assert(v1.equalSize(v2), "For this operation size of vectors(v1.szie: ${v1.size}; v2.size: ${v2.size})  must be equal.")
    assert(v1.size != 0, "Empty vectors not supported")
    var c: C = v1[0] * v2[0]
    v1.forAll {
        if (it != 0) {
            c = c + v1[it] * v2[it]
        }
    }
    return c
}

public fun <A, B, C> matrixMultiplyOperation(m1: Matrix<A>, m2: Matrix<B>, times: A.(B) -> C, plus: C.(C) -> C):
        ImageMatrix<C> {
    assert(m1.cols == m2.rows, "For this operation m1.cols(${m1.cols}) must be equal m2.rows(${m2.rows}).")
    assert(m1.cols != 0, "Are you kidding? It is matrix 0xN!")

    return object : ImageMatrix<C> {
        override val cols: Int = m2.cols
        override val rows: Int = m1.rows
        override fun get_correct(col: Int, row: Int): C {
            val colVector = m2.getCol(col)
            val rowVector = m1.getRow(row)
            return vectorMultiplyOperation(rowVector, colVector, times, plus)
        }
    }
}

public fun <T> matrixSumOperation(m: Matrix<T>, plus: T.(T) -> T): T {
    assert(m.isNotEmpty())
    var t = m[FIRST]
    m.forAll {
        if (it != FIRST)
            t += m[it]
    }
    return t
}

public fun <A, B, C> matrixResultant(m1: ImageMatrix<A>, m2: Matrix<B>, times: A.(B) -> C, plus: C.(C) -> C):
        ImageMatrix<C> {
    assert(m1.isNotEmpty() && m2.isNotEmpty())
    return object : ImageMatrix<C> {
        override val cols: Int = m1.cols
        override val rows: Int = m1.rows
        override fun get_correct(col: Int, row: Int): C {
            val partM1 = m1.getArea(coord(col, row)..coord(col + m2.cols - 1, row + m2.rows - 1))
            val mulPart = matrixElementOperation(partM1, m2, times)
            return matrixSumOperation(mulPart, plus)
        }

    }
}