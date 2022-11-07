package pini.mattia.coachtimer.data

import android.os.SystemClock
import pini.mattia.coachtimer.model.TimeRetriever
import javax.inject.Inject

class TimeRetrieverImpl @Inject constructor() : TimeRetriever {
    override fun getCurrentTimeMillis(): Long {
        return SystemClock.elapsedRealtime()
    }
}
