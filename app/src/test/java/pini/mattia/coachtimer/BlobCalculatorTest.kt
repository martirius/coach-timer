package pini.mattia.coachtimer

import org.junit.Test
import pini.mattia.coachtimer.bonus.BlobCalculator

@OptIn(ExperimentalUnsignedTypes::class)
class BlobCalculatorTest {

    @Test
    fun blob_calculator_return_correct_blob() {
        val blobCalculator = BlobCalculator()
        val blob = blobCalculator.calculateBlob(14, 14, 14, 20)
        println(blob)

        val decodedBlob = blobCalculator.decodeBlob(blob)

        println(decodedBlob)

        assert(decodedBlob[0] == 14)
        assert(decodedBlob[1] == 14)
        assert(decodedBlob[2] == 14)
        assert(decodedBlob[3] == 20)
    }
}
