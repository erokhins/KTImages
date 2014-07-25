package org.hanuna.images.common

import org.junit.Test
import ogr.hanuna.images.common.reverse
import kotlin.test.assertEquals

/**
 * Created by smevok on 7/25/14.
 */
public class SizesTest {
    Test fun testReverseFun() {
        var s = ""
        for (i in (1..3).reverse())
            s += i
        assertEquals("321", s)

        for (i in (1..3).reverse(false))
            s += i
        assertEquals("321123", s)
    }
}