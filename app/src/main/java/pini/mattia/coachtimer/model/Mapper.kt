package pini.mattia.coachtimer.model

interface Mapper<out T, R> {
    fun mapTo(objectToMap: R): T
}
