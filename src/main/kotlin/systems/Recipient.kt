package systems

import Constants
import kotlinx.coroutines.*

class Recipient(private val communicationSystem: CommunicationSystem) {

    private val coroutineScope = CoroutineScope(Dispatchers.Default + CoroutineName("Recipient"))

    private var lineCursor = 0

    private val nextLineReadingDelay: Long
        get() = 1 * Constants.timeSpeed.toLong()

    private val nextMessageReadingDelay: Long
        get() = (1..7).random().toLong() * Constants.timeSpeed

    fun start() {
        coroutineScope.launch {
            while (isActive) {
                print("reading line #$lineCursor: ")
                delay(nextLineReadingDelay)
                val message = communicationSystem.getMessage(lineCursor)

                message?.let {
                    println(it)
                    delay(nextMessageReadingDelay)
                } ?: println("this line is empty")

                updateLineCursor()
            }
        }
    }

    private fun updateLineCursor() {
        lineCursor = if (lineCursor == communicationSystem.numberOfLines - 1) 0 else lineCursor + 1
    }

    fun shutdown() {
        coroutineScope.cancel()
    }
}