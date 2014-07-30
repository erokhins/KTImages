package org.hanuna.images.common

import org.hanuna.images.common.reverse

/**
 * Created by smevok on 7/25/14.
 */

public fun OneDimension.forAll(reverse : Boolean = false, f: (Int) -> Unit) {
    for (i in (0..size - 1).reverse(reverse))
        f(i)
}

public enum class ForAllStrategy(val CRR: Boolean, val LRR: Boolean, val TBR: Boolean) {
    LR_TB : ForAllStrategy(false, false, false) // left -> right, top-> bottom (0,0) (1,0) .. (10,0) (0,1) (1,1) ..
    LR_BT : ForAllStrategy(false, false, true)
    RL_TB : ForAllStrategy(false, true, false)
    RL_BT : ForAllStrategy(false, true, true)

    TB_LR : ForAllStrategy(true, false, false) // (0,0) (0,1) .. (0,10) (1,0) (1,1) ..
    TB_RL : ForAllStrategy(true, true, false)
    BT_LR : ForAllStrategy(true, false, true)
    BT_RL : ForAllStrategy(true, true, true)
}

public fun TwoDimension.forAll2(strategy: ForAllStrategy = ForAllStrategy.LR_TB, f: (Int, Int) -> Unit) {
    if (strategy.CRR) {
        for (col in (0..cols - 1).reverse(strategy.LRR))
            for (row in (0..rows - 1).reverse(strategy.TBR))
                f(col, row)
    } else {
        for (row in (0..rows - 1).reverse(strategy.TBR))
            for (col in (0..cols - 1).reverse(strategy.LRR))
                f(col, row)
    }
}

public fun TwoDimension.forAll(strategy: ForAllStrategy = ForAllStrategy.LR_TB, f: (Coordinates) -> Unit) {
    forAll2(strategy) {
        (col, row) ->
        f(SimpleCoordinates(col, row))
    }
}