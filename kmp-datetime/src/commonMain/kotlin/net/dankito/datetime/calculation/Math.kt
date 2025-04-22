package net.dankito.datetime.calculation

import kotlin.math.abs

/**
 * Contains methods copied from java.lang.Math required for datetime calculations
 */
internal object Math {

    // copied from java.lang.Math.multiplyExact()
    /**
     * Returns the product of the arguments,
     * throwing an exception if the result overflows a {@code long}.
     *
     * @param x the first value
     * @param y the second value
     * @return the result
     * @throws ArithmeticException if the result overflows a long
     */
    fun multiplyExact(x: Long, y: Long): Long {
        val r = x * y
        val ax = abs(x.toDouble()).toLong()
        val ay = abs(y.toDouble()).toLong()
        if (((ax or ay) ushr 31 != 0L)) {
            // Some bits greater than 2^31 that might cause overflow
            // Check the result using the divide operator
            // and check for the special case of Long.MIN_VALUE * -1
            if (((y != 0L) && (r / y != x)) ||
                (x == Long.MIN_VALUE && y == -1L)
            ) {
                throw ArithmeticException("long overflow")
            }
        }
        return r
    }

    // copied from java.lang.Math.addExact()
    /**
     * Returns the sum of its arguments,
     * throwing an exception if the result overflows a `long`.
     *
     * @param x the first value
     * @param y the second value
     * @return the result
     * @throws ArithmeticException if the result overflows a long
     */
    fun addExact(x: Long, y: Long): Long {
        val r = x + y
        // HD 2-12 Overflow iff both arguments have the opposite sign of the result
        if (((x xor r) and (y xor r)) < 0) {
            throw ArithmeticException("long overflow")
        }
        return r
    }

    // copied from java.lang.Math.floorDiv()
    /**
     * Returns the largest (closest to positive infinity)
     * `long` value that is less than or equal to the algebraic quotient.
     * There is one special case, if the dividend is the
     * [Long.MIN_VALUE] and the divisor is `-1`,
     * then integer overflow occurs and
     * the result is equal to `Long.MIN_VALUE`.
     *
     *
     * Normal integer division operates under the round to zero rounding mode
     * (truncation).  This operation instead acts under the round toward
     * negative infinity (floor) rounding mode.
     * The floor rounding mode gives different results from truncation
     * when the exact result is negative.
     *
     *
     * For examples, see [.floorDiv].
     *
     * @param x the dividend
     * @param y the divisor
     * @return the largest (closest to positive infinity)
     * `long` value that is less than or equal to the algebraic quotient.
     * @throws ArithmeticException if the divisor `y` is zero
     * @see .floorMod
     * @see .floor
     */
    fun floorDiv(x: Long, y: Long): Long {
        var r = x / y
        // if the signs are different and modulo not zero, round down
        if ((x xor y) < 0 && (r * y != x)) {
            r--
        }
        return r
    }

    // copied from java.lang.Math.floorMod()
    /**
     * Returns the floor modulus of the `long` arguments.
     *
     *
     * The floor modulus is `x - (floorDiv(x, y) * y)`,
     * has the same sign as the divisor `y`, and
     * is in the range of `-abs(y) < r < +abs(y)`.
     *
     *
     *
     * The relationship between `floorDiv` and `floorMod` is such that:
     *
     *  * `floorDiv(x, y) * y + floorMod(x, y) == x`
     *
     *
     *
     * For examples, see [.floorMod].
     *
     * @param x the dividend
     * @param y the divisor
     * @return the floor modulus `x - (floorDiv(x, y) * y)`
     * @throws ArithmeticException if the divisor `y` is zero
     * @see .floorDiv
     */
    fun floorMod(x: Long, y: Long): Long {
        return x - floorDiv(x, y) * y
    }

}