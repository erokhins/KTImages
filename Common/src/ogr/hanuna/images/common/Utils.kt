package ogr.hanuna.images.common

/**
 * Created by smevok on 7/25/14.
 */

public fun IntRange.reverse(reverse: Boolean = true): Progression<Int> =
        if(reverse)
            IntProgression(end, start, -1)
        else
            this