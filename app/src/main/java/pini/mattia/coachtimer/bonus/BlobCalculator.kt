package pini.mattia.coachtimer.bonus

import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.pow

/**
 * Bonus feature to show case binary operator and operations
 * Using experimental UByteArray because if using signed byte array decoding fails because byte is signed and or operator doesn't work
 */

@OptIn(ExperimentalUnsignedTypes::class)
class BlobCalculator {
    /**
     * Return a [UByteArray] with the values passed in input compressed in order
     */
    fun calculateBlob(speed: Int, variance: Int, laps: Int, distance: Int): UByteArray {
        // first we need the size of the byte array, to do so we calculate how many bits we need for each knowing the
        // max value
        val speedSize: Int = ceil(log(MAX_SPEED.toDouble(), 2.toDouble())).toInt()
        val varianceSize = ceil(log(MAX_VARIANCE.toDouble(), 2.toDouble())).toInt()
        val lapsSize = ceil(log(MAX_LAPS.toDouble(), 2.toDouble())).toInt()

        // distance can be variable because it is the last value
        val distanceSize = ceil(log(distance.toDouble(), 2.toDouble())).toInt()
        val arraySize = ceil((speedSize + varianceSize + lapsSize + distanceSize) / 8.0).toInt()

        val byteArray = UByteArray(arraySize)
        val allValues: Int = speed or variance.shl(speedSize) or laps.shl(speedSize + varianceSize) or distance.shl(speedSize + varianceSize + lapsSize)

        for (i in 0 until arraySize) {
            byteArray[i] = allValues.shr(8 * i).toUByte()
        }
        return byteArray
    }

    /**
     * Decode the [UByteArray] created with [calculateBlob]
     * The returned list has 4 elements:
     * [0] = speed
     * [1] = variance
     * [2] = laps
     * [3] = distance
     */
    fun decodeBlob(byteArray: UByteArray): List<Int> {
        val speedSize: Int = ceil(log(MAX_SPEED.toDouble(), 2.toDouble())).toInt()
        val varianceSize = ceil(log(MAX_VARIANCE.toDouble(), 2.toDouble())).toInt()
        val lapsSize = ceil(log(MAX_LAPS.toDouble(), 2.toDouble())).toInt()

        var allValues = 0
        for (i in byteArray.indices) {
            // that's why unsigned is used!! otherwise this doesn't work!
            allValues = (byteArray[i].toInt().shl(8 * i)) or allValues
        }
        // use mask to extract correct value given by the max value of the number of bits (size)
        val speed = allValues and (2.0.pow(speedSize.toDouble()).toInt() - 1)
        val variance = allValues.shr(speedSize) and (2.0.pow(varianceSize.toDouble()).toInt() - 1)
        val laps = allValues.shr(speedSize + varianceSize) and (2.0.pow(lapsSize.toDouble()).toInt() - 1)
        val distance = allValues.shr(speedSize + varianceSize + lapsSize)

        return listOf(speed, variance, laps, distance)
    }

    companion object {
        private const val MAX_SPEED = 45
        private const val MAX_VARIANCE = 15
        private const val MAX_LAPS = 100
    }
}
