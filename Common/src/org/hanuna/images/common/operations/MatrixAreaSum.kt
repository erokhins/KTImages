package org.hanuna.images.common.operations

import org.hanuna.images.common.MatrixArea
import org.hanuna.images.common.MutableVector
import org.hanuna.images.common.MutableMatrix
import org.hanuna.images.common.Matrix
import org.hanuna.images.common.equalSize
import org.hanuna.images.common.isNotEmpty
import org.hanuna.images.common.forAll
import org.hanuna.images.common.ForAllStrategy
import org.hanuna.images.common.minus
import org.hanuna.images.common.row
import org.hanuna.images.common.col
import org.hanuna.images.common.Coordinates
import org.hanuna.images.common.plus
import org.hanuna.images.common.rangeTo

/**
 * Created by smevok on 7/30/14.
 */

public trait ImageMatrixAreaSumGetter<T> {
    public fun get(area: MatrixArea): T
}

public fun <T> Matrix<T>.createSumGetter(forPartialSums: MutableMatrix<T>, sumOperation: SumOperation<T>):
        ImageMatrixAreaSumGetter<T> {
    assert(this.equalSize(forPartialSums) && isNotEmpty(), "I'm angry!")
    calculatePartialSum(forPartialSums, sumOperation.plus)
    return Sum2Getter(forPartialSums, sumOperation)
}

private fun <T> Matrix<T>.calculatePartialSum(partialSums: MutableMatrix<T>, plus: T.(T) -> T) {
    var sumForRow = this[0,0]
    this.forAll(ForAllStrategy.LR_TB) {
        if (it.col == 0)
            sumForRow = this[it]
        else
            sumForRow = sumForRow + this[it]

        if (it.row == 0) {
            partialSums[it] = sumForRow
        } else {
            partialSums[it] = sumForRow + partialSums[it - 1.row]
        }
    }
}

private class Sum2Getter<T>(val partialSums: Matrix<T>, sumOperation: SumOperation<T>) : ImageMatrixAreaSumGetter<T> {
    private val plus = sumOperation.plus
    private val minus = sumOperation.minus
    private val times = sumOperation.nTimes

    private val cols = partialSums.cols
    private val rows = partialSums.rows

    private val correct2ImSumGetter: (col: Int, row: Int) -> T

    {
        val rowSym = Cols2SymSumGet(rows, cols, plus, minus) {(col, row) -> partialSums[row, col]}
        val colSym = Cols2SymSumGet(cols, 2*rows, plus, minus) {(col, row) -> rowSym[row, col]}
        correct2ImSumGetter = { (col, row) -> colSym[col, row]}
    }

    fun get(col: Int, row: Int): T {
        assert(col >= 0, row >= 0)
        val cols2 = 2 * cols
        val rows2 = 2 * rows
        val sum4 = correct2ImSumGetter(cols2 - 1, rows2 - 1)

        val kCol = col / cols2
        val rCol = col - kCol * cols2

        val kRow = row / rows2
        val rRow = row - kRow * rows2

        var sum = kCol * kRow * sum4

        sum += kCol * correct2ImSumGetter(cols2 - 1, rRow)
        sum += kRow * correct2ImSumGetter(rCol, rows2 - 1)

        sum += correct2ImSumGetter(rCol, rRow)
        return sum
    }

    override fun get(area: MatrixArea): T {
        val offset = offsetForImageIndex(area.cornerLT.col, 2 * cols).col +
                     offsetForImageIndex(area.cornerLT.row, 2 * rows).row

        val ca = area.cornerLT + offset..area.cornerRB + offset
        return get(ca.cornerRB.col, ca.cornerRB.row) + get(ca.cornerLT.col - 1, ca.cornerLT.row - 1) -
               get(ca.cornerLT.col - 1, ca.cornerRB.row) -  get(ca.cornerRB.col, ca.cornerLT.row - 1)
    }
}

private class Cols2SymSumGet<T>(val cols: Int, val rows: Int, val plus: T.(T) -> T, val minus: T.(T) -> T,
                                val getFun: (Int, Int) -> T) {
    fun get(col: Int, row: Int): T {
        assert(col >= 0 && row >= 0)
        assert(col < 2 * cols && row < rows)
        val allSum = getFun(cols - 1, row)

        if(col < cols)
            return getFun(col, row)

        if (col == 2 * cols - 1)
            return allSum + allSum

        assert(col in col..2*cols - 2)
        return allSum + allSum - getFun(2*cols - 2 - col, row)
    }
}