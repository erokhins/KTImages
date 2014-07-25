package ogr.hanuna.images.common

/**
 * Created by smevok on 7/25/14.
 */

public fun OneDimension.forAll(reverse : Boolean = false, f: (Int) -> Unit) {
    for (i in (0..size - 1).reverse(reverse))
        f(i)
}

public enum class ForAllStrategy(val CRR: Boolean, val LRR: Boolean, val TDR: Boolean) {
    LR_TD : ForAllStrategy(false, false, false) // left -> right, top-> down (0,0) (1,0) .. (10,0) (0,1) (1,1) ..
    LR_DT : ForAllStrategy(false, false, true)
    RL_TD : ForAllStrategy(false, true, false)
    RL_DT : ForAllStrategy(false, true, true)

    TD_LR : ForAllStrategy(true, false, false) // (0,0) (0,1) .. (0,10) (1,0) (1,1) ..
    TD_RL : ForAllStrategy(true, true, false)
    DT_LR : ForAllStrategy(true, false, true)
    DT_RL : ForAllStrategy(true, true, true)
}

public fun TwoDimension.forAll(strategy: ForAllStrategy = ForAllStrategy.LR_TD, f: (Int, Int) -> Unit) {
    if (!strategy.CRR) {
        for (col in (0..cols - 1).reverse(strategy.LRR))
            for (row in (0..rows - 1).reverse(strategy.TDR))
                f(col, row)
    } else {
        for (row in (0..rows - 1).reverse(strategy.TDR))
            for (col in (0..cols - 1).reverse(strategy.LRR))
                f(col, row)
    }
}

public fun TwoDimension.forAll(strategy: ForAllStrategy = ForAllStrategy.LR_TD, f: (Coordinates) -> Unit) {
    forAll(strategy) {
        (col, row) ->
        f(SimpleCoordinates(col, row))
    }
}