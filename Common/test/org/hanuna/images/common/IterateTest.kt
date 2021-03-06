package org.hanuna.images.common

import org.junit.Test
import org.hanuna.images.common.reverse
import kotlin.test.assertEquals
import org.hanuna.images.common.OneDimension
import org.hanuna.images.common.TwoDimension
import org.hanuna.images.common.forAll
import org.hanuna.images.common.ForAllStrategy
import org.hanuna.images.common.forAll2

/**
 * Created by smevok on 7/25/14.
 */
class IterateTest {
    Test fun reverseFun() {
        var s = ""
        for (i in (1..3).reverse())
            s += i
        assertEquals("321", s)

        for (i in (1..3).reverse(false))
            s += i
        assertEquals("321123", s)
    }

    val oneDim = object : OneDimension {
        override val size: Int = 3
    }

    Test fun forAllOneDim() {
        var s = ""
        oneDim.forAll {
            s += it
        }
        assertEquals("012", s)
        oneDim.forAll(true) {
            s += it
        }
        assertEquals("012210", s)
    }

    val twoDim = object : TwoDimension {
        override val cols: Int = 2
        override val rows: Int = 3
    }

    Test fun forAllTwoDim() {
        fun run(strategy: ForAllStrategy): String {
            var s = ""
            twoDim.forAll(strategy) {
                if (!s.isEmpty()) s += ":"
                s += "${it.col}${it.row}"
            }
            return s
        }
        fun run2(strategy: ForAllStrategy): String {
            var s = ""
            twoDim.forAll2(strategy) { (col, row) ->
                if (!s.isEmpty()) s += ":"
                s += "${col}${row}"
            }
            return s
        }

        fun runTest(strategy: ForAllStrategy, result: String) {
            assertEquals(result, run(strategy), "forAll, strategy: $strategy")
            assertEquals(result, run2(strategy), "forAll2, strategy: $strategy")
        }

        runTest(ForAllStrategy.LR_TB, "00:10:01:11:02:12")
        runTest(ForAllStrategy.LR_BT, "02:12:01:11:00:10")
        runTest(ForAllStrategy.RL_TB, "10:00:11:01:12:02")
        runTest(ForAllStrategy.RL_BT, "12:02:11:01:10:00")

        runTest(ForAllStrategy.TB_LR, "00:01:02:10:11:12")
        runTest(ForAllStrategy.TB_RL, "10:11:12:00:01:02")
        runTest(ForAllStrategy.BT_LR, "02:01:00:12:11:10")
        runTest(ForAllStrategy.BT_RL, "12:11:10:02:01:00")
    }
}